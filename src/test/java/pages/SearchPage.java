package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class SearchPage {

    WebDriver chDriver;

    //Constructor
    public SearchPage(WebDriver driver){
        this.chDriver= driver;
    }

    //Locators
    public By searchResultTitleLocator = By.xpath("//*[@id=\"plp-results-title-container\"]/h1/span[1]");
    private By firstResultLocator= By.xpath("//*[@id=\"platform_modernisation_product_summary_C22797\"]/div/div[1]/div[1]/div/div/div[1]/a/img");

    //Methods
    public void chooseFirstItem(){
        //לחיצה כפולה
        WebElement a= chDriver.findElement(firstResultLocator);
        Actions act= new Actions(chDriver);
        act.doubleClick(a).build().perform();
    }
}
