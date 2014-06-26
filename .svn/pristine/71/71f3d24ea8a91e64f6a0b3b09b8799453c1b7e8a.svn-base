package driver;

import ir.frame.Frame;
import ir.frame.x86_64.X86_64Frame;
import ir.interp.Interp;
import ir.interp.InterpMode;

import java.io.File;

import translate.Fragments;
import translate.Translator;

public class DTranslate {

	public static final Frame architecture = X86_64Frame.factory;
	
	protected static InterpMode getSimulationMode() {
		return InterpMode.BASIC_BLOCKS;
	}

	/**
	 * Given a source file, compile it and write the parse tree to System.out.
	 * 
	 * @param program  program to compile.
	 */
	public static void compile(File program) throws Exception {
		Fragments translated = Translator.translate(architecture, program);
		System.out.println("Fragments:");
		System.out.println(translated);

		System.out.println("Simulating IR code:");
		Interp interp = new Interp(translated, getSimulationMode());
		String result = interp.run();
		System.out.print(result);

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
