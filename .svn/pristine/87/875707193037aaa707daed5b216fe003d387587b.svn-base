package typechecker.implementation;

import java.util.ArrayList;
import java.util.List;

import util.ImpTable;
import util.ImpTable.DuplicateException;
import visitor.Visitor;

import ast.Type;

public class MethodType extends Type {
	
	public Type returnType;
	public List<Formal> formals = new ArrayList<Formal>();
	public ImpTable<Type> locals = new ImpTable<Type>();
	
	public MethodType(Type aReturnType) {
		this.returnType = aReturnType;
	}
	
	public void addFormal(Formal f) throws DuplicateException{
		if (this.lookupFormal(f.id) == null) {
			formals.add(f);
		} else {
			throw new DuplicateException("Duplicate entry: " + f.id);
		}
	}

	public void addLocal(String n, Type t) throws DuplicateException {
		locals.put(n, t);
	}
	
	public Formal lookupFormal(String name) {
		for( Formal f : formals) {
			if (name.equals(f.id)) {
				return f;
			}
		}
		return null;
	}
	
	public Type lookupLocal(String name) {
		return locals.lookup(name);
	}
	
	@Override
	public boolean equals(Object other) {
		return this.getClass()==other.getClass();
	}

	@Override
	public <R> R accept(Visitor<R> v) {
		return v.visit(this);
	}

}
