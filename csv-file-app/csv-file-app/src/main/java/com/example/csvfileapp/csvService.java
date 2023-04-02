package com.example.csvfileapp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class csvService {
	
	@Autowired
	csvRepository csvrepository;
	
	public StringBuilder save(MultipartFile file) {
    	StringBuilder errorMessage=new StringBuilder("");

	    try {
	    	int duplicateCount=0;
	    	int successCount=0;
	      List<csvBean> tutorials = csvHelper.saveToCsv(file.getInputStream());
	      List<csvBean> lst=csvrepository.findAll();
	      HashSet<csvBean> hsBean=new HashSet<>();
	    
	      
	    	  HashMap<Long,csvBean> hs=new HashMap<Long,csvBean>();
	    	  
	    	  for(csvBean c1:lst){
	    		  hs.put(c1.getId(),c1);
	    	  }
	    	  int errorCount =0 ;
	    	  int finalErrorCnt=0;
	    	  for(csvBean a:tutorials){
	    		   errorCount=Validate(a);
	    		   if(errorCount == 0){
	    		  if(!hs.keySet().contains(a.getId())){
	    			  hsBean.add(a);
	    		  }else{
	    			  System.out.println("duplicate count::"+duplicateCount);
	    			 duplicateCount++;
	    		  }
	    		  }
	    		  finalErrorCnt=errorCount;
	    		  errorCount=0;
	      }
		if(!hsBean.isEmpty()) csvrepository.saveAll(hsBean);
		System.out.println("overall dup coiunt"+duplicateCount);
		  errorMessage.append("Duplicate Count = "+duplicateCount);
	      errorMessage.append(",");
	      errorMessage.append("Error Count = "+finalErrorCnt);
	      errorMessage.append(",");
	      errorMessage.append("Success Count = "+hsBean.size());
	      
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store csv data: " + e.getMessage());
	    }
		return errorMessage;
	  }

	  private int Validate(csvBean a) {
		// TODO Auto-generated method stub
		  Pattern p = Pattern.compile("^(.+)@(.+)$");

		  int errorCount=0;
			  Matcher m = p.matcher(a.getEmailAddress());
			  if(a.getPhoneNumber().length() != 10){
				  errorCount++;
			  }else if(!m.matches()){
				  errorCount++;
			  }
		return errorCount;
	}

	public ByteArrayInputStream load() {
	    List<csvBean> tutorials = csvrepository.findAll();

	    ByteArrayInputStream in = csvHelper.DbToCsv(tutorials);
	    return in;
	  }
}
