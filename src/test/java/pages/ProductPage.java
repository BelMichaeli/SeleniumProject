package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ProductPage {

    WebDriver chDriver;

    //Constructor
    public ProductPage(WebDriver driver){
        this.chDriver= driver;
    }

    //Locators
    private By comboBoxColorLocator= By.id("dk_container_Colour-192064");
    private By colorFieldLocator= By.xpath("//*[@id=\"dk_container_Colour-192064\"]/a");
    private By color2Locator= By.xpath("//*[@id=\"dk_container_Colour-192064\"]/div/ul/li[2]/a");
    private By comboBoxSizeLocator= By.id("dk_container_Size-U94-265");
    private By size6_7Locator= By.xpath("//*[@id=\"dk_container_Size-U94-265\"]/div/ul/li[11]/a");
    private By sizeFieldLocator= By.xpath("//*[@id=\"dk_container_Size-U94-265\"]/a");
    private By addItemBtnLocator= By.xpath("//*[@id=\"Style192064\"]/section/div[4]/div[4]/div[4]/div/a[1]");
    private By goToBuketLocator= By.linkText("הצג/ערוך סל");

    //קישור בראש העמוד לחנות
    private By shoppingBagLinkLocator= By.xpath("//*[@id=\"platform_modernisation_header\"]/header/div[1]/nav/div[7]/div[2]/a");

    //Methods
    public void chooseColor(String value) throws InterruptedException {
        /*Select selectColor= new Select(chDriver.findElement(comboBoxColorLocator));
        selectColor.selectByVisibleText(value);*/
        Thread.sleep(2000);
        chDriver.findElement(comboBoxColorLocator).click();
        Thread.sleep(1000);
        chDriver.findElement(color2Locator).click();
    }

    public void chooseSize(String value) throws InterruptedException {
        /*Select selectColor= new Select(chDriver.findElement(comboBoxSizeLocator));
        selectColor.selectByVisibleText(value);*/
        chDriver.findElement(comboBoxSizeLocator).click();
        Thread.sleep(1000);
        chDriver.findElement(size6_7Locator).click();
    }

    public void addItemToBucket(){
        chDriver.findElement(addItemBtnLocator).click(); //הוספה של המוצר לסל
        chDriver.findElement(addItemBtnLocator).click(); //הוספה נוספת של המוצר לסל
    }

    public void goToBucket() throws InterruptedException {
        chDriver.findElement(shoppingBagLinkLocator).click();  //לחיצה על הקישור בראש הדף, המוביל לסל הקניות (בצורת סל קטן)
        Thread.sleep(3000);
        chDriver.findElement(goToBuketLocator).click();  //לחיצה על הקיישור בדיב שנפתח, המוביל ל- הצג/ערוך סל
    }

    public Boolean colorIsExist(){
        Boolean flag= false;
        if(chDriver.findElement(comboBoxColorLocator).isDisplayed())
            flag= true;

        return flag;
    }
}
