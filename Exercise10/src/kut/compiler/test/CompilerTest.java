package kut.compiler.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import kut.compiler.cgen.CodeGenerator;
import kut.compiler.parser.Parser;
import kut.compiler.parser.ast.AstNode;
import kut.compiler.test.util.CheckOutput;
import kut.compiler.tokenizer.Tokenizer;

/**
 * @author hnishino
 *
 */
class CompilerTest {


	@Test
	void gVarDec01() throws Exception {
		String testname = Thread.currentThread().getStackTrace()[1].getMethodName();
		test(testname, "testcode50");
	}

	@Test
	void gVarDec02() throws Exception {
		String testname = Thread.currentThread().getStackTrace()[1].getMethodName();
		test(testname, "testcode51");
	}




	/**
	 * @param testname
	 * @param codeFilename
	 * @throws Exception
	 */
	void test(String testname, String codeFilename) throws Exception {
		
		String baseFilename = codeFilename + ".";
		String minExt = "min";
		String asmExt = "asm";
		String ansExt = "asm";
		
		String minDir = "src/TestCaseCode/";
		String asmDir = "src/CompilerOutput/";
		String ansDir = "src/CompilerOutput/ans/";
		
		if (CodeGenerator.isMac() == true) {
			ansDir += "mac/";
		}
		else if (CodeGenerator.isWindows() == true) {
			ansDir += "win/";
		}

		String minFilename = minDir + baseFilename + minExt;
		String asmFilename = asmDir + baseFilename + asmExt;
		String ansFilename = ansDir + baseFilename + ansExt;
		
		Tokenizer tokenizer = new Tokenizer(minFilename);
		
		Parser parser = new Parser(tokenizer);
		parser.parse();
		
		CodeGenerator generator = CodeGenerator.getCodeGenerator();
		
		AstNode root = parser.getRootNode();
		generator.beforeCGEN(root);
		generator.cgen(root);
		generator.write(asmFilename);
		
		boolean ret = CheckOutput.getDiffString(testname, asmFilename, ansFilename);
		
		if (ret == false) {
			fail("the output asm code doesn't match the answer.");
		}
	}
	
}
