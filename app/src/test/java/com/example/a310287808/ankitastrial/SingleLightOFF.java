package com.example.a310287808.ankitastrial;

import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.Random;

/**
 * Created by 310287808 on 6/7/2017.
 */

public class SingleLightOFF {

    public String IPAddress = "192.168.86.21/api";
    public String HueUserName = "DtPRqP9ZCbVK0sradKByhPs3BwlIfR5bNX9zamFk";
    public String HueBridgeParameterType = "lights/1";
    public String finalURL;
    public String lightStatusReturned;

    public void SingleOFF(WebDriver driver) throws IOException, JSONException, InterruptedException {

        driver.navigate().back();

        driver.findElement(By.xpath("//android.widget.TextView[@text='Facebook']")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("com.facebook.katana:id/feed_composer_status_button")).click();
        driver.findElement(By.id("com.facebook.katana:id/composer_status_text")).click();
        Random rand = new Random();
        int  n = rand.nextInt() + 1;
        String status = String.valueOf(n);
        driver.findElement(By.id("com.facebook.katana:id/composer_status_text")).sendKeys(status);

        driver.findElement(By.id("com.facebook.katana:id/primary_named_button")).click();
        driver.navigate().back();
        TimeUnit.SECONDS.sleep(15);

        HttpURLConnection connection;

        finalURL = "http://" + IPAddress + "/" + HueUserName + "/" + HueBridgeParameterType;
        URL url = new URL(finalURL);
        connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        InputStream stream = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        StringBuffer br = new StringBuffer();

        String line = " ";
        while ((line = reader.readLine()) != null) {
            br.append(line);
        }
        String output = br.toString();
        //System.out.println(output);


        BridgeIndividualLightStateONOFF lOnOff = new BridgeIndividualLightStateONOFF();
        lightStatusReturned=lOnOff.stateONorOFF(output);

        if(lightStatusReturned=="false"){
            System.out.println("Test Pass: Light turned off with the facebook status update");

        }else{
            System.out.println("Test Fail: Light did not turned off with the facebook status update");

        }


    }
}
