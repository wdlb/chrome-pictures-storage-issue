package memory;

import memory.devices.AbstractAndroidDevice;
import memory.devices.MotoG;
import memory.devices.SamsungGalaxyS2;
import org.junit.Test;

import static memory.Utils.printCurrentTime;

/**
 * Created by ward.delabastita on 16/06/2016.
 */
public class TestRunner {

    @Test
    public void runMotoG() {
        runWith(new MotoG());
    }

    @Test
    public void runSamsungGalaxyS2() {
        runWith(new SamsungGalaxyS2());
    }

    private void runWith(AbstractAndroidDevice device) {
        printCurrentTime();
        device.navigateTo(Configuration.URL_TESTPAGE);
        int count = 1;
        for (int i = 0; i < 1000; i++) {
            System.out.println("Picture number " + count);
            device.selectPictureFromCamera();
            count++;
        }
        device.close();
        printCurrentTime();
    }

}
