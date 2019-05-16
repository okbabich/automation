package skillup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;


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
}