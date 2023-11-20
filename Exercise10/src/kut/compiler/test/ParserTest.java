package kut.compiler.test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import kut.compiler.parser.Parser;
import kut.compiler.tokenizer.Tokenizer;

/**
 * @author hnishino
 *
 */
class ParserTest {

	
	@Test
	void identifier01() throws Exception {
		Tokenizer tokenizer = new Tokenizer("src/TestCaseCode/testcode50.min");
		
		Parser parser = new Parser(tokenizer);
		parser.parse();
		
		String tree = parser.getTreeString();
		if (("Program:\n"
				+ "  GVarDec:a(int)\n"
				+ "  Identifier:a\n").equals(tree) == false) {
			fail("parsing failed.");
		}
	}

	@Test
	void identifier02() throws Exception {
		Tokenizer tokenizer = new Tokenizer("src/TestCaseCode/testcode51.min");
		
		Parser parser = new Parser(tokenizer);
		parser.parse();
		
		String tree = parser.getTreeString();
		if (("Program:\n"
				+ "  GVarDec:a(int)\n"
				+ "  GVarDec:b(int)\n"
				+ "  GVarDec:c(int)\n"
				+ "  AstBinOp:/\n"
				+ "    AstBinOp:*\n"
				+ "      Identifier:a\n"
				+ "      Identifier:b\n"
				+ "    AstBinOp:+\n"
				+ "      Identifier:c\n"
				+ "      IntLiteral:10\n").equals(tree) == false) {
			fail("parsing failed.");
		}
	}

}
