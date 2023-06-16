package com.reportmanagement.utils;

import com.reportmanagement.entity.CitizenPlan;
import com.reportmanagement.repository.CitizenPlanRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Component
public class ExcelGenerator {


    @Autowired
    private CitizenPlanRepository repository;

    public void generate(HttpServletResponse response, List<CitizenPlan> records, File file) throws Exception {

        //For .xlsx format file required XSSFWorkbook
        // Ex. Workbook workBook = new XSSFWorkbook;
        //remaining everything is same.

        //for .xls format file required HSSFWorkbook
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Citizen_Plan_Data");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Citizen Name");
        headerRow.createCell(2).setCellValue("Plan Name");
        headerRow.createCell(3).setCellValue("Plan StartDate");
        headerRow.createCell(4).setCellValue("Plan EndDate");
        headerRow.createCell(5).setCellValue("Benefit Amount");




        //Declare one local variable to iterating row from his default value
        int dataRowIndex=1;

        //Using for each loop to iterating data from database into excel file
        //fetching all the data from repository
        for(CitizenPlan plan:records){

            Row rowData = sheet.createRow(dataRowIndex);
            rowData.createCell(0).setCellValue(plan.getCitizenId());
            rowData.createCell(1).setCellValue(plan.getCitizenName());
            rowData.createCell(2).setCellValue(plan.getPlanName());
            if(null!=plan.getPlanStartDate()){
                rowData.createCell(3).setCellValue(plan.getPlanStartDate()+"");
            }
            if(null!=plan.getPlanEndDate()){
                rowData.createCell(4).setCellValue(plan.getPlanEndDate()+"");
            }
            else{
                rowData.createCell(3).setCellValue("-");
                rowData.createCell(4).setCellValue("-");
            }


            if(null!=plan.getBenefitAmount()){
                rowData.createCell(5).setCellValue(plan.getBenefitAmount());
            }
            else{
                rowData.createCell(5).setCellValue("-");
            }

            dataRowIndex++;

        }

        //create the file in current folder
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        fos.close();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();


    }
}
