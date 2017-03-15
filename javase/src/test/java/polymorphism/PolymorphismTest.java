package polymorphism;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.yimin.api.Account;
import com.yimin.api.AccountAccepter;
import com.yimin.api.ElectricityAccount;
import com.yimin.api.WaterAccount;

/**
 * @author yimin
 */
public class PolymorphismTest {
    @Test
    public void declarativeProgrammingLanguage() throws Exception {
        AccountAccepter accepter = new AccountAccepter();
        Account waterAccount = new WaterAccount();
        Account electricityAccount = new ElectricityAccount();

        assertThat(accepter.acceptAccount(waterAccount)).isEqualTo("Declarative Programming language").withFailMessage("java is Declarative Programming language");
        assertThat(accepter.acceptAccount(electricityAccount)).isEqualTo("Declarative Programming language").withFailMessage("java is Declarative Programming language");
    }
}
