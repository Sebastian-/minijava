package ast;

import visitor.Visitor;

public class Assign extends Statement {
	
	public final IdentifierExp name;
	public final Expression value;
	
	public Assign(IdentifierExp name, Expression value) {
		super();
		this.name = name;
		this.value = value;
	}

	@Override
	public <R> R accept(Visitor<R> v) {
		return v.visit(this);
	}

}
