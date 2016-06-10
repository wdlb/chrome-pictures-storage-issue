import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ward.delabastita on 08-Jun-16.
 */
public class ReplicationMotoG {

    private static String ANDROID_VERSION = "5.1";

    private AndroidDriver driver;

    @Test
    public void takePicturesUntilFailure() throws IOException, AWTException, InterruptedException {
        printCurrentTime();

        setUp();

        goToTestPage();

        int count = 1;

        for (int i = 0; i < 1000; i++) {
            System.out.println("Picture number " + count);
            addPictureFromCamera();
            count ++;
        }

    }

    private void setUp() throws MalformedURLException {
        // Create object of  DesiredCapabilities class and specify android platform
        DesiredCapabilities capabilities = DesiredCapabilities.android();

        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
        capabilities.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "TA34801FJ6");
        capabilities.setCapability(MobileCapabilityType.VERSION, ANDROID_VERSION);

        // Create object of URL class and specify the appium server address
        URL url = new URL("http://127.0.0.1:4723/wd/hub");

        // Create object of AndroidDriver class and pass the url and capability that we created
        driver = new AndroidDriver(url, capabilities);
    }

    private void goToTestPage() {
        driver.get("http://localhost:8085");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlMatches("http://localhost:8085"));
        assertThat(driver.getTitle()).isEqualToIgnoringCase("fileinput testpage");
    }

    private void addPictureFromCamera() throws InterruptedException {
        driver.context("WEBVIEW_1");
        clickChooseFile();
        driver.context("NATIVE_APP");
        if (isCameraPermissionDialogPresent()) {
            giveCameraPermission();
        }
        chooseCameraOption();
        takePicture();
    }

    private void clickChooseFile() {
        driver.findElement(By.id("file-input-field")).click();
    }

    private boolean isCameraPermissionDialogPresent() {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        boolean isPresent = driver.findElements( By.id("com.android.packageinstaller:id/permission_message") ).size() != 0;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        return isPresent;
    }

    private void giveCameraPermission() {
        MobileElement confirmCameraPermission = (MobileElement) driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button"));
        confirmCameraPermission.click();
    }

    private void chooseCameraOption() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("android:id/resolver_list")));
        MobileElement cameraOption = (MobileElement) driver.findElement(By.xpath("//*[@resource-id='android:id/resolver_list']/android.widget.LinearLayout[@index='0']"));
        cameraOption.click();
    }

    private void takePicture() {
        WebDriverWait cameraWait = new WebDriverWait(driver, 5);
        cameraWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.FrameLayout")));
        MobileElement cameraField = (MobileElement) driver.findElement(By.xpath("//android.widget.FrameLayout"));
        cameraField.click();
        WebDriverWait confirmationWait = new WebDriverWait(driver, 15);
        try {
            confirmationWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.motorola.camera:id/review_approve")));
            MobileElement confirmationButton = (MobileElement) driver.findElement(By.id("com.motorola.camera:id/review_approve"));
            confirmationButton.click();
        } catch (TimeoutException tex) {
            System.out.println("It looks like it went down");
            printCurrentTime();
        }

    }

    private void printCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
    }

}
