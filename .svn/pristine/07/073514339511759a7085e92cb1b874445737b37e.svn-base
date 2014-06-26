package typechecker.implementation;


import java.util.ArrayList;
import java.util.List;

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
import ast.Expression;
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
import ast.Type;
import ast.UnknownType;
import ast.VarDecl;
import ast.While;

import typechecker.ErrorReport;
import util.ImpTable;
import visitor.Visitor;

/**
 * This class implements Phase 2 of the Type Checker. This phase
 * assumes that we have already constructed the program's symbol table in
 * Phase1.
 * <p>
 * Phase 2 checks for the use of undefined identifiers and type errors.
 * <p>
 * Visitors may return a Type as a result. Generally, only visiting 
 * an expression or a type actually returns a type.
 * <p>
 * Visiting other nodes just returns null.
 * 
 * @author kdvolder
 */
public class TypeCheckVisitor implements Visitor<Type> {

	/**
	 * The place to send error messages to.
	 */
	private ErrorReport errors;

	/**
	 * The symbol table from Phase 1. 
	 */
	private ImpTable<Type> env;
	
	private ObjectType currentClass = null;
	private MethodType currentMethod = null;
	
	boolean haveMainClass = false;

	public TypeCheckVisitor(ImpTable<Type> variables, ErrorReport errors) {
		this.env = variables;
		this.errors = errors;
	}

	//// Helpers /////////////////////

	/**
	 * Check whether the type of a particular expression is as expected.
	 */
	private void check(Expression exp, Type expected) {
		Type actual = exp.accept(this);
		if (!assignableFrom(expected, actual))
			errors.typeError(exp, expected, actual);
	}

	/**
	 * Check whether two types in an expression are the same
	 */
	private void check(Expression exp, Type t1, Type t2) {
		if (!t1.equals(t2))
			errors.typeError(exp, t1, t2);
	}	

	private boolean assignableFrom(Type varType, Type valueType) {
		return varType.equals(valueType); 
	}
	
	/**
	 *  Searches for a method within a class, or its superclasses. If not found, returns null.
	 */
	private MethodType findMethod(String methodName, ObjectType target) {
		if (target.classInfo.getMethod(methodName) != null) {
			return target.classInfo.getMethod(methodName);
		} else {
			if (target.classInfo.superName != null) {
				// Class does have a superclass, check that the superclass is defined
				if (env.lookup(target.classInfo.superName) != null) {
					// Superclass is defined, check if the method exists there
					ObjectType superclass = (ObjectType) env.lookup(target.classInfo.superName);
					return findMethod(methodName, superclass);
				} else {
					// Superclass not defined
					errors.undefinedType(target.classInfo.superName);
					return null;
				}
			} else {
				// Class does not have a superclass, method not found
				errors.undefinedMethod(methodName);
				return null;
			}
		}
	}
	
	/**
	 *  Searches for a field within a class, or its superclasses. If not found, returns null.
	 */
	private Type findField(String fieldName, ObjectType target) {
		if (target.classInfo.getField(fieldName) != null) {
			return target.classInfo.getField(fieldName);
		} else {
			if (target.classInfo.superName != null) {
				// Class does have a superclass, check that the superclass is defined
				if (env.lookup(target.classInfo.superName) != null) {
					// Superclass is defined, check if the field exists there
					ObjectType superclass = (ObjectType) env.lookup(target.classInfo.superName);
					return findField(fieldName, superclass);
				} else {
					// Superclass not defined
					errors.undefinedType(target.classInfo.superName);
					return null;
				}
			} else {
				// Class does not have a superclass, field not found
				errors.undefinedId(fieldName);
				return null;
			}
		}
	}

	///////// Visitor implementation //////////////////////////////////////

	@Override
	public <T extends AST> Type visit(NodeList<T> ns) {
		for (int i = 0; i < ns.size(); i++) {
			ns.elementAt(i).accept(this);
		}
		return null;
	}

	@Override
	public Type visit(Program n) {
		n.mainClass.accept(this);
		n.classes.accept(this);
		return null;
	}

	@Override
	public Type visit(MainClass n) {
		if (!haveMainClass) {
			n.statement.accept(this);
			haveMainClass = true;
			return null;
		} else {
			errors.duplicateMainClass(n.className);
			return null;
		}

	}

	@Override
	public Type visit(ClassDecl n) {
		this.currentClass = (ObjectType) env.lookup(n.name);
		// Check that superclass is defined if one is declared
		if(n.superName != null) {
			Type superclass = env.lookup(n.superName);
			if(superclass == null) {
				errors.undefinedId(n.superName);
			}
		}
		n.vars.accept(this);
		n.methods.accept(this);
		this.currentClass = null;
		return null;
	}

	@Override
	public Type visit(MethodDecl n) {
		if (currentClass.classInfo.getMethod(n.name) == null) {
			errors.undefinedId(n.name);
			return null;
		} else {
			this.currentMethod = (MethodType) currentClass.classInfo.getMethod(n.name);
			// Check that there are no duplicate formals and locals
			for(int i = 0; i < n.formals.size(); i++) {
				VarDecl formal = n.formals.elementAt(i);
				if(currentMethod.locals.lookup(formal.name) != null) {
					errors.duplicateDefinition(formal.name);
				}
				formal.accept(this);
			}
			n.vars.accept(this);
			n.statements.accept(this);
			check(n.returnExp, n.returnType);
		}
		this.currentMethod = null;
		
		return null;
	}

	@Override
	public Type visit(VarDecl n) {
		if(n.type instanceof ObjectType) {
			ObjectType t = (ObjectType) n.type;
			if(env.lookup(t.name) == null) {
				errors.undefinedType(t.name);
			}
		}
		return null;
	}

	@Override
	public Type visit(BooleanType n) {
		return n;
	}

	@Override
	public Type visit(IntegerType n) {
		return n;
	}

	@Override
	public Type visit(IntArrayType n) {
		return n;
	}

	@Override
	public Type visit(ObjectType n) {
		return n;
	}

	@Override
	public Type visit(MethodType n) {
		return null;
	}
	
	@Override
	public Type visit(UnknownType n) {
		return n;
	}

	@Override
	public Type visit(Block n) {
		n.statements.accept(this);
		return null;
	}

	@Override
	public Type visit(If n) {
		check(n.test, new BooleanType());
		n.then.accept(this);
		n.els.accept(this);
		return null;
	}

	@Override
	public Type visit(While n) {
		check(n.test, new BooleanType());
		n.body.accept(this);
		return null;
	}

	@Override
	public Type visit(Print n) {
		Type actual = n.exp.accept(this);
		if (!assignableFrom(new IntegerType(), actual) && !assignableFrom(new BooleanType(), actual)) {
			List<Type> l = new ArrayList<Type>();
			l.add(new IntegerType());
			l.add(new BooleanType());
			errors.typeError(n.exp, l, actual);
		}
		return null;
	}

	@Override
	public Type visit(Assign n) {

		// Type check the variable being assigned to in the current scope
		// Look for it in the method formals
		if (currentMethod.lookupFormal(n.name) != null) {
			check(n.value, currentMethod.lookupFormal(n.name).type);
		// Look for it in the method locals
		} else if (currentMethod.lookupLocal(n.name) != null) {
			check(n.value, currentMethod.lookupLocal(n.name));
		// Look for it in the class fields
		} else {
			Type fieldType = findField(n.name, currentClass);
			
			if (fieldType != null) {
				check(n.value, fieldType);
			} else {
				errors.undefinedId(n.name);
			}
		} 
		
		return null;
	}

	@Override
	public Type visit(ArrayAssign n) {
		check(n.value, new IntegerType());
		check(n.index, new IntegerType());
		// Check that variable being assigned to is of type IntArray
		if (currentMethod.lookupFormal(n.name) != null) {
			Type formalType = currentMethod.lookupFormal(n.name).type;
			if(!assignableFrom(formalType, new IntArrayType())) {
				errors.typeError(new IdentifierExp(n.name), new IntArrayType(), formalType);
			}
		} else if (currentMethod.lookupLocal(n.name) != null) {
			Type localType = currentMethod.lookupLocal(n.name);
			if(!assignableFrom(localType, new IntArrayType())) {
				errors.typeError(new IdentifierExp(n.name), new IntArrayType(), localType);
			}
		} else {
			Type fieldType = findField(n.name, currentClass);
			
			if (fieldType != null) {
				if(!assignableFrom(fieldType, new IntArrayType())) {
					errors.typeError(new IdentifierExp(n.name), new IntArrayType(), fieldType);
				}
			} else {
				errors.undefinedId(n.name);
			}
		}
		
		return null;
	}

	@Override
	public Type visit(And n) {
		check(n.e1, new BooleanType());
		check(n.e2, new BooleanType());
		n.setType(new BooleanType());
		return n.getType();
	}

	@Override
	public Type visit(LessThan n) {
		check(n.e1, new IntegerType());
		check(n.e2, new IntegerType());
		n.setType(new BooleanType());
		return n.getType();
	}

	@Override
	public Type visit(Plus n) {
		check(n.e1, new IntegerType());
		check(n.e2, new IntegerType());
		n.setType(new IntegerType());
		return n.getType();
	}

	@Override
	public Type visit(Minus n) {
		check(n.e1, new IntegerType());
		check(n.e2, new IntegerType());
		n.setType(new IntegerType());
		return n.getType();
	}

	@Override
	public Type visit(Times n) {
		check(n.e1, new IntegerType());
		check(n.e2, new IntegerType());
		n.setType(new IntegerType());
		return n.getType();
	}

	@Override
	public Type visit(ArrayLookup n) {
		check(n.index, new IntegerType());
		check(n.array, new IntArrayType());
		n.setType(new IntegerType());
		return n.getType();
	}

	@Override
	public Type visit(ArrayLength n) {
		check(n.array, new IntArrayType());
		n.setType(new IntegerType());
		return n.getType();
	}
	
	@Override
	public Type visit(Call n) {
		
		ObjectType target;
		MethodType method;
		
		// check that the receiver is valid (i.e. is a defined object)
		Type receiver = n.receiver.accept(this);
			// first check that the receiver is an ObjectType
		if (receiver instanceof ObjectType) {
			target = (ObjectType) receiver;
		} else {
			errors.typeError(n.receiver, new ObjectType(), receiver);
			return new UnknownType();
		}
			// check that the object type has been defined
		if (env.lookup(target.name) == null) {
			errors.undefinedType(target.name);
			return new UnknownType();
		} else {
			target = (ObjectType) env.lookup(target.name);
		}

		// find the method in the receiver or its superclass
		method = findMethod(n.name, target);
		if (method == null) {
			return new UnknownType();
		}
		// check that the number of arguments and parameters are the same
		int numOfArgs = n.rands.size();
		int numOfParams = method.formals.size();
		if(numOfArgs != numOfParams) {
			errors.functionError(n.name, numOfParams, numOfArgs);
			return method.returnType;
		}
		// check that the types of the arguments match the types of the parameters
		for( int i = 0; i < n.rands.size(); i++){
			Type declaredType = method.formals.get(i).type;
			Type actualType = n.rands.elementAt(i).accept(this);
			if (actualType.getClass().isInstance(new ObjectType())) {
				actualType = (ObjectType) env.lookup(((ObjectType) actualType).name);
			}
			check(n.rands.elementAt(i), declaredType, actualType);
		}
		
		// return the return type of the method
		return method.returnType;
	}
	
	

	@Override
	public Type visit(IntegerLiteral n) {
		n.setType(new IntegerType());
		return n.getType();
	}

	@Override
	public Type visit(BooleanLiteral n) {
		n.setType(new BooleanType());
		return n.getType();
	}

	@Override
	public Type visit(IdentifierExp n) {
		if (currentMethod.lookupFormal(n.name) != null) {
			n.setType(currentMethod.lookupFormal(n.name).type);
			return n.getType();
		} else if (currentMethod.lookupLocal(n.name) != null) {
			n.setType(currentMethod.lookupLocal(n.name));
			return n.getType();
		} else {
			Type fieldType = findField(n.name, currentClass);
			
			if (fieldType != null) {
				n.setType(fieldType);
				return n.getType();
			} else {
				errors.undefinedId(n.name);
				return new UnknownType();
			}
		} 
		
	}

	@Override
	public Type visit(This n) {
		n.setType(currentClass);
		return n.getType();
	}

	@Override
	public Type visit(NewArray n) {
		check(n.size, new IntegerType());
		n.setType(new IntArrayType());
		return n.getType();
	}

	@Override
	public Type visit(NewObject n) {
		if (env.lookup(n.typeName) == null) {
			errors.undefinedId(n.typeName);
		} else {
			n.setType(env.lookup(n.typeName));
		}
		return n.getType();
	}

	@Override
	public Type visit(Not not) {
		check(not.e, new BooleanType());
		not.setType(new BooleanType());
		return not.getType();
	}

}
