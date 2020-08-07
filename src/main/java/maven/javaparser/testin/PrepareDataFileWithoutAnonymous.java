/*
  Program to create dataset file without anonymous classes
  feature set
 	*/

package maven.javaparser.testin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
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
			
			CSVParser csvParser = new CSVParserBuilder().withEscapeChar('\0').build();
			CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(csvParser).build();
			
			CSVWriter csvWriter = new CSVWriter(writer);
			
			String[] record;
			
			while((record = csvReader.readNext()) != null)
			{
				String temp = record[2];
				
				if( !(temp.equals("anonymous")) ) 
				{
					csvWriter.writeNext(record);
					count++;
					for(int i=0;i<record.length;i++)
					{
						System.out.print(record[i]+ " ");
					}
					
					System.out.println();
				}
			}
			csvWriter.flush();
			csvReader.close();
			csvWriter.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("count :: "+count);
		System.out.println("Completed");
	}
}
