package com.example.a310287808.ankitastrial;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 310287808 on 6/29/2017.
 */

public class GroupVerificationAPI {
    public String lightsIDs;

    public String GroupVerificationAPI(String input) throws JSONException {

        JSONObject jsonObject = new JSONObject(input);
        Object ob2 = jsonObject.get("name");
        lightsIDs = ob2.toString();
        //       System.out.println(lightsIDs);

        return lightsIDs;

    }
}