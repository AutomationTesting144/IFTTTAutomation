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
 * Created by 310287808 on 6/9/2017.
 */

public class ColorChangeAll {
    public String IPAddress = "192.168.86.21/api";
    public String HueUserName = "DtPRqP9ZCbVK0sradKByhPs3BwlIfR5bNX9zamFk";
    public String HueBridgeParameterType = "groups/0";
    public String finalURL;
    public String lightStatusReturned;

    public void ColorChangeAll(AndroidDriver driver) throws IOException, JSONException, InterruptedException {
        driver.navigate().back();
        System.out.println("Entered");

        //driver.findElement(By.id("com.ifttt.ifttt:id/widget_do_small_recipe_btn")).click();
        WebElement abc = driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[42,198][198,316]']"));
        abc.click();
        System.out.println("Clicked");

        driver.findElement(By.id("com.ifttt.ifttt:id/note_creation_scroll")).click();
        driver.findElement(By.id("com.ifttt.ifttt:id/note_creation_scroll")).sendKeys("Hello");
        driver.findElement(By.id("com.ifttt.ifttt:id/note_creation_icon")).click();
        WebElement abc1 = driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[16,48][128,176]']"));
        abc1.click();
        TimeUnit.SECONDS.sleep(10);

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

        ColorChangeAllStatus ColorStatus = new ColorChangeAllStatus();
        lightStatusReturned = ColorStatus.ColorChangeStatus(output);
        System.out.println(lightStatusReturned);

        String Xval=lightStatusReturned.substring(1,6);
        String Yval=lightStatusReturned.substring(7,12);
        System.out.println(Xval);
        System.out.println(Yval);
        String Xred="0.675";
        String Yred="0.322";

        if ((Xval.equals(Xred)) && (Yval.equals(Yred))) {
            System.out.println("Test PASS. Color changed to RED for all lights");
        } else {
            System.out.println("Test FAIL. Color is not RED");
        }
    }

}