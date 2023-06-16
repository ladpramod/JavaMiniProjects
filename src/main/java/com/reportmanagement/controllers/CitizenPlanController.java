package com.reportmanagement.controllers;

import com.reportmanagement.entity.CitizenPlan;
import com.reportmanagement.requests.SearchRequest;
import com.reportmanagement.service.CitizenPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CitizenPlanController {


	@Autowired
	private CitizenPlanService service;



	@PostMapping("/search")
	public String handelSearch( @ModelAttribute("search") SearchRequest search, Model model) {

		try {
			List<CitizenPlan> plans = this.service.Search(search);
			model.addAttribute("plans", plans);
			init(model);

		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		return "index";
	}
	
	
	@GetMapping("/")
	public String getIndex(Model model) {
						
		model.addAttribute("search", new SearchRequest());
		
		init(model);
		return "index";
	}


	private void init(Model model) {
		
		model.addAttribute("names", service.searchPlanName());
		model.addAttribute("status", service.searchPlanStatus());
	}

	@GetMapping("/excel")
	public void downloadExel(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition","attachment;filename=plans.xls");
		service.importExcelReport(response);
	}

	@GetMapping("/pdf")
	public void downloadPdf(HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition","attachment;filename=plans.pdf");
		service.importPdfReport(response);
	}

}
