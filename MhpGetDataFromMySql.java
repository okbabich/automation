package skillup;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Parameter;
import utils.Concurrent;
import utils.ConcurrentJunitRunner;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static skillup.Utils.*;
import static skillup.Xpathes.*;

@Concurrent(threads = 1)
@RunWith(ConcurrentJunitRunner.class)

public class MhpGetDataFromMySql {

    public static WebDriver driver;

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

    private static final String url = "jdbc:mysql://cppt.cearfhlon7we.us-west-2.rds.amazonaws.com/cppt";
    private static final String user = "tester";
    private static final String password = "lsjc1dc15mg";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet result;

    @BeforeClass
    public static void initializationDriver() {
        driver = initTheSameDriver();
    }

    @Test
    public void mhprofessionalMySqlTest() {
        driver.get(urlFromTable);
        getAndCompareIsbn(XPATH_TO_GET_ISBN_MHP, isbn13FromTable);
        getAndComparePrice(XPATH_TO_GET_PRICE_MHP, priceFromTable);
    }

    public static  ArrayList<Object[]> getDataFromMysql() throws IOException {
        String query = "select ISBN13, PRICE_NET_$, URL from mhprofessional where tag = 987 limit 10";
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
//                System.out.println(isbn13FromTable + " " + priceFromTable + " " + urlFromTable);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return data;
    }

    @AfterClass
    public static void stopDriver() {
        closeDriver();
    }
}

