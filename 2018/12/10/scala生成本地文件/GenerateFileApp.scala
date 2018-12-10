package github.fenian7788.io

import java.io.{File, PrintWriter}

object GenerateFileApp {

  val years = Array("2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019")

  val urls = Array("https://www.baidu.com/",
    "https://fenian7788.github.io/",
    "https://me.csdn.net/weixin_39702831",
    "https://blog.csdn.net/umdfml00",
    "https://blog.csdn.net/Jaserok",
    "https://www.jianshu.com/u/32a08e1b7af0",
    "https://blog.csdn.net/Sylvia_D507",
    "https://blog.csdn.net/qq_29416019",
    "https://blog.csdn.net/qq_39892028",
    "https://blog.csdn.net/u010854024",
    "https://blog.csdn.net/qq_34382453",
    "https://blog.csdn.net/weixin_42733888",
  )

  val filePath = "/data/data.txt"

  def main(args: Array[String]): Unit = {
    val writer = new PrintWriter(new File(filePath))
    for (i <- 1 to 10) {
      writer.println(generateLines)
    }
    writer.flush()
    writer.close()
  }

  def generateLines(): String = {
    var sb: StringBuilder = new StringBuilder()
    sb.append(generateRandomUrl())
      .append("\t")
      .append(generateRandomDate())
      .append("\t")
      .append(generateRandomNum2(1000))
    sb.toString()
  }


  //生成随机数
  def generateRandomNum(range: Int) = {
    (new util.Random).nextInt(range) + 1
  }

  def generateRandomNum2(range: Int) = {
    val num = (new util.Random).nextInt(range)
    if (num % 23 == 0) generateRandomNum(9).toString + "-" else generateRandomNum(100).toString
  }

  //会缺大括号的生成年份
  def generateRandomDate() = {
    var sb: StringBuilder = new StringBuilder()
    val randomNum = generateRandomNum(1000)
    if (randomNum % 97 != 0) sb.append("[") else sb.append(" ")
    //年份
    sb.append(years((new util.Random).nextInt(years.length))).append("-")
    //月份
    sb.append("%02d".format(generateRandomNum(12))).append("-")
    //日期
    sb.append("%02d".format(generateRandomNum(30))).append(" ")
    //小时
    sb.append("%02d".format(generateRandomNum(23))).append(":")
    //分钟
    sb.append("%02d".format(generateRandomNum(59))).append(":")
    //秒
    sb.append("%02d".format(generateRandomNum(59)))
    if (randomNum % 89 != 0) sb.append("]") else sb.append(" ")
    sb.toString()
  }

  //从urls随机获取一个地址
  def generateRandomUrl(): String = {
    urls((new util.Random).nextInt(urls.length))
  }

}
