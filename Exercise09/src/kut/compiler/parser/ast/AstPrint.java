package kut.compiler.parser.ast;

import java.util.Vector;

import kut.compiler.cgen.CodeGenerator;
import kut.compiler.tokenizer.Token;

/**
 * @author hnishino
 *
 */
public class AstPrint extends AstNode
{
	Token token = null;
	private Vector<AstNode> parameters = null;
	
	/**
	 * 
	 */
	public AstPrint(Token token) {
		this.token = token;
		this.parameters = new Vector<AstNode>();
	}
	
	/**
	 * @param param
	 */
	public void addParam(AstNode param) {
		parameters.add(param);
	}

	
	/**
	 *
	 */
	@Override
	public String getTreeString(int indent) {
		StringBuffer buf = new StringBuffer();
		buf.append(this.getIndentedStringWithCR(indent, "Print:"));
		for (AstNode n: parameters) {
			buf.append(n.getTreeString(indent + 1));
		}
		return buf.toString();
	}

	/**
	 *
	 */
	@Override
	public void cgen(CodeGenerator gen) {

		gen.appendCode("mov rax, 0");
		for (AstNode n: parameters) {
			gen.appendCode("push rax");			
			n.cgen(gen);
			gen.appendCode("call " + gen.getPrintIntLabel());		
			gen.appendCode("pop rbx");
			gen.appendCode("add rax, rbx");
		}		
	}
	
}
