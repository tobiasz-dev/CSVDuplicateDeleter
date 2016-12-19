import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class Controller {

	private File CSVFile;
	private JFrame mainFrame;
	private int duplicateNumber;
	private CSVParser parser;
	private ArrayList<CSVRecord> records;
	
	private String[] columns;
	private String filter;
	private ArrayList<CSVRecord> returnRecords;
	private String filePath;
	public Controller(){
		mainFrame = new MainFrame(this);
		mainFrame.setVisible(true);
	}
		
	public void fileToCSV(File file) throws IOException{	
		this.CSVFile = file;
		this.filePath = file.getPath();
		
		Reader in = new InputStreamReader(new FileInputStream(this.CSVFile.getPath()), "UTF-8");
		parser = new CSVParser(in, CSVFormat.EXCEL.withHeader().withQuote(null));
		this.records = (ArrayList<CSVRecord>) parser.getRecords();
		this.returnRecords = (ArrayList<CSVRecord>) records.clone();
		System.out.println("Return Record size: " + returnRecords.size());
		System.out.println("Record size: " + records.size());
		Map<String, Integer> columnRecord = parser.getHeaderMap();
		this.columns = new String[columnRecord.size()];
		int i = 0;
		for (String key : columnRecord.keySet()){
			columns[i] = key;
			i++;
		}
		duplicateNumber = 0;
		
	}
	
	public void deleteDuplicates(){
		String value; 
		int i;
		//for(CSVRecord record : this.records){
		for(i = 0; i < records.size(); i++)	{
			value = records.get(i).get(this.filter);
			if(!value.equals("") && value.length() > 5){
				System.out.println(value + " - current");
				//for(CSVRecord current : this.records){
				
				for(int j = i + 1; j < records.size(); j++)
					if(value.trim().toLowerCase().equals(records.get(j).get(filter).trim().toLowerCase())){
						System.out.println(value + " duplicated");
						this.duplicateNumber++;
						this.returnRecords.remove(records.get(j));
					}
				//}
			}
		}
	}
	
	public void writeToFile(){
		CSVFormat csvFileFormat = CSVFormat.EXCEL.withRecordSeparator("\n");
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		try{
			fileWriter = new FileWriter(new File(this.filePath + "_1"));
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			csvFilePrinter.printRecord(this.columns);
			//System.out.println("Returned records: " + returnRecords.size());
			for(CSVRecord record : this.returnRecords){
				
				//System.out.println("Writing: " + record.toString());
				csvFilePrinter.printRecord(record);
			}
			
		} catch (Exception e){
	          e.printStackTrace();

		} finally {
			
			 try {
				 	fileWriter.flush();
				 	fileWriter.close();
				 	csvFilePrinter.close();
				 	
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
		}
	}
	
	public String[] getColumns(){		
		return columns;
	}
	
	public void setFilter(String filter){
		this.filter = filter;
	}
	
	public int getduplicateNumber(){
		return this.duplicateNumber;
	}
	
}
