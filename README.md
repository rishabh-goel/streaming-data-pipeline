# Streaming Data Pipeline
## Team members: 
<table>
    <thead>
        <tr>
            <td><b>Name</b></td>
            <td><b>UIN</b></td>
            <td><b>Email</b></td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Rishabh Goel</td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>Samihan Nandedkar</td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>Amey Kasbe</td>
            <td>674285381</td>
            <td>akasbe2@uic.edu</td>
        </tr>
    </tbody>
<table>

## Project Description
The intention of the project is to create a streaming data pipeline. 
ADD CONTENT HERE - LOG GEN, S3, LAMBDA, KAFKA, SPARK

## Deployment
### [Amazon S3](https://aws.amazon.com/s3/)
* Amazon Simple Storage Service (Amazon S3) is an object storage service.
* The log files generated from the log generators running on the three EC2 instances are stored is S3 buckets.
* As soon as a file is inserted into S3 bucket, lambda function is triggered which in turn triggers (Setup and detailed explanation below).

### [Lambda Function](https://aws.amazon.com/lambda/)
* Lambda function is a serverless compute service which can be triggered by some events.

### Algorithm
* Python module boto3 is used to create, configure, and manage AWS services, such as Amazon EC2 and Amazon S3 with the help of [AWS Systems Manager Agent](https://docs.aws.amazon.com/systems-manager/latest/userguide/ssm-agent.html). 
* An SSM client is created. The created file's information including it's name is extracted from the event trigger. 
* Configurations of the EC2 instance is present in `application.conf` like AWS EC2 instance id and the name of the shell script to be triggered in it.0 
* With the help of ssm client, the shell script present in the EC2 instance is triggered using the `AWS-RunShellScript` document.


#### Setup AWS Lambda
1. Setup AWS Lambda function
    1. Navigate to AWS Lambda
    2. Create Function
        1. Select from scratch
            1. Enter name
            2. Select Python as language
            3. Enter the code from `lambda/lambda.py`
            4. Deploy
2. Setup Lambda configuration
    1. Add a JSON file with the configuration from `lambda/application.json` file.
    2. Make appropriate changes according to your AWS instance and shell script name.


#### Setup Lambda Trigger
* Under Lambda > Functions > <your_lambda_function.py>, click on "Add Trigger", select trigger - "S3"
* Select your S3 bucket.
* Select "All object create events" as Event type.

* Now, whenever there is any object creation in the specified S3 bucket, lambda function will be triggered in the specified EC2 instance.


## Kafka
* Apache Kafka is an open source, distributed streaming platform that allows for the development of real-time event-driven applications.
* It allows developers to make applications that continuously produce and consume streams of data records.

### How does Kafka helps in messaging system
![](etc/complicated_system.png)

* When companies start with implementing microservices the architecture is simple but after a while, it gets really complicated.

#### Problems
* If you have 4 source systems, and 6 target systems, you need to write, 24 integrations. 
* Each integration comes with difficulties around  
  * Protocol — how the data is transported (TCP, HTTP, REST, FTP,JDBC...) 
  * Data format — how the data is parsed (Binary, ) 
  * Data schema & evolution — how the data is shaped and may change 
  * Each source system will have an increased load from the connections
  * Each data pipelines have different requirements
  
### How Kafka helps
  * Kafka provides a messaging system between the services.
  * It follows distributed publish subscribe system, meaning, record is published and multiple consumers can subscribe to them.
  * It helps in decoupling system dependencies. 
    * Reduces the complexity of data pipelines making communication simpler and manageable 
    * Provides a common paradigm without any concern about the platform or languages 
    * Provides asynchronous communication
    * Facilitates reliable communication. Even if receiver not working - messages won't get lost
    
### Kafka Architecture
![](etc/kafka_archi.png)

### Terminologies
* Producer - 
	* The applications that produces messages.
	* Publishes data to a particular topic
* Consumer - 
    * The applications that consumes messages.
    * Producers send messages to kafka, kafka stores the messages, consumer who wants these messages subscribe to receive them. Adding consumer is easy in this way. 
* Broker
	* Single machine in Kafka cluster
* Zookeeper
	* Another Apache open source project
	* Stores the meta-data information related to kafka cluster - broker info, data info etc.
	* Manages the whole kafka cluster.
* Topic
	* Collection of logs of a particular category
	* Segregates messages and consumer will only subscribe to the topics they need
	* Can have 0/1/multiple consumers
		Example - sales topic, product topic
* Partition
	* Topics are divided into partitions
	* Partitioning allow parallel processing in multiple brokers - allows multiple consumers to read from a topic parallely


### Advantages of using Kafka
* Kafka is distributed. It runs as a cluster that can span multiple servers or even multiple data centers.
* The records that are produced are replicated and partitioned in such a way that allows for a high volume of users to use the application simultaneously without any perceptible lag in performance. So, with that, Apache Kafka is super fast.
* It also maintains a very high level of accuracy with the data records,
* Maintains the order of their occurrence
* Because it's replicated, Apache Kafka is also resilient and fault-tolerant.

### Kafka APIs
* Kafka works on 4 core APIs.
  * Producer API
      * The producer API allows your application to produce, to make, these streams of data.
      * It creates the records and produces them to topics. A "topic" is an ordered list of events.
      * Topic can persist to disk that's where it can be saved for just a matter of minutes if it's going to be consumed immediately or you can have it saved for hours, days, or even forever.
  * Consumer API
      * Subscribes to one or more topics and listens and ingests that data.
      * Producers can produce directly to consumers and that works for a simple Kafka application where the data doesn't change
  * Streams API
      * To transform the data we need Streams API
      * Leverages both Producer and Consumer API	
		
  * Connector API
      * Write connectors - reusable producers and consumers
      * In a Kafka cluster many developers might need to integrate the same type of data source, like a MongoDB, for example.
      * Not every single developer should have to write that integration, what the connector API allows is for that integration to get written once, the code is there, and then all the developer needs to do is configure it in order to get that data source into their cluster.

### Kafka Working
* With Kafka, messages are published onto topics.
* These topics are like never-ending log files.
* Producers put their messages onto a topic.
* Consumers drop in at any time, receive messages from the topic, and can even rewind and replay old messages.
* Messages are only deleted from a topic when you want them to be deleted.
* You can even run a Kafka broker that keeps every message ever (set log retention to “forever”), and Kafka will never delete anything.
* One of the best things about Kafka is that it can replicate messages between brokers in the cluster.
* So consumers can keep receiving messages, even if a broker crashes.
* This makes Kafka very capable of handling all sorts of scenarios, from simple point-to-point messaging, to stock price feeds, to processing massive streams of website clicks, and even using Kafka like a database (yes, some people are doing that).



## References
1. Dr. Grechanik, Mark, (2020) Cloud Computing: Theory and Practice.
2. [Kafka](https://kafka.apache.org/)
3. [TutorialWorks](https://www.tutorialworks.com/kafka-vs-streams-vs-connect/)
4. [Edureka](https://www.youtube.com/channel/UCkw4JCwteGrDHIsyIIKo4tQ)

