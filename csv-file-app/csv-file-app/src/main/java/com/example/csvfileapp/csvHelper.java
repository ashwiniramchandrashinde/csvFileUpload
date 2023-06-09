package com.example.csvfileapp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

public class csvHelper {
	  public static String TYPE = "text/csv";
	  static String[] HEADERs = { "Id", "userName", "emailAddress", "phoneNumber" };
	  
	  public static boolean hasCSVFormat(MultipartFile file) {
		    if (TYPE.equals(file.getContentType())
		    		|| file.getContentType().equals("application/vnd.ms-excel")) {
		      return true;
		    }

		    return false;
		  }
	  
	  public static List<csvBean> saveToCsv(InputStream is) {
		  
		    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        CSVParser csvParser = new CSVParser(fileReader,
		            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

		      List<csvBean> csvBeanList = new ArrayList<>();

		      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
		      

		      for (CSVRecord csvRecord : csvRecords) {
		    	  csvBean csvObj = new csvBean(
		              Long.parseLong(csvRecord.get("Id")),
		              csvRecord.get("userName"),
		              csvRecord.get("emailAddress"),
		              csvRecord.get("phoneNumber")
		            );

		    	  csvBeanList.add(csvObj);
		      }

		      return csvBeanList;
		    } catch (IOException e) {
		      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		    }
		  }
	  
	  public static ByteArrayInputStream DbToCsv(List<csvBean> developerTutorialList) {
		    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

		    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
		        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
		      for (csvBean csvbean : developerTutorialList) {
		        List<String> data = Arrays.asList(
		              String.valueOf(csvbean.getId()),
		              csvbean.getUserName(),
		              csvbean.getEmailAddress(),
		              csvbean.getPhoneNumber()
		            );

		        csvPrinter.printRecord(data);
		      }

		      csvPrinter.flush();
		      return new ByteArrayInputStream(out.toByteArray());
		    } catch (IOException e) {
		      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
		    }
		  }
}
