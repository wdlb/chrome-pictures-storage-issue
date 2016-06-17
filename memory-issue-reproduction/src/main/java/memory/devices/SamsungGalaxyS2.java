package memory.devices;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by ward.delabastita on 16/06/2016.
 */
public class SamsungGalaxyS2 extends AbstractAndroidDevice {

    public SamsungGalaxyS2() {
        super("6.0.1");
    }

    /* default */ void takePicture() {
        WebDriverWait cameraWait = new WebDriverWait(driver, 5);
        try {
            cameraWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MENUID_SHUTTER")));
            MobileElement cameraField = (MobileElement) driver.findElement(By.id("MENUID_SHUTTER"));
            cameraField.click();
        } catch (TimeoutException tex) {
            handleNotFoundException();
        }
        WebDriverWait confirmationWait = new WebDriverWait(driver, 15);
        try {
            confirmationWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.sec.android.app.camera:id/okay")));
            MobileElement confirmationButton = (MobileElement) driver.findElement(By.id("com.sec.android.app.camera:id/okay"));
            confirmationButton.click();
        } catch (TimeoutException tex) {
            handleNotFoundException();
        }
    }
}
