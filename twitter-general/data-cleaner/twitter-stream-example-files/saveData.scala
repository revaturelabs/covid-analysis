import java.util.TimerTask
import java.util.Timer
import java.io.{File,FileInputStream,FileOutputStream}
import scala.concurrent.duration._
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext
import java.util.Calendar

/**
  * Copies the data file on in RawResults every hour saves it based on the current time
  * This way, as long as the stream runs alongside this program, 1 hour segments of our 
  * streaming data will be automatically generated for us
  */
object saveData extends TimerTask with App {
    override def run() {
        val now = Calendar.getInstance.getTime().toString().replace(" ", "-").replace(":", ".")
        new File("RawResults/part-00000").renameTo(new File(s"HourlyResults/$now"))
        println(s"file renamed at: $now")
    }

    val timer = new Timer
    timer.schedule(saveData, 0, Duration(1, HOURS).toMillis)
}