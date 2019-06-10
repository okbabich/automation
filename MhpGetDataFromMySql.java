package skillup;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.yandex.qatools.allure.annotations.Parameter;
import utils.Concurrent;
import utils.ConcurrentJunitRunner;
import java.lang.Object;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static skillup.Utils.closeRemotWD;
import static skillup.Utils.initRemoteWD;
import static skillup.Xpathes.*;

@Concurrent(threads = 1)
@RunWith(ConcurrentJunitRunner.class)


public class MhpGetDataFromMySql {

    private static final String url = "jdbc:mysql://cppt.cearfhlon7we.us-west-2.rds.amazonaws.com/cppt";
    private static final String user = "tester";
    private static final String password = "lsjc1dc15mg";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet result;


    @Parameter("isbn13FromTable")
    private static String isbn13FromTable;

    @Parameter("priceFromTable")
    private static String priceFromTable;

    @Parameter("urlFromTable")
    private static String urlFromTable;

    @Parameterized.Parameters
    public static Collection<Object[]> params() throws Exception {
        return getDataFromMysql();
    }

    public MhpGetDataFromMySql(String isbn13FromTable, String priceFromTable, String urlFromTable) {
        this.isbn13FromTable = isbn13FromTable;
        this.priceFromTable = priceFromTable;
        this.urlFromTable = urlFromTable;
    }

    @Before
    public void initRemoteDriver() throws MalformedURLException {
        initRemoteWD();
    }

    @Test
    public void mhprofessionalMySqlTest() throws MalformedURLException{

        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setBrowserName("chrome");
        WebDriver driver1 = new RemoteWebDriver(new URL("http://10.10.83.231:4444/wd/hub"), capability);
//        WebDriver driver1 = new RemoteWebDriver(new URL("http://54.202.145.27:4444/wd/hub"), capability);

        driver1.get(urlFromTable);
        String IsbnFromThePage = driver1.findElement(By.xpath(XPATH_TO_GET_ISBN_MHP)).getText()
                .replace("-", "")
                .replace("ISBN: ", "")
                .replace("ISBN:", "");
        assertEquals(isbn13FromTable, IsbnFromThePage);
        System.out.println(IsbnFromThePage);


        String actualPrice = driver1.findElement(By.xpath(XPATH_TO_GET_PRICE_MHP)).getText()
                .replace("$", "");
        assertEquals(priceFromTable, actualPrice);
        System.out.println("Parsed price is matched with website");
        driver1.quit();
    }

    public static ArrayList<Object[]> getDataFromMysql() throws IOException {
        String query = "select ISBN13, PRICE_NET_$, URL from mhprofessional where tag = 1005 limit 10";
        ArrayList<Object[]> data = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                String isbn13FromTable = result.getString(1);
                String priceFromTable = result.getString(2);
                String urlFromTable = result.getString(3);

                data.add(new Object[]{isbn13FromTable, priceFromTable, urlFromTable});
                System.out.println(isbn13FromTable + " " + priceFromTable + " " + urlFromTable);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return data;
    }

    @After
    public  void stopDriver() {
        closeRemotWD();
    }
}

