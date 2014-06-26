package test.translate;

import ir.frame.Frame;
import ir.frame.x86_64.X86_64Frame;
import ir.interp.Interp;
import ir.interp.InterpMode;

import java.io.File;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import util.SampleCode;
import translate.Fragments;
import translate.Translator;
import typechecker.TypeCheckerException;
import util.Utils;


/**
 * Test the minijava translation phase that takes a (type-checked) program and turns
 * the bodies of all the methods in the program into IRtrees.
 * <p>
 * This test suite uses the IR interpreter to simulate the execution of the
 * resulting IR. This gives us some confidence that our translation works correctly :-)
 * 
 * @author kdvolder
 */
public class TestTranslate {

	public static final Frame architecture = X86_64Frame.factory;

	/**
	 * To make it easy to run all of these tests with the either 
	 * linearized ir code, basic blocks or trace scheduled code
	 * We determine the simulation mode via this method.
	 * <p>
	 * Simply creating a subclass and overriding this method will create
	 * a test suite that runs all the same tests in a different simulation 
	 * mode.
	 * 
	 * @return
	 */
	protected InterpMode getSimulationMode() {
		// return null;
		return InterpMode.LINEARIZED_IR;
	}

	/**
	 * Print out all the generated IR?
	 * <p>
	 * If false, only the result of simulating the IR execution 
	 * will be printed.
	 */
	protected boolean dumpIR() {
		return true;
	}
	
	@Test @Ignore
	public void BubbleSort() throws Exception {
		test("20\r\n" + 
				"7\r\n" + 
				"12\r\n" + 
				"18\r\n" + 
				"2\r\n" + 
				"11\r\n" + 
				"6\r\n" + 
				"9\r\n" + 
				"19\r\n" + 
				"5\r\n" + 
				"99999\r\n" + 
				"2\r\n" + 
				"5\r\n" + 
				"6\r\n" + 
				"7\r\n" + 
				"9\r\n" + 
				"11\r\n" + 
				"12\r\n" + 
				"18\r\n" + 
				"19\r\n" + 
				"20\r\n" + 
				"0\r\n" + 
				"",
				"class BubbleSort{\n" + 
				"	public static void main(String[] a){\n" + 
				"		System.out.println(new BBS().Start(10));\n" + 
				"	}\n" + 
				"}\n" + 
				"\n" + 
				"\n" + 
				"// This class contains the array of integers and\n" + 
				"// methods to initialize, print and sort the array\n" + 
				"// using Bublesort\n" + 
				"class BBS{\n" + 
				"\n" + 
				"	int[] number ;\n" + 
				"	int size ;\n" + 
				"\n" + 
				"	// Invoke the Initialization, Sort and Printing\n" + 
				"	// Methods\n" + 
				"	public int Start(int sz){\n" + 
				"		int aux01 ;\n" + 
				"		aux01 = this.Init(sz);\n" + 
				"		aux01 = this.Print();\n" + 
				"		System.out.println(99999);\n" + 
				"		aux01 = this.Sort();\n" + 
				"		aux01 = this.Print();\n" + 
				"		return 0 ;\n" + 
				"	}\n" + 
				"\n" + 
				"\n" + 
				"	// Sort array of integers using Bublesort method\n" + 
				"	public int Sort(){\n" + 
				"		int nt ;\n" + 
				"		int i ;\n" + 
				"		int aux02 ;\n" + 
				"		int aux04 ;\n" + 
				"		int aux05 ;\n" + 
				"		int aux06 ;\n" + 
				"		int aux07 ;\n" + 
				"		int j ;\n" + 
				"		int t ;\n" + 
				"		i = size - 1 ;\n" + 
				"		aux02 = 0 - 1 ;\n" + 
				"		while (aux02 < i) {\n" + 
				"			j = 1 ;\n" + 
				"			//aux03 = i+1 ;\n" + 
				"			while (j < (i+1)){\n" + 
				"				aux07 = j - 1 ;\n" + 
				"				aux04 = number[aux07] ;\n" + 
				"				aux05 = number[j] ;\n" + 
				"				if (aux05 < aux04) {\n" + 
				"					aux06 = j - 1 ;\n" + 
				"					t = number[aux06] ;\n" + 
				"					number[aux06] = number[j] ;\n" + 
				"					number[j] = t;\n" + 
				"				}\n" + 
				"				else nt = 0 ;\n" + 
				"				j = j + 1 ;\n" + 
				"			}\n" + 
				"			i = i - 1 ;\n" + 
				"		}\n" + 
				"		return 0 ;\n" + 
				"	}\n" + 
				"\n" + 
				"	// Printing method\n" + 
				"	public int Print(){\n" + 
				"		int j ;\n" + 
				"		j = 0 ;\n" + 
				"		while (j < (size)) {\n" + 
				"			System.out.println(number[j]);\n" + 
				"			j = j + 1 ;\n" + 
				"		}\n" + 
				"		return 0 ;\n" + 
				"	}\n" + 
				"\n" + 
				"	// Initialize array of integers\n" + 
				"	public int Init(int sz){\n" + 
				"		size = sz ;\n" + 
				"		number = new int[sz] ;\n" + 
				"\n" + 
				"		number[0] = 20 ;\n" + 
				"		number[1] = 7  ; \n" + 
				"		number[2] = 12 ;\n" + 
				"		number[3] = 18 ;\n" + 
				"		number[4] = 2  ; \n" + 
				"		number[5] = 11 ;\n" + 
				"		number[6] = 6  ; \n" + 
				"		number[7] = 9  ; \n" + 
				"		number[8] = 19 ; \n" + 
				"		number[9] = 5  ;\n" + 
				"\n" + 
				"		return 0 ;	\n" + 
				"	}\n" + 
				"\n" + 
				"}\n" + 
				""
		);
	}

	
	@Test @Ignore
	public void variableAccess() throws Exception {
		test("15\r\n",
				"class TranslateFieldsAndLocals {\n" + 
				"	public static void main(String[] a){\n" + 
				"	System.out.println(new Driver().start(2));\n" + 
				"    }\n" + 
				"}\n" + 
				"\n" + 
				"class Driver {\n" + 
				"	\n" + 
				"	int a;\n" + 
				"	int d;\n" + 
				"	int[] e;\n" + 
				"	\n" + 
				"	public int start(int b) {\n" + 
				"		int c;\n" + 
				"		e = new int[2];\n" + 
				"		\n" + 
				"		a = 1;\n" + 
				"		c = 3;\n" + 
				"		d = 0;\n" + 
				"		d = d + 4;\n" + 
				"		e[1] = 4;\n" + 
				"		e[1] = e[1] + 1;\n" + 
				"		return a + b + c + d + e[1];\n" + 
				"	}\n" + 
				"	\n" + 
				"}"
		);
	}
	
	@Test @Ignore
	public void arrayBad() throws Exception {
		test("MiniJava failure 1\n" + 
				"",
				"class Main {\n" + 
				"    public static void main(String[] args) {\n" + 
				"	System.out.println(new Foo().doit());\n" + 
				"    }\n" + 
				"}\n" + 
				"class Foo {\n" + 
				"    public int doit() {\n" + 
				"	int [] arr;\n" + 
				"	arr = new int[2];\n" + 
				"	arr[0] = 0;\n" + 
				"	arr[1] = 1;\n" + 
				"	arr[2] = 2;\n" + 
				"	return 0;\n" + 
				"    }\n" + 
				"}\n" + 
				""
		);
	}
	
	@Test @Ignore
	public void invokeBad() throws Exception {
		test("MiniJava failure 2\n" + 
				"",
				"class Array{\n" + 
				"    public static void main(String[] a){\n" + 
				"	System.out.println(new Test().do());\n" + 
				"    }\n" + 
				"}\n" + 
				"\n" + 
				"class A {\n" + 
				"    public int m() {\n" + 
				"	return 0;\n" + 
				"    }\n" + 
				"}\n" + 
				"\n" + 
				"class Test {\n" + 
				"    A outera;\n" + 
				"    public int do() {\n" + 
				"	A a;\n" + 
				"	int x;\n" + 
				"	x = outera.m();\n" + 
				"	x = a.m();\n" + 
				"	x = this.do();\n" + 
				"	return 0;\n" + 
				"    }\n" + 
				"}"
		);
	}

	
	@Test @Ignore
	public void array() throws Exception {
		test("0\r\n" + 
				"1\r\n" + 
				"4\r\n" + 
				"9\r\n" + 
				"16\r\n" + 
				"25\r\n" + 
				"36\r\n" + 
				"49\r\n" + 
				"64\r\n" + 
				"81\r\n" + 
				"0\r\n" + 
				"",
				"class Array{\n" + 
				"    public static void main(String[] a){\n" + 
				"	System.out.println(new Test().do());\n" + 
				"    }\n" + 
				"}\n" + 
				"\n" + 
				"class Test {\n" + 
				"    int [] a;\n" + 
				"    public int fill() {\n" + 
				"	int i;\n" + 
				"	a = new int[10];\n" + 
				"	i = 0;\n" + 
				"	while (i < 10) {\n" + 
				"	    a[i] = i * i;\n" + 
				"	    i = i + 1;\n" + 
				"	}\n" + 
				"	return 0;\n" + 
				"    }\n" + 
				"    public int print(){\n" + 
				"	int i;\n" + 
				"	i = 0;\n" + 
				"	while (i < 10) {\n" + 
				"	    System.out.println(a[i]);\n" + 
				"	    i = i + 1;\n" + 
				"	}\n" + 
				"	return 0;\n" + 
				"    }\n" + 
				"    public int inspect(){\n" + 
				"	int i;\n" + 
				"	int j;\n" + 
				"	int k;\n" + 
				"	int l;\n" + 
				"	int m;\n" + 
				"	j = 0;\n" + 
				"	k = 0;\n" + 
				"	l = 0;\n" + 
				"	m = 0;\n" + 
				"	i = 0;\n" + 
				"	while (i < 10) {\n" + 
				"	    j = j + a[i];\n" + 
				"	    k = k + a[i];\n" + 
				"	    l = l + a[i];\n" + 
				"	    m = m + a[i];\n" + 
				"\n" + 
				"	    i = i + 1;\n" + 
				"	}\n" + 
				"	return 0;\n" + 
				"    }\n" + 
				"    public int do() {\n" + 
				"	int i;\n" + 
				"	i = this.fill();\n" + 
				"	i = this.inspect();\n" + 
				"	i = this.print();\n" + 
				"	return 0;\n" + 
				"    }\n" + 
				"}\n" + 
				""
		);
	}
	
	@Test
	public void BinarySearch() throws Exception {
		test("20\r\n" + 
				"21\r\n" + 
				"22\r\n" + 
				"23\r\n" + 
				"24\r\n" + 
				"25\r\n" + 
				"26\r\n" + 
				"27\r\n" + 
				"28\r\n" + 
				"29\r\n" + 
				"30\r\n" + 
				"31\r\n" + 
				"32\r\n" + 
				"33\r\n" + 
				"34\r\n" + 
				"35\r\n" + 
				"36\r\n" + 
				"37\r\n" + 
				"38\r\n" + 
				"99999\r\n" + 
				"0\r\n" + 
				"0\r\n" + 
				"1\r\n" + 
				"1\r\n" + 
				"1\r\n" + 
				"1\r\n" + 
				"0\r\n" + 
				"0\r\n" + 
				"999\r\n" + 
				"",
				"class BinarySearch{\n" + 
				"	public static void main(String[] a){\n" + 
				"		System.out.println(new BS().Start(20));\n" + 
				"	}\n" + 
				"}\n" + 
				"// This class contains an array of integers and\n" + 
				"// methods to initialize, print and search the array\n" + 
				"// using Binary Search\n" + 
				"\n" + 
				"class BS {\n" + 
				"	int[] number ;\n" + 
				"	int size ;\n" + 
				"\n" + 
				"	// Invoke methods to initialize, print and search\n" + 
				"	// for elements on the array\n" + 
				"	public int Start(int sz){\n" + 
				"		int aux01 ;\n" + 
				"		int aux02 ;\n" + 
				"		aux01 = this.Init(sz);\n" + 
				"		aux02 = this.Print();\n" + 
				"		if (this.Search(8)) System.out.println(1) ;\n" + 
				"		else System.out.println(0) ;\n" + 
				"		if (this.Search(19)) System.out.println(1) ;\n" + 
				"		else System.out.println(0) ;\n" + 
				"		if (this.Search(20)) System.out.println(1) ;\n" + 
				"		else System.out.println(0) ;\n" + 
				"		if (this.Search(21)) System.out.println(1) ;\n" + 
				"		else System.out.println(0) ;\n" + 
				"		if (this.Search(37)) System.out.println(1) ;\n" + 
				"		else System.out.println(0) ;\n" + 
				"		if (this.Search(38)) System.out.println(1) ;\n" + 
				"		else System.out.println(0) ;\n" + 
				"		if (this.Search(39)) System.out.println(1) ;\n" + 
				"		else System.out.println(0) ;\n" + 
				"		if (this.Search(50)) System.out.println(1) ;\n" + 
				"		else System.out.println(0) ;\n" + 
				"\n" + 
				"		return 999 ;\n" + 
				"	}\n" + 
				"\n" + 
				"\n" + 
				"	// Search for a specific value (num) using\n" + 
				"	// binary search\n" + 
				"	public boolean Search(int num){\n" + 
				"		boolean bs01 ;\n" + 
				"		int right ;\n" + 
				"		int left ;\n" + 
				"		boolean var_cont ;\n" + 
				"		int medium ;\n" + 
				"		int aux01 ;\n" + 
				"		int nt ;\n" + 
				"\n" + 
				"		aux01 = 0 ;\n" + 
				"		bs01 = false ;\n" + 
				"		right = number.length ;\n" + 
				"		right = right - 1 ;\n" + 
				"		left = 0 ;\n" + 
				"		var_cont = true ;\n" + 
				"		while (var_cont){\n" + 
				"			medium = left + right ;\n" + 
				"			medium = this.Div(medium);\n" + 
				"			aux01 = number[medium] ;\n" + 
				"			if (num < aux01) right = medium - 1 ;\n" + 
				"			else left = medium + 1 ;\n" + 
				"			if (this.Compare(aux01,num)) var_cont = false ;\n" + 
				"			else var_cont = true ;\n" + 
				"			if (right < left) var_cont = false ;\n" + 
				"			else nt = 0 ;\n" + 
				"		}\n" + 
				"\n" + 
				"		if (this.Compare(aux01,num)) bs01 = true ;\n" + 
				"		else bs01 = false ;\n" + 
				"		return bs01 ;\n" + 
				"	}\n" + 
				"\n" + 
				"	// This method computes and returns the\n" + 
				"	// integer division of a number (num) by 2\n" + 
				"	public int Div(int num){\n" + 
				"		int count01 ;\n" + 
				"		int count02 ;\n" + 
				"		int aux03 ;\n" + 
				"\n" + 
				"		count01 = 0 ;\n" + 
				"		count02 = 0 ;\n" + 
				"		aux03 = num - 1 ;\n" + 
				"		while (count02 < aux03) {\n" + 
				"			count01 = count01 + 1 ;\n" + 
				"			count02 = count02 + 2 ;\n" + 
				"		}\n" + 
				"		return count01 ;	\n" + 
				"	}\n" + 
				"\n" + 
				"\n" + 
				"	// This method compares two integers and\n" + 
				"	// returns true if they are equal and false\n" + 
				"	// otherwise\n" + 
				"	public boolean Compare(int num1 , int num2){\n" + 
				"		boolean retval ;\n" + 
				"		int aux02 ;\n" + 
				"\n" + 
				"		retval = false ;\n" + 
				"		aux02 = num2 + 1 ;\n" + 
				"		if (num1 < num2) retval = false ;\n" + 
				"		else if (!(num1 < aux02)) retval = false ;\n" + 
				"		else retval = true ;\n" + 
				"		return retval ;\n" + 
				"	}\n" + 
				"\n" + 
				"	// Print the integer array\n" + 
				"	public int Print(){\n" + 
				"		int j ;\n" + 
				"\n" + 
				"		j = 1 ;\n" + 
				"		while (j < (size)) {\n" + 
				"			System.out.println(number[j]);\n" + 
				"			j = j + 1 ;\n" + 
				"		}\n" + 
				"		System.out.println(99999);\n" + 
				"		return 0 ;\n" + 
				"	}\n" + 
				"\n" + 
				"\n" + 
				"	// Initialize the integer array\n" + 
				"	public int Init(int sz){\n" + 
				"		int j ;\n" + 
				"		int k ;\n" + 
				"		int aux02 ;\n" + 
				"		int aux01 ;\n" + 
				"\n" + 
				"		size = sz ;\n" + 
				"		number = new int[sz] ;\n" + 
				"\n" + 
				"		j = 1 ;\n" + 
				"		k = size + 1 ;\n" + 
				"		while (j < (size)) {\n" + 
				"			aux01 = 2 * j ;\n" + 
				"			aux02 = k - 3 ;\n" + 
				"			number[j] = aux01 + aux02 ;\n" + 
				"			j = j + 1 ;\n" + 
				"			k = k - 1 ;\n" + 
				"		}\n" + 
				"		return 0 ;	\n" + 
				"	}\n" + 
				"\n" + 
				"}\n" + 
				""
		);
	}
	
//	@Test public void complexBranches() throws Exception {
//		//Trying to create a program that has many choices... so many traces.
//		//We want to work the trace schedule to a point where it actually 
//		//gets driven into some of the rarer cases.
//		test( "1\n7\n",
//		"class Main {\n" +
//		"  public static void main(String[] args) {\n" +
//		"      System.out.println(new Test().doit());\n" +
//		"  }\n" +
//		"}\n" +
//		"class Test {\n" +
//		"   int count;\n"+
//		"   public boolean cond() {\n" +
//		"      count = count + 1;\n"+
//		"      return true;\n"+
//		"   }\n"+
//		"   public int doit() {\n" +
//		"     boolean a;\n"+
//		"     a = this.cond() && this.cond() && this.cond();\n"+
//		"     if (a && this.cond() && this.cond() && this.cond() && this.cond())\n" +
//		"        System.out.println(1);" +
//		"     else\n"+
//		"        System.out.println(0);" +
//		"     return count;\n" +
//		"   }\n"+
//		"}");
//	}


	//////////////// Sample code //////////////////////////////////
	
	@Test
	public void testSampleCode() throws Exception {
		File[] files = SampleCode.sampleFiles("exp", "f");
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (!optionalSample(f))
				test(f);
		}
	}
	@Test @Ignore // Don't run this unless you are implementing inheritance support!
	public void testOptionalSampleCode() throws Exception {
		File[] files = SampleCode.sampleFiles("exp", "f");
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (optionalSample(f))
				test(f);
		}
	}
	
	protected Fragments test(File program) throws TypeCheckerException, Exception {
		System.out.println("Translating: "+program);
		String expected = Utils.getExpected(program);
		
		return test(expected, program);
	}	

	protected Fragments test(String expected, File program)
			throws TypeCheckerException, Exception {
		Fragments translated = Translator.translate(architecture, program);
		if (dumpIR()) {
			System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
			System.out.println(translated);
			System.out.println();
		}
			
		if (getSimulationMode() != null) {
			System.out.println("Simulating IR code:");
			Interp interp = new Interp(translated, getSimulationMode());
			String result = interp.run();
			System.out.println(result);
			Assert.assertEquals(expected, result);
		}
		System.out.println("=================================");
		return translated;
	}
	
	private boolean optionalSample(File f) {
		return false;
	}
	
	protected Fragments test(String expected, String program) throws Exception {
		System.out.println("Translating program: ");
		System.out.println(program);
		Fragments translated = Translator.translate(architecture, program);
		if (dumpIR()) {
			System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
			System.out.println(translated);
			System.out.println();
		}
		if (getSimulationMode()!=null) {
			System.out.println("Simulating IR code:");
			Interp interp = new Interp(translated, getSimulationMode());
			String result = interp.run();
			System.out.print(result);
			Assert.assertEquals(expected, result);
		}
		System.out.println("=================================");
		return translated;
	}

}