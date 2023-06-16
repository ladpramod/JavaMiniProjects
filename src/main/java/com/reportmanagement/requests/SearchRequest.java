package com.reportmanagement.requests;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data


public class SearchRequest {

    private String planName;
    private String planStatus;
    private String gender;

    private String planStartDate;

    private String planEndDate;

	
    
    

	
}
