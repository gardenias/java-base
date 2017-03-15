package java.lang;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author yimin
 */
public class IntegerCacheTest {

    @Test
    public void valueOfUseCache() throws Exception {
        Integer first = Integer.valueOf(100);
        Integer second = Integer.valueOf(100);
        assertThat(second).isSameAs(first);
    }
}
