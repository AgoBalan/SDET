package com.example.test;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.checkerframework.checker.units.qual.s;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GridServer1Test extends BaseTest {
    
    @Test(dataProvider = "excelData")
    public void testAsiaCustomers(String region) throws Exception {
        
        // Navigate to your web application
        driver.get("https://www.google.com");
     
        driver.findElement(By.xpath("//textarea[@title='Search']")).sendKeys(region);
           System.out.println("Title: " + driver.getTitle());
        driver.quit();
        try{
        Thread.sleep(1000);
        }catch(Exception e){}
    }

    @DataProvider(name = "customerData")
    public Object[][] customerDataProvider() {
        return new Object[][] {
            {"Asia"},
            {"Europe"},
            {"America"}
        };
    }

      @DataProvider(name = "excelData")
    public Object[][] excelDataProvider() throws IOException {
     FileInputStream file = new FileInputStream("ExcelForDataProvider.xlsx");
     try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getLastCellNum();
        Object[][] data = new Object[rowCount-1][colCount];
        for(int i=1; i<rowCount; i++) {
            for(int j=0; j<colCount; j++) {
                data[i-1][j] = sheet.getRow(i).getCell(j).toString();
            }
        }
        return data;
    }
    }
}
