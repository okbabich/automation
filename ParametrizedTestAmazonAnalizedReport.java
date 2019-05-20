package skillup;

import au.com.bytecode.opencsv.CSVReader;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.qatools.allure.annotations.Parameter;
import utils.Concurrent;
import utils.ConcurrentJunitRunner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static skillup.Utils.*;
import static skillup.Xpathes.*;


@Concurrent(threads = 1)//count of threads
@RunWith(ConcurrentJunitRunner.class)


public class ParametrizedTestAmazonAnalizedReport {

    public static WebDriver driver;

    @Parameter("URL")
    private static String URL;

    @Parameter("ISBN13")
    private static String ISBN13;

    @Parameterized.Parameters
    public static Collection<Object[]> params() throws Exception {
        return getDataFromTheCsvFile(0);//method to collect parameters. As a rule located in some other class
    }

    public ParametrizedTestAmazonAnalizedReport(String URL, String ISBN13) {
        this.URL = URL;
        this.ISBN13 = ISBN13;
    }

    @BeforeClass
    public static void initializationDriver() {
        driver = initTheSameDriver();
    }

    @Test
    public void amazonPageTest() throws IOException {
        System.out.println(URL);
        driver.get(URL);
        List<WebElement> items = driver.findElements(By.xpath(XPATH_FOR_PRICES_BOX_AMAZON));

        if (items.size() > 0) {
            getAndCompareIsbn(XPATH_TO_GET_ISBN13_AMAZON_NEW_DESIGN, ISBN13);
            checkPriceFromThePage(XPATH_TO_GET_BUY_NEW_PRICE_AMAZON_NEW_DESIGN);
        } else {
            getAndCompareIsbn(XPATH_TO_GET_ISBN13_AMAZON_OLD_DESIGN, ISBN13);
            checkPriceFromThePage(XPATH_TO_GET_BUY_NEW_AMAZON_OLD_DESIGN);
        }
    }

//    public void checkPriceFromThePage(String xpathGetPrice) {
//        List<WebElement> buyNewPriceBox = driver.findElements(By.xpath(xpathGetPrice));
//
//        if(driver.findElements(By.xpath(XPATH_CURRENTLY_UNAVAILABLE)).size() > 0 ){
//            System.out.println("Buy New price is absent");
//        }
//        else if(buyNewPriceBox.size() > 0 ) {
//            System.out.println("Buy New price exists - " + buyNewPriceBox.get(0).getText());
//            assertFalse(buyNewPriceBox.get(0).getText().contains("$"));
//        }
//        else {
//            System.out.println("Buy New price is absent");
//            assertTrue(true);
//        }
//    }


//    public void getAndCompareIsbn(String xpathGetIsbn) {
//        String IsbnFromThePage = driver.findElement(By.xpath(xpathGetIsbn)).getText()
//                .replace("-","")
//                .replace("ISBN: ","");
//        assertEquals(ISBN13, IsbnFromThePage);
//        System.out.println(IsbnFromThePage);
//    }

    @AfterClass
    public static void stopDriver() {
        closeDriver();
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
        assertThat("File not found", folder.listFiles(), arrayWithSize(greaterThanOrEqualTo(1))); // проверяем, что папка не пустая
        CSVReader csvReader = new CSVReader(new FileReader(folder.listFiles()[0]), ",".charAt(0), "\"".charAt(0), 1); // в new FileReader() может быть указан цсв файл

        //.charAt() возвращает символ, расположенный по указанному индексу строки
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
