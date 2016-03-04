package io.github.binaryfoo.ciscoconf

import java.nio.file.{Files, Paths}

import org.scalatest.{FlatSpec, Matchers}

class ConfSpec extends FlatSpec with Matchers {

  def contentsOf(fileName: String): String = {
    new String(Files.readAllBytes(Paths.get(testFile(fileName))))
  }

  def testFile(fileName: String): String = {
    s"src/test/resources/$fileName"
  }

}
