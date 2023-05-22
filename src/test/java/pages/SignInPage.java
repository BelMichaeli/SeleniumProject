package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.xml.sax.SAXException;
import utilities.Data;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class SignInPage {

    WebDriver chDriver;

    //Constructor
    public SignInPage(WebDriver driver){
        this.chDriver= driver;
    }

    //locators
    public By iconBtnLocator= By.xpath("//*[@id=\"platform_modernisation_header\"]/header/div[1]/nav/div[4]/div[2]/div/a/img");
    private By exitAccountBtnLocator= By.xpath("//*[@id=\"platform_modernisation_header\"]/header/div[1]/nav/div[4]/div[2]/div[2]/div/div[2]/div[2]/div[2]/a");
    public By userNameFieldLocator= By.name("EmailOrAccountNumber");
    public By passwordFieldLocator= By.id("Password");
    private By submitBtnLocator= By.id("SignInNow");
    public By homePageBtnLocators= By.xpath("//*[@id=\"platform_modernisation_header\"]/header/div[1]/nav/div[1]/a/div[2]/img");


    //methods
    public void enterIcon(){
        chDriver.findElement(iconBtnLocator).click();
    }

    public void enterChangeUser(){
        chDriver.findElement(exitAccountBtnLocator).click();
    }

    public void signInProcess(String value) throws ParserConfigurationException, IOException, SAXException {
        if(value== "username")
            enterSigningDetails(userNameFieldLocator, value);
        else if (value == "password")
            enterSigningDetails(passwordFieldLocator, value);

        else if(value== "submit")
            chDriver.findElement(submitBtnLocator).click();
    }

    //פונקציה להזנת הערכים בשדות של שם משתמש וסיסמה בהתאמה
    public void enterSigningDetails(By detail, String keyName) throws ParserConfigurationException, IOException, SAXException {
        chDriver.findElement(detail).sendKeys(Data.getData(keyName,0));
    }

    public String validationSigningDetails(String value) throws ParserConfigurationException, IOException, SAXException {
        if(value== "username")
            return chDriver.findElement(userNameFieldLocator).getText();
        else if (value == "password") {
            String passText= chDriver.findElement(passwordFieldLocator).getText();//שומר את הטקסט של שדה הסיסמה
            //return chDriver.findElement(passwordFieldLocator).getText();
            return passText;
        }
        return null;
    }
}
