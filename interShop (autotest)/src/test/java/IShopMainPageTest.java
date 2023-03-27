import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IShopMainPageTest
{
    private WebDriver driver;
    private WebDriverWait wait;
    private String path = "http://intershop5.skillbox.ru/";

    private String contactPhone = "+7-999-123-12-12";
    private String contactEmail = "skillbox@skillbox.ru";

    private String consumerName = "Alexey";
    private String consumerEmail = "test@mail.com";
    private String consumerPassword = "Korovka-2022";


    @Before
    public void setUp()
    {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        //var sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        //FileUtils.copyFile(sourceFile, new File("C:\\Users\\Alex\\Desktop\\Skillbox\\Screenshots\\screenshot.png"));
        driver.quit();
    }

    //объявляем переменные локаторов
    //headerElements
    private By headerLinkLogoLocator = By.xpath("//*[@id='site-branding']//a[1]");
    private By headerPhoneLocator = By.xpath("//i[contains(@class, 'fa-phone')]/parent::a");
    private By headerEmailLocator = By.xpath("//i[contains(@class, 'fa-envelope')]/parent::a");
    private By searchFieldLocator = By.xpath("//input[@class='search-field']");
    private By buttonSearchSubmitLocator = By.xpath("//button[@class='searchsubmit']");
    private By buttonLoginLocator = By.xpath("//a[@class='account']");
    //navMenuElements
    private By navMainButtonLocator = By.xpath("//*[@id='menu-primary-menu']/li/a[contains(text(), 'Главная')]");
    private By navCatalogButtonLocator = By.xpath("//*[@id='menu-primary-menu']/li/a[contains(text(), 'Каталог')]");
    private By navAccountButtonLocator = By.xpath("//*[@id='menu-primary-menu']/li/a[contains(text(), 'аккаунт')]");
    private By navBasketButtonLocator = By.xpath("//*[@id='menu-primary-menu']/li/a[contains(text(), 'Корзина')]");
    private By navMakingOrderButtonLocator = By.xpath("//*[@id='menu-primary-menu']/li/a[contains(text(), 'заказ')]");
    //footerElements
    private By footerLocator = By.cssSelector("*#top-footer");
    private By footerPhoneLocator = By.xpath("//*[contains(text(), 'Телефон')]//ancestor::*[@class='text-5-value']");
    private By footerEmailLocator = By.xpath("//*[contains(text(), 'Email')]//ancestor::*[@class='text-5-value']");
    //promoSectionElements
    private By promoBlocksLocator = By.xpath("//section[@id='promo-section1']//aside");
    private By promoBlockTitleLocator = By.xpath("//section[@id='promo-section1']//aside//h4");
    //saleBlockElements
    private By saleListLocator = By.xpath("//*[@id='accesspress_store_product-2']//ul[contains(@class, 'new-prod-slide')]");
    private By saleItemLocator = By.xpath("//*[@id='accesspress_store_product-2']//li[contains(@class, 'span3')]");
    private By labelOnSaleLocator = By.xpath("//*[@id='accesspress_store_product-2']//li[contains(@class, 'span3')]//span[@class='onsale']");
    //newProductsElements
    private By newProductsItemLocator = By.xpath("//*[@id='product2']//li");
    private By newProductsLabelLocator = By.xpath("//span[@class='label-new']");
    //viewedProductsElements
    private By viewedProductsItemsLocator = By.xpath("//*[contains(@class, 'ap-cat-list')]//li");
    private By viewedProductsItemTitleLocator = By.xpath("//*[contains(@class, 'ap-cat-list')]//li//span[@class='product-title']");
    //myAccountElements
    private By authNameEmailFieldLocator = By.xpath("//input[@id='username']");
    private By authPasswordFieldLocator = By.xpath("//input[@id='password']");
    private By authButtonLoginLocator = By.xpath("//button[@name='login']");


    //проверяем контактные данные в хедере и футуре сайта
    @Test
    public void checkContactInformation()
    {
        ///arrange
        driver.navigate().to(path);
        var headerPhone = driver.findElement(headerPhoneLocator);
        var headerEmail = driver.findElement(headerEmailLocator);
        var footer = driver.findElement(footerLocator);
        var footerPhone = driver.findElement(footerPhoneLocator);
        var footerEmail = driver.findElement(footerEmailLocator);

        //act
        Actions moveToFooter = new Actions(driver);
        moveToFooter.moveToElement(footer);
        moveToFooter.perform();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(footerPhoneLocator, contactPhone));

        //assert
        Assert.assertEquals("Номер телефона в хидере указан неверно", contactPhone, headerPhone.getText());
        Assert.assertEquals("email в хидере указан неверно", contactEmail, headerEmail.getText());
        Assert.assertTrue("телефон в футере указан неверно", footerPhone.getText().contains(contactPhone));
        Assert.assertTrue("email в футере указан неверно", footerEmail.getText().contains(contactEmail));
    }

    //проверяем отображение имени пользователя в хидере после авторизации
    @Test
    public void checkNameConsumerInHeaderAfterAuthorization()
    {
        //arrange
        driver.navigate().to(path);
        var buttonLogin = driver.findElement(buttonLoginLocator);

        //act
        buttonLogin.click();

        var authorizationNameEmailField = driver.findElement(authNameEmailFieldLocator);
        authorizationNameEmailField.sendKeys(consumerEmail);
        var authPasswordField = driver.findElement(authPasswordFieldLocator);
        authPasswordField.sendKeys(consumerPassword);
        var authButtonLogin = driver.findElement(authButtonLoginLocator);
        authButtonLogin.click();

        //assert
        String nameInInformationBlock = driver.findElement(By.xpath("//*[@class='woocommerce-MyAccount-content']//strong")).getText();
        String nameInHeader = driver.findElement(By.xpath("//span[@class='user-name']")).getText();
        Assert.assertEquals("имя отображаемое в блоке информации личного кабинета не совпадает с именем пользователя", consumerName, nameInInformationBlock);
        var linkLogo = driver.findElement(headerLinkLogoLocator);
        linkLogo.click();
        Assert.assertEquals("имя отображаемое в хидере не совпадает с именем пользователя", consumerName, nameInHeader);
    }

    //тестируем поле поиска товара по введённому значению
    @Test
    public void testSearchProductField()
    {
        //arrange
        driver.navigate().to(path);
        String searchExample = "ХОЛОДИЛ";
        var searchField = driver.findElement(searchFieldLocator);
        var buttonSearchSubmit = driver.findElement(buttonSearchSubmitLocator);

        //act
        searchField.sendKeys(searchExample);
        buttonSearchSubmit.click();
        searchExample = searchExample.toLowerCase(Locale.ROOT);
        String resultPageTitle = driver.findElement(By.xpath("//*[@id='content']//h1")).getText().toLowerCase(
            Locale.ROOT);
        String resultSpan = driver.findElement(By.xpath("//*[@id='title_bread_wrap']//span")).getText().toLowerCase(
            Locale.ROOT);
        var resultItemsTitle = driver.findElements(By.xpath("//*[@class='wc-products']//li//h3"));

        //assert
        Assert.assertTrue("параметр поиска не отображается в заголовке страницы с результатом поиска", resultPageTitle.contains(searchExample));
        Assert.assertTrue("параметр поиска не отображается в пути по категориям", resultSpan.contains(searchExample));

        String finalSearchExample = searchExample;
        resultItemsTitle.forEach(r -> {
            Assert.assertTrue("параметр поиска не отображается в заголовке карточки найденного товара", r.getText().toLowerCase(Locale.ROOT).contains(finalSearchExample));
        });
    }

    //переход в каталог и обратно на главную по кнопкам меню навигации
    @Test
    public void clickCatalogButtonThenClickMainButton()
    {
        //arrange
        driver.navigate().to(path);
        var catalogButton = driver.findElement(navCatalogButtonLocator);
        String catalogUrl = "http://intershop5.skillbox.ru/product-category/catalog/";
        String catalogTitle = "каталог";

        //assert
        catalogButton.click();
        Assert.assertEquals("url открываемой стнаницы не соответствует url каталога", catalogUrl, driver.getCurrentUrl());
        var pageTitle = driver.findElement(By.xpath("//*[@id='content']//h1")).getText();
        Assert.assertEquals("заголовок страницы не соответствует ожидаемому", catalogTitle, pageTitle.toLowerCase(
            Locale.ROOT));

        var mainButton = driver.findElement(navMainButtonLocator);
        mainButton.click();
        Assert.assertEquals("клик по кнопке ГЛАВНАЯ не ведёт на ожидаемый url", path, driver.getCurrentUrl());
    }

    //переход на страницу авторизации по кнопке меню навигации
    @Test
    public void clickMyAccountButton()
    {
        //arrange
        driver.navigate().to(path);
        var accountButton = driver.findElement(navAccountButtonLocator);
        String authPageUrl = "http://intershop5.skillbox.ru/my-account/";
        String authPageTitle = "мой аккаунт";

        //assert
        accountButton.click();
        Assert.assertEquals("url открываемой стнаницы не соответствует url страницы авторизации", authPageUrl, driver.getCurrentUrl());
        var pageTitle = driver.findElement(By.xpath("//*[@id='content']//h2[@class='post-title']")).getText().toLowerCase(
            Locale.ROOT);
        Assert.assertEquals("заголовок страницы не соответствует ожидаемому", authPageTitle, pageTitle);
    }

    //переход на страницу корзины по кнопке меню навигации
    @Test
    public void clickBasketButton()
    {
        //arrange
        driver.navigate().to(path);
        var basketButton = driver.findElement(navBasketButtonLocator);
        String basketPageUrl = "http://intershop5.skillbox.ru/cart/";
        String basketTitle = "корзина";

        //assert
        basketButton.click();
        Assert.assertEquals("url открываемой страницы не соответствует url корзины", basketPageUrl, driver.getCurrentUrl());
        var pageTitle = driver.findElement(By.xpath("//*[@id='accesspress-breadcrumb']//span[contains(text(), 'Корзина')]")).getText().toLowerCase(
            Locale.ROOT);
        Assert.assertEquals("заголовок страницы не соответствует ожидаемому", basketTitle, pageTitle);
    }

    //переход на страницу оформления заказа по кнопке меню навигации
    @Test
    public void clickMakingOrderButton()
    {
        //arrange
        driver.navigate().to(path);
        var catalogButton = driver.findElement(navCatalogButtonLocator);

        String makingOrderPageUrl = "http://intershop5.skillbox.ru/checkout/";
        String makingOrderTitle = "оформление заказа";

        //act
        catalogButton.click();
        driver.findElement(By.xpath("//*[contains(text(), 'В корзину')]")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//a[contains(@class, 'loading')]")));
        var makingOrderButton = driver.findElement(navMakingOrderButtonLocator);
        makingOrderButton.click();

        //assert
        Assert.assertEquals("url открываемой страницы не соответствует url страницы оформления заказа", makingOrderPageUrl, driver.getCurrentUrl());
        var pageTitle = driver.findElement(By.xpath("//*[@id='primary']//h2[contains(text(), 'заказ')]")).getText().toLowerCase(
            Locale.ROOT);
        Assert.assertEquals("заголовок страницы не соответствуе ожидаемому", makingOrderTitle, pageTitle);
    }

    //тестируем блок промо - клик по карточке КНИГИ приводит в категорию товаров КНИГИ и тд
    @Test
    public void clickOnPromoBlockAndCheckPage()
    {
        //arrange
        driver.navigate().to(path);

        //act
        var promoBlocks = driver.findElements(promoBlocksLocator);
        var promoBlockTitle = driver.findElement(promoBlockTitleLocator);
        AtomicInteger i = new AtomicInteger();

        //assert
        promoBlocks.forEach(p -> {
            wait.until(ExpectedConditions.visibilityOf(promoBlockTitle));
            String blockTitle = p.findElements(By.xpath("//h4")).get(i.get()).getText();
            i.getAndIncrement();
            p.click();
            String pageTitle = driver.findElement(By.xpath("//*[@id='content']//h1")).getText();
            Assert.assertEquals("отличаются названия карточки в блоке ПРОМО и название раздела, к которому та карточка ведёт", blockTitle, pageTitle);
            driver.navigate().back();
        });
    }

    //проверяем, чтоб у всех карточек товаров категории РАСПРОДАЖА был лейбл лейбл СКИДКА!
    @Test
    public void labelOnSaleForEachItem()
    {
        //arrange
        driver.navigate().to(path);
        var saleList = driver.findElement(saleListLocator);
        var saleItem = driver.findElements(saleItemLocator);
        var labelOnSale = driver.findElements(labelOnSaleLocator);

        //act
        Actions moveToSaleBlock = new Actions(driver);
        moveToSaleBlock.moveToElement(saleList);
        moveToSaleBlock.perform();

        //assert
        Assert.assertEquals("не у всех карточек блока РАСПРОДАЖА есть лейбл СКИДКА", saleItem.size(), labelOnSale.size());
    }

    //проверяем, чтоб у всех карточек товаров категории НОВЫЕ ПОСТУПЛЕНИЯ был лейбл НОВЫЙ!
    @Test
    public void labelNewInProduct2SectionForEachItem()
    {
        //arrange
        driver.navigate().to(path);
        var newProductsItems = driver.findElements(newProductsItemLocator);
        var newProductsLabels = driver.findElements(newProductsLabelLocator);
        var newProductsSection = driver.findElement(By.xpath("//section[@id='product2']"));

        //act
        Actions moveToSaleBlock = new Actions(driver);
        moveToSaleBlock.moveToElement(newProductsSection);
        moveToSaleBlock.perform();

        //assert
        Assert.assertEquals("не у всех карточек НОВЫЕ ПОСТУПЛЕНИЯ есть лейбл НОВЫЙ!", newProductsLabels.size(), newProductsItems.size());
    }

    //проверяем, что после просмотра карточки товара, она появляется в разделе ПРОСМОТРЕННЫЕ внизу главной страницы сайта
    @Test
    public void viewedProductsTest()
    {
        //arrange
        driver.navigate().to(path);
        var saleList = driver.findElement(saleListLocator);

        //act
        Actions moveToSaleBlock = new Actions(driver);
        moveToSaleBlock.moveToElement(saleList);
        moveToSaleBlock.perform();

        var randomItem = driver.findElement(By.xpath("//*[@id='accesspress_store_product-2']//li[contains(@class, 'span3')][6]"));
        wait.until(ExpectedConditions.visibilityOf(randomItem));
        var randomItemNameElement = driver.findElement(By.xpath("//*[@id='accesspress_store_product-2']//li[contains(@class, 'span3')][6]//h3"));
        String secondItemName = randomItemNameElement.getText();

        randomItem.click();

        var linkLogo = driver.findElement(headerLinkLogoLocator);
        linkLogo.click();

        driver.navigate().refresh();

        Actions moveToViewedProductsBlock = new Actions(driver);
        moveToViewedProductsBlock.moveToElement(driver.findElement(By.xpath("//*[contains(@class, 'ap-cat-list')]")));
        moveToViewedProductsBlock.perform();

        var viewedProductsItems = driver.findElements(viewedProductsItemsLocator);
        var lastViewedProduct = viewedProductsItems.get(0);
        wait.until(ExpectedConditions.visibilityOf(lastViewedProduct));
        String lastViewedProductTitle = lastViewedProduct.findElement(By.xpath("//span[@class='product-title']")).getText();

        //assert
        Assert.assertEquals("", secondItemName.toLowerCase(Locale.ROOT), lastViewedProductTitle.toLowerCase(
            Locale.ROOT));
    }










































}
