package utilites

import java.io.{BufferedReader, File, FileOutputStream, InputStreamReader}

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.{AmazonClientException, AmazonServiceException}
import org.apache.commons.io.IOUtils
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.collection.mutable.ArrayBuffer

case class s3DAO (
   amazonS3Client: AmazonS3Client,
   BUCKET_NAME: String = "covid-analysis-p3",
   DATA_LAKE: String = "datalake/infection-gdp/",
   DATA_WAREHOUSE: String = "datawarehouse/infection-gdp/",
   DOWNLOAD_PATH: String = "CovidResponse/src/main/resources/"
   ) {

  def uploadFile(file: File, fileName: String): Unit = {
    try {
      amazonS3Client.putObject(BUCKET_NAME, fileName, file)
    } catch {
      case e: AmazonClientException => System.err.println("Exception: " + e.toString)
    }
  }

  def getTsvRdd(fileName: String, hasHeader: Boolean = true): List[List[String]] = {
    var list = ArrayBuffer[List[String]]()

    val s3Object = amazonS3Client.getObject(BUCKET_NAME, fileName)
    val reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent))
    var line = reader.readLine
    while (reader.readLine != null) {
      list += line.split("\t").map(_.trim).toList
      line = reader.readLine
    }
    println(list.toString())
    if (hasHeader) list.toList.tail else list.toList
  }

  // download file and console out each line
  def downloadFile(fileName: String): Unit = {
    try {
      val obj = amazonS3Client.getObject(BUCKET_NAME, fileName)
      val reader = new BufferedReader(new InputStreamReader(obj.getObjectContent))
      var line = reader.readLine
      while (reader.readLine != null) {
        println(line)
        line = reader.readLine
      }
    } catch {
      case e: AmazonClientException => System.err.println("Exception: " + e.toString)
    }
  }

  // Downloads file from s3 and writes to local fs.  Uses callback to create and return a spark dataframe.
  def loadDFFromBucket(filesName: String, cb: String => DataFrame): DataFrame = {
      val s3Object = amazonS3Client.getObject(BUCKET_NAME, DATA_LAKE + filesName)
      val bytes = IOUtils.toByteArray(s3Object.getObjectContent)
      val file = new FileOutputStream(DOWNLOAD_PATH + filesName)
      file.write(bytes)

    cb(DOWNLOAD_PATH + filesName)
  }

//  def setDirectoryPath(key: String, value: String): Unit = this.dirPaths += (key -> value)
//
//  def setDirectoryPath(sources: Map[String, String]): Unit = this.dirPaths ++= dirPaths
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
        System.err.println("Connecting to AWS s3 failed. Confirm your credentials.")
        None
    }
  }
}