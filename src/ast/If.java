package ast;

import visitor.Visitor;

public class If extends Statement {
	
	public final Expression test;
	public final Statement then;
	public final Statement els;
	
	public If(Expression tst, Statement thn, Statement els) {
		super();
		this.test = tst;
		this.then = thn;
		this.els = els;
	}

	@Override
	public <R> R accept(Visitor<R> v) {
		return v.visit(this);
	}
	
}
