
import org.scalatest.funspec.AnyFunSpec
import com.typesafe.config.ConfigFactory



class StreamingDataPipelineTestSuite extends AnyFunSpec {
  val config = ConfigFactory.load()

  describe("Config file") {
    it("should be present") {
      assert(!config.isEmpty)
    }
  }

  describe("Config parameters"){
    it("should have correct dispatcher") {
      assert(config.getString("akka.kafka.producer.use-dispatcher") == "akka.kafka.default-dispatcher")
    }
    it("should have a topic name") {
      assert(config.getString("spark.topic-name") == "topic1")
    }
  }
}
