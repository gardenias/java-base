package ariTemperature;

import com.yimin.apache.hadoop.MaxTemperatureReducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by yimin on 15/4/28.
 */
public class MaxTemperatureReducerTest {
    ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;

    @Before
    public void setUp() {
        reduceDriver = ReduceDriver.newReduceDriver(new MaxTemperatureReducer());
    }

    @Test
    public void testReduce() throws IOException {

        reduceDriver.withInput(new Text("1950"),
                Arrays.asList(
                        new IntWritable(10),
                        new IntWritable(4),
                        new IntWritable(45),
                        new IntWritable(34)
                ));

        reduceDriver.withOutput(new Text("1950"), new IntWritable(45));
        reduceDriver.runTest();
    }
}
