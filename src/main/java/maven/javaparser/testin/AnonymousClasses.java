package maven.javaparser.testin;

import java.io.File;
import java.util.Optional;

import com.github.javaparser.Position;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.TreeVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;

public class AnonymousClasses {
	
	private static class AnnonyDetails extends VoidVisitorAdapter<Void>
	{
		@Override 
		public void visit(ObjectCreationExpr md,Void arg)
		{
			super.visit(md, arg);
			System.out.println(md.getAnonymousClassBody());
			if(md.isObjectCreationExpr())
			{
				if(!(md.getAnonymousClassBody().isEmpty() ) )
					System.out.println(md.getAnonymousClassBody().getClass());
			}
		}
	}

	public static void main(String[] args) throws Exception {
		
		String FILE_PATH = "119227.java";
		
		File file = new File(FILE_PATH);
		
		CompilationUnit cu = StaticJavaParser.parse(file);
		
		AnnonyDetails check = new AnnonyDetails();
		check.visit(cu, null);
		
		
		
	}
		
}
