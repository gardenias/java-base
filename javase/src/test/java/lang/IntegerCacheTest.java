package lang;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * @see Integer#valueOf(int)
 *
 *      cached:
 *
 * @see Boolean
 * @see java.lang.Byte.ByteCache
 * @see java.lang.Character.CharacterCache
 * @see java.lang.Integer.IntegerCache
 * @see java.lang.Long.LongCache
 * @see java.lang.Short.ShortCache
 *
 *      no cache:
 * 
 * @see Float
 * @see Double
 */
public class IntegerCacheTest {
    static final Integer cons = 1000;
    private Integer first;
    private Integer second;

    @Test
    public void valueOfUseCache() throws Exception {
        first = Integer.valueOf(100);
        second = Integer.valueOf(100);
        assertThat(second).isSameAs(first);
    }

    @Test
    public void newStatementNotCache() {
        first = new Integer(101);
        second = new Integer(101);
        assertThat(second).isNotSameAs(first);
    }

    /**
     * {@code -XX:AutoBoxCacheMax=<size>}
     *
     * really do:
     * this.first = Integer.valueOf(102);
     * this.second = Integer.valueOf(102);
     *
     * check IntegerCacheTest.class file
     *
     * <a href="http://docs.oracle.com/javase/tutorial/java/data/autoboxing.html">Java Autoboxing</a>
     */
    @Test
    public void primitiveAssignmentUseCache() {
        first = 102;
        second = 102;
        assertThat(second).isSameAs(first);

        int i = second;// int i = this.second.intValue();
    }

    @Test
    public void equalsToConstVariableNotCacheToo() {
        first = 1000;
        second = 1000;
        assertThat(second).isNotSameAs(first);
    }

    @Test
    public void integerDefaultCacheRange_128_127() {
        first = Integer.valueOf(128);
        second = Integer.valueOf(128);
        assertThat(second).isNotSameAs(first);

        first = new Integer(129);
        second = new Integer(129);
        assertThat(second).isNotSameAs(first);
    }
}
