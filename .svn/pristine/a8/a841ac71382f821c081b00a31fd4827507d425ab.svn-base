package typechecker.implementation;

import ast.Program;
import ast.Type;
import typechecker.ErrorReport;
import typechecker.TypeChecked;
import typechecker.TypeCheckerException;
import util.ImpTable;


public class TypeCheckerImplementation extends TypeChecked {
	
	/**
	 * The AST of the program we are type checking.
	 */
	private Program program;

	/**
	 * The place to which error messages get sent.
	 */
	private ErrorReport errors = new ErrorReport();

	/**
	 * The symbol table computed by phase 1:
	 */
	private ImpTable<Type> variables;

	public TypeCheckerImplementation(Program program) {
		this.program = program;
	}

	public TypeChecked typeCheck() throws TypeCheckerException {
		//Phase 1:
		variables = buildTable();
		//Phase 2:
		program.accept(new TypeCheckVisitor(variables, errors));
		//Throw an exception if there were errors:
		errors.close();
		// If there was no exception:
		return this;
	}

	/**
	 * This is really an internal helper method, which should not be public.
	 * It has only been made public to allow us to test Phase 1 of the typechecker
	 * in isolation. In normal operation (not unit testing) this method should 
	 * not be called by code outside the type checker.
	 */
	public ImpTable<Type> buildTable() {
		variables = program.accept(new BuildSymbolTableVisitor(errors));
		return variables;
	}

	public ImpTable<Type> typeCheckPhaseTwo() throws TypeCheckerException {
		program.accept(new TypeCheckVisitor(variables, errors));
		errors.close();
		return variables;
	}
	public Program getProgram() {
		return program;
	}

	public ImpTable<Type> getTable() {
		return variables;
	}

}
