package SparkProject

import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

import java.util.logging.Level
import org.apache.spark.sql.{ForeachWriter, Row, SparkSession}
import org.apache.spark.sql.functions.{col, date_format, second, to_timestamp, trunc, window}
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.streaming.StreamingContext.rddToFileName
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext, Time}
import org.slf4j.{Logger, LoggerFactory}

import java.io.File
import java.text.SimpleDateFormat
import java.util.Properties
import java.util.regex.Pattern
import javax.activation.{DataHandler, FileDataSource}
import scala.concurrent.duration.DurationInt
import javax.mail._
import javax.mail.internet._
import scala.math.Ordered.orderingToOrdered
import scala.sys.process.Process
object Spark {


  def main(args:Array[String]): Unit = {
    val logger: Logger = LoggerFactory.getLogger(this.getClass.getSimpleName)

    val conf = ConfigFactory.load()
    val NO_SPACE = ""

    //Part 2 - Using Spark Streaming
    val brokerId = "localhost:9092"
    val groupId = "GRP1"
    val topics = "topic1"
    val offset = "earliest"
    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("SparkWordCount")

    val ssc = new StreamingContext(sparkConf, Seconds(60))

    val sc = ssc.sparkContext
    sc.setLogLevel("OFF")

    val topicSet = topics.split(",").toSet

    logger.info("Part1")
    val kafkaParams = Map[String, Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> brokerId,
      ConsumerConfig.GROUP_ID_CONFIG -> groupId,
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
      ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> offset,
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer]
    )

    logger.info("Part2")
    val messages = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topicSet, kafkaParams)
    )

    logger.info("Part3")
    val msg_types = Set("ERROR", "INFO", "WARN","DEBUG")
    val words = messages.map(_.value()).flatMap(_.split(" ")).filter(msg_types.contains(_))

    logger.info("Part4")
    val countwords = words.map(x => (x, 1)).reduceByKey(_ + _)

    logger.info("Part5")
    countwords.print()

    logger.info("Part6")
    val config = ConfigFactory.load()
    val reportSaveLocation = config.getString("emailservice.report-save-location")
    val emailServiceLocation = config.getString("emailservice.email-service")
    val emailService = "sh " + emailServiceLocation
    countwords.foreachRDD(rdd => {
      val map = rdd.collect().toMap
      val count: Int = map.getOrElse("ERROR",0);
      if(count > 1)
      {
        sc.parallelize(map.toSeq).saveAsTextFile(reportSaveLocation)
        Process(emailService)
      }
      else
      {
        logger.info("COUNT IS LESS THAN 2")
      }
    })

    ssc.start()

    ssc.awaitTermination()

  }
}