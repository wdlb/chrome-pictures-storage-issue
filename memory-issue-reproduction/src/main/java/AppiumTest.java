import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ward.delabastita on 07-Jun-16.
 */
public class AppiumTest {

    @Test
    public void goToSeleniumHomepage() throws MalformedURLException {

        // Create object of  DesiredCapabilities class and specify android platform
        DesiredCapabilities capabilities = DesiredCapabilities.android();

        // set the capability to execute test in chrome browser
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);

        // set the capability to execute our test in Android Platform
        capabilities.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);

        // we need to define platform name
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");

        // Set the device name as well (you can give any name)
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "device_test");

        // set the android version as well
        capabilities.setCapability(MobileCapabilityType.VERSION, "5.1");

        // Create object of URL class and specify the appium server address
        URL url = new URL("http://127.0.0.1:4723/wd/hub");

        // Create object of  AndroidDriver class and pass the url and capability that we created
        WebDriver driver = new AndroidDriver(url, capabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // Go to Selenium homepage
        driver.get("http://seleniumhq.org");
        assertThat(driver.getTitle()).isEqualToIgnoringCase("selenium - web browser automation");

        // Go to Download tab
        WebElement downloadTab = driver.findElement(By.id("menu_download"));
        WebElement downloadLink = downloadTab.findElement(By.tagName("a"));
        downloadLink.click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlMatches("http://docs.seleniumhq.org/download/"));
        assertThat(driver.getTitle()).isEqualTo("Downloads");

        // close the browser
        driver.quit();
    }

}
