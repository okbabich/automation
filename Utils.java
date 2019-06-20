package skillup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static skillup.Xpathes.*;


public class Utils {

    public Utils(WebDriver driver1) {
        this.driver1 = driver1;
    }

    public static WebDriver driver;
    public WebDriver driver1;

    public static void initDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChrDriver\\chromedriver.exe");
        //System.setProperty(ChromeDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");       //supress logs with warning
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public static WebDriver initTheSameDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChrDriver\\chromedriver.exe");
        //System.setProperty(ChromeDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");       //supress logs with warning
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    public WebDriver initRemoteWD() throws MalformedURLException {

        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setBrowserName("chrome");
        WebDriver driver1 = new RemoteWebDriver(new URL("http://10.10.83.231:4444/wd/hub"), capability);
        return driver1;
    }

    public void closeRemotWD() {
        if (driver != null)
            driver.quit();
    }

    public static void closeDriver() {
        if (driver != null)
            driver.quit();
    }

    public static void pageRefresher(String XPATH_FOR_REFRESHER) {
        int maxRefreshCount = 5;
        for (int actualCount = 0; actualCount < maxRefreshCount; actualCount++) {
            if (driver.findElements(By.xpath(XPATH_FOR_REFRESHER)).size() > 0) {
                driver.navigate().refresh();
            }
        }
    }

    public static void getAndCompareIsbn(String xpathGetIsbn, String ISBN13, WebDriver driver1) {
        String isbnFromThePage = driver1.findElement(By.xpath(xpathGetIsbn)).getText()
                .replace("-", "")
                .replace("ISBN: ", "")
                .replace("ISBN:", "");
        assertEquals(ISBN13, isbnFromThePage);
        System.out.println(isbnFromThePage);
    }

    public static void getAndCompareIsbnDifferentTabs(String xpathGetIsbn, String xpathGetIsbnAnotherTab,String isbn13FromTable, String urlFromTable, WebDriver driver1){
        String IsbnFromThePage = driver1.findElement(By.xpath(xpathGetIsbn)).getText();
        System.out.println(urlFromTable);

        if (isbn13FromTable.equals(IsbnFromThePage)) {
            System.out.println("MySQL data " + isbn13FromTable + " = site data (first tab) " + IsbnFromThePage);
        } else {
            driver1.findElement(By.xpath(xpathGetIsbnAnotherTab)).click();
            String IsbnFromThePageAnotherTab = driver1.findElement(By.xpath(xpathGetIsbn)).getText();

            if (isbn13FromTable.equals(IsbnFromThePageAnotherTab)) {
                System.out.println("MySQL data " + isbn13FromTable + " = site data (another tab) " + IsbnFromThePageAnotherTab);
            }
        }
    }

    public static void checkPriceFromThePage(String xpathGetPrice, WebDriver driver1) {
        List<WebElement> buyNewPriceBox = driver1.findElements(By.xpath(xpathGetPrice));

        if (driver1.findElements(By.xpath(XPATH_CURRENTLY_UNAVAILABLE_AMAZON)).size() > 0) {
            System.out.println("Buy New price is absent (old design)");
        } else if (buyNewPriceBox.size() > 0) {
            System.out.println("Buy New price exists - " + buyNewPriceBox.get(0).getText());
            assertFalse(buyNewPriceBox.get(0).getText().contains("$"));
        } else {
            System.out.println("Buy New price is absent");
            assertTrue(true);
        }
    }

    public static void getAndComparePrice(String xpathGetPrice, String priceFromTable, WebDriver driver1) {
        String actualPrice = driver1.findElement(By.xpath(xpathGetPrice)).getText()
                .replace("$", "");
        assertEquals(priceFromTable, actualPrice);
        System.out.println("Parsed price " + priceFromTable + " is matched with website " + actualPrice);
    }
    }

