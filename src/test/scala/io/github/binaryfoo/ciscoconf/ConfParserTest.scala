package io.github.binaryfoo.ciscoconf

class ConfParserTest extends ConfSpec {

  "Parser" should "handle ASA config" in {
    val config = ConfParser.parse(contentsOf("asa-sample.txt"))
    config.children.size shouldBe 299
  }

  "ASA config" should "have interfaces" in {
    val config = ConfParser.parse(contentsOf("asa-sample.txt"))
    val interfaces = config.findChildren("^interface".r)
    interfaces.size shouldBe 12
  }

  "ConfLine" should "get values" in {
    val conf = """interface Vlan200
                  | nameif INSIDE
                  | security-level 100
                  | ip address 192.0.2.1 255.255.255.0
                  |!""".stripMargin
    val lines = ConfParser.parse(conf)
    lines.children(0).get("interface") shouldBe "Vlan200"
    lines.children(0).get("ip address") shouldBe "192.0.2.1 255.255.255.0"
  }
}
