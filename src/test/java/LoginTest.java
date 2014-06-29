import com.betfair.aping.LoginResponse;
import com.betfair.aping.LoginStatus;
import org.testng.annotations.Test;
import pt.paulosantos.betfair.aping.impl.SportsApiRescriptClient;

import static org.testng.Assert.assertEquals;

/**
 * Created by Paulo. 20-04-2014.
 */
public class LoginTest {
    @Test(enabled = false)
    public void login() throws Exception {
        SportsApiRescriptClient sportsApiRescriptClient = new SportsApiRescriptClient();

        //LoginResponse response = sportsApiRescriptClient.login("username", "password", "C:\\Path\\to\\keystore\\client-2048.p12","keyStorePassword","APP_KEY");
        LoginResponse response = sportsApiRescriptClient.login("username.", "password", "classpath:client-2048.p12","keyStorePassword","APP_KEY");

        if (response.getLoginStatus().equals(LoginStatus.SUCCESS)) {
            System.out.println(response.getSessionToken());
        } else {
            System.err.println("Something was wrong!");
            System.out.println(response.getLoginStatus());
        }

        assertEquals(response.getLoginStatus(), LoginStatus.SUCCESS);
    }

}
