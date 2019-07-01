package skillup;

/*#кликатьНеПерекликать - взять один ПОЛНЫЙ ccs с BN (свежий тег + убедится что у этого ccs есть книги) ,
прокликать его, начиная со страницы https://www.bncollege.com/campus-stores/   со страницы результатов вывести в консоль все исбны что там есть*/

import org.junit.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.LibraryUtils;

import java.util.List;

import static skillup.Utils.*;
import static skillup.Xpathes.*;


public class BarnesNobleFindIsbnsByFullCcs {

    private String definiteCampus = "Barnes & Noble @ Quincy College";
    private String definiteTerm = "Fall 2019";
    private String definiteDept = "ACC";
    private String definiteCourse = "101";
    private String definiteSection = "01";

    @BeforeClass
    public static void setUpClass() {

        String proxy = "34.222.39.93:3128";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--proxy-server=http://" + proxy);
        System.setProperty("webdriver.chrome.driver", "C:\\ChrDriver\\chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.get("https://www.bncollege.com/campus-stores/");
    }

    @Test
    public void goByFullCcs() {

        pageRefresher(XPATH_FOR_REFRESHER_BN);

        WebElement searchBox = driver.findElement(By.xpath(XPATH_SUBMIT_BUTTON_BN));
        LibraryUtils.waitForElementToBeVisible(driver, searchBox, 10);

        driver.findElement(By.linkText("Quincy College")).click();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }

        WebElement textbooks = driver.findElement(By.xpath(XPATH_TEXTBOOK_FIELD_BN));
        LibraryUtils.waitForElementToBeClickable(driver, textbooks, 10).click();


        WebElement campusBox = driver.findElement(By.xpath(XPATH_CAMPUS_BOX_BN));
        LibraryUtils.waitForElementToBeClickable(driver, campusBox, 10).click();
        List<WebElement> campuses = campusBox.findElements(By.xpath(XPATH_CAMPUSES_BN));
        for (WebElement option : campuses) {
            if (option.getText().equals(definiteCampus)) {
                option.click();
            }
        }

        WebElement termBox = driver.findElement(By.xpath(XPATH_TERM_BOX_BN));
        LibraryUtils.waitForElementToBeClickable(driver, termBox, 10).click();
        List<WebElement> terms = termBox.findElements(By.xpath(XPATH_TERMS_BN));
        for (WebElement option : terms) {
            if (option.getText().equals(definiteTerm)) {
                option.click();
            }
        }

        WebElement departmentBox = driver.findElement(By.xpath(XPATH_DEPARTMENT_BOX_BN));
        LibraryUtils.waitForElementToBeClickable(driver, departmentBox, 10).click();
        List<WebElement> departments = departmentBox.findElements(By.xpath(XPATH_RESULTS_BN));
        for (WebElement option : departments) {
            if (option.getText().equals(definiteDept)) {
                option.click();
                break;
            }
        }

        WebElement courseBox = driver.findElement(By.xpath(XPATH_COURSE_BOX_BN));
        LibraryUtils.waitForElementToBeClickable(driver, courseBox, 10).click();
        List<WebElement> courses = courseBox.findElements(By.xpath(XPATH_RESULTS_BN));
        for (WebElement option : courses) {
            if (option.getText().equals(definiteCourse)) {
                option.click();
                break;
            }
        }

        WebElement sectionBox = driver.findElement(By.xpath(XPATH_SECTION_BOX_BN));
        LibraryUtils.waitForElementToBeClickable(driver, sectionBox, 10).click();
        List<WebElement> sections = sectionBox.findElements(By.xpath(XPATH_RESULTS_BN));
        for (WebElement option : sections) {
            if (option.getText().equals(definiteSection)) {
                option.click();
                break;
            }
        }

        WebElement button = driver.findElement(By.xpath(XPATH_BUTTON_SHOW_RESULTS_BN));
        LibraryUtils.waitForElementToBeClickable(driver, button, 10).click();

        WebElement booksSection = driver.findElement(By.xpath(XPATH_ALL_BOOK_SECTION_BN));
        LibraryUtils.waitForElementToBeVisible(driver, booksSection, 10);
        List<WebElement> booksList = booksSection.findElements(By.xpath(XPATH_BOOK_LIST_BN));

        for (WebElement isbns : booksList) {
            String isbn13 = isbns.findElement(By.xpath(XPATH_TO_GET_ISBN_BN)).getText();
            isbn13 = isbn13.replace("ISBN:   ", "");
            System.out.println(isbn13);
        }
    }
    @AfterClass
    public static void tearDownClass() {
        closeDriver();
    }
}







