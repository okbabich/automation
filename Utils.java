package skillup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static skillup.Xpathes.*;


public class Utils {

    public static WebDriver driver;


    public static void initDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Chrome Driver\\chromedriver.exe");
        //System.setProperty(ChromeDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");       //supress logs with warning
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public static WebDriver initTheSameDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Chrome Driver\\chromedriver.exe");
        //System.setProperty(ChromeDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");       //supress logs with warning
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return  driver;
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
    public static void getAndCompareIsbn(String xpathGetIsbn, String ISBN13) {
        String IsbnFromThePage = driver.findElement(By.xpath(xpathGetIsbn)).getText()
                .replace("-","")
                .replace("ISBN: ","")
                .replace("ISBN:","");
        assertEquals(ISBN13, IsbnFromThePage);
        System.out.println(IsbnFromThePage);
    }

    public static void checkPriceFromThePage(String xpathGetPrice) {
        List<WebElement> buyNewPriceBox = driver.findElements(By.xpath(xpathGetPrice));

        if(driver.findElements(By.xpath(XPATH_CURRENTLY_UNAVAILABLE_AMAZON)).size() > 0 ){
            System.out.println("Buy New price is absent");
        }
        else if(buyNewPriceBox.size() > 0 ) {
            System.out.println("Buy New price exists - " + buyNewPriceBox.get(0).getText());
            assertFalse(buyNewPriceBox.get(0).getText().contains("$"));
        }
        else {
            System.out.println("Buy New price is absent");
            assertTrue(true);
        }
    }
    public static void getAndComparePrice(String xpathGetPrice, String expectedPrice){
        String actualPrice = driver.findElement(By.xpath(xpathGetPrice)).getText()
                .replace("$","");
        assertEquals(expectedPrice, actualPrice);
        System.out.println("Parsed price is matched with website");
    }
}