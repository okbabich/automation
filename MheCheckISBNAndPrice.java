package skillup;

//Перейти на страницу - https://www.mheducation.com/highered/product/1260110710.html, проверить что на этой странице есть ISBN 9781260110715 и проверить что цена для него равна 185


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.junit.Assert.*;
import static skillup.Utils.*;

public class MheCheckISBNAndPrice{

    private String isbn10 = "1260110710";

    @BeforeClass
    public static void setUpClass() {
        initDriver();
        driver.get("https://www.mheducation.com/highered/product/1260110710.html");
    }

    @Test
    public void checkingBook() {
        int count = driver.findElements(By.xpath("//div[@class='pdp-card col-xs-12 col-sm-6 col-md-4']")).size();
        if (count == 1) {
            verifyISBN();
            checkingPrice();
        } else if (count > 1) {
            System.out.println("There is more, than 1 book");
        } else {
            System.out.println("There is no books");
        }
    }

    public void verifyISBN() {
        String expectedISBN = "9781260110715";
        String actualString = driver.findElement(By.xpath("//div[@id='product-card-" + isbn10 + "']")).getText();
        assertTrue(actualString.contains(expectedISBN));
    }

    public void checkingPrice() {
        String expectedPrice = "$185.00";
        String actualPrice = driver.findElement(By.xpath("//div[@id='product-card-" + isbn10 + "']//span[@class='buying-card-price']")).getText();
        System.out.println(actualPrice);
        assertTrue(actualPrice.equals(expectedPrice));
    }

    @AfterClass
    public static void tearDownClass(){
        closeDriver();
    }
}