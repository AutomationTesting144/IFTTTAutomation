package com.example.a310287808.ankitastrial;


import android.app.Activity;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 6/12/2017.
 */

public class LightsNameChange extends Activity {


    private Spinner spinner1, spinner2;

    private Button btnSubmit;
    MobileElement listItem;
    public void LightsNameChange (AndroidDriver driver) throws IOException, JSONException, InterruptedException  {
        driver.navigate().back();
        driver.findElement(By.xpath("//android.widget.TextView[@bounds='[24,1380][216,1572]']")).click();
        TimeUnit.SECONDS.sleep(2);
        driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[1026,184][1074,232]']")).click();
        TimeUnit.SECONDS.sleep(2);
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[0,552][152,680]']")).click();
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[1080,676][1200,796]']")).click();
        driver.findElement(By.id("com.philips.lighting.hue2:id/form_field_text")).click();
        driver.findElement(By.id("com.philips.lighting.hue2:id/form_field_text")).clear();
        TimeUnit.SECONDS.sleep(5);
        driver.findElement(By.id("com.philips.lighting.hue2:id/form_field_text")).click();
        driver.findElement(By.id("com.philips.lighting.hue2:id/form_field_text")).sendKeys("Testing Lamp"+"\n");
        TimeUnit.SECONDS.sleep(5);
        driver.findElement(By.id("com.philips.lighting.hue2:id/details_device_icon")).click();
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[16,48][128,160]']")).click();
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[16,48][128,160]']")).click();
        driver.navigate().back();

        WebElement abc = driver.findElement(By.xpath("//android.widget.TextView[@bounds='[544,1670][656,1782]']"));
        abc.click();

        driver.findElement(By.xpath("//android.widget.TextView[@text='IFTTT']")).click();
        WebElement abc2 = driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[426,1724][474,1772]']"));
        abc2.click();
        driver.findElement(By.id("com.ifttt.ifttt:id/boxed_edit_text")).click();
        driver.findElement(By.id("com.ifttt.ifttt:id/boxed_edit_text")).sendKeys("Hue" + "\n");
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='Press a button to make your Hue lights color loop']")));
        driver.findElement(By.xpath("//android.widget.TextView[@text='Press a button to make your Hue lights color loop']")).click();
        driver.findElement(By.id("com.ifttt.ifttt:id/edit")).click();

        driver.findElement(By.id("com.ifttt.ifttt:id/spinner_arrow")).click();
        WebElement abc1 = driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[864,1350][912,1398]']"));
        abc1.click();

        //Locate all drop down list elements
        List dropList = driver.findElements(By.id("android:id/text1"));

        for(int i=0; i< dropList.size(); i++){
            listItem = (MobileElement) dropList.get(i);
            if (listItem.getText().equals("Testing Lamp")==true){
                System.out.println("Name changed from ");
            }
            else {
            System.out.println("TEST FAIL: Light didnot renamed to Testing Lamp");}

            }

            //System.out.println(listItem.getText());

        }




    }





