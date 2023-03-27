import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IShopMakingAnOrderTest {

  private WebDriver driver;
  private WebDriverWait wait;
  private String path = "http://intershop5.skillbox.ru/";

  private String userName = "Homer";
  private String lastName = "Simpson";
  private String email = "simpson@mail.com";
  private String password = "Springfield-1956";
  private String address = "Evergreen Terrace, 742";
  private String town = "Springfield";
  private String state = "Illinois";
  private String postCode = "123456";
  private String phone = "+79991231212";

  //объявляем переменные локаторов дл предусловия
  private By singInLocator = By.xpath("//a[@class='account']");

  private By usernameFieldLocator = By.xpath("//input[@id='username']");
  private By passwordFieldLocator = By.xpath("//input[@id='password']");
  private By loginButtonLocator = By.xpath("//button[@name='login']");

  private By navCatalogLinkLocator = By.xpath("//*[@class='store-menu']//a[contains(text(), 'Каталог')]");

  private By categoryLinkLocator = By.xpath("//ul[@class='product-categories']//a[contains(text(), 'Холодильник')]");
  private By firstProductInBasketLocator = By.xpath("//ul[contains(@class, 'products')]/li//a[contains(text(), 'корзину')]");
  private By moreLinkLocator = By.xpath("//a[contains(text(), 'Подробнее')]");

  private By makingAnOrderButtonLocator = By.xpath("//a[contains(text(), 'ОФОРМИТЬ')]");

  //перед прохождением каждого теста авторизуемся
  @Before
  public void connect()
  {

    System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, 5);
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.manage().window().maximize();

    driver.navigate().to(path);

    var singIn = driver.findElement(singInLocator);
    singIn.click();

    var usernameField = driver.findElement(usernameFieldLocator);
    usernameField.sendKeys(userName);
    var passwordField = driver.findElement(passwordFieldLocator);
    passwordField.sendKeys(password);
    var loginButton = driver.findElement(loginButtonLocator);
    loginButton.click();

    var navCatalogLink = driver.findElement(navCatalogLinkLocator);
    navCatalogLink.click();

    var categoryLink = driver.findElement(categoryLinkLocator);
    categoryLink.click();

    var firstProductInBasket = driver.findElement(firstProductInBasketLocator);
    firstProductInBasket.click();
    var moreLink = driver.findElement(moreLinkLocator);
    moreLink.click();

    var makingAnOrderButton = driver.findElement(makingAnOrderButtonLocator);
    makingAnOrderButton.click();
  }

  @After
  public void quit()
  {
    driver.quit();
  }

  //объявляем переменные локаторов непосредственно для тестов
  //страница с формой оформления
  private By firstNameFieldLocator = By.xpath("//input[@id='billing_first_name']");
  private By lastNameFieldLocator = By.xpath("//input[@id='billing_last_name']");
  private By addressFieldLocator = By.xpath("//input[@id='billing_address_1']");
  private By townFieldLocator = By.xpath("//input[@id='billing_city']");
  private By stateFieldLocator = By.xpath("//input[@id='billing_state']");
  private By postCodeFieldLocator = By.xpath("//input[@id='billing_postcode']");
  private By phoneFieldLocator = By.xpath("//input[@id='billing_phone']");
  private By emailFieldLocator = By.xpath("//input[@id='billing_email']");
  private By placeOrderButtonLocator = By.xpath("//button[@id='place_order']");
  private By paymentMethodBankLocator = By.xpath("//input[@id='payment_method_bacs']");
  private By paymentMethodReceivingLocator = By.xpath("//input[@id='payment_method_cod']");
  private By alertLocator = By.xpath("//*[contains(@role, 'alert')]");
  //страница завершения оформления
  private By orderReceivedTitleLocator = By.xpath("//h2[contains(text(), 'Заказ получен')]");


  //заполнили все поля - метод оплаты по умолчанию (банковский перевод)
  @Test
  public void positiveTestPaymentMethodBank()
  {
    var firstNameField = driver.findElement(firstNameFieldLocator);
    firstNameField.clear();
    firstNameField.sendKeys(userName);

    var lastNameField = driver.findElement(lastNameFieldLocator);
    lastNameField.clear();
    lastNameField.sendKeys(lastName);

    var addressField = driver.findElement(addressFieldLocator);
    addressField.clear();
    addressField.sendKeys(address);

    var townField = driver.findElement(townFieldLocator);
    townField.clear();
    townField.sendKeys(town);

    var stateField = driver.findElement(stateFieldLocator);
    stateField.clear();
    stateField.sendKeys(state);

    var postCodeField = driver.findElement(postCodeFieldLocator);
    postCodeField.clear();
    postCodeField.sendKeys(postCode);

    var phoneField = driver.findElement(phoneFieldLocator);
    phoneField.clear();
    phoneField.sendKeys(phone);

    var placeOrderButton = driver.findElement(placeOrderButtonLocator);
    placeOrderButton.click();

    Assert.assertTrue("Не был осуществлён переход на страницу с подтверждением заказа", driver.findElement(orderReceivedTitleLocator).isDisplayed());
  }

  //заполнили все поля - метод оплаты при получении
  @Test
  public void positiveTestPaymentMethodReceiving()
  {
    var firstNameField = driver.findElement(firstNameFieldLocator);
    firstNameField.clear();
    firstNameField.sendKeys(userName);

    var lastNameField = driver.findElement(lastNameFieldLocator);
    lastNameField.clear();
    lastNameField.sendKeys(lastName);

    var addressField = driver.findElement(addressFieldLocator);
    addressField.clear();
    addressField.sendKeys(address);

    var townField = driver.findElement(townFieldLocator);
    townField.clear();
    townField.sendKeys(town);

    var stateField = driver.findElement(stateFieldLocator);
    stateField.clear();
    stateField.sendKeys(state);

    var postCodeField = driver.findElement(postCodeFieldLocator);
    postCodeField.clear();
    postCodeField.sendKeys(postCode);

    var phoneField = driver.findElement(phoneFieldLocator);
    phoneField.clear();
    phoneField.sendKeys(phone);

    var paymentMethodReceiving = driver.findElement(paymentMethodReceivingLocator);
    paymentMethodReceiving.click();

    var placeOrderButton = driver.findElement(placeOrderButtonLocator);
    placeOrderButton.click();

    Assert.assertTrue("Не был осуществлён переход на страницу с подтверждением заказа", driver.findElement(orderReceivedTitleLocator).isDisplayed());
  }

  //оставляем пустым поле ИМЯ и оформляем заказ
  @Test
  public void emptyNameField()
  {
    var firstNameField = driver.findElement(firstNameFieldLocator);
    firstNameField.clear();

    var lastNameField = driver.findElement(lastNameFieldLocator);
    lastNameField.clear();
    lastNameField.sendKeys(lastName);

    var addressField = driver.findElement(addressFieldLocator);
    addressField.clear();
    addressField.sendKeys(address);

    var townField = driver.findElement(townFieldLocator);
    townField.clear();
    townField.sendKeys(town);

    var stateField = driver.findElement(stateFieldLocator);
    stateField.clear();
    stateField.sendKeys(state);

    var postCodeField = driver.findElement(postCodeFieldLocator);
    postCodeField.clear();
    postCodeField.sendKeys(postCode);

    var phoneField = driver.findElement(phoneFieldLocator);
    phoneField.clear();
    phoneField.sendKeys(phone);

    var placeOrderButton = driver.findElement(placeOrderButtonLocator);
    placeOrderButton.click();

    Assert.assertTrue("Не высплыло сообщение об ошибке при попытке оформить заказ с незаполненым полем ИМЯ", driver.findElement(alertLocator).isDisplayed());
  }

  //оставляем пустым поле ФАМИЛИЯ и оформляем заказ
  @Test
  public void emptyLastNameField()
  {
    var firstNameField = driver.findElement(firstNameFieldLocator);
    firstNameField.clear();
    firstNameField.sendKeys(userName);

    var lastNameField = driver.findElement(lastNameFieldLocator);
    lastNameField.clear();

    var addressField = driver.findElement(addressFieldLocator);
    addressField.clear();
    addressField.sendKeys(address);

    var townField = driver.findElement(townFieldLocator);
    townField.clear();
    townField.sendKeys(town);

    var stateField = driver.findElement(stateFieldLocator);
    stateField.clear();
    stateField.sendKeys(state);

    var postCodeField = driver.findElement(postCodeFieldLocator);
    postCodeField.clear();
    postCodeField.sendKeys(postCode);

    var phoneField = driver.findElement(phoneFieldLocator);
    phoneField.clear();
    phoneField.sendKeys(phone);

    var placeOrderButton = driver.findElement(placeOrderButtonLocator);
    placeOrderButton.click();

    Assert.assertTrue("Не высплыло сообщение об ошибке при попытке оформить заказ с незаполненым полем ФАМИЛИЯ", driver.findElement(alertLocator).isDisplayed());
  }

  //оставляем пустым поле АДРЕС и оформляем заказ
  @Test
  public void emptyAddressField()
  {
    var firstNameField = driver.findElement(firstNameFieldLocator);
    firstNameField.clear();
    firstNameField.sendKeys(userName);

    var lastNameField = driver.findElement(lastNameFieldLocator);
    lastNameField.clear();
    lastNameField.sendKeys(lastName);

    var addressField = driver.findElement(addressFieldLocator);
    addressField.clear();

    var townField = driver.findElement(townFieldLocator);
    townField.clear();
    townField.sendKeys(town);

    var stateField = driver.findElement(stateFieldLocator);
    stateField.clear();
    stateField.sendKeys(state);

    var postCodeField = driver.findElement(postCodeFieldLocator);
    postCodeField.clear();
    postCodeField.sendKeys(postCode);

    var phoneField = driver.findElement(phoneFieldLocator);
    phoneField.clear();
    phoneField.sendKeys(phone);

    var placeOrderButton = driver.findElement(placeOrderButtonLocator);
    placeOrderButton.click();

    Assert.assertTrue("Не высплыло сообщение об ошибке при попытке оформить заказ с незаполненым полем АДРЕС", driver.findElement(alertLocator).isDisplayed());
  }

  //оставляем пустым поле ГОРОД и оформляем заказ
  @Test
  public void emptyTownField()
  {
    var firstNameField = driver.findElement(firstNameFieldLocator);
    firstNameField.clear();
    firstNameField.sendKeys(userName);

    var lastNameField = driver.findElement(lastNameFieldLocator);
    lastNameField.clear();
    lastNameField.sendKeys(lastName);

    var addressField = driver.findElement(addressFieldLocator);
    addressField.clear();
    addressField.sendKeys(address);

    var townField = driver.findElement(townFieldLocator);
    townField.clear();

    var stateField = driver.findElement(stateFieldLocator);
    stateField.clear();
    stateField.sendKeys(state);

    var postCodeField = driver.findElement(postCodeFieldLocator);
    postCodeField.clear();
    postCodeField.sendKeys(postCode);

    var phoneField = driver.findElement(phoneFieldLocator);
    phoneField.clear();
    phoneField.sendKeys(phone);

    var placeOrderButton = driver.findElement(placeOrderButtonLocator);
    placeOrderButton.click();

    Assert.assertTrue("Не высплыло сообщение об ошибке при попытке оформить заказ с незаполненым полем ГОРОД", driver.findElement(alertLocator).isDisplayed());
  }

  //оставляем пустым поле ОБЛАСТЬ и оформляем заказ
  @Test
  public void emptyStateField()
  {
    var firstNameField = driver.findElement(firstNameFieldLocator);
    firstNameField.clear();
    firstNameField.sendKeys(userName);

    var lastNameField = driver.findElement(lastNameFieldLocator);
    lastNameField.clear();
    lastNameField.sendKeys(lastName);

    var addressField = driver.findElement(addressFieldLocator);
    addressField.clear();
    addressField.sendKeys(address);

    var townField = driver.findElement(townFieldLocator);
    townField.clear();
    townField.sendKeys(town);

    var stateField = driver.findElement(stateFieldLocator);
    stateField.clear();

    var postCodeField = driver.findElement(postCodeFieldLocator);
    postCodeField.clear();
    postCodeField.sendKeys(postCode);

    var phoneField = driver.findElement(phoneFieldLocator);
    phoneField.clear();
    phoneField.sendKeys(phone);

    var placeOrderButton = driver.findElement(placeOrderButtonLocator);
    placeOrderButton.click();

    Assert.assertTrue("Не высплыло сообщение об ошибке при попытке оформить заказ с незаполненым полем ОБЛАСТЬ", driver.findElement(alertLocator).isDisplayed());
  }

  //оставляем пустым поле ПОЧТОВЫЙ ИНДЕКС и оформляем заказ
  @Test
  public void emptyPostCodeField()
  {
    var firstNameField = driver.findElement(firstNameFieldLocator);
    firstNameField.clear();
    firstNameField.sendKeys(userName);

    var lastNameField = driver.findElement(lastNameFieldLocator);
    lastNameField.clear();
    lastNameField.sendKeys(lastName);

    var addressField = driver.findElement(addressFieldLocator);
    addressField.clear();
    addressField.sendKeys(address);

    var townField = driver.findElement(townFieldLocator);
    townField.clear();
    townField.sendKeys(town);

    var stateField = driver.findElement(stateFieldLocator);
    stateField.clear();
    stateField.sendKeys(state);

    var postCodeField = driver.findElement(postCodeFieldLocator);
    postCodeField.clear();

    var phoneField = driver.findElement(phoneFieldLocator);
    phoneField.clear();
    phoneField.sendKeys(phone);

    var placeOrderButton = driver.findElement(placeOrderButtonLocator);
    placeOrderButton.click();

    Assert.assertTrue("Не высплыло сообщение об ошибке при попытке оформить заказ с незаполненым полем ПОЧТОВЫЙ ИНДЕКС", driver.findElement(alertLocator).isDisplayed());
  }

  //оставляем пустым поле ТЕЛЕФОН и оформляем заказ
  @Test
  public void emptyPhoneField()
  {
    var firstNameField = driver.findElement(firstNameFieldLocator);
    firstNameField.clear();
    firstNameField.sendKeys(userName);

    var lastNameField = driver.findElement(lastNameFieldLocator);
    lastNameField.clear();
    lastNameField.sendKeys(lastName);

    var addressField = driver.findElement(addressFieldLocator);
    addressField.clear();
    addressField.sendKeys(address);

    var townField = driver.findElement(townFieldLocator);
    townField.clear();
    townField.sendKeys(town);

    var stateField = driver.findElement(stateFieldLocator);
    stateField.clear();
    stateField.sendKeys(state);

    var postCodeField = driver.findElement(postCodeFieldLocator);
    postCodeField.clear();
    postCodeField.sendKeys(postCode);

    var phoneField = driver.findElement(phoneFieldLocator);
    phoneField.clear();

    var placeOrderButton = driver.findElement(placeOrderButtonLocator);
    placeOrderButton.click();

    Assert.assertTrue("Не высплыло сообщение об ошибке при попытке оформить заказ с незаполненым полем ТЕЛЕФОН", driver.findElement(alertLocator).isDisplayed());
  }

  //оставляем пустым поле АДРЕС ПОЧТЫ и оформляем заказ
  @Test
  public void emptyEmailField()
  {
    var firstNameField = driver.findElement(firstNameFieldLocator);
    firstNameField.clear();
    firstNameField.sendKeys(userName);

    var lastNameField = driver.findElement(lastNameFieldLocator);
    lastNameField.clear();
    lastNameField.sendKeys(lastName);

    var addressField = driver.findElement(addressFieldLocator);
    addressField.clear();
    addressField.sendKeys(address);

    var townField = driver.findElement(townFieldLocator);
    townField.clear();
    townField.sendKeys(town);

    var stateField = driver.findElement(stateFieldLocator);
    stateField.clear();
    stateField.sendKeys(state);

    var postCodeField = driver.findElement(postCodeFieldLocator);
    postCodeField.clear();
    postCodeField.sendKeys(postCode);

    var phoneField = driver.findElement(phoneFieldLocator);
    phoneField.clear();
    phoneField.sendKeys(phone);

    var emailField = driver.findElement(emailFieldLocator);
    emailField.clear();

    var placeOrderButton = driver.findElement(placeOrderButtonLocator);
    placeOrderButton.click();

    Assert.assertTrue("Не высплыло сообщение об ошибке при попытке оформить заказ с незаполненым полем АДРЕС ПОЧТЫ", driver.findElement(alertLocator).isDisplayed());
  }

}
