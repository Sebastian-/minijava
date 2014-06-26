package ast;

import visitor.Visitor;


public class VarDecl extends AST {

	public static enum Kind {
		FIELD, LOCAL, FORMAL
	}

	public final Kind   kind;
	public final Type   type;
	public final String name;

	public VarDecl(Kind kind, Type type, String name) {
		super();
		this.kind = kind;
		this.type = type;
		this.name = name;
	}
	
	public String kindToString() {
		switch (kind) {
		 case FIELD:
             return "FIELD";
		 case LOCAL:
             return "LOCAL";
		 case FORMAL:
             return "FORMAL";
         default:
        	 return null;
		}
	}

	@Override
	public <R> R accept(Visitor<R> v) {
		return v.visit(this);
	}

}
