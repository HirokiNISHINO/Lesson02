package kut.compiler.test.util;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import kut.compiler.parser.Parser;
import kut.compiler.tokenizer.Tokenizer;

/**
 * @author hnishino
 *
 */
class ParserTest02 {

	
	@Test
	void negativeIntLiteral03() throws Exception {
		Tokenizer tokenizer = new Tokenizer("src/TestCaseCode/testcode34.min");
		
		Parser parser = new Parser(tokenizer);
		parser.parse();
		
		String tree = parser.getTreeString();
		if (("Program:\n"
				+ "  IntLiteral:-1\n").equals(tree) == false) {
			fail("parsing failed.");
		}
	}
	
	@Test
	void negativeIntLiteral04() throws Exception {
		Tokenizer tokenizer = new Tokenizer("src/TestCaseCode/testcode35.min");
		
		Parser parser = new Parser(tokenizer);
		parser.parse();
		
		String tree = parser.getTreeString();
		if (("Program:\n"
				+ "  AstBinOp:*\n"
				+ "    IntLiteral:5\n"
				+ "    IntLiteral:-1\n").equals(tree) == false) {
			fail("parsing failed.");
		}
	}
	
	@Test
	void print02() throws Exception {
		Tokenizer tokenizer = new Tokenizer("src/TestCaseCode/testcode36.min");
		
		Parser parser = new Parser(tokenizer);
		parser.parse();
		
		String tree = parser.getTreeString();
		if (("Print:\n"
				+ "  IntLiteral:0\n"
				+ "  IntLiteral:1\n"
				+ "  IntLiteral:-1\n"
				+ "  AstBinOp:*\n"
				+ "    IntLiteral:1\n"
				+ "    IntLiteral:10\n"
				+ "  AstBinOp:/\n"
				+ "    IntLiteral:20\n"
				+ "    IntLiteral:5\n").equals(tree) == false) {
			fail("parsing failed.");
		}
	}
}
