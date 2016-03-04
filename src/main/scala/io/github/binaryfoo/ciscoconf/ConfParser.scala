package io.github.binaryfoo.ciscoconf

import scala.collection.mutable
import scala.io.Source

object ConfParser {

  def parse(conf: String): Config = {
    parse(Source.fromString(conf))
  }

  def parse(source: Source): Config = {
    val dummy = parse(ConfLine(""), source.getLines().buffered)
    Config(dummy.children)
  }

  private def parse(parent: ConfLine, remaining: BufferedIterator[String], depth: String = ""): ConfLine = {
    val children = mutable.ArrayBuffer[ConfLine]()
    val nextDepth = " " + depth
    var stop = false
    while (remaining.nonEmpty && !stop) {
      val line = remaining.head
      if (line.startsWith(nextDepth)) {
        val last = children.remove(children.size - 1)
        children += parse(last, remaining, nextDepth)
      } else if (depth.length == 0 || line.startsWith(depth)) {
        children += ConfLine(remaining.next().trim)
      } else {
        stop = true
      }
    }
    parent.copy(children = children.toSeq)
  }

}
