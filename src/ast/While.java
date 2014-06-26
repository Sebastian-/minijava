package ast;

import visitor.Visitor;


public class While extends Statement {

	public final Expression test;
	public final Statement body;
	
	public While(Expression tst, Statement body) {
		super();
		this.test = tst;
		this.body = body;
	}

	@Override
	public <R> R accept(Visitor<R> v) {
		return v.visit(this);
	}

}
