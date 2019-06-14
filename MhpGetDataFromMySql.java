package skillup;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
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

import static org.junit.Assert.assertEquals;
import static skillup.Xpathes.*;


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
            String IsbnFromThePage = driver1.findElement(By.xpath(XPATH_TO_GET_ISBN_MHP)).getText();
            System.out.println(urlFromTable);

            if (isbn13FromTable.equals(IsbnFromThePage)) {
                System.out.println("MySQL data " + isbn13FromTable + " = site data (first tab) " + IsbnFromThePage);
            } else {
                driver1.findElement(By.xpath(XPATH_TO_GET_ISBN_MHP_ANOTHER_TAB)).click();
                String IsbnFromThePageAnotherTab = driver1.findElement(By.xpath(XPATH_TO_GET_ISBN_MHP)).getText();

                if (isbn13FromTable.equals(IsbnFromThePageAnotherTab)) {
                    System.out.println("MySQL data " + isbn13FromTable + " = site data (another tab) " + IsbnFromThePageAnotherTab);
                }
            }

            String actualPrice = driver1.findElement(By.xpath(XPATH_TO_GET_PRICE_MHP)).getText()
                    .replace("$", "");
            assertEquals(priceFromTable, actualPrice);
            System.out.println("Parsed price " + priceFromTable + " is matched with website " + actualPrice);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Object[]> getDataFromMysql() throws IOException {
        String query = "select ISBN13, PRICE_NET_$, URL from mhprofessional where tag = 1007 limit 10";
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

