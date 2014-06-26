package driver;

import java.io.File;

import parser.Parser;

import ast.Program;

import typechecker.implementation.TypeCheckerImplementation;

public class DTypeChecker {

	/**
	 * Given a source file, compile it and write the parse tree to System.out.
	 * 
	 * @param program  program to compile.
	 */
	public static void compile(File program) throws Exception {
		Program p = Parser.parse(program);
		TypeCheckerImplementation tc = new TypeCheckerImplementation(p);
		tc.typeCheck();

		System.out.println("Symbol table:");
		System.out.println(tc.getTable());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String program = args[0];
		try {
			compile(new File(program));
		} catch (Exception e) {
			System.out.println("Compilation problem");
			e.printStackTrace();
		}
	}

}
