package com.yimin.apache.hadoop;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * java-libs
 * Created by WuYimin on 2015/4/28.
 */
public class MaxTemperatureDriver {

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.printf("Usage:MaxTemperatureDriver [generic options] <input path> <output path>\n");
            System.exit(-1);
        }
        //1. job instance
        Job job = Job.getInstance();
        //2. append input path, set output path
        //job configuration property name:mapreduce.input.fileinputformat.inputdir
        FileInputFormat.addInputPath(job, new Path(args[0]));
        //job configuration property name:mapreduce.output.fileoutputformat.outputdir
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //additional:job name
        job.setJobName("Max Ari Temperature");// job configuration property name:mapreduce.job.name

        job.setJar("max.ari.temperature.jar"); // job configuration property name:mapreduce.job.jar

        //3.mapper,reducer,combiner class
        job.setMapperClass(MaxTemperatureMapper.class);// job configuration property name: mapreduce.job.map.class
        job.setReducerClass(MaxTemperatureReducer.class);//job configuration property name:mapreduce.job.reduce.class
        job.setCombinerClass(MaxTemperatureReducer.class);//job configuration property name:mapreduce.job.combine.class

        //4. set output key and value type
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
