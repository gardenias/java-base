package com.yimin.apache.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by yimin on 15/4/28.
 */
public class MaxTemperatureMapper extends
        Mapper<LongWritable, Text, Text, IntWritable> {

    private NDCRecordParser ndcRecordParser = new NDCRecordParser();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        ndcRecordParser.parse(value);

        if (ndcRecordParser.isValidTemperature()) {
            context.write(new Text(ndcRecordParser.getYear()), new IntWritable(ndcRecordParser.getAriTemperature()));
        }
    }
}
