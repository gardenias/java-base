package search;

import org.junit.Assert;
import org.junit.Test;

import com.yimin.algorithm.search.InflectionPoint;

/**
 *
 * Created by WuYimin on 2015/6/12.
 */
public class InflectionPointTest {
    @Test
    public void testInflection() throws Exception {
        Assert.assertEquals(-1, InflectionPoint.inflection(null));
        Assert.assertEquals(-1, InflectionPoint.inflection(new int[]{}));
        Assert.assertEquals(0, InflectionPoint.inflection(new int[]{1}));
        Assert.assertEquals(1, InflectionPoint.inflection(new int[]{1, 3}));
        Assert.assertEquals(2, InflectionPoint.inflection(new int[]{1, 3, 4}));
        Assert.assertEquals(0, InflectionPoint.inflection(new int[]{3, 1}));
        Assert.assertEquals(0, InflectionPoint.inflection(new int[]{3, 2, 1}));
        Assert.assertEquals(1, InflectionPoint.inflection(new int[]{1, 3, 2}));
        Assert.assertEquals(7, InflectionPoint.inflection(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 2, 1,}));
    }
}
