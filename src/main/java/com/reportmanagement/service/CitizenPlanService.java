package com.reportmanagement.service;

import com.reportmanagement.entity.CitizenPlan;
import com.reportmanagement.requests.SearchRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CitizenPlanService{


    List<String> searchPlanName();

    List<String> searchPlanStatus();

    List<CitizenPlan> Search(SearchRequest request);

    boolean importPdfReport(HttpServletResponse response) throws Exception;

    boolean importExcelReport(HttpServletResponse response) throws Exception;

}
