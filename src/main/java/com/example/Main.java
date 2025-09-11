package com.example;

import com.example.Selenium.standalone;
import com.example.model.CustomerInfo;
import com.example.model.CustomerInfoDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Main {
    public static void main(String[] args) throws JsonProcessingException, MalformedURLException {
        try {
            CustomerInfoDAO dao = new CustomerInfoDAO();
                       
            // Get all employees
            List<CustomerInfo> cus = dao.getAllCustomers();
            List<String> cusJson  = new ArrayList<String>();
             List<String> cusJsonfromJackson  = new ArrayList<String>();
        
            ObjectMapper mapper = new ObjectMapper();
            Gson gson = new Gson();
            //create multiple json files for each customer
            for(int i=0; i<cus.size(); i++) {
            	  mapper.writeValue(new File("jdbc-demo\\customer"+i+".json"), cus.get(i));
                    //Convert classobject to json using gson
                 cusJson.add(gson.toJson(cus.get(i)));  
                 //using jackson convert POJO to json
                 cusJsonfromJackson.add(mapper.writeValueAsString(cus.get(i)));//Convert classobject to json using jackson
            }

            //using simple json construct nested json
            //Ex: {"customers":["{\"courseName\":\"selenium\",\"purchasedDate\":\"Sep 10, 2025\",\"amount\":120,\"location\":\"Africa\"}","{\"courseName\":\"Protractor\",\"purchasedDate\":\"Sep 10, 2025\",\"amount\":45,\"location\":\"Africa\"}"]}
            JSONObject json = new JSONObject();
            json.put("customers", cusJson);
            //System.out.println("\nJSON Representation:");
            //System.out.println(json.toJSONString()); 

            //Above can be achieved by Map
            // Wrap in nested JSON
            //OP: {"customers":["{\"courseName\":\"selenium\",\"purchasedDate\":\"Sep 10, 2025\",\"amount\":120,\"location\":\"Africa\"}","{\"courseName\":\"Protractor\",\"purchasedDate\":\"Sep 10, 2025\",\"amount\":45,\"location\":\"Africa\"}"]}
            Map<String, List<String>> wrapper = new HashMap<>();
            wrapper.put("customers", cusJson);
            //System.out.println(mapper.writeValueAsString(wrapper));

            //Use apache coomons text, remove all escape charater before double quotes in jsin string
          //  String unEscap = StringEscapeUtils.unescapeJava(mapper.writeValueAsString(wrapper));
          //  System.out.println(unEscap);
            //OP: {"customers":["{"courseName":"selenium","purchasedDate":"Sep 10, 2025","amount":120,"location":"Africa"}","{"courseName":"Protractor","purchasedDate":"Sep 10, 2025","amount":45,"location":"Africa"}"]}

            // convert json to pojo object using jackson
            CustomerInfo c = mapper.readValue(cusJsonfromJackson.get(0), CustomerInfo.class);
      
            
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (java.io.IOException e) {
            System.err.println("IO error: " + e.getMessage());
            e.printStackTrace();
        }

       // Run selenium in standalone
       standalone.run();
       
    }
}
