package skillup;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;


public class ScreenshotTaker {


    public static void captureScreenshot(WebDriver driver, String screenshotName) {

        try {

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File("D:\\NewProject\\MyNewProject\\Screenshots\\" + screenshotName + ".jpg"));

        } catch (Exception e) {
            System.out.println("Exception while taking the screenshot" + e.getMessage());
        }
    }
}