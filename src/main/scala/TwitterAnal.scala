import org.apache.spark.streaming.{Seconds, StreamingContext}
import StreamingContext._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.twitter._
import org.apache.spark.SparkConf
import scala.util.Random



object TwitterAnalysis {


  def main(args: Array[String]) {
    
    val (master, filters) = (args(0), args.slice(5, args.length))
    //Our main confs
    val sparkConf = new SparkConf()
    .setAppName("TwitterAnalysis")
    .setMaster(master)
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    // Twitter Authentication credentials
    // Change it with appropriate keys
    val twitterKeys = List("<consumer_key>", "<consumer_secret>", "<access_token>", "<access_secret>")
    val map = twitterKeys.zip(args.slice(1, 5).toList).toMap

    twitterKeys.foreach(key => {
      if (!map.contains(key)) {
        throw new Exception("Error with OAuth")
      }
      val fullKey = "twitter4j.oauth." + key
      System.setProperty(fullKey, map(key))
    })

    // sets stream and chosen data
    val stream = TwitterUtils.createStream(ssc, None, filters)

    val twitterHash = stream.flatMap(status => status.getText.split(" ").filter(_.startsWith("#")))


    val topHashs = twitterHash.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(10))
                     .map{case (topic, count) => (count, topic)}
                     .transform(_.sortByKey(false))


    topHashs.foreachRDD(rdd => {
      val name = Random.nextInt
      val topList = rdd.map(s => s.toString().replace("(","").replace(")","")).saveAsTextFile("hdfs://ip-xx-xx-xxx-xx.ec2.internal:<port>/spark/out"+urname)
      rdd.map(s => s.toString().replace("(","").replace(")","")).foreach(println)
    })


    ssc.start()
    ssc.awaitTermination()
  }
}

