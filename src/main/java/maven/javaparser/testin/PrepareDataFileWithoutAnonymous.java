/*
  Program to create dataset file without anonymous classes
  feature set
 	*/

package maven.javaparser.testin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class PrepareDataFileWithoutAnonymous {
	
	public static void main(String[] args) throws Exception {
		
		String READ_FILE_PATH = "TestSet.csv";
		String WRITE_FILE_PATH = "TestingDataset.csv";
		
		int count=0;
		try
		{
			File read_File = new File(READ_FILE_PATH);
			File write_File = new File(WRITE_FILE_PATH);
			
			FileReader reader = new FileReader(read_File);
			FileWriter writer = new FileWriter(write_File);
			
			CSVReader csvReader = new CSVReader(reader);
			CSVWriter csvWriter = new CSVWriter(writer);
			
			String[] record;
			
			while((record = csvReader.readNext()) != null)
			{
				String temp = record[2];
				
				if( !(temp.equals("anonymous")) ) 
				{
					csvWriter.writeNext(record);
					count++;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("count :: "+count);
		System.out.println("Completed");
	}
}
