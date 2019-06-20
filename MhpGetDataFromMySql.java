package skillup;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Parallelized;

import java.lang.Object;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

import static skillup.Xpathes.*;
import static skillup.Utils.*;


@RunWith(Parallelized.class)


public class MhpGetDataFromMySql {

    public MhpGetDataFromMySql(String isbn13FromTable, String priceFromTable, String urlFromTable) {
        this.isbn13FromTable = isbn13FromTable;
        this.priceFromTable = priceFromTable;
        this.urlFromTable = urlFromTable;
    }

    private String isbn13FromTable;
    private String priceFromTable;
    private String urlFromTable;

    private static final String url = "jdbc:mysql://cppt.cearfhlon7we.us-west-2.rds.amazonaws.com/cppt";
    private static final String user = "tester";
    private static final String password = "lsjc1dc15mg";
    private static Connection connection;
    private static Statement statement;
    private static ResultSet result;
    public RemoteWebDriver driver1;


    @Parameterized.Parameters
    public static Collection<Object[]> params() throws Exception {
        return getDataFromMysql();
    }

    @Before
    public void setUP() {
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setBrowserName("chrome");
        try {
            driver1 = new RemoteWebDriver(new URL("http://10.10.83.231:4444/wd/hub"), capability);
        } catch (MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void mhprofessionalMySqlTest() {
        try {
            driver1.get(urlFromTable);

            getAndCompareIsbnDifferentTabs(XPATH_TO_GET_ISBN_MHP, XPATH_TO_GET_ISBN_MHP_ANOTHER_TAB, isbn13FromTable, urlFromTable, driver1);
            getAndComparePrice(XPATH_TO_GET_PRICE_MHP, priceFromTable, driver1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Object[]> getDataFromMysql() throws IOException {
        String query = "select ISBN13, PRICE_NET_$, URL from mhprofessional where tag = 1014 limit 10";
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
    public void stopDriver() {
        if (driver1 != null)
            driver1.quit();

    }
}

