import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IShopRegAuthFormTest {

  private WebDriver driver;
  private WebDriverWait wait;
  private String path = "http://intershop5.skillbox.ru/";
  private String myAccountPageUrl = "http://intershop5.skillbox.ru/my-account/";
  private String registerPageUrl = "http://intershop5.skillbox.ru/register/";
  private String lostPasswordPageUrl = "http://intershop5.skillbox.ru/my-account/lost-password/";

  char r = (char) ((Math.random() * 10) + 97);
  char d = (char) ((Math.random() * 10) + 97);
  char n = (char) ((Math.random() * 10) + 97);
  char a = (char) ((Math.random() * 10) + 97);
  char m = (char) ((Math.random() * 10) + 97);
  char e = (char) ((Math.random() * 10) + 97);

  String chars = String.valueOf(r) + String.valueOf(d) + String.valueOf(n) + String.valueOf(a) + String.valueOf(m) + String.valueOf(e);
  int randomDigit = (int) (Math.random() * 9999) + 1111;

  private String randomName = chars;
  private String randomEmail = chars + "@mail.com";

  private String userName = "Homer";
  private String email = "simpson@mail.com";
  private String password = "Springfield-1956";

  @Before
  public void setUp() {
    System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, 5);
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.manage().window().maximize();
  }

  @After
  public void tearDown() throws IOException {
    //var sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    //FileUtils.copyFile(sourceFile, new File("C:\\Users\\Alex\\Desktop"));
    driver.quit();
  }

  //объявляем переменные локаторов
  //headerElements
  private By entryButtonLocator = By.xpath("//a[contains(text(), 'Войти')]");
  private By headerHelloNameLocator = By.xpath("//span[@class='user-name']");
  //myAccountPageElements
  private By registerButtonLocator = By.xpath("//button[@class='custom-register-button']");
  //registrationPageElements
  private By regNameFieldLocator = By.xpath("//input[@name='username']");
  private By regEmailFieldLocator = By.xpath("//input[@name='email']");
  private By regPasswordFieldLocator = By.xpath("//input[@name='password']");
  private By regRegistrationButtonLocator = By.xpath("//button[@name='register']");
  private By regDoneLocator = By.xpath("//*[contains(text(), 'Регистрация завершена')]");
  private By regAlertLocator = By.xpath("//*[@role='alert']");
  //authorizationPageElements
  private By authNameFieldLocator = By.xpath("//input[@id='username']");
  private By authPasswordFieldLocator = By.xpath("//input[@id='password']");
  private By authLoginButtonLocator = By.xpath("//button[@name='login']");
  private By authCheckboxLocator = By.xpath("//input[@id='rememberme']");
  private By authAlertLocator = By.xpath("//ul[@role='alert']");
  private By authForgotPasswordLinkLocator = By.xpath("//p[contains(@class, 'lost_password')]//a");
  //lostPasswordPageElements
  private By lostPassNameFieldLocator = By.xpath("//input[@id='user_login']");
  private By lostPassButtonLocator = By.xpath("//button[@value='Reset password']");
  private By lostPassPositiveAlertLocator = By.xpath("//div[@role='alert']");
  private String lostPassPositiveAlertText = "Password reset email has been sent.";
  private By lostPassNegativeAlertLocator = By.xpath("//ul[@role='alert']");


  //регистрируем пользователя с валидными данными
  @Test
  public void positiveRegistration() {
    //arrange
    driver.navigate().to(path);
    var entryButton = driver.findElement(entryButtonLocator);
    entryButton.click();
    Assert.assertEquals("Клик по кнопке ВОЙТИ не ведёт на страницу МОЙ АККАУНТ", myAccountPageUrl,
        driver.getCurrentUrl());

    var registerButton = driver.findElement(registerButtonLocator);
    registerButton.click();
    Assert.assertEquals("Клик по кнопке ЗАРЕГИСТРИРОВАТЬСЯ не ведёт на страницу регистрации",
        registerPageUrl, driver.getCurrentUrl());

    //act
    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys(randomName);

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys(randomEmail);

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys(password);

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    //assert
    Assert.assertTrue("Не отображается окно с сообщением о завершении регистрации",
        driver.findElement(regDoneLocator).isDisplayed());
    Assert.assertEquals("Не отображается приветствие в хидере ПРИВЕТ USERNAME!", randomName,
        driver.findElement(headerHelloNameLocator).getText());
  }

  //форма регистрации - оставляем поля пустыми и кликаем ЗАРЕГИСТРИРОВАТЬСЯ
  @Test
  public void registrationWithEmptyFields() {
    driver.navigate().to(registerPageUrl);
    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertTrue(
        "Не отображается предупреждение при попытке зарегистрироваться не заполнив ни одного поля.",
        driver.findElement(regAlertLocator).isDisplayed());
    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - заполняем только ИМЯ и кликаем ЗАРЕГИСТРИРОВАТЬСЯ
  @Test
  public void registrationWithUsernameOnly() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("Funtik");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertTrue(
        "Не отображается предупреждение при попытке зарегистрироваться заполнив только поле ИМЯ.",
        driver.findElement(regAlertLocator).isDisplayed());
    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - заполняем только поле АДРЕС ПОЧТЫ и кликаем ЗАРЕГИСТРИРОВАТЬСЯ
  @Test
  public void registrationWithEmailOnly() {
    driver.navigate().to(registerPageUrl);

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("funtik@mail.com");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertTrue(
        "Не отображается предупреждение при попытке зарегистрироваться заполнив только поле АДРЕС ПОЧТЫ.",
        driver.findElement(regAlertLocator).isDisplayed());
    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - заполняем только поле ПАРОЛЬ и кликаем ЗАРЕГИСТРИРОВАТЬСЯ
  @Test
  public void registrationWithPasswordOnly() {
    driver.navigate().to(registerPageUrl);

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Funtik-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertTrue(
        "Не отображается предупреждение при попытке зарегистрироваться заполнив только поле ПАРОЛЬ.",
        driver.findElement(regAlertLocator).isDisplayed());
    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - заполняем только поля ИМЯ, АДРЕС ПОЧТЫ и кликаем ЗАРЕГИСТРИРОВАТЬСЯ
  @Test
  public void registrationWithUsernameAndEmail() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("Anchous");

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("anchous@mail.com");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertTrue(
        "Не отображается предупреждение при попытке зарегистрироваться не заполнив поле ПАРОЛЬ.",
        driver.findElement(regAlertLocator).isDisplayed());
    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - заполняем только поля ИМЯ, ПАРОЛЬ и кликаем ЗАРЕГИСТРИРОВАТЬСЯ
  @Test
  public void registrationWithUsernameAndPassword() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("Pyatachok");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pyatachok-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertTrue(
        "Не отображается предупреждение при попытке зарегистрироваться не заполнив поле АДРЕС ПОЧТЫ.",
        driver.findElement(regAlertLocator).isDisplayed());
    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - заполняем только поля АДРЕС ПОЧТЫ, ПАРОЛЬ и кликаем ЗАРЕГИСТРИРОВАТЬСЯ
  @Test
  public void registrationWithEmailAndPassword() {
    driver.navigate().to(registerPageUrl);

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("rabbit@mail.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Rabbit-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertTrue(
        "Не отображается предупреждение при попытке зарегистрироваться не заполнив поле ИМЯ.",
        driver.findElement(regAlertLocator).isDisplayed());
    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - в поле имя вставляем скрипт
  @Test
  public void registrationWithScriptCodeInNameField() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("<script>alert('тестовый скрипт для [fieldname]')</script>");

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("script@mail.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Script-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertTrue(
        "Не отображается предупреждение при попытке зарегистрироваться заполнив поле ИМЯ скриптовым кодом.",
        driver.findElement(regAlertLocator).isDisplayed());
    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - email без локальной части @mail.com
  @Test
  public void registrationEmailWithoutLocalName() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("NotLocal");

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("@mail.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - email без доменной части notdomain@.com
  @Test
  public void registrationEmailWithoutDomainName() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("Not Domain");

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("notdomain@.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - email с двумя @@
  @Test
  public void registrationEmailWithTwoCommercialAt() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("Two Commercial");

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("two@commercial@at.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - email без @
  @Test
  public void registrationEmailWithoutCommercialAt() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("Not Commercial");

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("notcommercialat.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - email с пробелом в локальной части wi th@space.com
  @Test
  public void registrationEmailWithSpaceInLocalName() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("With Space");

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("wi th@space.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - email с пробелом в доменной части with@spa ce.com
  @Test
  public void registrationEmailWithSpaceInDomainName() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("With Spaces");

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("with@spa ce.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - email с двумя точками подряд twodots@inarow..com
  @Test
  public void registrationEmailWithTwoDotsInARow() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("Two Dots");

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("twodots@inarow..com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - локальная часть почты начинается с точки .dot@start.com
  @Test
  public void registrationEmailStartWithADot() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("Dot Start");

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys(".dot@start.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - доменная часть почты начинается с точки dots@.start.com
  @Test
  public void registrationEmailDomainNameStartWithDot() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("Dot Domain");

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("dots@.start.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - email вообще не содержит точки not@dots
  @Test
  public void registrationEmailWithoutDots() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("Without Dots");

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("not@dots");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - кириллическая доменная часть cyrillic@домен.рф
  @Test
  public void registrationEmailWithCyrillicDomainName() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("Cyrillic Domain");

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("cyrillic@домен.рф");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - email c цифрами в локальной части 12345@mail.com
  @Test
  public void registrationEmailWithDigitsLocalName() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys(randomName);

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys(randomDigit + "@mail.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertTrue("Не отображается окно с сообщением о завершении регистрации",
        driver.findElement(regDoneLocator).isDisplayed());
  }

  //форма регистрации - email c цифрами в доменной части user@12345.com
  @Test
  public void registrationEmailWithDigitsDomainName() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys(randomName);

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("user@" + randomDigit + ".com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertTrue("Не отображается окно с сообщением о завершении регистрации",
        driver.findElement(regDoneLocator).isDisplayed());
  }

  //форма регистрации - email c дефисом в локальной части da-sh@mail.com
  @Test
  public void registrationEmailWithDashInLocalName() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys(randomName);

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys(randomName + "-sh@mail.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertTrue("Не отображается окно с сообщением о завершении регистрации",
        driver.findElement(regDoneLocator).isDisplayed());
  }

  //форма регистрации - email c дефисом в доменной части dash@ma-il.com
  @Test
  public void registrationEmailWithDashInDomainName() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys(randomName);

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("dash@ma-" + randomName + ".com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertTrue("Не отображается окно с сообщением о завершении регистрации",
        driver.findElement(regDoneLocator).isDisplayed());
  }

  //форма регистрации - email c нижним подчёркиванием в локальной части da_sh@mail.com
  @Test
  public void registrationEmailWithUnderliningInLocalName() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys(randomName);

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys(randomName + "da_sh@mail.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertTrue("Не отображается окно с сообщением о завершении регистрации",
        driver.findElement(regDoneLocator).isDisplayed());
  }

  //форма регистрации - email c нижним подчёркиванием в доменной части dash@ma_il.com
  @Test
  public void registrationEmailWithUnderliningInDomainName() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys(randomName);

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys("dash@ma_il" + randomName + ".com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertFalse("Не должно допускаться нижнее подчёркивание в доменной части",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //форма регистрации - email с точкой в локальной части user.name@mail.com
  @Test
  public void registrationEmailWithDotInLocalName() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys(randomName);

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys( randomName + ".name@mail.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertTrue("Не отображается окно с сообщением о завершении регистрации",
        driver.findElement(regDoneLocator).isDisplayed());
  }

  //форма регистрации - email с точками в доменной части user@mail.post.com
  @Test
  public void registrationEmailWithDotInDomainName() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys(randomName);

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys(randomName + "@mail.post.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertTrue("Не отображается окно с сообщением о завершении регистрации",
        driver.findElement(regDoneLocator).isDisplayed());
  }

  //форма регистрации с длинным email tensymbols_tensymbols_tensymbols_tensymbols_tensymbols_tensymbols@tensymbolstensymbolstensymbolstensymbolstensymbolstensymbols.com
  @Test
  public void registrationLongEmail() {
    driver.navigate().to(registerPageUrl);

    var regNameField = driver.findElement(regNameFieldLocator);
    regNameField.sendKeys("Long Email");

    var regEmailField = driver.findElement(regEmailFieldLocator);
    regEmailField.sendKeys(
        "tensymbols_tensymbols_tensymbols_tensymbols_tensymbols_tensymbols@tensymbolstensymbolstensymbolstensymbolstensymbolstensymbols.com");

    var regPasswordField = driver.findElement(regPasswordFieldLocator);
    regPasswordField.sendKeys("Pass-123");

    var regRegistrationButton = driver.findElement(regRegistrationButtonLocator);
    regRegistrationButton.click();

    Assert.assertFalse("Отображается окно с сообщением о завершении регистрации",
        driver.findElements(regDoneLocator).size() > 0);
  }

  //тестирование формы авторизации

  //авторизация с вводом валидных данных (пара ИМЯ и ПАРОЛЬ)
  @Test
  public void positiveAuthNamePass() {
    driver.navigate().to(myAccountPageUrl);

    var authNameField = driver.findElement(authNameFieldLocator);
    authNameField.sendKeys("Homer");

    var authPasswordField = driver.findElement(authPasswordFieldLocator);
    authPasswordField.sendKeys(password);

    var authLoginButton = driver.findElement(authLoginButtonLocator);
    authLoginButton.click();

    Assert.assertTrue("не был осуществлён вход в ЛК",
        driver.findElement(By.xpath("//div[@id='primary']//strong")).getText().contains("Homer"));
  }

  //авторизация с вводом валидных данных (пара EMAIL и ПАРОЛЬ, клик по чекбоксу)
  @Test
  public void positiveAuthEmailPassCheckBox() {
    driver.navigate().to(myAccountPageUrl);

    var authNameField = driver.findElement(authNameFieldLocator);
    authNameField.sendKeys(email);

    var authPasswordField = driver.findElement(authPasswordFieldLocator);
    authPasswordField.sendKeys(password);

    var authCheckBox = driver.findElement(authCheckboxLocator);
    authCheckBox.click();

    var authLoginButton = driver.findElement(authLoginButtonLocator);
    authLoginButton.click();

    Assert.assertTrue("не был осуществлён вход в ЛК",
        driver.findElement(By.xpath("//div[@id='primary']//strong")).getText().contains("Homer"));
  }

  //авторизация с пустыми полями
  @Test
  public void authEmptyFields() {
    driver.navigate().to(myAccountPageUrl);

    var authLoginButton = driver.findElement(authLoginButtonLocator);
    authLoginButton.click();

    Assert.assertTrue("не отображается предупреждение об ошибке",
        driver.findElement(authAlertLocator).isDisplayed());
  }

  //авторизация с пустым полем ИМЯ ИЛИ ПОЧТА
  @Test
  public void authEmptyNameField() {
    driver.navigate().to(myAccountPageUrl);

    var authPasswordField = driver.findElement(authPasswordFieldLocator);
    authPasswordField.sendKeys(password);

    var authLoginButton = driver.findElement(authLoginButtonLocator);
    authLoginButton.click();

    Assert.assertTrue("не отображается предупреждение об ошибке",
        driver.findElement(authAlertLocator).isDisplayed());
  }

  //авторизация с пустым полем ПАРОЛЬ
  @Test
  public void authEmptyPasswordField() {
    driver.navigate().to(myAccountPageUrl);

    var authNameField = driver.findElement(authNameFieldLocator);
    authNameField.sendKeys(randomName);

    var authLoginButton = driver.findElement(authLoginButtonLocator);
    authLoginButton.click();

    Assert.assertTrue("не отображается предупреждение об ошибке",
        driver.findElement(authAlertLocator).isDisplayed());
  }

  //авторизация - невалидное имя и валидный пароль
  @Test
  public void authFalseName()
  {
    driver.navigate().to(myAccountPageUrl);

    var authNameField = driver.findElement(authNameFieldLocator);
    authNameField.sendKeys(randomName);

    var authPasswordField = driver.findElement(authPasswordFieldLocator);
    authPasswordField.sendKeys(password);

    var authLoginButton = driver.findElement(authLoginButtonLocator);
    authLoginButton.click();

    Assert.assertTrue("не отображается предупреждение об ошибке",
        driver.findElement(authAlertLocator).isDisplayed());
  }

  //авторизация - валидное имя и невалидный пароль
  @Test
  public void authFalsePassword()
  {
    driver.navigate().to(myAccountPageUrl);

    var authNameField = driver.findElement(authNameFieldLocator);
    authNameField.sendKeys(randomName);

    var authPasswordField = driver.findElement(authPasswordFieldLocator);
    authPasswordField.sendKeys("dasdasda");

    var authLoginButton = driver.findElement(authLoginButtonLocator);
    authLoginButton.click();

    Assert.assertTrue("не отображается предупреждение об ошибке",
        driver.findElement(authAlertLocator).isDisplayed());
  }

  //форма восстановления пароля

  //проверяем переход на страницу восстановления пароля
  @Test
  public void authClickForgotPassword()
  {
    driver.navigate().to(myAccountPageUrl);

    var authForgotPasswordLink = driver.findElement(authForgotPasswordLinkLocator);
    authForgotPasswordLink.click();

    Assert.assertEquals("не был осуществлён переход на страницу восстановления пароля", lostPasswordPageUrl, driver.getCurrentUrl());
  }

  //восстановление пароля - ввод валидного имени
  @Test
  public void lostPassPositiveName()
  {
    driver.navigate().to(lostPasswordPageUrl);

    var lostPassNameField = driver.findElement(lostPassNameFieldLocator);
    lostPassNameField.sendKeys(userName);

    var lostPassButton = driver.findElement(lostPassButtonLocator);
    lostPassButton.click();

    Assert.assertTrue("отсутствует позитивный URL запрос", driver.getCurrentUrl().contains("reset-link-sent=true"));
  }

  //восстановление пароля - ввод валидного EMAIL
  @Test
  public void lostPassPositiveEmail()
  {
    driver.navigate().to(lostPasswordPageUrl);

    var lostPassNameField = driver.findElement(lostPassNameFieldLocator);
    lostPassNameField.sendKeys(email);

    var lostPassButton = driver.findElement(lostPassButtonLocator);
    lostPassButton.click();

    var lostPassPositiveAlert = driver.findElement(lostPassPositiveAlertLocator);

    Assert.assertEquals("не совпадает текст об успешном сбросе пароля", lostPassPositiveAlertText, lostPassPositiveAlert.getText());
  }

  //восстановление пароля - пустое поле ИМЯ
  @Test
  public void lostPassEmptyFieldName()
  {
    driver.navigate().to(lostPasswordPageUrl);

    var lostPassButton = driver.findElement(lostPassButtonLocator);
    lostPassButton.click();

    Assert.assertTrue("не отображается сообщение об ошибке", driver.findElement(lostPassNegativeAlertLocator).isDisplayed());
  }

  //восстановление пароля - ввод невалидного ИМЕНИ
  @Test
  public void lostPassNegativeName()
  {
    driver.navigate().to(lostPasswordPageUrl);

    var lostPassNameField = driver.findElement(lostPassNameFieldLocator);
    lostPassNameField.sendKeys("blablaName");

    var lostPassButton = driver.findElement(lostPassButtonLocator);
    lostPassButton.click();

    Assert.assertTrue("не отображается сообщение об ошибке", driver.findElement(lostPassNegativeAlertLocator).isDisplayed());
  }

  //восстановление пароля - ввод несуществующего EMAIL
  @Test
  public void lostPassNegativeEmail()
  {
    driver.navigate().to(lostPasswordPageUrl);

    var lostPassNameField = driver.findElement(lostPassNameFieldLocator);
    lostPassNameField.sendKeys("blablaMail@mail.com");

    var lostPassButton = driver.findElement(lostPassButtonLocator);
    lostPassButton.click();

    Assert.assertTrue("не отображается сообщение об ошибке", driver.findElement(lostPassNegativeAlertLocator).isDisplayed());
  }
}
