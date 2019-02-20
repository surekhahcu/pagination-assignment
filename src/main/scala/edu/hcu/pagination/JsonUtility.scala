package edu.hcu.pagination

import org.json4s._
import org.json4s.native.{JsonMethods, Serialization}

object JsonUtility {

  implicit val formats = DefaultFormats

  def write[T <: AnyRef](value: T): String = Serialization.write(value)

  def parse(value: String): JValue = JsonMethods.parse(value)
}
