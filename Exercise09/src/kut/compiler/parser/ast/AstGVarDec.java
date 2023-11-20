package kut.compiler.parser.ast;

import kut.compiler.cgen.CodeGenerator;
import kut.compiler.tokenizer.Token;

/**
 * @author hnishino
 *
 */
public class AstGVarDec extends AstNode
{
	@SuppressWarnings("unused")
	private Token t	;
	private Token type;
	private Token id;
	/**
	 * 
	 */
	public AstGVarDec(Token t, Token type, Token id) {
		this.t  	= t;
		this.type 	= type;
		this.id 	= id;
	}
	
	/**
	 * @return
	 */
	public String getId() {
		return this.id.getLexeme();
	}
	
	/**
	 * @return
	 */
	public String getType() {
		return this.type.getLexeme();
	}
	
	/**
	 *
	 */
	@Override
	public String getTreeString(int indent) {
		return this.getIndentedStringWithCR(indent, "GVarDec:" + this.getId() + "(" + this.getType() +")");
	}
	
	/**
	 * @param gen
	 */
	public void beforeCGEN(CodeGenerator gen) {
		//TODO:CodeGeneratorのdeclareGlobalVariableメソッドに変数名を渡して、変数宣言を行え。
	}
	
	/**
	 *
	 */
	@Override
	public void cgen(CodeGenerator gen) {
		//TODO:変数宣言なので、コードの生成は不要。
	}
		
}
