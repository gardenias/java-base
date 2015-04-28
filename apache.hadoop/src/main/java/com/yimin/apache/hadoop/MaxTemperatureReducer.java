package com.yimin.apache.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Created by yimin on 15/4/28.
 */
public class MaxTemperatureReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        final int[] maxAriTemperature = {-1};
        values.forEach(new Consumer<IntWritable>() {
            @Override
            public void accept(IntWritable ariTemperature) {
                maxAriTemperature[0] = Math.max(maxAriTemperature[0], ariTemperature.get());
            }
        });
        context.write(key,new IntWritable(maxAriTemperature[0]));
    }
}
