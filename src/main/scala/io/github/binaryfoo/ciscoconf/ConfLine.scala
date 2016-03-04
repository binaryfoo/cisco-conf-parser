package io.github.binaryfoo.ciscoconf

import scala.util.matching.Regex

trait ConfParent {
  def children: Seq[ConfLine]
  def findChildren(r: Regex): Seq[ConfLine] = {
    for (child <- children if r.findFirstIn(child.text).nonEmpty) yield child
  }
}

case class Config(children: Seq[ConfLine]) extends ConfParent

case class ConfLine(text: String, children: Seq[ConfLine] = Seq.empty) extends ConfParent {

  def get(name: String): Option[String] = {
    if (text.startsWith(name + " ")) {
      Some(text.replace(name + " ", ""))
    } else {
      children.collectFirst { case child if child.has(name) => child.get(name).get }
    }
  }

  def has(name: String): Boolean = get(name).nonEmpty
}
