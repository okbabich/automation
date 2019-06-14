package skillup;

public class Xpathes {

    public static final String XPATH_TO_GET_ISBN13_AMAZON_NEW_DESIGN = "//div[@class='a-row'][1]//span[@class='a-size-base a-color-base']";
    public static final String XPATH_TO_GET_ISBN13_AMAZON_OLD_DESIGN = "//*[@id='feature-bullets']//span[contains (text(), 'ISBN')]";
    public static final String XPATH_TO_GET_BUY_NEW_PRICE_AMAZON_NEW_DESIGN = "//div[@id='newOfferAccordionRow']//span[@class='a-size-medium a-color-price header-price']";
    public static final String XPATH_TO_GET_BUY_NEW_AMAZON_OLD_DESIGN = "//div[@id='desktop_unifiedPrice']";
    public static final String XPATH_CURRENTLY_UNAVAILABLE_AMAZON = "//*[@id='availability']/span[contains(text(),'Currently unavailable')]";
    public static final String XPATH_FOR_PRICES_BOX_AMAZON = "//div[@id='ppdFixedGridRightColumn']";

    public static final String XPATH_TO_GET_ISBN_MHP = "//div[@class='prod-head-attr-data']//li[4]//span";
    public static final String XPATH_TO_GET_PRICE_MHP = "//div[@class='price-box']//span[@class='regular-price']//span[@class='price']";
    public static final String XPATH_TO_GET_ISBN_MHP_ANOTHER_TAB = "//*[@id='cti-config-variants-145']//li[2]//label";

    public static final String XPATH_SEARCH_FIELD_MHE = "//*[@id='higherEdQuery']";
    public static final String XPATH_SEARCH_BUTTON_MHE = "//button[@id='submit_search']";
    public static final String XPATH_NO_RESULTS_MHE = "//div[@id='no-result-count']";

    public static final String XPATH_SEARCH_BUTTON_CENGAGE = "//button[@id='headerSubmitSearch']";

}
