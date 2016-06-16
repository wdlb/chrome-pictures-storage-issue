package memory.devices;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by ward.delabastita on 16/06/2016.
 */
public class MotoG extends AbstractAndroidDevice {

    public MotoG() {
        super("5.1");
    }

    /* default */ void takePicture() {
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
            handleNotFoundException();
        }
    }
}
