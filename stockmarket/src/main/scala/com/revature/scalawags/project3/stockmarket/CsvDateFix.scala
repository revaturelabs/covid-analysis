package com.revature.scalawags.project3.stockmarket

import java.io.File

import scala.util.{Try, Success, Failure}

import com.github.nscala_time.time.Imports._
import com.github.tototoshi.csv._

/** Used to bring the formatting of the African Composite Index CSVs in line with
  * the other indices' data.
  * 
  * In your runner method, call `CsvDateFix.formatCsv` for each file that needs
  * formatting, passing in the path to that file as a string.
  *
  * Example:
  *
  * ```
  * CsvDateFix.formatCsv("data/datalake/Africa_Kenya_CompositeIndex.csv")
  * CsvDateFix.formatCsv("data/datalake/Africa_Nigeria_CompositeIndex.csv")
  * ```
  *
  * Each file will be overwritten in place with properly formatted data.
  */
object CsvDateFix {
  /** Reads from a csv with improper date formatting and overwrites it with
    * proper formatting. 
    */
  def formatCsv(path: String): Boolean = {
    val file = new File(path)
    val reader = CSVReader
      .open(file)
      .all()

    // Make sure we're actually being given a proper file path with formatting that
    // these methods can correct.
    if (reader.length < 2 || reader(1).length != 7) {
      println(s"File path specified doesn't have appropriate data to convert. " +
        s"Aborting formatting of $path.")
      return false
    }
    stringToDate(reader(1)(0)) match {
      case Some(value) => ()
      case None =>
        println(s"Cannot parse value ${reader(1)(0)} into proper format. " +
          s"Aborting formatting of $path.")
        return false
    }

    // Convert date field to proper formatting.
    val header = reader.head
    val formatted = for (record <- reader.tail)
      yield { stringToDate(record.head) :: record.tail }
    val all = header +: formatted

    val writer = CSVWriter.open(file)
    writer.writeAll(all)
    writer.close()

    true
  }  

  /** Returns a string with the `MM/dd/yyyy` format from one with the
    * `MMM dd, yyy` format.
    * 
    * @return `None` if the data is improperly formatted for conversion.
    */
  def stringToDate(s: String): Option[String] = {
    val formatter = DateTimeFormat.forPattern("MMM dd, yyyy")
    val old = Try(DateTime.parse(s, formatter))
    
    old match {
      case Success(date) => 
        val month = formatDigitString(date.getMonthOfYear())
        val day = formatDigitString(date.getDayOfMonth())
        val year = date.getYear()

        Option(s"$month/$day/$year")

      case Failure(exception) => None
    }
  }

  /** Adds a `0` to the beginning of an `Int` based string if the `Int` is less
    * than 10.
    */
  def formatDigitString(num: Int): String = {
    if (num < 10) 
      s"0${num}" 
    else 
      s"${num}"
  }
}
