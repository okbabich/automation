package skillup;

/*#кликатьНеПерекликать - взять один ПОЛНЫЙ ccs с BN (свежий тег + убедится что у этого ccs есть книги) ,
прокликать его, начиная со страницы https://www.bncollege.com/campus-stores/   со страницы результатов вывести в консоль все исбны что там есть*/

import org.junit.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import utils.LibraryUtils;

import java.util.List;


public class BarnesNobleFindIsbnsByFullCcs extends Utils {

    private String definiteCampus = "Barnes & Noble @ Quincy College";
    private String definiteTerm = "Spring 2019";
    private String definiteDept = "ACC";
    private String definiteCourse = "101";
    private String definiteSection = "01";



    @Test
    public void goByFullCcs() throws InterruptedException {

        driver.get("https://www.bncollege.com/campus-stores/");

        while (driver.findElements(By.xpath("//*[text()='This site can’t be reached']")).size() > 0) {
            driver.navigate().refresh();
        }

        WebElement searchBox = driver.findElement(By.xpath("//button[@type='submit']"));
        LibraryUtils.waitForElementToBeVisible(driver, searchBox, 10);

        driver.findElement(By.linkText("Quincy College")).click();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }

        WebElement textbooks = driver.findElement(By.xpath("//div[@class='mainNav']//li[@id='topCat_2']/a"));
        LibraryUtils.waitForElementToBeClickable(driver, textbooks, 10).click();


        WebElement campusBox = driver.findElement(By.xpath("//div[@class='campusContainer'][1]//div[@class='bncbSelectBox campusSectionHeader']"));
        LibraryUtils.waitForElementToBeClickable(driver, campusBox, 10).click();
        //campusBox.click();
        List<WebElement> campuses = campusBox.findElements(By.xpath("*//li[@class='bncbOptionItem']"));
        for (WebElement option : campuses) {
            if (option.getText().equals(definiteCampus)) {
                option.click();
            }
        }
        WebElement termBox = driver.findElement(By.xpath("//div[@class='bookRowContainer'][1]//div[@class='bncbSelectBox termHeader']"));
        LibraryUtils.waitForElementToBeClickable(driver, termBox, 10).click();
        List<WebElement> terms = termBox.findElements(By.xpath("*//li[@class='bncbOptionItem termOption']"));
        for (WebElement option : terms) {
            if (option.getText().equals(definiteTerm)) {
                option.click();
            }
        }

        WebElement departmentBox = driver.findElement(By.xpath("//div[@class='bookRowContainer'][1]//li[@class='deptColumn']"));
        LibraryUtils.waitForElementToBeClickable(driver, departmentBox, 10).click();
        List<WebElement> departments = departmentBox.findElements(By.xpath("*//li[@class='result']"));
        for (WebElement option : departments) {
            if (option.getText().equals(definiteDept)) {
                option.click();
                break;
            }
        }

        WebElement courseBox = driver.findElement(By.xpath("//div[@class='bookRowContainer activeStripeBorderBox'][1]//div[@class='courseBookSelector']//li[@class='courseColumn']"));
        LibraryUtils.waitForElementToBeClickable(driver, courseBox, 10).click();
        List<WebElement> courses = courseBox.findElements(By.xpath("*//li[@class='result']"));
        for (WebElement option : courses) {
            if (option.getText().equals(definiteCourse)) {
                option.click();
                break;
            }
        }

        WebElement sectionBox = driver.findElement(By.xpath("//div[@class='bookRowContainer activeStripeBorderBox'][1]//div[@class='courseBookSelector']//li[@class='sectionColumn']"));
        LibraryUtils.waitForElementToBeClickable(driver, sectionBox, 10).click();
        List<WebElement> sections = sectionBox.findElements(By.xpath("*//li[@class='result']"));
        for (WebElement option : sections) {
            if (option.getText().equals(definiteSection)) {
                option.click();
                break;
            }
        }

        WebElement button = driver.findElement(By.xpath("//div[@class='selectButtonContainer']//a[@id='findMaterialButton']"));
        LibraryUtils.waitForElementToBeClickable(driver, button, 10).click();

        WebElement booksSection = driver.findElement(By.xpath("//div[@class='book_sec']"));
        LibraryUtils.waitForElementToBeVisible(driver, booksSection, 10);
        List<WebElement> booksList = booksSection.findElements(By.xpath("*//div[@class='book-list']"));

        for (WebElement isbns : booksList) {
            String isbn13 = isbns.findElement(By.xpath("*//li[@class='book_c2_180616']")).getText();
            isbn13 = isbn13.replace("ISBN:   ","");
            System.out.println(isbn13);
        }
        }
    }






