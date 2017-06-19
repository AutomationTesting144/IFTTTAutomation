package com.example.a310287808.ankitastrial;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 6/9/2017.
 */

public class ColorChangeAll {
    public String IPAddress = "192.168.86.21/api";
    public String HueUserName = "DtPRqP9ZCbVK0sradKByhPs3BwlIfR5bNX9zamFk";
    public String HueBridgeParameterType = "groups/0";
    public String HueBridgeIndLightType = "lights";
    public String finalURL;
    public String finalURLIndLight;
    public String lightStatusReturned;
    public String Result;
    public String Comments;
    public int lightCounter=0;
    public String x;

    public void ColorChangeAll(AndroidDriver driver) throws IOException, JSONException, InterruptedException {
        driver.navigate().back();
        // Widget for changing color using notepad application is already created in the device as a precondition. This code will click on the widget and check
        // whether all the lights which are connected to the bridge are switched off or not.
        WebElement abc = driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[42,198][198,316]']"));
        abc.click();
        // Writing into notepad
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
        HashMap<String,Integer> lightIDs = new HashMap<>();
        lightIDs.put("1",1);
        lightIDs.put("4",2);
        lightIDs.put("26",3);
        lightIDs.put("27",4);
        lightIDs.put("28",5);
        lightIDs.put("45",6);
        lightIDs.put("30",7);
        lightIDs.put("29",8);
        lightIDs.put("31",9);

        StringBuffer sb = new StringBuffer();

        String Xval=lightStatusReturned.substring(1,6);
        String Yval=lightStatusReturned.substring(7,12);
//        System.out.println(Xval);
//        System.out.println(Yval);
        String Xred="0.675";
        String Yred="0.322";

        boolean finalResult=(Xval.equals(Xred)) && (Yval.equals(Yred));
        if (finalResult==true){
            Result = "PASS";
            Comments = "Color changed to RED for all lights";
            System.out.println("Result: "+Result+"\n"+"Comment: "+Comments);


        }else
            {

                for(Map.Entry<String,Integer> lights : lightIDs.entrySet()) {


                    finalURLIndLight = "http://" + IPAddress + "/" + HueUserName + "/" + HueBridgeIndLightType
                            + "/" + lights.getKey();

                    URL url1 = new URL(finalURLIndLight);
                    connection = (HttpURLConnection) url1.openConnection();
                    connection.connect();

                    InputStream stream1 = connection.getInputStream();

                    BufferedReader reader1 = new BufferedReader(new InputStreamReader(stream1));

                    StringBuffer br1 = new StringBuffer();

                    String line1 = " ";

                    while ((line1 = reader1.readLine()) != null) {

                        br1.append(line1);
                    }
                    String output1 = br1.toString();
                    JSONObject jsonObject = new JSONObject(output1);
                    Object ob = jsonObject.get("state");
                    String newString = ob.toString();
                    Object lightNameObject = jsonObject.get("name");
                    String lightName = lightNameObject.toString();
                    JSONObject jsonObject1 = new JSONObject(newString);
                    if (jsonObject1.has("xy") == true) {
                        Object ob1 = jsonObject1.get("xy");
                        x = ob1.toString();
                        String Xval1 = lightStatusReturned.substring(1, 6);
                        String Yval1 = lightStatusReturned.substring(8, 13);
                        String Xred1 = "0.675";
                        String Yred1 = "0.322";

                        Boolean result = (Xval1.equals(Xred1)) && (Yval1.equals(Yred1));
                        if (result == false) {
                            lightCounter++;
                            sb.append(lightName);
                            sb.append("\n");


                        } else {
                            continue;
                        }

                    } else {
                        continue;
                    }

                }
            Result = "Fail";
            Comments = "Following Lights are still not RED in color:"+"\n"+sb.toString();
            System.out.println("Result: "+Result+"\n"+"Comment: "+Comments);

        }

    }

}