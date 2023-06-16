package com.reportmanagement.service;

import com.reportmanagement.entity.CitizenPlan;
import com.reportmanagement.repository.CitizenPlanRepository;
import com.reportmanagement.requests.SearchRequest;
import com.reportmanagement.utils.EmailUtils;
import com.reportmanagement.utils.ExcelGenerator;
import com.reportmanagement.utils.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CitizenPlanServiceImpl implements CitizenPlanService {

	@Autowired
	private CitizenPlanRepository repository;

	@Autowired
	private ExcelGenerator excelGenerator;
	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	private EmailUtils emailUtils;
	@Override
	public List<CitizenPlan> Search(SearchRequest request) {
		
		CitizenPlan entity = new CitizenPlan();

		if(null!=request.getPlanName()&& !"".equals(request.getPlanName())){
			entity.setPlanName(request.getPlanName());
		}

		if(null!=request.getPlanStatus()&& !"".equals(request.getPlanStatus())){
			entity.setPlanStatus(request.getPlanStatus());
		}

		if(null!=request.getGender()&& !"".equals(request.getGender())){
			entity.setGender(request.getGender());
		}

		if (null != request.getPlanStartDate() && !"".equals(request.getPlanStartDate())) {
			String startDate = request.getPlanStartDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate parse = LocalDate.parse(startDate, formatter);
			entity.setPlanStartDate(parse);
		}

		if (null != request.getPlanEndDate() && !"".equals(request.getPlanEndDate())) {
			String endDate = request.getPlanEndDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate parse = LocalDate.parse(endDate, formatter);
			entity.setPlanEndDate(parse);
		}

		return repository.findAll(Example.of(entity));
	
	}


	@Override
	public List<String> searchPlanName() {
		return this.repository.getPlanNames();
	}

	@Override
	public List<String> searchPlanStatus() {
		return this.repository.getPlanStatuses();
	}



	@Override
	public boolean importPdfReport(HttpServletResponse response) throws Exception {

		File f = new File("Plans.pdf");

		List<CitizenPlan> plans = repository.findAll();
		pdfGenerator.generator(response,plans,f);

		String to = "pramod85shriram@gmail.com";
		String subject = "Test mail subject";
		String body = "<h1> Test mail body</h1>";

		emailUtils.sendEmail(subject,body,to,f);

		f.delete();

		return true;
	}

	@Override
	public boolean importExcelReport(HttpServletResponse response) throws Exception {

		File f = new File("Plans.xls");
		List<CitizenPlan> plans = repository.findAll();
		excelGenerator.generate(response,plans,f);

		String to = "pramod85shriram@gmail.com";
		String subject = "Test Mail Subject";
		String body = "<H1> Test mail Body </H1>";

		emailUtils.sendEmail(subject,body,to,f);

		f.delete();
		return true;
	}
}
