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
import java.util.stream.Stream;

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
      

            //Streams in java
            ArrayList<String> courses = new ArrayList<String>();
            courses.add("Selenium");
            courses.add("QTP");
            courses.add("TestComplete");
            //Filter
            //stream  //intermediate operation   //terminal operation
            courses.stream().filter(x-> x.contains("S")).limit(1).forEach(x->System.out.println(x));
            courses.stream().filter(x-> x.contains("S")).toList(); //terminal operation
            courses.stream().filter(x-> x.contains("S")).count();

            //Right side can be a 
            courses.stream().filter(x->
            {
                x.contains("e");
                x.startsWith("S");
                return true;
            }) .count();

            //map to conver the given input to some other form
            courses.stream().map(x->x.toUpperCase()).forEach(x->System.out.println(x));
            long d = courses.stream().map(x->x.toUpperCase()).filter(x->x.contains("E")).count();
            System.out.println(d);

            //sort the stream
            courses.stream().sorted().forEach(x->System.out.println(x));    
           //Merge 2 lists
            ArrayList<String> courses2 = new ArrayList<String>();           
            courses2.add("Cypress");
            courses2.add("Playwright");

            List<String> merged =  new ArrayList<String>();
            merged.addAll(courses); 
            merged.addAll(courses2);
            //using stream merge 2 lists
            Stream.concat(courses.stream(), courses2.stream()).forEach(x->System.out.println(x));

            //anyMatch, allMatch, noneMatch
            boolean b = courses.stream().anyMatch(x->x.contains("S"));  
            System.out.println(b);
            b = courses.stream().allMatch(x->x.contains("S"));         System.out.println(b);

            //sort based on length of string
            courses.stream().sorted((x,y)->x.length()-y.length()).forEach(x->System.out.println(x));

            //avoid duplicate in stream
            courses.stream().distinct().forEach(x->System.out.println(x));
            


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
