package maven.javaparser.testin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;

public class TestingFile {

	public static void main(String[] args) {
		
		List<String[]> Test_Data = new ArrayList<String[]>();
		String READ_FILE_PATH = "TestingDataset.csv";
		
		
		try 
		{
			File  read_File = new File(READ_FILE_PATH);
			FileReader fileReader = new FileReader(read_File);
			
			CSVParser csvParser = new CSVParserBuilder().withEscapeChar('\0').build();
			CSVReader csvReader = new CSVReaderBuilder(fileReader).withCSVParser(csvParser).build(); 
			
			String record[];
			int j=0;
			while( (record = csvReader.readNext()) != null )
			{
				String temp[] = new String[record.length];
				for(int i=0; i<record.length;i++)
				{
					temp[i] = record[i];
				}
				Test_Data.add(temp);
			}
			csvReader.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Reading Done");
		String WRITE_FILE = "FinalDatset.csv";
		try
		{
			File file = new File(WRITE_FILE);
			FileWriter fileWriter = new FileWriter(file);
			
			CSVWriter csvWriter = new CSVWriter(fileWriter);
			
			
			for(int i=0;i<Test_Data.size();i++)
			{
				String temp[] = Test_Data.get(i);
				String fTemp[] = new String[temp.length * 2];
				int k;
				for(k=0;k<temp.length;k++)
				{
					fTemp[k] = temp[k];
				}
				
				for(int j=i+1;j<Test_Data.size();j++)
				{
					if(i==j)
					{
						continue;
					}
					String temp2[] = Test_Data.get(j);
					
					for(k=0;k<temp.length;k++)
					{
						fTemp[k+temp.length] = temp2[k];
					}
					
					csvWriter.writeNext(fTemp);
				
				}
				System.out.println("Point :: "+i+" Done");
			}
			csvWriter.flush();
			csvWriter.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		System.out.println("Execution Done");
	}
}
