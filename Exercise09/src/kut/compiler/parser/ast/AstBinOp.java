package kut.compiler.parser.ast;

import kut.compiler.cgen.CodeGenerator;
import kut.compiler.tokenizer.Token;

/**
 * @author hnishino
 *
 */
public class AstBinOp extends AstNode
{
	private Token	token;
	private AstNode lhs;
	private AstNode rhs;
	
	
	/**
	 * 
	 */
	public AstBinOp(Token token, AstNode lhs, AstNode rhs) {
		this.token 	= token;
		this.lhs 	= lhs;
		this.rhs 	= rhs;
	}
	

	
	/**
	 *
	 */
	@Override
	public String getTreeString(int indent) {
		String r 	=  this.getIndentedStringWithCR(indent, "AstBinOp:" + this.token.getLexeme())
					+ lhs.getTreeString(indent + 1) 
					+ rhs.getTreeString(indent + 1);
		
		return r;
	}



	@Override
	public void cgen(CodeGenerator gen) {
		
		
		switch(token.getKlazz()) {
		case '+':
			this.lhs.cgen(gen);
			gen.appendCode("push rax");
			this.rhs.cgen(gen);
			gen.appendCode("pop rbx");
			gen.appendCode("add rax, rbx");
			break;

		case '-':
			this.lhs.cgen(gen);
			gen.appendCode("push rax");
			this.rhs.cgen(gen);
			gen.appendCode("mov rbx, rax");
			gen.appendCode("pop rax");
			gen.appendCode("sub rax, rbx");
			break;

		case '*':
			this.lhs.cgen(gen);
			gen.appendCode("push rax");
			this.rhs.cgen(gen);
			gen.appendCode("pop rbx");
			gen.appendCode("imul rax, rbx");
			break;

		case '/':
			this.lhs.cgen(gen);
			gen.appendCode("push rax");
			this.rhs.cgen(gen);
			gen.appendCode("mov rbx, rax");
			gen.appendCode("pop rax");
			gen.appendCode("mov rdx, 0");
			gen.appendCode("idiv rbx");
			break;

		case '%':
			this.lhs.cgen(gen);
			gen.appendCode("push rax");
			this.rhs.cgen(gen);
			gen.appendCode("mov rbx, rax");
			gen.appendCode("pop rax");
			gen.appendCode("mov rdx, 0");
			gen.appendCode("idiv rbx");
			gen.appendCode("mov rax, rdx");
			break;
			
		default:
			break;
		}
	}
	
	
	
	

}
