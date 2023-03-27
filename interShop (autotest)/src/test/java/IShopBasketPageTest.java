import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IShopBasketPageTest {

  private WebDriver driver;
  private WebDriverWait wait;
  private String path = "http://intershop5.skillbox.ru/";
  private String coupon = "sert500";
  private String checkoutPageUrl = "http://intershop5.skillbox.ru/checkout/";
  private String catalogUrl = "http://intershop5.skillbox.ru/shop/";

  @Before
  public void setUp()
  {
    System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, 5);
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.manage().window().maximize();
  }

  @After
  public void tearDown()
  {
    //var sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    //FileUtils.copyFile(sourceFile, new File("C:\\Users\\Alex\\Desktop\\Skillbox\\Screenshots\\screenshot.png"));
    driver.quit();
  }

  //объявляем переменные локаторов
  private By navCatalogLinkLocator = By.xpath("//*[@id='menu-primary-menu']//a[contains(text(), 'Каталог')]");
  private By categoryLocator = By.xpath("//*[@id='woocommerce_product_categories-2']//*[contains(text(), 'Холодил')]");
  private By firstProductLocator = By.xpath("//*[@id='primary']//li[1]");
  private By firstProductPriceLocator = By.xpath(".//span[@class='price']//bdi");
  private By saleProductLocator = By.xpath("//*[@id='primary']//span[@class='onsale']/ancestor::li");
  //private By saleProductPriceWithoutDiscountLocator = By.xpath("//*[@class='price']//del//bdi");
  private By saleProductPriceWithDiscountLocator = By.xpath("//*[@class='price']//ins//bdi");
  private By productTitleLocator = By.xpath(".//h3");
  private By buttonInBasketLocator = By.xpath(".//a[contains(text(), 'В корзину')]");
  private By buttonMoreLocator = By.xpath("//a[contains(text(), 'Подробнее')]");
  //basketElements
  private By removeProductLocator = By.xpath("//a[@class='remove']");
  private By getBackDeletedProductLinkLocator = By.xpath("//a[@class='restore-item']");
  private By tableRawLocator = By.xpath("//tr[contains(@class, 'cart-item')]");
  private By tableProductTitleLocator = By.xpath(".//td[@class='product-name']");
  private By tableProductPriceLocator = By.xpath(".//td[@class='product-price']");
  private By countProductsFieldLocator = By.xpath(".//input[contains(@id, 'quantity')]");
  private By cartUpdatedAlertLocator = By.xpath("//div[@role='alert']");
  private By subTotalPriceLocator = By.xpath(".//td[@class='product-subtotal']//bdi");
  private By couponFieldLocator = By.xpath("//input[@id='coupon_code']");
  private By couponButtonLocator = By.xpath("//button[@name='apply_coupon']");
  private By totalPriceLocator = By.xpath("//tr[@class='order-total']//bdi");
  private By placeAnOrderLinkLocator = By.xpath("//a[contains(@class, 'checkout-button')]");
  private By couponTableRawLocator = By.xpath("//tr[contains(@class, 'cart-discount')]");
  private By couponTableRawAmountLocator = By.xpath(".//span[contains(@class, 'amount')]");
  private By couponDeleteButtonLocator = By.xpath("//a[contains(@class, 'remove-coupon')]");
  private By couponDeletedAlertLocator = By.xpath("//*[contains(text(), 'Купон удален')]");
  private By wrongCouponAlertLocator = By.xpath("//li[contains(text(), 'Неверный купон')]");
  private By cartEmptyLocator = By.xpath("//*[contains(@class, 'cart-empty')]");
  private By backToCatalogLinkLocator = By.xpath("//*[@class='return-to-shop']//a");

  //проверяем название товара в каталоге и его же название в корзине
  @Test
  public void checkProductTitlesInBasket()
  {
    driver.navigate().to(path);

    var navCatalogLink = driver.findElement(navCatalogLinkLocator);
    navCatalogLink.click();

    var category = driver.findElement(categoryLocator);
    category.click();

    var firstProduct = driver.findElement(firstProductLocator);
    String productTitle = firstProduct.findElement(productTitleLocator).getText().toLowerCase(Locale.ROOT);
    var buttonInBasket = firstProduct.findElement(buttonInBasketLocator);
    buttonInBasket.click();
    var buttonMore = driver.findElement(buttonMoreLocator);
    buttonMore.click();

    var tableRaw = driver.findElement(tableRawLocator);
    String tableProductTitle = tableRaw.findElement(tableProductTitleLocator).getText().toLowerCase(
        Locale.ROOT);
    Assert.assertEquals("не совпадают названия карточки товара и название товара в корзине", productTitle, tableProductTitle);
  }

  //кладём товар со скидкой в корзину и проверяем цену
  @Test
  public void checkProductPriceInBasket()
  {
    driver.navigate().to(path);

    var navCatalogLink = driver.findElement(navCatalogLinkLocator);
    navCatalogLink.click();

    var category = driver.findElement(categoryLocator);
    category.click();

    var saleProduct = driver.findElement(saleProductLocator);
    var saleProductPriceWithDiscount = saleProduct.findElement(saleProductPriceWithDiscountLocator).getText();
    var buttonInBasket = saleProduct.findElement(buttonInBasketLocator);
    buttonInBasket.click();
    var buttonMore = driver.findElement(buttonMoreLocator);
    buttonMore.click();

    var tableRaw = driver.findElement(tableRawLocator);
    var tableProductPrice = tableRaw.findElement(tableProductPriceLocator).getText();
    Assert.assertEquals("цена товара в каталоге отличается от цены этого же товара в корзине", saleProductPriceWithDiscount, tableProductPrice);
  }

  //кладём в корзину один товар и проверяем количество в корзине
  @Test
  public void checkCountProductsInBasket()
  {
    driver.navigate().to(path);

    var navCatalogLink = driver.findElement(navCatalogLinkLocator);
    navCatalogLink.click();

    var category = driver.findElement(categoryLocator);
    category.click();

    var firstProduct = driver.findElement(firstProductLocator);
    var buttonInBasket = firstProduct.findElement(buttonInBasketLocator);
    buttonInBasket.click();
    var buttonMore = driver.findElement(buttonMoreLocator);
    buttonMore.click();

    var tableRaw = driver.findElement(tableRawLocator);
    var productCountInBasket = tableRaw.findElement(countProductsFieldLocator).getAttribute("value");
    Assert.assertEquals("не совпадает количество товара, добавленного в корзину", "1", productCountInBasket);
  }

  //кладём товар в корзину, меняем количество (3 шт), смотрим колонку ОБЩАЯ СТОИМОСТЬ
  @Test
  public void changeCountAndCheckSubTotalPrice()
  {
    driver.navigate().to(path);

    var navCatalogLink = driver.findElement(navCatalogLinkLocator);
    navCatalogLink.click();

    var category = driver.findElement(categoryLocator);
    category.click();

    var firstProduct = driver.findElement(firstProductLocator);
    var firstProductPrice = firstProduct.findElement(firstProductPriceLocator).getText();

    //преобразуем цену к числовому значению
    firstProductPrice = StringUtils.substring(firstProductPrice, 0, firstProductPrice.length() - 1);
    firstProductPrice = firstProductPrice.replace(",", ".");
    double price = Double.parseDouble(firstProductPrice);
    //возвращаем в строковое значение умноженное на 3
    var threeProducts = String.valueOf(price * 3);

    var buttonInBasket = firstProduct.findElement(buttonInBasketLocator);
    buttonInBasket.click();
    var buttonMore = driver.findElement(buttonMoreLocator);
    buttonMore.click();

    var tableRaw = driver.findElement(tableRawLocator);
    var countProductsField = tableRaw.findElement(countProductsFieldLocator);
    countProductsField.clear();
    countProductsField.sendKeys("3" + Keys.ENTER);
    var cartUpdatedAlert = driver.findElement(cartUpdatedAlertLocator);
    Assert.assertTrue("не отображается аллерт о том, что корзина обновлена", cartUpdatedAlert.isDisplayed());

    var newTableRaw = driver.findElement(tableRawLocator);
    var s = newTableRaw.findElement(subTotalPriceLocator).getText();
    s = StringUtils.substring(s, 0, s.length() - 2);
    s = s.replace(",", ".");
    var subTotalPrice = String.valueOf(s);
    Assert.assertEquals("", threeProducts, subTotalPrice);
  }

  //кладём товар со скидкой в корзину и применяем купон, затем удаляем купон
  @Test
  public void putSaleProductAndApplyCoupon()
  {
    driver.navigate().to(path);

    var navCatalogLink = driver.findElement(navCatalogLinkLocator);
    navCatalogLink.click();

    var category = driver.findElement(categoryLocator);
    category.click();

    var saleProduct = driver.findElement(saleProductLocator);
    String saleProductPriceWithDiscount = saleProduct.findElement(saleProductPriceWithDiscountLocator).getText();
    var buttonInBasket = saleProduct.findElement(buttonInBasketLocator);
    buttonInBasket.click();
    var buttonMore = driver.findElement(buttonMoreLocator);
    buttonMore.click();

    var couponField = driver.findElement(couponFieldLocator);
    couponField.sendKeys(coupon);
    var couponButton = driver.findElement(couponButtonLocator);
    couponButton.click();

    var tableRaw = driver.findElement(tableRawLocator);
    //var countProductsField = tableRaw.findElement(countProductsFieldLocator);

    //countProductsField.clear();
    //countProductsField.sendKeys("1" + Keys.ENTER);
    var couponTableRaw = driver.findElement(couponTableRawLocator);
    Assert.assertTrue("не отобразилось поле со скидкой по купону", couponTableRaw.isDisplayed());

    //цена товара в таблице корзины после применения скидки
    var newTableRaw = driver.findElement(tableRawLocator);
    String tableProductPrice = newTableRaw.findElement(tableProductPriceLocator).getText();
    Assert.assertEquals("скидка товара по распроже пропадает после применения купона", saleProductPriceWithDiscount, tableProductPrice);
    tableProductPrice = StringUtils.substring(tableProductPrice, 0, tableProductPrice.length() - 1);
    tableProductPrice = tableProductPrice.replace(",", ".");
    double t = Double.parseDouble(tableProductPrice);

    //сумма скидки по купону
    var newCouponTableRaw = driver.findElement(couponTableRawLocator);
    String couponTableRawAmount = newCouponTableRaw.findElement(couponTableRawAmountLocator).getText();
    couponTableRawAmount = StringUtils.substring(couponTableRawAmount, 0, couponTableRawAmount.length() - 1);
    couponTableRawAmount = couponTableRawAmount.replace(",", ".");
    double c = Double.parseDouble(couponTableRawAmount);

    //цена товара за минусом скидки по купону
    double r = (t * 2) - c;
    String result = String.valueOf(r);

    //общая стоимость товара с учётом скидки по купону
    String totalPrice = driver.findElement(totalPriceLocator).getText();
    totalPrice = StringUtils.substring(totalPrice, 0, totalPrice.length() - 2);
    totalPrice = totalPrice.replace(",", ".");

    Assert.assertEquals("цена товара со скидкой по купону отличается от суммы корзины в строке К ОПЛАТЕ", result, totalPrice);

    //удаляем купон и проверяем всплывающую подсказку
    var couponDeleteButton = driver.findElement(couponDeleteButtonLocator);
    couponDeleteButton.click();

    var couponDeletedAlert = driver.findElement(couponDeletedAlertLocator);
    Assert.assertTrue("не показывается алерт, что купон удалён", couponDeletedAlert.isDisplayed());
  }

  //применяем неправильный купон sert1000 (заодно проверим, не применяется ли скидка, если у купона
  // изменить номинал)
  @Test
  public void applyWrongCoupon()
  {
    driver.navigate().to(path);

    var navCatalogLink = driver.findElement(navCatalogLinkLocator);
    navCatalogLink.click();

    var category = driver.findElement(categoryLocator);
    category.click();

    var saleProduct = driver.findElement(saleProductLocator);
    var buttonInBasket = saleProduct.findElement(buttonInBasketLocator);
    buttonInBasket.click();
    var buttonMore = driver.findElement(buttonMoreLocator);
    buttonMore.click();

    var couponField = driver.findElement(couponFieldLocator);
    couponField.sendKeys("sert1000");
    var couponButton = driver.findElement(couponButtonLocator);
    couponButton.click();

    Assert.assertTrue("не отображается аллерт НЕВЕРНЫЙ КУПОН", driver.findElement(wrongCouponAlertLocator).isDisplayed());
  }

  //кладём в корзину два товара, один из них удаляем, затем возвращаем
  @Test
  public void deleteOneOfTheTwoProduct()
  {
    driver.navigate().to(path);

    var navCatalogLink = driver.findElement(navCatalogLinkLocator);
    navCatalogLink.click();

    var category = driver.findElement(categoryLocator);
    category.click();

    var firstProduct = driver.findElement(firstProductLocator);
    var buttonInBasket = firstProduct.findElement(buttonInBasketLocator);
    buttonInBasket.click();

    var saleProduct = driver.findElement(saleProductLocator);
    var buttonInBasketSaleProduct = saleProduct.findElement(buttonInBasketLocator);
    buttonInBasketSaleProduct.click();

    var buttonMore = driver.findElement(buttonMoreLocator);
    buttonMore.click();

    var removeProductButton = driver.findElement(removeProductLocator);
    removeProductButton.click();

    var getBackDeletedProductLink = driver.findElement(getBackDeletedProductLinkLocator);
    getBackDeletedProductLink.click();

    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("*[contains(@class, 'blockUI')]")));

    var tableRaws = driver.findElements(tableRawLocator);
    Assert.assertEquals("", 2, tableRaws.size());
  }

  //удаяем единственный товар из корзины и по ссылке НАЗАД В МАГАЗИН возвращаемся в каталог
  @Test
  public void deleteSingleProductFromBasketAndBackToCatalog()
  {
    driver.navigate().to(path);

    var navCatalogLink = driver.findElement(navCatalogLinkLocator);
    navCatalogLink.click();

    var category = driver.findElement(categoryLocator);
    category.click();

    var firstProduct = driver.findElement(firstProductLocator);
    var buttonInBasket = firstProduct.findElement(buttonInBasketLocator);
    buttonInBasket.click();

    var buttonMore = driver.findElement(buttonMoreLocator);
    buttonMore.click();

    var removeProductButton = driver.findElement(removeProductLocator);
    removeProductButton.click();

    Assert.assertTrue("не отображается поле КОРЗИНА ПУСТА", driver.findElement(cartEmptyLocator).isDisplayed());

    var backToCatalogLink = driver.findElement(backToCatalogLinkLocator);
    backToCatalogLink.click();

    Assert.assertEquals("клик по ссылке НАЗАД В МАГАЗИН не ведёт в каталог товаров", catalogUrl, driver.getCurrentUrl());
  }

  //кладём товар в корзину и кликаем ОФОРМИТЬ ЗАКАЗ
  @Test
  public void putProductThenPlaceAnOrder()
  {
    driver.navigate().to(path);

    var navCatalogLink = driver.findElement(navCatalogLinkLocator);
    navCatalogLink.click();

    var category = driver.findElement(categoryLocator);
    category.click();

    var firstProduct = driver.findElement(firstProductLocator);
    var buttonInBasket = firstProduct.findElement(buttonInBasketLocator);
    buttonInBasket.click();

    var buttonMore = driver.findElement(buttonMoreLocator);
    buttonMore.click();

    var placeAnOrderButton = driver.findElement(placeAnOrderLinkLocator);
    placeAnOrderButton.click();

    Assert.assertEquals("клик по кнопке ОФОРМИТЬ ЗАКАЗ не ведёт на страницу оформления заказа", checkoutPageUrl, driver.getCurrentUrl());
  }








































}
