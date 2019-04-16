package skillup;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/*На странице http://www.mypearsonstore.com/bookstore/introductory-statistics-plus-mylab-statistics-with-9780135229996 найти все предложения (правая колонка)
 и вывести в консольку тайтл тех книг у кого исбн начинается на 9780 и цена покупки меньше или равна 200
        Порядок действий:
        а) найти все предложения и записать их в список (List<WebElement>)
        б) найти в этом списке те предложения, которые удовлетворяют условию и вывести в консоль
*/


public class PearsonShowBookTitleTwoConditions extends Utils {
    @Test
 public void findBooks() {
        driver.get("http://www.mypearsonstore.com/bookstore/introductory-statistics-plus-mylab-statistics-with-9780135229996");

        List<WebElement> items = driver.findElements(By.xpath("//div[@id='genericChoice']"));

        for (WebElement element : items) {
            String isbn = element.findElement(By.className("isbn13")).getText();
            String price = element.findElement(By.className("price")).getText();
            Double priceNumber = Double.valueOf(price.substring(1));
            String title = element.findElement(By.className("title")).getText();
            if (isMatched(priceNumber, isbn)) {
                System.out.println(title);
            }
        }
    }

    private boolean isMatched(Double priceNumber, String isbn) {
        return (priceNumber <= 200.00) && (isbn.substring(9).startsWith("978-0"));
    }
}








//        String digitalXpath =  "";
//        String digPrintXpath =  "";
//        String printXpath = "";
//        List<WebElement> blocks = driver.findElements(By.xpath(digitalXpath+i));
//
//        List<>
//        for(WebElement element : blocks) {
//            List<Object[]> items = new ArrayList<>();
//            String isbn = driver.findElement(By.xpath("//span[@class='isbn13']")).getText();
//            //String price = driver.findElement(By.xpath("//span[@class='isbn13']")).getText();
//           // Double priceNumber = Double.valueOf(price.substring(1));
//            String title = driver.findElement(By.xpath("//p[@class='title']")).getText();
//            items.add(new Object[]{isbn});
//            //items.add(new Object[]{priceNumber});
//            items.add(new Object[]{title});
//            System.out.println(items);
//        }
//


