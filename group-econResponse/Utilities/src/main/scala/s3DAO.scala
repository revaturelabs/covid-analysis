package utilites

import java.io.{BufferedReader, File, FileOutputStream, InputStreamReader}

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.{AmazonClientException, AmazonServiceException}
import org.apache.commons.io.IOUtils
import org.apache.spark.sql.DataFrame
import java.util.Calendar

import com.amazonaws.services.s3.transfer.TransferManager

case class s3DAO(
    amazonS3Client: AmazonS3Client,
    BUCKET_NAME: String = "covid-analysis-p3",
    DATA_LAKE: String = "datalake/infection-gdp/",
    DATA_WAREHOUSE: String = "datawarehouse/infection-gdp/",
    var localWarehouse: String = "",
    var localLakePath: String = ""
) {

  // Downloads file from s3 and writes to local fs.  Uses callback to create and return a spark dataframe.
  def loadDFFromBucket(filesName: String,
                       cb: String => DataFrame): DataFrame = {
    val s3Object = amazonS3Client.getObject(BUCKET_NAME, DATA_LAKE + filesName)
    val bytes = IOUtils.toByteArray(s3Object.getObjectContent)
    val file = new FileOutputStream(s"$localLakePath/$filesName")
    file.write(bytes)

    cb(s"$localLakePath/$filesName")
  }

  //copy directory content to s3.
  def uploadDirTos3(file: File, prefix: String): Unit = {
    val tm: TransferManager = new TransferManager(amazonS3Client)
    tm.uploadDirectory(BUCKET_NAME, s"$DATA_WAREHOUSE/$prefix", file, true)
  }

  //local save and upload to s3.
  def localSaveAndUploadTos3(df: DataFrame, dirPath: String): Unit = {
    val csv = this.saveToLocalDir(df, dirPath)
    val file = new File(csv)
    this.uploadDirTos3(file, dirPath)
  }

  //write csv file to local directory.
  def saveToLocalDir(df: DataFrame, directory: String): String = {
    val path =
      s"$localWarehouse/$directory-${Calendar.getInstance().getTimeInMillis}"
    df.coalesce(1)
      .write
      .format("csv")
      .option("header", "true")
      .save(path)
    path
  }

  //copy files to s3 (not in use).
  def uploadFileTos3(file: File, prefix: String): Unit = {
    try {
      amazonS3Client.putObject(BUCKET_NAME, DATA_WAREHOUSE + prefix, file)
    } catch {
      case e: AmazonClientException =>
        System.err.println("Exception: " + e.toString)
    }
  }

  // download file and console out each line (not in use).
  def downloadFile(fileName: String): Unit = {
    try {
      val obj = amazonS3Client.getObject(BUCKET_NAME, fileName)
      val reader = new BufferedReader(
        new InputStreamReader(obj.getObjectContent))
      var line = reader.readLine
      while (reader.readLine != null) {
        println(line)
        line = reader.readLine
      }
    } catch {
      case e: AmazonClientException =>
        System.err.println("Exception: " + e.toString)
    }
  }

  def getLocalLakePath: String = localLakePath

  def setLocalLakePath(path: String): Unit = this.localLakePath = path

  def setLocalWarehousePath(path: String): Unit = this.localWarehouse = path
}

object s3DAO {
  def apply(): s3DAO = {
    val AWS_ACCESS_KEY = System.getenv("AWS_ACCESS_KEY_ID")
    val AWS_SECRET_KEY = System.getenv("AWS_SECRET_ACCESS_KEY")

    new s3DAO(getS3Connection(AWS_ACCESS_KEY, AWS_SECRET_KEY).get)
  }

  def getS3Connection(aKey: String, sKey: String): Option[AmazonS3Client] = {
    try {
      val awsCredentials = new BasicAWSCredentials(aKey, sKey)
      val amazonS3Client = new AmazonS3Client(awsCredentials)

      Some(amazonS3Client)
    } catch {
      case _: AmazonServiceException | _: AmazonClientException =>
        System.err.println(
          "Connecting to AWS s3 failed. Confirm your credentials.")
        None
    }
  }
}