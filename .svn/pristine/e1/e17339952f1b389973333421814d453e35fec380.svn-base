package ast;

import typechecker.implementation.ClassInfo;
import visitor.Visitor;

public class ObjectType extends Type {
	
	public final String name;
	public ClassInfo classInfo;

	public ObjectType(String name) {
		super();
		this.name = name;
	}

	public ObjectType(String name, ClassInfo classInfo) {
		this(name);
		this.classInfo = classInfo;
	}

	@Override
	public <R> R accept(Visitor<R> v) {
		return v.visit(this);
	}

	@Override
	public boolean equals(Object other) {
		if (this.getClass()==other.getClass()) {
			return this.name.equals(((ObjectType)other).name);
		}
		return false;
	}
}
