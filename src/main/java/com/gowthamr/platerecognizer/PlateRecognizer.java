/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gowthamr.platerecognizer;

import java.io.File;
import org.json.JSONArray;
import org.json.JSONObject;
import kong.unirest.Unirest;
import kong.unirest.HttpResponse;

/**
 * 
 *
 * @author Gowtham R
 */
public class PlateRecognizer {
    public static void main(String[] args){
        PlateRecog pr=new PlateRecog();
    }
}

class PlateRecog {
    public static String func(String path){
               // Get api key from https://app.platerecognizer.com/start/ and replace MY_API_KEY
        String token = "INSERT_YOUR_KEY_HERE";
        String file = path;
        String plateValue="Error parsing Image";

        try{
            HttpResponse<String> response = Unirest.post("https://api.platerecognizer.com/v1/plate-reader/")
            .header("Authorization", "Token "+token)
            .field("upload", new File(file))
            .asString();
            
            System.out.println();
            String obj=response.getBody();
          
            JSONObject responseObj = new JSONObject(obj);
            System.out.println(obj);
        JSONArray resultsArray = responseObj.getJSONArray("results");
        JSONObject firstResult = resultsArray.getJSONObject(0);
        plateValue = firstResult.getString("plate");
        
            
            
            
            
        }
        catch(Exception e){
            System.out.println(e);
        }

        try{
            HttpResponse<String> response = Unirest.get("https://api.platerecognizer.com/v1/statistics/")
            .header("Authorization", "Token "+token)
            .asString();
            System.out.println("Usage:");
            System.out.println();
            System.out.println(response.getBody().toString());
           
            
        }
        catch(Exception e){
            System.out.println(e);
        }
        return plateValue;
}
}
