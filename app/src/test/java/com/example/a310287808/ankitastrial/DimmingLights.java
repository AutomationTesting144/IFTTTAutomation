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
 * Created by 310287808 on 6/12/2017.
 */

public class DimmingLights {
    public String IPAddress = "192.168.86.21/api";
    public String HueUserName = "DtPRqP9ZCbVK0sradKByhPs3BwlIfR5bNX9zamFk";
    public String HueBridgeParameterType = "groups/0";
    public String finalURL;
    public String lightStatusReturned;

    public void DimmingLights(AndroidDriver driver) throws IOException, JSONException, InterruptedException {
        driver.navigate().back();

        //driver.findElement(By.id("com.ifttt.ifttt:id/widget_do_small_recipe_btn")).click();
        WebElement abc = driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[234,198][390,316]']"));
        abc.click();
        System.out.println("Clicked");

        TimeUnit.SECONDS.sleep(20);

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

        DimmingLightStatus DimStatus=new DimmingLightStatus();
        lightStatusReturned = DimStatus.DimmingLightStatus(output);
        System.out.println(lightStatusReturned);


        if (lightStatusReturned.equals("127")) {
            System.out.println("Test PASS. Brightness is set to 50%  ");
        } else {
            System.out.println("Test FAIL. Brightness is not 50% ");
        }
    }
}
