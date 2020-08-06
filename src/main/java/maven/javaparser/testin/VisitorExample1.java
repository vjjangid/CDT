package maven.javaparser.testin;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Optional;

import com.github.javaparser.Position;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.Node.BreadthFirstIterator;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.TreeVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;



public class VisitorExample1 {
	
	public static List<String[]> details = new ArrayList<String[]>();
	public static int count=0;
	public static int classCount = 0;
	public static String startLine;
	public static String endLine;
	
	
	/* Visitor method to get class declaration in whole code*/
	private static class ClassDetails extends VoidVisitorAdapter<List<String[]>>
	{
		//Overriding visit method for  class declaration
		@Override
		public void visit(ClassOrInterfaceDeclaration md, List<String[]> collector)
		{
			super.visit(md, collector);
			//System.out.println("Class Name Printed : " + md.getName());
			
			String[] temp = new String[4];
			collector.add(temp);
			
			collector.get(classCount)[1] = md.getNameAsString();
			
			/*details.get(count)[1]= md.getNameAsString();
			classCount++;*/
			ASTtraversal trav = new ASTtraversal();
			trav.visitBreadthFirst(md);
			collector.get(classCount)[2] = startLine;
			collector.get(classCount)[3] = endLine;
			startLine="";
			endLine="";
			classCount++;
			
		}
		
		
	}
	
	/* Using TreeVisitor to process each node and getting its information  */
	private static class ASTtraversal extends TreeVisitor
	{
		
		//Using BFS traversal to walk in tree
		@Override 
		public void visitBreadthFirst(Node node)
		{
			Optional<Position> start = node.getBegin();
			Position posStart = start.get();
			//System.out.print("Starting line of class :: " + posStart.line + "\t");
			Optional<Position> end = node.getEnd();
			Position posEnd = end.get();
			//System.out.println("Ending line of class :: " + posEnd.line);
			startLine = Integer.toString(posStart.line);
			endLine = Integer.toString(posEnd.line);
			
		}

		@Override
		public void process(Node node) {
			// TODO Auto-generated method stub
			
		}
		
	}

	
	public static void main(String[] args) throws Exception {
		
		final String FILE_PATH = "Example1.java";
		//CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
		
		//System.out.println(cu);
		
		//VoidVisitor<?> helper = new ClassDetails();
		//helper.visit(cu, null);
		
		String LINE_FILE_PATH = "Line_Info.csv";
		String ALL_FILES_PATH = "File_Paths.csv";
		
		File writeFile = new File(LINE_FILE_PATH);
		File readFile = new File(ALL_FILES_PATH);
		
		try
		{
			//Reading part
			FileReader fileReader = new FileReader(readFile);
			
			CSVParser csvParser = new CSVParserBuilder().withEscapeChar('\0').build();
			CSVReader csvReader = new CSVReaderBuilder(fileReader).withCSVParser(csvParser).build();
			
			List<String[]> allData = csvReader.readAll();
			
			//Writing part
			FileWriter outputFile = new FileWriter(LINE_FILE_PATH);
			
			CSVWriter csvWriter = new CSVWriter(outputFile);
			
			String[] header = { "FILE PATH", "CLASS NAME" , "START LINE", "END LINE"};
			
			csvWriter.writeNext(header);
			
			for(int i=0;i<allData.size();i++)
			{
				String[] temp = allData.get(i);
				
				String TEMP_PATH = temp[0];
				
				/*String[] data =  new String[4];
				data[0] = TEMP_PATH;
				details.add(data);*/
				
				List<String[]> collector = new ArrayList<String[]>();
				
				CompilationUnit cu = StaticJavaParser.parse(new File(TEMP_PATH));
				
				VoidVisitor< List<String[]> > helper = new ClassDetails();
				helper.visit(cu, collector);
				
				for(int j=0;j<classCount;j++)
				{
					details.add(collector.get(j));
					details.get(count)[0] = TEMP_PATH;
					count++;
				}
				classCount = 0;
				System.out.println(i + " File completed");
			}
			csvWriter.writeAll(details);
			csvWriter.flush();
			csvWriter.close();
			outputFile.close();
			fileReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		/*for(int i=0;i<details.size();i++)
		{
			System.out.println(details.get(i)[0]+"\t"+details.get(i)[1]+"\t"+details.get(i)[2]+"\t"+details.get(i)[3]);
		}*/
		/*try
		{
			FileWriter outputFile = new FileWriter(file);
			
			
			CSVWriter writer = new CSVWriter(outputFile);
			
			String header[] = { "FILE PATH", "CLASS NAME" , "START LINE", "END LINE"}
			
			writer.writeNext(header);
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}*/
		
		/*ASTtraversal trav = new ASTtraversal();
		trav.visitBreadthFirst(cu);*/
		
		/*Node.BreadthFirstIterator iterator = new Node.BreadthFirstIterator(cu);
		while(iterator.hasNext())
		{
			
			System.out.println("* " + iterator.next());
		}*/
		
		
		
	}
}
