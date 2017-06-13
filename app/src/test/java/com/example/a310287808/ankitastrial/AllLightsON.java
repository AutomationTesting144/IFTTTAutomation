package com.example.a310287808.ankitastrial;

import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 6/7/2017.
 */

public class AllLightsON {
    public String IPAddress = "192.168.86.21/api";
    public String HueUserName = "DtPRqP9ZCbVK0sradKByhPs3BwlIfR5bNX9zamFk";
    public String HueBridgeParameterType = "groups/0";
    public String finalURL;
    public String lightStatusReturned;

    public void AllLightsONorOFF(AndroidDriver driver) throws IOException, JSONException, InterruptedException {
        driver.navigate().back();
        WebElement abc = driver.findElement(By.xpath("//android.widget.TextView[@bounds='[544,1670][656,1782]']"));
        abc.click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Gmail']")).click();
        driver.findElement(By.id("com.google.android.gm:id/compose_button")).click();
        driver.findElement(By.id("com.google.android.gm:id/to")).click();
        driver.findElement(By.id("com.google.android.gm:id/to")).sendKeys("ifttt1automation@gmail.com");
        driver.findElement(By.id("com.google.android.gm:id/subject")).click();
        driver.findElement(By.id("com.google.android.gm:id/subject")).sendKeys("hello");
        driver.findElement(By.id("com.google.android.gm:id/composearea_tap_trap_bottom")).click();
        driver.findElement(By.id("com.google.android.gm:id/composearea_tap_trap_bottom")).sendKeys("hello");
        driver.findElement(By.id("com.google.android.gm:id/send")).click();
        driver.navigate().back();

        TimeUnit.SECONDS.sleep(90);

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

        BridgeALLONOFF ballonoff = new BridgeALLONOFF() ;
        lightStatusReturned= ballonoff.AllONOFFStatus(output);

        if(lightStatusReturned=="true"){
            System.out.println("Test PASS. All Lights are On after receiving mail");
        }else{
            System.out.println("Test FAIL. Applet did not trigerred after receiving  mail.");
        }
    }
}
