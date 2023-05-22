package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.xml.sax.SAXException;
import utilities.Data;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class PaymentPage {

    WebDriver chDriver;

    //Constructor
    public PaymentPage(WebDriver driver){
        this.chDriver= driver;
    }

    //Locators
    public By radioCreditCardLocator= By.id("PaymentId");
    private By cardNumberLocator= By.id("cardNumber");
    private By expiryMonthLocator= By.id("expiryMonth");
    private By expiryYearLocator= By.id("expiryYear");
    private By securityCodeLocator= By.id("securityCode");
    private By submitPayBtnLocator= By.xpath("//*[@id=\"submitButton\"]");
    public By cardNumberHintLocator= By.id("cardNumber-hint");

    //Methods
    public void chooseCreditCard(){
        chDriver.findElement(radioCreditCardLocator).click();
    }

    public void fillCardNumber() throws ParserConfigurationException, IOException, SAXException {
        chDriver.findElement(cardNumberLocator).sendKeys(Data.getData("card_number", 0));
    }

    public void fillMonth() throws ParserConfigurationException, IOException, SAXException {
        chDriver.findElement(expiryMonthLocator).sendKeys(Data.getData("date_month", 0));
    }

    public void fillYear() throws ParserConfigurationException, IOException, SAXException {
        chDriver.findElement(expiryYearLocator).sendKeys(Data.getData("date_year", 0));
    }

    public void fillCode() throws ParserConfigurationException, IOException, SAXException {
        chDriver.findElement(securityCodeLocator).sendKeys(Data.getData("code", 0));
    }

    public void clickSubmitPay(){
        chDriver.findElement(submitPayBtnLocator).click();
    }

}
