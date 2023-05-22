package testCases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.xml.sax.SAXException;
import pages.*;
import utilities.Data;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class SanityTest {

    //הגדרת משתנים לדוחות
    private static ExtentSparkReporter spark=new ExtentSparkReporter("index.html");
    private static ExtentReports report1=new ExtentReports();
    //private static ExtentReports report;
    private static ExtentTest test;
    String currentTime = String.valueOf(System.currentTimeMillis());

    private static WebDriver chDriver;
    //הגדרת משתנים מסוגי דפי האתר
    SignInPage signInPage= new SignInPage(chDriver);
    HomePage homePage= new HomePage(chDriver);
    SearchPage searchPage= new SearchPage(chDriver);
    ProductPage productPage= new ProductPage(chDriver);
    ShoppingBagPage shoppingBagPage= new ShoppingBagPage(chDriver);
    PaymentPage paymentPage= new PaymentPage(chDriver);

    @BeforeClass
    public static void beforeClass() {
        System.out.println("before class");
        System.setProperty("webdriver.chrome.driver", Constants.CRM_DRIVER_LOCATION);
        chDriver= new ChromeDriver();

        ChromeOptions chromeOptions= new ChromeOptions();
        chromeOptions.addArguments("-incognito");
        report1.attachReporter(spark);
        spark.config().setReportName("My report");
        //תואם לגרסאות ישנות. תיקון למטה
        chDriver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        //chDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        chDriver.manage().window().maximize();
        chDriver.get(Constants.NEXT_SITE_URL);
    }

    @Before
    public void before(){
        System.out.println("before");
    }


    @Test
    //לחיצה על אייקון ההתחברות מתוך הדף הראשי
    public void a_SignInPage() throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        ExtentTest enterSingInTest =report1.createTest("EnterSingInTest");  //יצירת הטסט בתוך הדוח וקריאה בשם
        enterSingInTest.log(Status.INFO, "Start test EnterSignIn");  //רישום מידע לדוח- תחילת הטסט
        signInPage.enterIcon();
        Thread.sleep(3000);
        //מוודא שהלחיצה על האייקון תוביל לדף ההחברות
        try{
            Assert.assertEquals("the title are not equals", Constants.SIGN_IN_PAGE_TITLE, chDriver.getTitle());
            enterSingInTest.pass("Step 1 clickSignIcon passed");
        }catch (AssertionError error){  //במקרה שגיאה יוצג צילום מסך
            enterSingInTest.fail("Step 1 clickSignIcon failed- the signIn page isn't displayed", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\screenshots\\"+ currentTime+" enterSignInTest")).build());
        }

        //בתוך דף ההתחברות, מילוי השדות
        signInPage.signInProcess("username");
        signInPage.signInProcess("password");
        /*Thread.sleep(5000);
        try{  //בדיקה האם שדה שם המשתמש מלא כפי המצופה
            Assert.assertEquals(Data.getData("username",0), signInPage.validationSigningDetails("username"));
        }
        catch (Exception e){
            System.out.println("The user-name field is not filled in as expected");
        }

        try{  //בדיקה האם שדה הסיסמה מלא כפי שמצופה
            Assert.assertEquals(Data.getData("password",0), signInPage.validationSigningDetails("password"));
        }
        catch (Exception e){
            System.out.println("The password field is not filled in as expected");
        }*/

        //לחיצה על כפתור אישור התחברות
        signInPage.signInProcess("submit");
        Thread.sleep(2000);
        chDriver.navigate().to(Constants.NEXT_SITE_URL);//מנווט בחזרה לדף הראשי, כי האתר לא נותן להמשיך מכאן באוטומציה
        try{
            Assert.assertEquals(Constants.NEXT_SITE_TITLE, chDriver.getTitle());
            enterSingInTest.pass("Step 2 enterDetails passed");
        } catch (AssertionError error){  //במקרה שגיאה יוצג צילום מסך
            enterSingInTest.fail("Step 2 enterDetails failed- the main page isn't displayed", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\screenshots\\"+ currentTime+" enterDetailsTest")).build());
        }
    }

    @Test
    //כניסה לדף עיצוב הבית
    public void b_enterHomePage() throws InterruptedException {
        ExtentTest homePageTest= report1.createTest("HomePageTest");
        homePage.enterHomePage();//לחיצה כפולה על לינק של עיצוב הבית
        Thread.sleep(3000);
        try {  //מוודא שעברנו לדף הרצוי
            Assert.assertEquals(Constants.HOME_PAGE_URL, chDriver.getCurrentUrl());
            homePageTest.pass("Step 1 passed- you got the home page");
        }catch (AssertionError error){
            homePageTest.fail("Step 1 enterHomePage failed- the home page isn't displayed", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\screenshots\\"+ currentTime+" enterHomePage")).build());
        }

        homePage.enterSalon();  //לחיצה על לינק בצד שמאל של הדף
        Thread.sleep(6000);
        try { //מוודא שעברנו לדף הרצוי
            Assert.assertEquals(Constants.SALON_DESIGN_PAGE_URL, chDriver.getCurrentUrl());
            homePageTest.pass("Step 2 passed- you got the living-room page");
        }catch (AssertionError error){
            homePageTest.fail("Step 2 enterLivingRoomPage failed- the living- room page isn't displayed", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\screenshots\\"+ currentTime+" enter livingRoom page")).build());
        }
        Thread.sleep(2000);
        chDriver.get(Constants.HOME_PAGE_URL);  //ניווט לדף עיצוב הבית בחזרה

        homePage.enterBath();  //לחיצה על לינק במרכז הדף
        Thread.sleep(2000);
        try { //מוודא שעברנו לדף הרצוי
            Assert.assertEquals(Constants.BATH_DESIGN_PAGE_URL, chDriver.getCurrentUrl());
            homePageTest.pass("Step 3 passed- you got the Bath page");
        }catch (Exception e){
            homePageTest.fail("Step 3 enterBathPage failed- the bath room page is not displayed", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\screenshots\\"+ currentTime+" enter Bath page")).build());
        }
        chDriver.get(Constants.HOME_PAGE_URL);  //ניווט לדף עיצוב הבית בחזרה

        homePage.enterPremium();  //לחיצה על לינק בבאנר של הדף
        Thread.sleep(2000);
        try {  //מוודא שעברנו לדף הרצוי
            Assert.assertEquals(Constants.PREMIUM_PAGE_URL, chDriver.getCurrentUrl());
            homePageTest.pass("Step 4 passed- you got the Premium page");
        }catch (Exception e){
            homePageTest.fail("Step 4 enterPremiumPage failed- the Premium page is not displayed", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\screenshots\\"+ currentTime+" enter Premium page")).build());
        }
        chDriver.get(Constants.HOME_PAGE_URL);  //ניווט לדף עיצוב הבית בחזרה
    }

    @Test
    public void c_changeLang() throws InterruptedException, ParserConfigurationException, IOException, SAXException {
        ExtentTest changeLangTest= report1.createTest("ChageLangTest");
        homePage.changeLang(); // לוחץ על אייקון השפה ופותח את הדיב
        Thread.sleep(2000);
        try{       //בודק האם הדיב של שינוי השפה מוצג
            if(!chDriver.findElement(homePage.countryDivLocator).isDisplayed());
            changeLangTest.pass("Step 1 passed- you got into the language div");
        } catch (AssertionError error){
            System.out.println("the change country div is not displayed");
            changeLangTest.fail("Step 1 enterLanguageDiv failed- the Language div is not displayed", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\screenshots\\"+ currentTime+" enter language div#1")).build());
        }

        homePage.chooseLanguage("english");  //בוחר בשפה אנגלית
        homePage.submitLang();  //מאשר את הבחירה- לוחץ אישור

        Thread.sleep(2000);
        try{  //בודק מילה אחת במסך האם היא שונתה לשפה האנגלית
            Assert.assertEquals(Data.getData("english",0), chDriver.findElement(homePage.premiumBtnLocator).getText());
            changeLangTest.pass("Step 2 passed- the website language has changed to English");
        } catch (AssertionError error){
            changeLangTest.fail("Step 2 changeLanguage failed- the Language has not changed to English like expected", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\screenshots\\"+ currentTime+" changeLanguage")).build());
        }

        homePage.changeLang(); // לוחץ על אייקון השפה ופותח את הדיב
        Thread.sleep(2000);
        try{       //בודק האם הדיב של שינוי השפה מוצג
            if(!chDriver.findElement(homePage.countryDivLocator).isDisplayed());
            changeLangTest.pass("Step 3 passed- you got into the language div for change to Hebrew");
        } catch (AssertionError error){
            changeLangTest.fail("Step 3 enterLanguageDiv failed- the Language div is not displayed", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\screenshots\\"+ currentTime+" enter language div#2")).build());
        }
        homePage.chooseLanguage("hebrew");  //בוחר בשפה עברית בחזרה
        Thread.sleep(2000);
        homePage.submitLang();  //לוחץ על אישור בחירת השפה, מנווט לדף הקודם שנמצאנו בו באתר
    }

    @Test
    public void d_searchItem() throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        ExtentTest searchItemTest= report1.createTest("SearchItemTest");
        homePage.searchWord();  //הכנסת המילה לבאנר החיפוש, לחיצה על אייקון החיפוש
        Thread.sleep(4000);

        searchPage.chooseFirstItem();  //בדף של תוצאות החיפוש, בוחר באפשרות הראשונה של המוצרים
        Thread.sleep(3000);
        try {  //לוודא שהגענו לדף הנכון של המוצר שבחרנו
            Assert.assertEquals(Constants.SPECIFIC_PRODUCT_PAGE_URL, chDriver.getCurrentUrl());
            searchItemTest.pass("Step 1 passed- you got the chosen item page");
        }catch (AssertionError error){
            searchItemTest.fail("Step 1 get item page failed- the page of the chosen product is not displayed", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\screenshots\\"+ currentTime+" search Item page")).build());
        }

        if(productPage.colorIsExist()) {  //בודק האם אפשרות בחירת צבע קיימת, אם כן, בוחר צבע
            productPage.chooseColor(Data.getData("color", 1));  //בוחר בצבע המוצר, מתוך רשימת צבעים בxml
        }
        //Assert.assertEquals("the color is not selected like expected", Data.getData("color",1), chDriver.findElement(productPage.colorFieldLocator).getText());
        productPage.chooseSize(Data.getData("size", 1));  //בוחר מידה למוצר, מתוך הרשימה בטופס xml
        //Assert.assertEquals("the size is not selected like expected", Data.getData("size",1), chDriver.findElement(productPage.sizeFieldLocator).getText());

        productPage.addItemToBucket();  // הפונקציה מוסיפה שני מוצרים לסל (לוחצים פעמיים על הוספת הפריט)
    }

    @Test
    //מעבר לדף סל הקניות
    public void e_goToBucket() throws InterruptedException {
        ExtentTest shoppingBagTest= report1.createTest("ShoppingBagTest");
        productPage.goToBucket();  //לוחץ על לחצן סל הקניות הממוקם בראש הדף- בצורת סל קטן
        Thread.sleep(3000);
        try {  //מוודא שעברנו לדף  של סל הקניות
            Assert.assertEquals("the bucket page is not displayed", Constants.SHOPPING_BAG_PAGE_URL, chDriver.getCurrentUrl());
            shoppingBagTest.pass("Step 1 passed- you got into the shopping bag page");
        } catch (AssertionError error){
            shoppingBagTest.fail("Step 1 shopping bag page failed- the page is not displayed", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\screenshots\\"+ currentTime+" shopping bag page")).build());
        }

        /*shoppingBagPage.choose1Qty();  //משנה בכמות של המוצר הראשון לכמות של מוצר 1
        Thread.sleep(2000);
        Assert.assertEquals("the quantity is not changed like expected", "1", chDriver.findElement(shoppingBagPage.comboBoxQtyFieldLocator).getText());
       */

    }

    @Test
    //דף התחברות מחדש לשם תשלום מאובטח
    public void f_loginCheckout() throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        ExtentTest loginCheckOutTest= report1.createTest("LoginCheckOutTest");
        shoppingBagPage.goToCashPage();  //לוחץ על לחצן 'לקופה'
        Thread.sleep(2000);
        try{  //מוודא שהגענו לדף של כניסה לחשבון לצורך אבטחה
            Assert.assertEquals(Constants.LOGIN_CHECKOUT_PAGE_URL, chDriver.getCurrentUrl());
            loginCheckOutTest.pass("Step 1 passed- you got the login page for secure payment");
        } catch (AssertionError error){
            loginCheckOutTest.fail("Step 1 enter secure login failed- the page is not displayed", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\screenshots\\"+ currentTime+" secure login page")).build());
        }

        Thread.sleep(2000);
        signInPage.signInProcess("username");
        signInPage.signInProcess("password");
        Thread.sleep(2000);
        signInPage.signInProcess("submit");
        Thread.sleep(2000);
        chDriver.navigate().to(Constants.NEXT_SITE_URL);//מנווט בחזרה לדף הראשי, כי האתר לא נותן להמשיך מכאן באוטומציה
        try{  //מוודר שהאתר עבר לדף הבית הראשי
            Assert.assertEquals(Constants.NEXT_SITE_TITLE, chDriver.getTitle());
            loginCheckOutTest.pass("Step 2 enterDetails to secure login passed");
        } catch (AssertionError error){  //במקרה שגיאה יוצג צילום מסך
            loginCheckOutTest.fail("Step 2 enterDetails failed- enter details to secure login isn't passed", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\screenshots\\"+ currentTime+" check out login page")).build());
        }
    }

    @Ignore
    @Test
    //דף בחירת אמצעי תשלום ומילוי פרטי אשראי- ההרצה מעלמת מהטסט כי הוא לא פועל בהרצת אוטומציה
    public void g_CreditPaymentDetails() throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        ExtentTest cardDetailsTest= report1.createTest("CardDetailsTest");
        paymentPage.chooseCreditCard();  //בוחר באפשרות תשלום בכרטיס אשראי
        try{       //בודק האם האפשרות לתשלום בכרטיס נבחרה
            if(!chDriver.findElement(paymentPage.radioCreditCardLocator).isSelected())
                cardDetailsTest.pass("Step 1 passed- the credit card payment is chosen");
        }
        catch (Exception e){
            System.out.println("the credit card payment option is not selected");
            cardDetailsTest.fail("Step 1 choose payment failed- the credit card payment option is not selected", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\screenshots\\"+ currentTime+" credit card page")).build());
        }

        paymentPage.fillCardNumber();
        paymentPage.fillMonth();
        paymentPage.fillYear();
        paymentPage.fillCode();
        paymentPage.clickSubmitPay();
        Thread.sleep(2000);
        try {
            Assert.assertEquals("must display the text", Data.getData("card_hint_text", 0), chDriver.findElement(paymentPage.cardNumberHintLocator).getText());
            cardDetailsTest.pass("Step 2 passed- the hint sentence is displayed");
        } catch (AssertionError error){
            cardDetailsTest.fail("Step 2 display hint sentence failed- the hint sentence isn't displayed", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot("target\\screenshots\\"+ currentTime+" hint massage")).build());
        }
    }

    @After
    public void after(){
        System.out.println("after");
    }

    @AfterClass
    public static void afterClass() throws InterruptedException {
        System.out.println("after class");
        Thread.sleep(5000);
        chDriver.quit();
        report1.flush();
        /* report.flush();*/
    }

    //Methods

    private static String takeScreenShot(String ImagesPath) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) chDriver;
        File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(ImagesPath+".png");
        try {
            FileUtils.copyFile(screenShotFile, destinationFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ImagesPath+".png";
    }
}
