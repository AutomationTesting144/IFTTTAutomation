package com.example.a310287808.ankitastrial;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    public String Status;
    public String Comments;
    public int lightCounter=0;
    public String x;
    public String ActualResult;
    public String ExpectedResult;

    // Widget for switching the lights OFF is already created in the device as a precondition. This code will click on the widget and check
// whether all the lights which are connected to the bridge are switched off or not.
    public void AllLightsONorOFF(AndroidDriver driver,String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException {
        driver.navigate().back();

// Clicking on the widget created in the device
        //driver.findElement(By.id("com.ifttt.ifttt:id/widget_do_small_recipe_btn")).click();
//        WebElement abc = driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[1002,1158][1158,1276]']"));
//        abc.click();
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
        lightIDs.put("26",1);
        lightIDs.put("27",2);
        lightIDs.put("28",3);
        lightIDs.put("30",4);
       // lightIDs.put("44",5);
        lightIDs.put("46",6);
        lightIDs.put("47",7);
        lightIDs.put("48",8);
        lightIDs.put("49",9);
        lightIDs.put("50",10);


        StringBuffer sb = new StringBuffer();

        if(lightStatusReturned=="false"){

            Status = "1";
            ActualResult = "All Lights Turned OFF";
            Comments = "NA";
            ExpectedResult= " Light should be turned off after clicking on widget";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);


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
                //System.out.println(output1);
                JSONObject jsonObject = new JSONObject(output1);
                Object ob =  jsonObject.get("state");
                String newString = ob.toString();
                Object lightNameObject = jsonObject.get("name");
                String lightName = lightNameObject.toString();
                //System.out.println(lightName);

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
            Status = "0";
            ActualResult = "Following Lights are still ON:"+"\n"+sb.toString();
            Comments = "FAIL:Lights are not turned off";
            ExpectedResult= "All lights should be turned off after clicking the widget";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);

        }
        storeResultsExcel(Status, ActualResult, Comments, fileName, ExpectedResult,APIVersion,SWVersion);

    }
    public String CurrentdateTime;
    public int nextRowNumber;
    public void storeResultsExcel(String excelStatus, String excelActualResult, String excelComments, String resultFileName, String ExcelExpectedResult
            ,String resultAPIVersion, String resultSWVersion) throws IOException {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS aa");
        CurrentdateTime = sdf.format(cal.getTime());
        FileInputStream fsIP = new FileInputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        nextRowNumber=workbook.getSheetAt(0).getLastRowNum();
        nextRowNumber++;
        HSSFSheet sheet = workbook.getSheetAt(0);

        HSSFRow row2 = sheet.createRow(nextRowNumber);
        HSSFCell r2c1 = row2.createCell(0);
        r2c1.setCellValue(CurrentdateTime);

        HSSFCell r2c2 = row2.createCell(1);
        r2c2.setCellValue("2");

        HSSFCell r2c3 = row2.createCell(2);
        r2c3.setCellValue(excelStatus);

        HSSFCell r2c4 = row2.createCell(3);
        r2c4.setCellValue(excelActualResult);

        HSSFCell r2c5 = row2.createCell(4);
        r2c5.setCellValue(excelComments);

        HSSFCell r2c6 = row2.createCell(5);
        r2c6.setCellValue(resultAPIVersion);

        HSSFCell r2c7 = row2.createCell(6);
        r2c7.setCellValue(resultSWVersion);

        fsIP.close();
        FileOutputStream out =
                new FileOutputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        workbook.write(out);
        out.close();


    }
}