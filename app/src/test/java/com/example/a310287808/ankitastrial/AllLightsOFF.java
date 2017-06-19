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
 * Created by 310287808 on 6/7/2017.
 */

public class AllLightsOFF {

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

// Widget for switching the lights OFF is already created in the device as a precondition. This code will click on the widget and check
// whether all the lights which are connected to the bridge are switched off or not.
    public void AllLightsONorOFF(AndroidDriver driver) throws IOException, JSONException, InterruptedException {
        driver.navigate().back();

// Clicking on the widget created in the device
        //driver.findElement(By.id("com.ifttt.ifttt:id/widget_do_small_recipe_btn")).click();
        WebElement abc = driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[1002,1158][1158,1276]']"));
        abc.click();
        TimeUnit.SECONDS.sleep(20);

        //Connecting with the API to fetch the status of the lights
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

        //HashMap<String,Integer> FailedLights = new HashMap<>();
        StringBuffer sb = new StringBuffer();

        if(lightStatusReturned=="false"){
            Result = "PASS";
            Comments = "All Lights Turned OFF";
            System.out.println("Result: "+Result+"\n"+"Comment: "+Comments);


        }else{

            for(Map.Entry<String,Integer> lights : lightIDs.entrySet()){



                    finalURLIndLight = "http://" + IPAddress + "/" + HueUserName + "/" + HueBridgeIndLightType
                            +"/"+lights.getKey();
                    //System.out.println(finalURLIndLight);

                    URL url1 = new URL(finalURLIndLight);
                    connection = (HttpURLConnection) url1.openConnection();
                    connection.connect();

                    InputStream stream1 = connection.getInputStream();

                    BufferedReader reader1 = new BufferedReader(new InputStreamReader(stream1));

                    StringBuffer br1 = new StringBuffer();

                    String line1 = " ";
                String change = null;
                    while ((line1 = reader1.readLine()) != null) {
                        //change = line1.replace("[", "").replace("]", "");
                        br1.append(line1);
                    }
                    String output1 = br1.toString();
                System.out.println(output1);
                    JSONObject jsonObject = new JSONObject(output1);


                    Object ob =  jsonObject.get("state");
                    String newString = ob.toString();
                    Object lightNameObject = jsonObject.get("name");
                    String lightName = lightNameObject.toString();
                    System.out.println(lightName);

                    JSONObject jsonObject1 = new JSONObject(newString);
                    Object ob1 = jsonObject1.get("on");
                    x=ob1.toString();

                    if(x.equals("true")){
                        lightCounter++;

                        sb.append(lightName);

                        sb.append("\n");

                    }
                    else{
                        continue;
                    }

                }

            Result = "Fail";
            Comments = "Following Lights are still ON:"+"\n"+sb.toString();
            System.out.println("Result: "+Result+"\n"+"Comment: "+Comments);
            }


    }
}
