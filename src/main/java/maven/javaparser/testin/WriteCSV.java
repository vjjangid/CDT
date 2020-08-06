package maven.javaparser.testin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

public class WriteCSV {
	
	public static List<String[]> ALL_FILES = new ArrayList<String[]>();
	static int count = 0;
	public static void displayDirectoryFiles(File dir)
	{
		try {
			File[] files = dir.listFiles();
			for(File file:files)
			{
				if(file.isDirectory())
				{
					System.out.println("Directory :: " + file.getCanonicalPath());
					displayDirectoryFiles(file);
				}
				else
				{
					String addr = file.getCanonicalPath();

					ALL_FILES.add(new String[] {addr});
					//System.out.println("     file :: "+file.getCanonicalPath());
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		System.out.println(++count +" directory completed");
	}
	public static void main(String[] args) throws Exception {
		
		String DIR_PATH = "D:/Datasets_for_ML/IJaDataset_BCEvalVersion/bcb_reduced";
		File dir = new File(DIR_PATH);
		
		displayDirectoryFiles(dir);
		
		String FILE_PATH = "File_Paths.csv";
		
		File file = new File(FILE_PATH);
		
		try
		{
			FileWriter outputfile = new FileWriter(file);
			
			CSVWriter writer = new CSVWriter(outputfile);
			
			
			writer.writeAll(ALL_FILES);
			writer.flush();
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		System.out.println("DONE");
		
		/*String[] files = dir.list();
		
		if(files.length == 0)
		{
			System.out.println("The directory is empty");
		}
		else
		{
			for(String aFile: files)
			{
				System.out.println(aFile);
			}
		}*/
		
		
		
	}
}
