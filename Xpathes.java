package skillup;

public class Xpathes {

    public static final String XPATH_TO_GET_ISBN13_AMAZON_NEW_DESIGN = "//div[@class='a-row'][1]//span[@class='a-size-base a-color-base']";
    public static final String XPATH_TO_GET_ISBN13_AMAZON_OLD_DESIGN = "//*[@id='feature-bullets']//span[contains (text(), 'ISBN')]";
    public static final String XPATH_TO_GET_BUY_NEW_PRICE_AMAZON_NEW_DESIGN = "//div[@id='newOfferAccordionRow']//span[@class='a-size-medium a-color-price header-price']";
    public static final String XPATH_TO_GET_BUY_NEW_AMAZON_OLD_DESIGN = "//div[@id='desktop_unifiedPrice']";
    public static final String XPATH_CURRENTLY_UNAVAILABLE_AMAZON = "//*[@id='availability']/span[contains(text(),'Currently unavailable')]";
    public static final String XPATH_FOR_PRICES_BOX_AMAZON = "//div[@id='ppdFixedGridRightColumn']";

    public static final String XPATH_TO_GET_ISBN_MHP = "//div[@class='prod-head-attr-data']//li[@class='even item'][2]";
    public static final String XPATH_TO_GET_PRICE_MHP = "//div[@class='price-info price-default']//span[@class='price']";
}
