package skillup;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static skillup.Utils.*;



/*На странице http://www.mypearsonstore.com/bookstore/introductory-statistics-plus-mylab-statistics-with-9780135229996 найти все предложения (правая колонка)
 и вывести в консольку тайтл тех книг у кого исбн начинается на 9780 и цена покупки меньше или равна 200
        Порядок действий:
        а) найти все предложения и записать их в список (List<WebElement>)
        б) найти в этом списке те предложения, которые удовлетворяют условию и вывести в консоль
*/


public class PearsonShowBookTitleTwoConditions{

    @BeforeClass
    public static void setUpClass() {
        initDriver();
        driver.get("http://www.mypearsonstore.com/bookstore/introductory-statistics-plus-mylab-statistics-with-9780135229996");
    }

    @Test
 public void findBooks() {

        List<WebElement> items = driver.findElements(By.xpath("//div[@id='genericChoice']"));

        for (WebElement element : items) {
            String isbn = element.findElement(By.className("isbn13")).getText();
            String price = element.findElement(By.className("price")).getText();
            Double priceNumber = Double.valueOf(price.substring(1));
            if (isMatched(priceNumber, isbn)) {
                String title = element.findElement(By.className("title")).getText();
                System.out.println(title);
            }
        }
    }

    private boolean isMatched(Double priceNumber, String isbn) {
        return (priceNumber <= 200.00) && (isbn.substring(9).startsWith("978-0"));
    }

    @AfterClass
    public static void tearDownClass(){
        closeDriver();
    }
}


