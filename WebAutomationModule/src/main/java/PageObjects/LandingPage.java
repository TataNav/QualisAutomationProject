package PageObjects;

import General.BasePage;
import General.ConfigFileReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

public class LandingPage extends BasePage {
    Actions action = new Actions(driver);

    //Page title
    @FindBy(tagName = "h3")
    private WebElement page_Title;

    //Top bar elements
    @FindBy(xpath = "//div/header/div[2]/div[2]/button/span")
    private WebElement notifyBtn;
    @FindBy(xpath = "//div[@class = 'mat-tab-labels']/div[1]/div")
    private WebElement ordersNotifyTab;
    @FindBy(xpath = "//div[@class = 'mat-tab-labels']/div[2]/div")
    private WebElement fundsNotifyTab;
    @FindBy(xpath = "//div/header/div[2]/span[1]")
    private WebElement loggedInUserSettings;
    @FindBy(xpath = "//span[contains(text(),'Change Password')]")
    private WebElement changePassword;
    @FindBy(xpath = "//span[contains(text(),'Logout')]")
    private WebElement logout;

    //Sidebar elements
    @FindBy(xpath = "//a[contains(text(),'Dashboard')]")
    WebElement dashboard;
    @FindBy(xpath = "//a[contains(text(),'Funds')]")
    private WebElement funds;
    @FindBy(xpath = "//a[contains(text(),'Investors')]")
    private WebElement investors;
    @FindBy(xpath = "//a[contains(text(),'Holdings')]")
    private WebElement holdings;
    @FindBy(xpath = "//a[contains(text(),'Orders')]")
    private WebElement orders;
    @FindBy(xpath = "//a[contains(text(),'Organization')]")
    private WebElement organization;
    @FindBy(xpath = "//a[contains(text(),'Activity Logs')]")
    private WebElement activityLogs;
    @FindBy(xpath = "//a[contains(text(),'Notification Management')]")
    private WebElement notificationManagement;
    //APPROVAL
    @FindBy(xpath = "//a[contains(text(),'Approval')]")
    private WebElement approval;
    @FindBy(xpath = "//span[(text() = 'Investors')]")
    private WebElement investorsapproval;
    @FindBy(xpath = "//span[contains(text(),'Funds')]")
    private WebElement approveFunds;
    //ADMIN
    @FindBy(xpath = "//a[contains(text(),'Admin')]")
    private WebElement admin;
    @FindBy(xpath = "//span[contains(text(),'User')]")
    private WebElement user;
    @FindBy(xpath = "//span[contains(text(),'Role')]")
    private WebElement roleaccess;
    @FindBy(xpath = "//span[contains(text(),'Team')]")
    private WebElement teammanagement;
    @FindBy(xpath = "//span[contains(text(),'APL')]")
    private WebElement APL;
    @FindBy(xpath = "//span[contains(text(),'Events')]")
    private WebElement events;

    public LandingPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    private String GoTo(WebElement locator){
        waitForElementToAppear(locator);
        locator.click();
        if(locator.toString().equalsIgnoreCase("user") || locator.toString().equalsIgnoreCase("admin")){
            driver.navigate().to(ConfigFileReader.getShortenedURL() + "usermanagement/list");
        } else if (locator.toString().equalsIgnoreCase("notificationManagement")){
            driver.navigate().to(ConfigFileReader.getShortenedURL() + "Notification-Managment");
        } else {
            driver.navigate().to(ConfigFileReader.getShortenedURL() + locator.toString());
        }
        waitForElementToAppear(page_Title);
        return page_Title.getText();
    }

    public String GoToSubmenus(WebElement menuItem, WebElement subMenuItem) {
        String currentURL = driver.getCurrentUrl();
        System.out.println(currentURL);
        waitForElementToAppear(menuItem);
        if(!subMenuItem.isDisplayed()){
            menuItem.click();
        }
        waitForElementToAppear(subMenuItem);
        action.moveToElement(subMenuItem).click().build().perform();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentURL)));
        return page_Title.getText();
    }

    //Below are the methods required to navigate through pages
    public String GoToDashboard(){
        return GoTo(dashboard);
    }

    public String GoToFundsPage(){
        return GoTo(funds);
    }

    public String GoToInvestorsPage(){
        return GoTo(investors);
    }

    public String GoToHoldingsPage(){
        return GoTo(holdings);
    }

    public String GoToOrdersPage(){
        return GoTo(orders);
    }

    public String GoToOrganizationPage(){
        return GoTo(organization);
    }

    public String GoToActivityLogsPage(){
        return GoTo(activityLogs);
    }

    public String GoToNotificationManagementPage(){
        return GoTo(notificationManagement);
    }

    public String GoToFundApprovalPage() {
        return GoToSubmenus(approval, approveFunds);
    }

    public String GoToInvestorsApprovalPage(){
        return GoToSubmenus(approval, investorsapproval);
    }

    public String GoToUserManagementPage(){
        return GoToSubmenus(admin, user);
    }

    public String GoToRoleAccessPage(){
        return GoToSubmenus(admin, roleaccess);
    }

    public String GoToTeamManagementPage(){
        return GoToSubmenus(admin, teammanagement);
    }

    public String GoToAPLPage(){
        return GoToSubmenus(admin, APL);
    }

    public String GoToEventsPage(){
        return GoToSubmenus(admin, events);
    }

    public String GoToLoggedInUserSettingsToChangePassword(){
        loggedInUserSettings.click();
        wait.until(ExpectedConditions.visibilityOf(changePassword));
        action.moveToElement(changePassword).click().build().perform();
        waitForElementToAppear(page_Title);
        return page_Title.getText();
    }

    public void GoToLoggedInUserSettingsToLogout() throws IOException {
        loggedInUserSettings.click();
        wait.until(ExpectedConditions.visibilityOf(logout));
        action.moveToElement(logout).click().build().perform();
        wait.until(ExpectedConditions.urlToBe(ConfigFileReader.getURL()));
    }

    public void GetOrdersNotifications(){
        waitForElementToAppear(notifyBtn);
        notifyBtn.click();
        waitForElementToAppear(ordersNotifyTab);
        //action.moveToElement(ordersNotifyTab).click().build().perform();
    }

    public void GetFundsNotifications(){
        if (!fundsNotifyTab.isDisplayed()) {
            waitForElementToAppear(notifyBtn);
            notifyBtn.click();
            waitForElementToAppear(fundsNotifyTab);
        }
        action.moveToElement(fundsNotifyTab).click().build().perform();
    }

    public WebElement getPage_Title(){
        return page_Title;
    }
}
