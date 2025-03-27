/************************************************
 * dev: Nicholas Perez
 * desc: Code for test 2 Software testing
 * Date: 3/20/2025
 *********************************************/

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.*;

import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Test2 {

    private WebDriver driver;
    
    @BeforeMethod
    //Setup function for drivers and window
    public void setup() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\sting\\webdrivers\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    //Test 1 worth 20 points
    public void test_1() throws InterruptedException {
        //Get website and click home link
        driver.get("https://demoblaze.com/");
        Thread.sleep(2000);

        WebElement homeLink = driver.findElement(By.className("nav-link"));
        homeLink.click();
        Thread.sleep(2000);

        //Scroll down
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000);");

        //Click Samsung Galaxy S6 link
        WebElement samsungLink = driver.findElement(By.className("hrefch"));
        samsungLink.click();
        Thread.sleep(2000);

        WebElement addToCart = driver.findElement(By.xpath("//*[@id=\"tbodyid\"]/div[2]/div/a"));
        addToCart.click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();

        //Print current URL and page title
        String url = driver.getCurrentUrl();
        String pageTitle = driver.getTitle();
        Dimension pageSize = driver.manage().window().getSize();
        List<WebElement> allElements = driver.findElements(By.xpath("//*"));

        System.out.println("Current URL: " + url + "\n" + "Current Page Title: " + pageTitle);
        System.out.println("Current Page Size: " + pageSize);
        System.out.println("Window Height: " + pageSize.getHeight());
        System.out.println("Window Width: " + pageSize.getWidth());

        for(WebElement element: allElements){
            System.out.println("Tag: " + element.getTagName());
        }
    }

    @Test(priority = 2)
    //Test 2 worth 20 points
    public void test_2() throws InterruptedException {
        driver.get("https://www.saucedemo.com/");

        //Input valid/invalid username and password
        WebElement username = driver.findElement(By.id("user-name"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement login = driver.findElement(By.id("login-button"));

        //Valid login credentials
        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        Thread.sleep(1000);
        login.click();
        Thread.sleep(2000);

        //Click burger element on dashboard
        WebElement burgerElement = driver.findElement(By.id("react-burger-menu-btn"));
        burgerElement.click();
        Thread.sleep(1000);

        //Logout
        WebElement logout = driver.findElement(By.id("logout_sidebar_link"));
        logout.click();
        Thread.sleep(2000);

        //Invalid login credentials
        //Verify first that it is on the main web page
        //Must refind elements after page refresh to avoid stale reference
        username = driver.findElement(By.id("user-name"));
        password = driver.findElement(By.id("password"));
        login = driver.findElement(By.id("login-button"));

        username.sendKeys("Nicholas Perez");
        password.sendKeys("123456");
        Thread.sleep(2000);
        login.click();
        Thread.sleep(2000);
    }

    @Test(priority = 3)
    //Test 3 worth 20 points
    public void test_3() throws InterruptedException, IOException {
        //Get link and click on elements dropdown
        driver.get("https://www.tutorialspoint.com/selenium/practice/selenium_automation_practice.php");
        WebElement dropdown = driver.findElement(By.className("accordion-button"));
        dropdown.click();
        Thread.sleep(1000);

        //Click on the Link element in the dropdown
        WebElement link = driver.findElement(By.linkText("Links"));
        link.click();
        Thread.sleep(2000);

        String url = driver.getCurrentUrl();
        System.out.println("Current URL: " + url);
        Thread.sleep(1000);

        //Navigate to Home link
        String parentHandle = driver.getWindowHandle(); //Curent web handle
        WebElement homeLink = driver.findElement(By.linkText("Home"));
        homeLink.click();

        Set<String> handles = driver.getWindowHandles();
        for(String handle: handles){
            //Switch to new Home web handle if current handle is parent
            if(!handle.equals(parentHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        //Take screenshot of Home link page
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File("C:\\Users\\sting\\Downloads\\screenshot.png");
        FileHandler.copy(screenshot, destFile);

        //Make sure the file is in my download directory
        Assert.assertTrue(destFile.exists());
    }

    @Test(priority = 4)
    //Test 4 worth 20 points
    public void test_4() throws InterruptedException, IOException {
        driver.get("https://www.tutorialspoint.com/selenium/practice/selenium_automation_practice.php");

        //Navigate to Upload and Download Link
        WebElement dropdown = driver.findElement(By.className("accordion-button"));
        dropdown.click();
        Thread.sleep(1000);
        WebElement link = driver.findElement(By.linkText("Upload and Download"));
        link.click();
        Thread.sleep(2000);

        //Send apple.png to the upload box
        WebElement fileInput = driver.findElement(By.id("uploadFile"));
        fileInput.sendKeys("C:\\Users\\sting\\Downloads\\apple.jpg");
        Thread.sleep(3000);
    }

    @Test(priority = 5)
    //Test 5 worth 20 points
    public void test_5() throws InterruptedException, IOException {
        driver.get("https://jqueryui.com/sortable/");
        Thread.sleep(2000);

        //Navigate to Draggable link
        WebElement dragNdrop = driver.findElement(By.linkText("Draggable"));
        dragNdrop.click();

        //Find box and drag the box around
        //First, switch to the iframe
        driver.switchTo().frame(0);

        WebElement draggableBox = driver.findElement(By.id("draggable"));
        Actions drag = new Actions(driver);
        Thread.sleep(2000);

        drag.clickAndHold(draggableBox).moveByOffset(50,100)
                .release().perform();
        Thread.sleep(2000);

        driver.switchTo().defaultContent();

        //Navigate to Resizeable link
        WebElement resize = driver.findElement(By.linkText("Resizable"));
        resize.click();
        Thread.sleep(2000);

        //Resize box but first switch to the iframe
        driver.switchTo().frame(0);

        WebElement resizeHandle = driver.findElement(By.className("ui-resizable-handle"));
        Actions resizable = new Actions(driver);
        resizable.clickAndHold(resizeHandle).moveByOffset(50,100)
                .release().perform();
        Thread.sleep(2000);

        driver.switchTo().defaultContent();

        //Switch to Selectable link
        WebElement select = driver.findElement(By.linkText("Selectable"));
        select.click();
        Thread.sleep(2000);

        //Switch to ifram and create list of selectable items and select top 3
        driver.switchTo().frame(0);
        List<WebElement> selectable = driver.findElements(By.cssSelector("#selectable li"));
        Actions selectMultiple = new Actions(driver);
        //Create an action that holds control to select multiple list elements
        selectMultiple.keyDown(Keys.CONTROL)
                .click(selectable.get(0))
                .click(selectable.get(2))
                .click(selectable.get(4))
                .keyUp(Keys.CONTROL)
                .build().perform();
        Thread.sleep(2000);
        driver.switchTo().defaultContent();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.close();
        }
        System.out.println("Test performed successfully");
    }
}
