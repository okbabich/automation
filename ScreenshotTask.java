package skillup;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.LibraryUtils;

import java.util.ArrayList;
import java.util.List;

public class ScreenshotTask extends Utils {

    @Test

    public void takeScreenshot() throws InterruptedException {

        List<Object[]> urls = new ArrayList<>();

        urls.add(new Object[]{"https://www.amazon.co.uk", "amazon.uk"});
        urls.add(new Object[]{"https://www.amazon.com/", "amazon"});
        urls.add(new Object[]{"https://www.bncollege.com/campus-stores/", "bn"});
        urls.add(new Object[]{"https://www.mheducation.com/home.html", "mhe"});

        for (Object[] urlWithName : urls) {
            driver.get(urlWithName[0].toString());
            WebElement careersText = driver.findElement(By.xpath("//a[contains(text(),'Careers')]"));
            LibraryUtils.waitForElementToBeVisible(driver, careersText, 15);
            ScreenshotTaker.captureScreenshot(driver, urlWithName[1].toString());
        }
    }
}











