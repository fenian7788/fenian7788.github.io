package github.fenian7788.io

import scala.io.Source


/**
  * 使用scala实现一个wc小程序
  */
object WordCountApp {

  def main(args: Array[String]): Unit = {
    val filePath = "D:/word.txt"
    val codec = "utf-8"
    val file = Source.fromFile(filePath, codec)
    val wc = file
      .getLines() //["hadoop	hadoop	hadoop","word	world	world","pig	hive	word	hive	pig"]

      .flatMap(_.split("\t")) //["hadoop","hadoop","hadoop","word","	world","world","pig","hive","word","hive","pig"]

      .toList //List(hadoop, hadoop, hadoop, word, world, world, pig, hive, word, hive, pig)

      .map((_, 1)) //List((hadoop,1), (hadoop,1), (hadoop,1), (word,1), (world,1), (world,1), (pig,1), (hive,1), (word,1), (hive,1), (pig,1))

      .groupBy((_._1)) //Map(world -> List((world,1), (world,1)), hadoop -> List((hadoop,1), (hadoop,1), (hadoop,1)), hive -> List((hive,1), (hive,1)), word -> List((word,1), (word,1)), pig -> List((pig,1), (pig,1)))

      .mapValues(_.size) //Map(world -> 2, hadoop -> 3, hive -> 2, word -> 2, pig -> 2)
    println(wc)
    file.close()
  }
}

