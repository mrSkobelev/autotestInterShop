import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IShopCatalogPageTest {
  private WebDriver driver;
  private WebDriverWait wait;
  private String catalogPath = "http://intershop5.skillbox.ru/product-category/catalog/";

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
  private By chooseCategoryLocator = By.xpath("//*[@id='woocommerce_product_categories-2']//*[contains(text(), 'Холодил')]");
  private By chooseFirstProductLocator = By.xpath("//*[@id='primary']//li[1]");
  private By productTitleLocator = By.xpath("//h3");
  private By buttonInBasketLocator = By.xpath("//a[contains(text(), 'В корзину')]");
  private By buttonMoreLocator = By.xpath("//a[contains(text(), 'Подробнее')]");
  //productCartElements
  private By cartProductTitleLocator = By.xpath("//*[@id='primary']//h1");
  private By cartInputQuantityLocator = By.xpath("//input[@name='quantity']");
  private By cartButtonInBasketLocator = By.xpath("//button[@name='add-to-cart']");
  private By cartFeedbackLocator = By.xpath("//*[@id='tab-title-reviews']//a");
  private By cartFeedbackTitleLocator = By.xpath("//*[@id='tab-reviews']//h2");
  private By cartDescriptionLocator = By.xpath("//*[@id='tab-title-description']//a");
  private By cartDescriptionTitleLocator = By.xpath("//*[@id='tab-description']//h2");


  //пользователь кладёт товар в корзину прям из каталога
  @Test
  public void fastPutProductInBasket()
  {
    driver.navigate().to(catalogPath);

    var chooseCategory = driver.findElement(chooseCategoryLocator);
    chooseCategory.click();

    var firstProduct = driver.findElement(chooseFirstProductLocator);
    String firstProductTitle = firstProduct.findElement(productTitleLocator).getText().toLowerCase(
        Locale.ROOT);

    var buttonInBasket = driver.findElement(buttonInBasketLocator);
    buttonInBasket.click();

    var buttonMore = driver.findElement(buttonMoreLocator);
    buttonMore.click();

    Assert.assertEquals("клик по кнопке Подробнее не привел в корзину", "Корзина", driver.findElement(By.xpath("//span[@class='current']")).getText());
    Assert.assertEquals("название товара в корзине не соответствует ожидаемому", firstProductTitle, driver.findElement(By.xpath("//td[@class='product-name']/a")).getText().toLowerCase(
        Locale.ROOT));
  }

  //пользователь выбирает товар к из каталога, переходит в карточку, читает отзывы и описание, выбирает количество и кладёт товар в корзину
  @Test
  public void viewProductThenPutInBasket()
  {
    driver.navigate().to(catalogPath);
    String quantity = "3";

    var chooseCategory = driver.findElement(chooseCategoryLocator);
    chooseCategory.click();

    var firstProduct = driver.findElement(chooseFirstProductLocator);
    String firstProductTitle = firstProduct.findElement(productTitleLocator).getText().toLowerCase(
        Locale.ROOT);
    firstProduct.click();

    var cartProductTitle = driver.findElement(cartProductTitleLocator).getText().toLowerCase(Locale.ROOT);
    Assert.assertEquals("не совпадает заголовок товара в каталоке с заголовком товара в каточке", firstProductTitle, cartProductTitle);

    var cartFeedback = driver.findElement(cartFeedbackLocator);
    cartFeedback.click();
    var cartFeedbackTitle = driver.findElement(cartFeedbackTitleLocator).getText().toLowerCase(
        Locale.ROOT);
    Assert.assertEquals("клик по ссылке ОТЗЫВЫ не отображает поле с отзывами", "отзывы", cartFeedbackTitle);

    var cartDescription = driver.findElement(cartDescriptionLocator);
    cartDescription.click();
    var cartDescriptionTitle = driver.findElement(cartDescriptionTitleLocator).getText().toLowerCase(
        Locale.ROOT);
    Assert.assertEquals("клик по ссылке ОПИСАНИЕ не отображает поле с описанием", "описание", cartDescriptionTitle);

    var cartInputQuantity = driver.findElement(cartInputQuantityLocator);
    cartInputQuantity.clear();
    cartInputQuantity.sendKeys(quantity);

    var cartButtonInBasket = driver.findElement(cartButtonInBasketLocator);
    cartButtonInBasket.click();

    var buttonMore = driver.findElement(buttonMoreLocator);
    buttonMore.click();

    Assert.assertEquals("клик по кнопке Подробнее не привел в корзину", "Корзина", driver.findElement(By.xpath("//span[@class='current']")).getText());
    Assert.assertEquals("название товара в корзине не соответствует ожидаемому", firstProductTitle, driver.findElement(By.xpath("//td[@class='product-name']/a")).getText().toLowerCase(
        Locale.ROOT));
    Assert.assertEquals("количество товара в корзине не соответствует заявленному", quantity, driver.findElement(By.xpath("//input[contains(@id, 'quantity')]")).getAttribute("value"));
  }
}

//фиксирую время на написание автотеста 16-30
//время написания первого теста 15 минн
//время написания второго теста 30 мин

//время прохождения каждого сценария вручную по 1 мин

