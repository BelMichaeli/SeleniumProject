package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShoppingBagPage {
    WebDriver chDriver;

    //Constructor
    public ShoppingBagPage(WebDriver driver){
        this.chDriver= driver;
    }

    //Locators
    private By comboBoxQtyLocator= By.xpath("//*[@id=\"dk_container_Qty_2\"]/a");
    private By chooseQty1Locator= By.xpath("//*[@id=\"dk_container_Qty_2\"]/div/ul/li[1]/a");
    private By comboBoxQtyFieldLocator= By.xpath("//*[@id=\"dk_container_Qty_2\"]/a");
    private By toCashBtnLocator= By.xpath("//*[@id=\"pageWidth\"]/div[5]/div[3]/a[3]");

    //Methods
    public void choose1Qty(){  //עבור המוצר הראשון, לבחור כמות של פריט 1
        chDriver.findElement(comboBoxQtyLocator).click();
        chDriver.findElement(chooseQty1Locator).click();
    }

    public void goToCashPage(){  //לחיצה על לחצן 'לקופה'
        chDriver.findElement(toCashBtnLocator).click();
    }
}
