package com.example.a310287808.ankitastrial;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ControllingScript {

    //WebDriver driver;
    AndroidDriver driver;
    Dimension size;
    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Nexus 7");
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
        capabilities.setCapability(CapabilityType.VERSION, "6.0.1");
        capabilities.setCapability("platformName", "Android");
        //capabilities.setCapability("autoAcceptAlters",true);
        capabilities.setCapability("appPackage", "com.google.android.calculator");
        capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");
        //capabilities.setCapability("appActivity", "com.ifttt.ifttt.doandroid.WidgetSearchActivity");

//        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
//        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

    }


    @Test
    public void testRuns() throws Exception {

        // Whitelist Test case
       /* Whitelist_UserFriendly wu = new Whitelist_UserFriendly();
        wu.CheckUserFriendlyWL();*/

        //Connection flow test case
        //RemoteConnectionFlow rc = new RemoteConnectionFlow();
        //rc.remoteConnection(driver);

        //Individual Lights ON or OFF
        /*IndividualLightONOFFTest Lonoff = new IndividualLightONOFFTest();
        Lonoff.IndividualTestONorOFF(driver);*/

        //All Lights ON or OFF


//        AllLightsOFF AllOFF = new AllLightsOFF();
//        AllOFF.AllLightsONorOFF(driver);
//
//        AllLightsON AllON = new AllLightsON();
//        AllON.AllLightsONorOFF(driver);

//        SingleLightOFF SingleOFF= new SingleLightOFF();
//        SingleOFF.SingleOFF(driver);
//
//        SingleLightON SingleON= new SingleLightON();
//        SingleON.SingleON(driver);

//          ColorChangeAll colorStatus=new ColorChangeAll();
//          colorStatus.ColorChangeAll(driver);

//          ColorChangeSingle SingleStatus=new ColorChangeSingle();
//          SingleStatus.ColorChangeSingle(driver);

//        DimmingLights DimLights=new DimmingLights();
//        DimLights.DimmingLights(driver);

//        LightsNameChange NameChange=new LightsNameChange();
//        NameChange.LightsNameChange(driver);
////
//        LightDelete delete=new LightDelete();
//        delete.LightDelete(driver);

        LightAddition addition=new LightAddition();
        addition.LightAddition(driver);


        //Check new whitelist
        // NewWhiteListCreation nwc = new NewWhiteListCreation();
//        nwc.checkNewWhiteList();

    }

  /*  @After
    public void Closing(){
        driver.close();

    }*/
}