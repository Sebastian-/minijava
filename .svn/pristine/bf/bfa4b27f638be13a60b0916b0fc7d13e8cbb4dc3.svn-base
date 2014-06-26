package typechecker.implementation;


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
import ast.Type;
import ast.VarDecl;
import ast.While;
import typechecker.ErrorReport;
import util.ImpTable;
import util.ImpTable.DuplicateException;
import visitor.DefaultVisitor;

/**
 * This visitor implements Phase 1 of the TypeChecker. It constructs the symbol table.
 * 
 * @author norm
 */
public class BuildSymbolTableVisitor extends DefaultVisitor<ImpTable<Type>> {
	

	
	private final ImpTable<Type> env = new ImpTable<Type>();
	private ClassInfo currentClass = null;
	private MethodType currentMethod = null;
	
	private final ErrorReport errors;
	
	public BuildSymbolTableVisitor(ErrorReport errors) {
		this.errors = errors;
	}

	/////////////////// Phase 1 ///////////////////////////////////////////////////////
	// In our implementation, Phase 1 builds up a single symbol table containing all the
	// identifiers defined in an Expression program. 
	//
	// We also check for duplicate identifier definitions 

	@Override
	public <T extends AST> ImpTable<Type> visit(NodeList<T> ns) {
		for (int i = 0; i < ns.size(); i++)
			ns.elementAt(i).accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(Program n) {
		n.mainClass.accept(this);
		n.classes.accept(this);
		return env;
	}

	@Override
	public ImpTable<Type> visit(MainClass n) {
		return null;
	}

	@Override
	public ImpTable<Type> visit(ClassDecl n) {
		if (n.superName == null) {
			currentClass = new ClassInfo();
		} else {
			currentClass = new ClassInfo();
			currentClass.addSuperName(n.superName);
		}
		n.vars.accept(this);
		n.methods.accept(this);
		def(env, n.name, new ObjectType(n.name, currentClass));
		currentClass = null;
		
		return null;
	}

	@Override
	public ImpTable<Type> visit(MethodDecl n) {
		currentMethod = new MethodType(n.returnType);
		n.formals.accept(this);
		n.vars.accept(this);
		methodDef(currentClass, n.name, currentMethod);
		currentMethod = null;
		
		return null;
	}

	@Override
	public ImpTable<Type> visit(VarDecl n) {
		if (currentMethod == null) {
			try {
				currentClass.addField(n.name, n.type);
			} catch (DuplicateException e) {
				errors.duplicateDefinition(n.name);
			}
		} else {
			if (n.kind == VarDecl.Kind.LOCAL) {
				try {
					currentMethod.addLocal(n.name, n.type);
				} catch (DuplicateException e) {
					errors.duplicateDefinition(n.name);
				}
			} else {
				try {
					currentMethod.addFormal(new Formal(n.name, n.type));
				} catch (DuplicateException e) {
					errors.duplicateDefinition(n.name);
				}
			}
		}
		
		return null;
	}

	@Override
	public ImpTable<Type> visit(BooleanType n) {
		return null;
	}

	@Override
	public ImpTable<Type> visit(IntegerType n) {
		return null;
	}

	@Override
	public ImpTable<Type> visit(IntArrayType n) {
		return null;
	}

	@Override
	public ImpTable<Type> visit(ObjectType n) {
		return null;
	}

	@Override
	public ImpTable<Type> visit(Block n) {
		n.statements.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(If n) {
		n.test.accept(this);
		n.then.accept(this);
		n.els.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(While n) {
		n.test.accept(this);
		n.body.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(Print n) {
		n.exp.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(Assign n) {
		n.value.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(ArrayAssign n) {
		n.index.accept(this);
		n.value.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(And n) {
		n.e1.accept(this);
		n.e2.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(LessThan n) {
		n.e1.accept(this);
		n.e2.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(Plus n) {
		n.e1.accept(this);
		n.e2.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(Minus n) {
		n.e1.accept(this);
		n.e2.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(Times n) {
		n.e1.accept(this);
		n.e2.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(ArrayLookup n) {
		n.array.accept(this);
		n.index.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(ArrayLength n) {
		n.array.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(Call n) {
		n.receiver.accept(this);
		n.rands.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(IntegerLiteral n) {
		return null;
	}

	@Override
	public ImpTable<Type> visit(BooleanLiteral n) {
		return null;
	}

	@Override
	public ImpTable<Type> visit(IdentifierExp n) {
		return null;
	}

	@Override
	public ImpTable<Type> visit(This n) {
		return null;
	}

	@Override
	public ImpTable<Type> visit(NewArray n) {
		n.size.accept(this);
		return null;
	}

	@Override
	public ImpTable<Type> visit(NewObject n) {
		return null;
	}

	@Override
	public ImpTable<Type> visit(Not not) {
		not.e.accept(this);
		return null;
	}

	///////////////////// Helpers ///////////////////////////////////////////////
	
	/**
	 * Add an entry to a table, and check whether the name already existed.
	 * If the name already existed before, the new definition is ignored and
	 * an error is sent to the error report.
	 */
	private <V> void def(ImpTable<V> tab, String name, V value) {
		try {
			tab.put(name, value);
		} catch (DuplicateException e) {
			errors.duplicateDefinition(name);
		}
	}
	
	private void methodDef(ClassInfo currentClass, String methodName, MethodType methodTable) {
		try {
			currentClass.methods.put(methodName, methodTable);
		} catch (DuplicateException e) {
			errors.duplicateDefinition(methodName);
		}
	}
}