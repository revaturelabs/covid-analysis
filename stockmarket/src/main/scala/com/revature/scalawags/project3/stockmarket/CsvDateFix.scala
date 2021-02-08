package com.revature.scalawags.project3.stockmarket

import java.io.File

import com.github.nscala_time.time.Imports._
import com.github.tototoshi.csv._

object CsvDateFix {
  /** Reads from a properly formatted csv file and overwrites it with
    * proper formatting. 
    */
  def formatCsv(path: String): Unit = {
    val file = new File(path)
    val reader = CSVReader
      .open(file)
      .all()

    val header = reader.head
    val formatted = for (record <- reader.tail)
      yield { 
        List(
          stringToDate(record(0)),
          record(1),
          record(2),
          record(3),
          record(4),
          record(5),
          record(6)
        )
      }
    val all = header +: formatted

    val writer = CSVWriter.open(file)
    writer.writeAll(all)
  }

  /** Returns a string with the `MM/dd/yyyy` format from one with the
    * `MMM dd, yyy` format.
    */
  def stringToDate(s: String): String = {
    val formatter = DateTimeFormat.forPattern("MMM dd, yyyy")
    val old = DateTime.parse(s, formatter)
    val month = formatDigitString(old.getMonthOfYear())
    val day = formatDigitString(old.getDayOfMonth())
    val year = old.getYear()
    
    s"$month/$day/$year"
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
