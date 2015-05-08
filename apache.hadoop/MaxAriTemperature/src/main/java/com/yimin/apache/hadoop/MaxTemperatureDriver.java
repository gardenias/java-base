package com.yimin.apache.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * java-libs
 * Created by WuYimin on 2015/4/28.
 */
public class MaxTemperatureDriver extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new MaxTemperatureDriver(), args);
        System.exit(res);
    }

    @Override
    public int run(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println("Usage:MaxTemperatureDriver <input path> <output path>");
            ToolRunner.printGenericCommandUsage(System.out);
            return 1;
        }
        Configuration conf = getConf();
        //1. job instance
        Job job = Job.getInstance(conf);
        //2. append input path, set output path
        //job configuration property name:mapreduce.input.fileinputformat.inputdir
        FileInputFormat.setInputPaths(job, args[0]);
        //job configuration property name:mapreduce.output.fileoutputformat.outputdir
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //additional:job name
        job.setJobName("Max Ari Temperature");// job configuration property name:mapreduce.job.name

        //job.setJar("max.ari.temperature.jar"); // job configuration property name:mapreduce.job.jar
        job.setJarByClass(MaxTemperatureDriver.class);

        //3.mapper,reducer,combiner class
        job.setMapperClass(MaxTemperatureMapper.class);// job configuration property name: mapreduce.job.map.class
        job.setReducerClass(MaxTemperatureReducer.class);//job configuration property name:mapreduce.job.reduce.class
        job.setCombinerClass(MaxTemperatureReducer.class);//job configuration property name:mapreduce.job.combine.class

        //4. set output key and value type
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }
}
