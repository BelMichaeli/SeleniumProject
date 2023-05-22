package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.xml.sax.SAXException;
import utilities.Data;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class HomePage {

    WebDriver chDriver;

    //Constructor
    public HomePage(WebDriver driver){
        this.chDriver= driver;
    }


    //locators
    private By homewearBtnLocator= By.xpath("//*[@id=\"meganav-link-5\"]/div");
    private By salonLinkLocator= By.linkText("סלון");
    private By bathBtnLocator= By.xpath("//*[@id=\"buttonlist1\"]/div/div[2]/div/div/div[3]/a");
    public By premiumBtnLocator= By.xpath("//*[@id=\"meganav-link-6\"]/div");
    private By countryIconLocator= By.xpath("//*[@id=\"platform_modernisation_header\"]/header/div[1]/nav/div[9]/button/img");
    private By englishBtnLocator= By.xpath("//*[@id=\"platform_modernisation_header\"]/header/div[1]/nav/div[10]/div/div[3]/div/div[4]/div/button[2]");
    private By hebrewBtnLocator= By.xpath("//*[@id=\"platform_modernisation_header\"]/header/div[1]/nav/div[10]/div/div[3]/div/div[4]/div/button[1]");
    public By countryDivLocator= By.xpath("//*[@id=\"platform_modernisation_header\"]/header/div[1]/nav/div[10]/div/div[3]");
    private By submitLangLocator= By.xpath("//*[@id=\"platform_modernisation_header\"]/header/div[1]/nav/div[10]/div/div[3]/div/div[5]/button");
    private By searchBoxLocator= By.id("header-big-screen-search-box");
    private By searchBtnLocator= By.xpath("//*[@id=\"header-search-form\"]/button/img");

    //methods
    public void enterHomePage(){
        //לחיצה כפולה
        WebElement a= chDriver.findElement(homewearBtnLocator);
        Actions act= new Actions(chDriver);
        act.doubleClick(a).build().perform();
    }

    public void enterSalon() throws InterruptedException {
        Thread.sleep(3000);
        chDriver.findElement(salonLinkLocator).click();
    }

    public void enterBath(){
        chDriver.findElement(bathBtnLocator).click();
    }

    public void enterPremium(){
        //לחיצה כפולה
        WebElement a= chDriver.findElement(premiumBtnLocator);
        Actions act= new Actions(chDriver);
        act.doubleClick(a).build().perform();
    }

    public void changeLang(){
        chDriver.findElement(countryIconLocator).click();
    }

    public void chooseLanguage(String lang){
        if(lang == "english") {
            System.out.println("enter english btn");
            chDriver.findElement(englishBtnLocator).click();
        }
        else if (lang == "hebrew") {  //לחיצה כמה פעמים על כפתור העברית, כי בלחיצה אחת משום מה לא קולט
            System.out.println("enter hebrew btn");
            chDriver.findElement(hebrewBtnLocator).click();
            chDriver.findElement(hebrewBtnLocator).click();
            chDriver.findElement(hebrewBtnLocator).click();
        }
    }

    public void submitLang(){
        chDriver.findElement(submitLangLocator).click();
    }

    public void searchWord() throws ParserConfigurationException, IOException, SAXException {
        chDriver.findElement(searchBoxLocator).sendKeys(Data.getData("search", 0));
        chDriver.findElement(searchBtnLocator).click();  //לחיצה על חיפוש- זכוכית מגדלת
    }
}
