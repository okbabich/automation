package skillup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(value = Parameterized.class)
public class AmazonNotFoundTest extends Utils {

    private String isbn;

    public AmazonNotFoundTest(String isbn) {
        this.isbn = isbn;
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][]{
                {"9780357007891"},
                {"9780357011461"},
                {"9780357011508"},
                {"9780030119194"},
                {"9780357011553"},
                {"9780357011607"},
                {"9780357013601"}
        });
    }

    @Test
    public void findISBN() {

        driver.get("https://www.amazon.com/");
        driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).click();
        driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).clear();
        driver.findElement(By.xpath("//input[@type='text']")).sendKeys(isbn);
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        List<WebElement> noResultBlock = driver.findElements(By.xpath("//*[@id='search']/div[1]/div[2]/div/span[2]/div/div[1]")); //для того, чтобы тест не фейлился.
                                                                                                                                  //создается коллекция,
                                                                                                                                  //в которую складываются найденные блочки
        if (noResultBlock.size() > 0) {                                               //если в коллекции есть какой-то элемент, то
            assertTrue(noResultBlock.get(0).getText().contains("No results"));       //возьми этот элемент, текст в нем должен быть "No results"

        } else {                                                                   //если такого элемента нет,
            System.out.println("ISBN is present" + isbn);                         //выведи на экран, какой ISBN есть на сайте
            assertTrue(false);
        }

    }
}






