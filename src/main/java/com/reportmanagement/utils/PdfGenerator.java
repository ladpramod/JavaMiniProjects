package com.reportmanagement.utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.reportmanagement.entity.CitizenPlan;
import com.reportmanagement.repository.CitizenPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Component
public class PdfGenerator {

    @Autowired
    private CitizenPlanRepository repository;

    public void generator(HttpServletResponse response, List<CitizenPlan> plans, File file) throws Exception{

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        PdfWriter.getInstance(document,new FileOutputStream(file));

        document.open();

        Font font = FontFactory.getFont("ARIAL-BOLD");
        font.setColor(Color.BLUE);
        font.setSize(14);


        Paragraph p = new Paragraph("- CITIZEN PLAN INFO -",font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);


        PdfPTable table = new PdfPTable(7);


        table.setWidthPercentage(100f);

        table.setSpacingBefore(7);

        table.addCell("ID");

        table.addCell("Citizen Name");

        table.addCell("Citizen Plan");

        table.addCell("Plan Status");

        table.addCell("Start Date");

        table.addCell("End Date");

        table.addCell("Amount");


        for(CitizenPlan plan:plans){

            table.addCell(String.valueOf(plan.getCitizenId()));
            table.addCell(plan.getCitizenName());
            table.addCell(plan.getPlanName());
            table.addCell(plan.getPlanStatus());
            table.addCell(plan.getPlanStartDate()+"");
            table.addCell(plan.getPlanEndDate()+"");
            table.addCell(String.valueOf(plan.getBenefitAmount()));
        }

        document.add(table);

        document.close();
    }
}
