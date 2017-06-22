package com.example.a310287808.ankitastrial;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 6/7/2017.
 */

public class IndividualLightONOFFTest {

    public String lightID;
    public String IPAddress = "192.168.86.21/api";
    public String HueUserName = "DtPRqP9ZCbVK0sradKByhPs3BwlIfR5bNX9zamFk";
    public String HueBridgeParameterType = "lights";
    public String finalURL;
    public String lightStatusReturned;


    public void IndividualTestONorOFF(AndroidDriver driver) throws IOException, JSONException {

        HttpURLConnection connection;

        HashMap lightIDMap = new HashMap<Integer,String>();
        lightIDMap.put("26",1);
        lightIDMap.put("27",2);
        lightIDMap.put("28",3);
        lightIDMap.put("30",4);
        lightIDMap.put("44",5);
        lightIDMap.put("46",6);
        lightIDMap.put("47",7);
        lightIDMap.put("48",8);
        lightIDMap.put("49",9);
        lightIDMap.put("50",10);


        for(Object lightValue : lightIDMap.values()) {
            lightID = lightValue.toString();

            finalURL = "http://" + IPAddress + "/" + HueUserName + "/" + HueBridgeParameterType + "/" + lightID;


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

            if(lightStatusReturned=="true"){
                System.out.println(lightID+":Light ID, ON/OFF:"+lightStatusReturned);
                //continue;
            }else{
                System.out.println(lightID+": is OFF");

            }
        }
    }

}
