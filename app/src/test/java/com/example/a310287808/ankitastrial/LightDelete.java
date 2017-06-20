package com.example.a310287808.ankitastrial;

import android.app.Activity;
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
 * Created by 310287808 on 6/14/2017.
 */

public class LightDelete extends Activity{

    MobileElement listItem;
    public String Result;
    public String Comments;

        public void LightDelete(AndroidDriver driver) throws IOException, JSONException, InterruptedException {
            driver.navigate().back();
            //Opening Hue applictaion
            driver.findElement(By.xpath("//android.widget.TextView[@bounds='[24,1380][216,1572]']")).click();
            TimeUnit.SECONDS.sleep(2);
            //Clicking on settings button
            driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[1026,184][1074,232]']")).click();
            TimeUnit.SECONDS.sleep(2);
            //Selecting light setup
            driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[0,552][152,680]']")).click();
            //Choosing light to delete
            driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[1080,196][1200,316]']")).click();
            //getting the value of light to be deleted
            String lightValue=driver.findElement(By.id("com.philips.lighting.hue2:id/form_field_text")).getText();

            //Clicking on DELETE button at the bottom
            driver.findElement(By.id("com.philips.lighting.hue2:id/details_delete_device_button")).click();
            //Confirming DELETION by click on delete button
            driver.findElement(By.id("android:id/button1")).click();
            //Going back in the hue application Home page
            driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[16,48][128,160]']")).click();
            driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[126,184][174,232]']")).click();
            driver.navigate().back();

            // Clicking the home button in the device
            WebElement abc = driver.findElement(By.xpath("//android.widget.TextView[@bounds='[544,1670][656,1782]']"));
            abc.click();

            //Opening IFTTT
            driver.findElement(By.xpath("//android.widget.TextView[@text='IFTTT']")).click();
            //clicking on search button
            WebElement abc2 = driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[426,1724][474,1772]']"));
            abc2.click();

//            if(driver.findElement(By.id("com.ifttt.ifttt:id/content_root")).isEnabled())
//            {
//                driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[16,48][128,176]']")).click();
//                WebElement abc3 = driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[426,1724][474,1772]']"));
//                abc3.click();
//            }
            //Clicking on the search text box
            driver.findElement(By.id("com.ifttt.ifttt:id/boxed_edit_text")).click();
            TimeUnit.SECONDS.sleep(5);
            //Entering aplication name
            driver.findElement(By.id("com.ifttt.ifttt:id/boxed_edit_text")).sendKeys("Hue" + "\n");
            new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='Press a button to make your Hue lights color loop']")));
            TimeUnit.MINUTES.sleep(8);
            //Clicking on the discovered applet
            driver.findElement(By.xpath("//android.widget.TextView[@text='Press a button to make your Hue lights color loop']")).click();
            //clicking on the edit button
            driver.findElement(By.id("com.ifttt.ifttt:id/edit")).click();
            //clicking on dropdown
            driver.findElement(By.id("com.ifttt.ifttt:id/spinner_arrow")).click();
            while(driver.findElements(By.id("android:id/text1")).isEmpty()) {

                driver.findElement(By.id("com.ifttt.ifttt:id/spinner_arrow")).click();
            }

            //Locate all drop down list elements
            List dropList = driver.findElements(By.id("android:id/text1"));

            for (int i = 0; i < dropList.size(); i++) {
                listItem = (MobileElement) dropList.get(i);

                if (listItem.getText().equals(lightValue) == true) {
                    Result = "Fail";
                    Comments = "Following Light(s) is not deleted: "+lightValue;
                    System.out.println("Result: "+Result+"\n"+"Comment: "+Comments);
                }
            }
            Result = "PASS";
            Comments = "Light: "+lightValue+" is deleted from the list";
            System.out.println("Result: "+Result+"\n"+"Comment: "+Comments);
            //Going back on home from IFTTT


            driver.findElement(By.id("android:id/text1")).click();
            driver.findElement(By.xpath("//android.widget.TextView[@bounds='[1088,64][1184,160]']")).click();
            driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[16,48][128,176]']")).click();
            driver.navigate().back();
            driver.navigate().back();
            driver.navigate().back();
        }
    }
