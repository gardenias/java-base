package ariTemperature;

import com.yimin.apache.hadoop.MaxTemperatureMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by yimin on 15/4/28.
 */
public class MaxTemperatureMapperTest {

    MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;

    @Before
    public void setUp() {
        mapDriver = MapDriver.newMapDriver(new MaxTemperatureMapper());
    }

    @Test
    public void testMap() throws IOException {
        Text value = new Text("0029029070999991901010106004+64333+023450FM-12+000599999V0202701N015919999999N0000001N9-00781+99999102001ADDGF108991999999999999999999");
        mapDriver.withInput(new LongWritable(10L),value);
        mapDriver.withOutput(new Text("1901"), new IntWritable(-78));
        mapDriver.runTest();
    }
}
