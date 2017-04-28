package memory.devices;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import memory.Configuration;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static memory.Utils.printCurrentTime;

/**
 * Created by ward.delabastita on 16/06/2016.
 */
public abstract class AbstractAndroidDevice {

    /* default */ final AndroidDriver driver;

    /* default */ AbstractAndroidDevice(String androidVersion) {
        this.driver = instantiateDriver(androidVersion);
    }

    private AndroidDriver instantiateDriver(String androidVersion) {
        DesiredCapabilities capabilities = DesiredCapabilities.android();
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
        capabilities.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "device_name");
        capabilities.setCapability(MobileCapabilityType.VERSION, androidVersion);

        // Create object of URL class and specify the appium server address
        URL url = null;
        try {
            url = new URL(Configuration.URL_APPIUM_SERVER);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Create object of AndroidDriver class and pass the url and capability that we created
        return new AndroidDriver(url, capabilities);
    }

    public void navigateTo(String url) {
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlMatches(url));
    }

    public void selectPictureFromCamera() {
        driver.context("WEBVIEW_1");
        clickChooseFile();
        driver.context("NATIVE_APP");
        if (isCameraPermissionDialogPresent()) {
            giveCameraPermission();
        }
        chooseCameraOption();
        takePicture();
    }

    public void close() {
        driver.quit();
    }

    private void clickChooseFile() {
        driver.findElement(By.id("file-input-field")).click();
    }

    private boolean isCameraPermissionDialogPresent() {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        boolean isPresent = driver.findElements(By.id("com.android.packageinstaller:id/permission_message")).size() != 0;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return isPresent;
    }

    private void giveCameraPermission() {
        MobileElement confirmCameraPermission = (MobileElement) driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button"));
        confirmCameraPermission.click();
    }

    private void chooseCameraOption() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/resolver_list")));
            MobileElement cameraOption = (MobileElement) driver.findElement(By.xpath("//*[@resource-id='android:id/resolver_list']/android.widget.LinearLayout[@index='0']"));
            cameraOption.click();
        } catch (TimeoutException tex) {
            handleNotFoundException();
        }
    }

    abstract void takePicture();

    /* default */ void handleNotFoundException() {
        System.out.println("It looks like it went down");
        printCurrentTime();
        screenshot();
    }

    private void screenshot() {
        File srcFile = driver.getScreenshotAs(OutputType.FILE);
        String filename = UUID.randomUUID().toString();
        File targetFile = new File(Configuration.LOCATION_SCREENSHOT + filename + ".jpg");
        try {
            FileUtils.copyFile(srcFile, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
