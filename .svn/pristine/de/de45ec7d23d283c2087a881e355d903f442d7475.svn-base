package ast;

import java.io.PrintWriter;
import java.io.StringWriter;

import visitor.PrettyPrintVisitor;
import visitor.StructurePrintVisitor;
import visitor.Visitor;


public abstract class AST {

	public abstract <R> R accept(Visitor<R> v);

	@Override
	public String toString() {
		StringWriter out = new StringWriter();
		this.accept(new PrettyPrintVisitor(new PrintWriter(out)));
		return out.toString();
	}
	public String dump() {
		StringWriter out = new StringWriter();
		this.accept(new StructurePrintVisitor(new PrintWriter(out)));
		return out.toString();		
	}

}
