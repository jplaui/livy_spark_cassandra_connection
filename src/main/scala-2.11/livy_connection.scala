import scalaj.http._
import play.api.libs.json._

object livy_connection {
  def main(args: Array[String]): Unit = {

    val url = "YOUR_URL"
    val session_url = url + "/sessions"

// still using hard coded session numbers-------------

// creates livy session:
    val request = Http(session_url)
      .postData("""{"kind": "spark", "conf": {
        | "spark.driver.allowMultipleContexts": "true",
        | "spark.jars.packages": "com.datastax.spark:spark-cassandra-connector_2.10:1.6.2,org.apache.spark:spark-core_2.10:1.6.2,org.apache.spark:spark-sql_2.10:1.6.0",
        | "spark.driver.extraClassPath": "/home/livy/.ivy2/jars/com.datastax.spark_spark-cassandra-connector_2.10-1.6.2.jar:/home/livy/.ivy2/jars/joda-time_joda-time-2.3.jar:/home/livy/.ivy2/jars/com.twitter_jsr166e-1.1.0.jar:/home/livy/.ivy2/jars/io.netty_netty-all-4.0.33.Final.jar:/home/livy/.ivy2/jars/org.joda_joda-convert-1.2.jar:/home/livy/.ivy2/jars/org.scala-lang_scala-reflect-2.10.5.jar",
        | "spark.executor.extraClassPath": "/home/livy/.ivy2/jars/com.datastax.spark_spark-cassandra-connector_2.10-1.6.2.jar:/home/livy/.ivy2/jars/joda-time_joda-time-2.3.jar:/home/livy/.ivy2/jars/com.twitter_jsr166e-1.1.0.jar:/home/livy/.ivy2/jars/io.netty_netty-all-4.0.33.Final.jar:/home/livy/.ivy2/jars/org.joda_joda-convert-1.2.jar:/home/livy/.ivy2/jars/org.scala-lang_scala-reflect-2.10.5.jar"
        | }}""".stripMargin('|'))
      .headers(Seq("Content-Type"->"application/json"))
      .asString

// scala code that becomes executed
    val code = JsString("""
      import org.apache.spark.{SparkContext, SparkConf};
      import com.datastax.spark.connector._;
      val conf = new SparkConf(true)
        .setMaster("local[*]")
        .setAppName("MIME_type_statistics")
        .set("spark.cassandra.connection.host", "COMMA_SEPARATED_LIST_OF_HOSTS")
        .set("spark.cassandra.auth.username", "USER_NAME")
        .set("spark.cassandra.auth.password", "PW");

      val sc = new SparkContext(conf);

      val cas_table = sc.cassandraTable("TABLE", "OBJECTS")
        .select("TYPE")
        .map(row => row.toString().split(" ")(1))
        .countByValue();

      println(cas_table);
      """.replaceAll("\n","").replaceAll("  ",""))

    val json_data = Json.obj("code" -> code)
    // println(json_data.toString())

// wait until session state turn into idle: then possible to send this request
    val statements_url = session_url + "/8/statements"
    val request = Http(statements_url)
      .headers(Seq("Content-Type"->"text/text"))
      .postData(json_data.toString())
      //.postData("""{"code":"import com.datastax.spark.connector._;import org.apache.spark.{SparkContext, SparkConf};val conf = new SparkConf(true).setAppName(\"MIME_type_statistics\")"}""")
      .asString

// created a simple GET command
   val request: HttpResponse[String] = Http(session_url)
     .method("GET")
     .headers(Seq("Content-Type"->"application/json"))
     .asString

// deletes session
    val delete_url = url + "/sessions/7"
    val request = Http(delete_url)
      .method("DELETE")
      .headers(Seq("Content-Type"->"application/json"))
      .asString

    println(request.body)

  }
}
