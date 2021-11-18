package PageObjects;

import General.Helper;
import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyPairGeneratorSpi;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;

public class OrdersPage {
    Actions action;
    Select select;
    private LandingPage landingPage;
    private WebDriver driver;

    //"orders" screen elements
    @FindBy(xpath = "//button/span[contains(text(),'Add New')]")
    private WebElement addNewBtn;
    @FindBy(xpath = "//button/span[contains(text(),'Last Updated At')]")
    private WebElement lastUpdatedAt;
    @FindBy(xpath = "//mat-row[1]")
    private WebElement lastOrder;
    @FindBy(xpath = "//mat-row[1]/mat-cell")
    private List<WebElement> orderCells;
    //"Order Details" screen elements
    //@FindBy(xpath = "//input[@id= 'mat-input-3']")
    @FindBy(xpath = "//input[@name='fundName']")
    private WebElement fundName;
    @FindBy(xpath = "//div[@class='cdk-overlay-pane']/mat-dialog-container/app-pickup-list/div/div[1]/div/mat-form-field/div/div[1]/div/input")
    private WebElement search;
    @FindBy(xpath = "//div[@class='cdk-overlay-pane']/mat-dialog-container/app-pickup-list/div/div[2]/div/mat-table/mat-row")
    private WebElement rowData;
    @FindBy(xpath = "//input[@name='investorAccountName']")
    private WebElement investorIdentification;
    @FindBy(xpath = "//mat-select[@name='orderEventId']")
    private WebElement event;
    @FindBy(xpath = "//mat-option/span[contains(text(),'Redemption')]")
    private WebElement redemption;
    @FindBy(xpath = "//mat-select[@name='redemptionType']")
    private WebElement redemptionType;
    @FindBy(xpath = "//mat-option/span[contains(text(),'Units')]")
    private WebElement units;
    @FindBy(xpath = "//mat-option/span[contains(text(), 'Full Redemption')]")
    private WebElement fullRedemption;
    @FindBy(xpath = "//mat-option/span[contains(text(), 'Value')]")
    private WebElement value;
    @FindBy(xpath = "//mat-form-field/div/div[1]/div[2]/input")
    private WebElement subscription;
    @FindBy(xpath = "//form/div[2]/div[2]/mat-form-field/div/div/div/input")
    private WebElement datePicker;
    @FindBy(xpath = "//form/div[3]/div/mat-form-field/div/div[1]/div/textarea")
    private WebElement comment;
    @FindBy(xpath = "//button/span[contains(text(),'Save')]")
    private WebElement save;
    //"Documents" screen elements
    @FindBy(xpath = "//mat-step-header/div[3][contains(text(),'Documents')]")
    private WebElement documents;
    @FindBy(xpath = "//button[contains(text(),'Select a file')]")
    private WebElement selectFile;
    @FindBy(xpath = "//button[contains(text(),'Add Other Documents')]")
    private WebElement addOtherDocument;
    @FindBy(xpath = "//mat-card[2]/div/div[1]/mat-form-field/div/div[1]/div/input")
    private WebElement documentDescription;
    @FindBy(xpath = "//button/span[contains(text(),'Done')]")
    private WebElement done;
    @FindBy(xpath = "//button/span[contains(text(),'Request Approval')]")
    private WebElement requestApproval;
    @FindBy(xpath = "//ul/li/notifier-notification/p")
    private WebElement notification;

    //Edit order
    @FindBy(xpath = "//div/span[contains(text(),'Status')]")
    private WebElement orderStatus;
    @FindBy(xpath = "//button/span[contains(text(),'Save')]")
    private WebElement saveChanges;
    @FindBy(tagName = "select")
    private WebElement actionList;
    @FindBy(xpath = "//mat-form-field/div/div[1]/div/textarea")
    private WebElement comments;

    //Filter
    @FindBy(xpath = "//app-list/div[2]/span/mat-icon")
    private WebElement filter;
    @FindBy(xpath = "//mat-expansion-panel-header/span[1]/mat-panel-title[contains(text(), 'Status')]")
    private WebElement status;
    @FindBy(xpath = "//div[10]/mat-checkbox/label/span")
    private WebElement orderCompleted;
    @FindBy(xpath = "//button/span[contains(text(),'Ok')]")
    private WebElement ok;

    public OrdersPage(WebDriver driver){
        this.driver = driver;
        landingPage = new LandingPage(driver);
        action = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public void AddNewOrder(){
        landingPage.waitForElementIsClickable(addNewBtn);
        addNewBtn.click();
    }

    public void AddFundName(String fundNameToBeAdded) throws InterruptedException {
        landingPage.waitForElementIsClickable(fundName);
        Thread.sleep(1000);
        fundName.click();
        landingPage.waitForElementIsClickable(search);
        search.clear();
        search.sendKeys(fundNameToBeAdded, Keys.ENTER);
        landingPage.waitForElementIsClickable(rowData);
        rowData.click();
        landingPage.waitForElementToAppear(fundName);
    }

    public void AddInvestorIdentification(String investorToBeAdded) throws InterruptedException {
        Thread.sleep(1000);
        investorIdentification.click();
        landingPage.waitForElementIsClickable(search);
        search.clear();
        search.sendKeys(investorToBeAdded, Keys.ENTER);
        landingPage.waitForElementToAppear(rowData);
        rowData.click();
        landingPage.waitForElementToAppear(investorIdentification);
    }

    public void SelectEvent(String eventType){
        landingPage.waitForElementIsClickable(event);
        event.click();
        landingPage.waitForElementToAppear(redemption);
        switch (eventType){
            case "Redemption":
            case "redemption":
                redemption.click();
                break;
            case "Subscription":
            case "subscription":
                subscription.click();
                break;
        }
        landingPage.waitForElementToAppear(event);
    }

    public void InsertEventDetails(String eventType){
        switch (eventType){
            case "Full Redemption":
            case "full redemption":
                landingPage.waitForElementIsClickable(redemptionType);
                redemptionType.click();
                landingPage.waitForElementIsClickable(fullRedemption);
                fullRedemption.click();
                landingPage.waitForElementToAppear(redemptionType);
                break;
            case "Subscription":
            case "subscription":
                landingPage.waitForElementToAppear(subscription);
                subscription.clear();
                subscription.sendKeys("1000");
                datePicker.sendKeys(Keys.TAB);
        }
    }

    public void PickDate(String date){
        landingPage.waitForElementIsClickable(datePicker);
        datePicker.click();
        datePicker.clear();
        datePicker.sendKeys(date);
        datePicker.sendKeys(Keys.TAB);
    }

    public void AddComment(String commentText){
        landingPage.waitForElementToAppear(comment);
        comment.clear();
        comment.sendKeys(commentText);
        comment.sendKeys(Keys.TAB);
    }

    public void Save(){
        landingPage.waitForElementIsClickable(save);
        save.click();
    }

    public void AddDocument() throws AWTException, InterruptedException {
        landingPage.waitForElementToAppear(documentDescription);
        documentDescription.sendKeys("Document description");
        documentDescription.sendKeys(Keys.TAB);
        Thread.sleep(500);
        action.moveToElement(addOtherDocument).click().build().perform();
        //TODO add path using "user.dir"
        String path = "C:\\QualisAutomationProject\\WebAutomationModule\\src\\test\\testResources\\Redemption.pdf";
        Helper.FileUpload_Robo(path);
    }

    public void RequestApproval() throws InterruptedException {
        landingPage.CloseNotification();
        requestApproval.click();
    }

    public String GetStatusOfOrder() {
        landingPage.waitForElementIsClickable(lastUpdatedAt);
        landingPage.waitForElementIsClickable(lastOrder);
        driver.navigate().refresh();
        String status = orderCells.get(7).getText();
        return status;
    }

    public String GetNotification(){
        landingPage.waitForElementToAppear(notification);
        return notification.getText();
    }

    public void EditLastOrder(String action){
        landingPage.waitForElementIsClickable(lastOrder);
        lastOrder.click();
        landingPage.waitForElementToAppear(orderStatus);
        select = new Select(actionList);
        select.selectByValue(action);
        if(!action.equalsIgnoreCase("Approve")){
            landingPage.waitForElementIsClickable(comments);
            comments.sendKeys("Confirmation");
            comments.sendKeys(Keys.TAB);
        }
        landingPage.waitForElementIsClickable(saveChanges);
        saveChanges.click();
    }

    public void FilterCompletedOrder() throws InterruptedException {
        landingPage.waitForElementIsClickable(filter);
        filter.click();
        landingPage.waitForElementIsClickable(status);
        status.click();
        landingPage.waitForElementIsClickable(orderCompleted);
        if(!orderCompleted.isSelected()){
            Thread.sleep(200);
            orderCompleted.click();
        }
        landingPage.waitForElementIsClickable(ok);
        ok.click();
    }

    public LandingPage GetLandingPage(){
        return landingPage;
    }
}
