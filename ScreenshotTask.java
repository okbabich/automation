package skillup;

import org.junit.Test;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class ScreenshotTask extends Utils {


    @Test

    public void takeScreenshot() {

        List<Object[]> urls = new ArrayList<>();

        urls.add(new Object[]{"https://www.amazon.co.uk", "amazon.uk"});
        urls.add(new Object[]{"https://www.amazon.com/", "amazon"});
        urls.add(new Object[]{"https://www.bncollege.com/campus-stores/", "bn"});
        urls.add(new Object[]{"https://www.mheducation.com/home.html", "mhe"});

        for (int i = 0; i < urls.size(); i++) {
            driver.get(urls.get(i)[0].toString());
            pageIsLoaded();
            ScreenshotTaker.captureScreenshot(driver, urls.get(i)[1].toString());
        }
    }

    public void pageIsLoaded() {

        driver.findElement(By.xpath("//a[contains(text(),'Careers')]"));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}








