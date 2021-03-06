package visitor;

import typechecker.implementation.MethodType;
import ast.AST;
import ast.And;
import ast.ArrayAssign;
import ast.ArrayLength;
import ast.ArrayLookup;
import ast.Assign;
import ast.Block;
import ast.BooleanLiteral;
import ast.BooleanType;
import ast.Call;
import ast.ClassDecl;
import ast.IdentifierExp;
import ast.If;
import ast.IntArrayType;
import ast.IntegerLiteral;
import ast.IntegerType;
import ast.LessThan;
import ast.MainClass;
import ast.MethodDecl;
import ast.Minus;
import ast.NewArray;
import ast.NewObject;
import ast.NodeList;
import ast.Not;
import ast.ObjectType;
import ast.Plus;
import ast.Print;
import ast.Program;
import ast.This;
import ast.Times;
import ast.UnknownType;
import ast.VarDecl;
import ast.While;


/**
 * A modernized version of the Visitor interface, adapted from the textbook's
 * version.
 * <p>
 * Changes: this visitor allows you to return something as a result. 
 * The "something" can be of any particular type, so the Visitor 
 * uses Java generics to express this.
 * 
 * @author kdvolder
 */
public interface Visitor<R> {

	//Lists
	public <T extends AST> R visit(NodeList<T> ns);
	
	//Declarations
	public R visit(Program n);
	public R visit(MainClass n);
	public R visit(ClassDecl n);
	public R visit(MethodDecl n);
	public R visit(VarDecl n);
	
	//Types
	public R visit(BooleanType n);
	public R visit(IntegerType n);
	public R visit(IntArrayType n);
	public R visit(ObjectType n);
	public R visit(MethodType n);
	public R visit(UnknownType unknownType);

	//Statements
	public R visit(Block n);
	public R visit(If n);
	public R visit(While n);
	public R visit(Print n);
	public R visit(Assign n);
	public R visit(ArrayAssign n);
	
	//Expressions
	public R visit(And n);
	public R visit(LessThan n);
	public R visit(Plus n);
	public R visit(Minus n);
	public R visit(Times n);
	public R visit(ArrayLookup n);
	public R visit(ArrayLength n);
	public R visit(Call n);
	public R visit(IntegerLiteral n);
	public R visit(BooleanLiteral n);
	public R visit(IdentifierExp n);
	public R visit(This n);
	public R visit(NewArray n);
	public R visit(NewObject n);
	public R visit(Not not);




	
}
