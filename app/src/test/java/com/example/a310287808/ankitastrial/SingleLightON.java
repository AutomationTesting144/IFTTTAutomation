package com.example.a310287808.ankitastrial;

import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 6/7/2017.
 */

public class SingleLightON {
    public String IPAddress = "192.168.86.21/api";
    public String HueUserName = "DtPRqP9ZCbVK0sradKByhPs3BwlIfR5bNX9zamFk";
    public String HueBridgeParameterType = "lights/1";
    public String finalURL;
    public String lightStatusReturned;
    public String StrMin;
    public String StrHrs;
    public String TimeSys1;

    public  void SingleON(AndroidDriver driver) throws IOException, JSONException, InterruptedException, ParseException {
        driver.navigate().back();
        WebElement abc = driver.findElement(By.xpath("//android.widget.TextView[@bounds='[544,1670][656,1782]']"));
        abc.click();

        driver.findElement(By.xpath("//android.widget.TextView[@text='IFTTT']")).click();
        WebElement abc2 = driver.findElement(By.xpath("//android.widget.LinearLayout[@bounds='[300,1712][600,1824]']"));
        abc2.click();
        driver.findElement(By.id("com.ifttt.ifttt:id/boxed_edit_text")).click();
        driver.findElement(By.id("com.ifttt.ifttt:id/boxed_edit_text")).sendKeys("Turn your lights on every day at a certain time"+ "\n");
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='Turn your lights on every day at a certain time']")));
        driver.findElement(By.xpath("//android.widget.TextView[@text='Turn your lights on every day at a certain time']")).click();
        driver.findElement(By.id("com.ifttt.ifttt:id/edit")).click();

        WebElement abc1 = driver.findElement(By.xpath("//android.widget.TextView[@bounds='[288,1314][912,1348]']"));
        abc1.click();

        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.className("android.view.View")));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        String TimeSys=sdf.format(cal.getTime());
        String hrs=sdf.format(cal.getTime()).substring(0,2);
        String min=sdf.format(cal.getTime()).substring(3,5);
        String AmPm=sdf.format(cal.getTime()).substring(6,8);


        Integer Minutes = Integer.valueOf(min);
        Integer hours = Integer.valueOf(hrs);
        DecimalFormat df = new DecimalFormat("00");

        System.out.println("Entering Loop");
        if ((Minutes>=0) && (Minutes<=14)){
            Minutes=15;
            System.out.println(Minutes);
        }
        else if ((Minutes>14) && (Minutes<=29)){
            Minutes=30;
            System.out.println(Minutes);

        }
        else if ((Minutes>29) && (Minutes<=44)){
            Minutes=45;
            System.out.println(Minutes);
        }
        else if((Minutes>44) && (Minutes<=59)){
            Minutes=00;
            hours=hours+1;
            System.out.println(hours);
        }
        System.out.println("Out of Loop");

        if(hours<10){
            StrHrs=(df.format(hours));
            System.out.println(StrHrs);
        }
        else {
            StrHrs=String.valueOf(hours);
            System.out.println(StrHrs);
        }
        System.out.println(StrHrs);
        Minutes=Minutes+2;
        //Minutes=Minutes+1;
        StrMin=(df.format(Minutes));

        driver.findElement(By.id("com.ifttt.ifttt:id/hours")).click();
        driver.findElement(By.id("com.ifttt.ifttt:id/hours")).sendKeys(StrHrs+StrMin+AmPm+ "\n");
        WebElement abc3 = driver.findElement(By.xpath("//android.widget.TextView[@bounds='[1088,64][1184,160]']"));
        abc3.click();
        driver.navigate().back();
        driver.navigate().back();

        String TimeCode=StrHrs+":"+StrMin+" "+AmPm;
        System.out.println(TimeCode);
        System.out.println(TimeSys);




        do {
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
            lightStatusReturned = lOnOff.stateONorOFF(output);

            Calendar cal1 = Calendar.getInstance();
            SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm aa");
            TimeSys1=sdf1.format(cal1.getTime());

            System.out.print(TimeSys1+"\n");

           System.out.println(TimeSys1.equals(TimeCode));

        } while(TimeSys1.equals(TimeCode)==false);

        if (lightStatusReturned == "true")

        {
            System.out.println("Test Pass: Light turned on at specific time");

        }
        else
        {
            System.out.println("Test Fail: Light did not turned on at specific time");

        }


    }
}
