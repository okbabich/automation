package skillup;

import au.com.bytecode.opencsv.CSVReader;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Parallelized;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static skillup.Utils.*;
import static skillup.Xpathes.*;


@RunWith(Parallelized.class)


public class ParametrizedTestAmazonAnalizedReport {

    public ParametrizedTestAmazonAnalizedReport(String URL, String ISBN13) {
        this.URL = URL;
        this.ISBN13 = ISBN13;
    }

    private String URL;
    private String ISBN13;
    private RemoteWebDriver driver1;

    @Parameterized.Parameters
    public static Collection<Object[]> params() throws Exception {
        return getDataFromTheCsvFile(0);//method to collect parameters. As a rule located in some other class
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
    public void amazonPageTest() throws IOException {
        System.out.println(URL);
        driver1.get(URL);
        List<WebElement> items = driver1.findElements(By.xpath(XPATH_FOR_PRICES_BOX_AMAZON));

        if (items.size() > 0) {
            getAndCompareIsbn(XPATH_TO_GET_ISBN13_AMAZON_NEW_DESIGN, ISBN13, driver1);
            checkPriceFromThePage(XPATH_TO_GET_BUY_NEW_PRICE_AMAZON_NEW_DESIGN, driver1);
        } else {
            getAndCompareIsbn(XPATH_TO_GET_ISBN13_AMAZON_OLD_DESIGN, ISBN13, driver1);
            checkPriceFromThePage(XPATH_TO_GET_BUY_NEW_AMAZON_OLD_DESIGN, driver1);
        }
    }

    @After
    public void stopDriver() {
        if (driver1 != null)
            driver1.quit();
    }

    /**
     * This method is using for getting two columns from csv file
     *
     * @param ColumnNumber - numeration of csv columns starts with 0
     * @return - array list of objects/ Count of returned objects should be equal count of test parameters
     * @throws IOException
     * @throws AssertionError
     */

    public static ArrayList<Object[]> getDataFromTheCsvFile(int ColumnNumber) throws IOException, AssertionError {
        ArrayList<Object[]> data = new ArrayList<>();
        File folder = new File("src/main/resources/generic");//path to file location in you project
        assertThat("File not found", folder.listFiles(), arrayWithSize(greaterThanOrEqualTo(1)));
        CSVReader csvReader = new CSVReader(new FileReader(folder.listFiles()[0]), ",".charAt(0), "\"".charAt(0), 1);
        List<String[]> content = csvReader.readAll();
        csvReader.close();
        for (String[] row : content) {
            if (row[ColumnNumber + 6].contains("print") && row[ColumnNumber + 12].contains("No data")) {
                data.add(new Object[]{row[ColumnNumber + 14], row[ColumnNumber]});//just example. You can add to object any data from csv current line
            }
        }
        System.out.println(data.size() + " records with No data for print was found ");
        return data;

    }
}
