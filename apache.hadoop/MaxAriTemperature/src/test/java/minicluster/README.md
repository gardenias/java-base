##Mini Cluster

1. [hdfs web interface](http://localhost:49633/webapps/hdfs/)`http://localhost:49633/webapps/hdfs/`
2. [data node web interface](http://localhost:49636/webapps/datanode/)`http://localhost:49636/webapps/datanode/`
3. [data node web interface](http://localhost:49641/webapps/datanode/)`http://localhost:49641/webapps/datanode/`
4. and`histroy web interface`,`node web inteface`,`cluster web interface`

### submit task on mini cluseter

```java
Configuration conf = new Configuration();
conf.addResource(new Path(new File("conf.xml").getAbsolutePath().toString()));
Job job = Job.getInstance(conf);
```
