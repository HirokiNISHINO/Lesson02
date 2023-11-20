package kut.compiler.parser.ast;

import kut.compiler.cgen.CodeGenerator;
import kut.compiler.error.SemanticErrorException;
import kut.compiler.tokenizer.Token;

/**
 * @author hnishino
 *
 */
public class AstAssignment extends AstNode
{
	private Token	t;
	private AstNode lhs;
	private AstNode rhs;
	
	/**
	 * 
	 */
	public AstAssignment(Token t, AstNode lhs, AstNode rhs) {
		this.t = t;
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	
	/**
	 *
	 */
	@Override
	public String getTreeString(int indent) {
		String node = this.getIndentedStringWithCR(indent, "Assignment:");
		String lhs  = this.lhs.getTreeString(indent + 1);
		String rhs  = this.rhs.getTreeString(indent + 1);
		return node + lhs + rhs;
	}

	/**
	 * @param gen
	 */
	public void beforeCGEN(CodeGenerator gen) throws SemanticErrorException {
		//TODO: 左辺がAstIdentifierのインスタンスでない場合、SemanticErrorException例外を発生させよ。(instanceof演算子を支えば良い）
		//TODO: 左辺がAstIdentifierのインスタンスであった場合は、問題がないので何もしないで良い。
		//TODO: さらに最後に、lhs/rhs両方に対しbeforeCGENメソッドを呼び出し、構文木をたどらせえて下の枝葉の意味解析を行え。
	}
	
	/**
	 *
	 */
	@Override
	public void cgen(CodeGenerator gen) {
		
		this.rhs.cgen(gen);
		AstIdentifier id = (AstIdentifier)lhs;
		
		//TODO: CodeGeneratorのgetGlobalVariableMemoryAddressLabelに識別子を渡し、グローバル変数のメモリアドレスを取得せよ。
		//TODO: その後、mov命令を使い、そのアドレスにraxの値を書き込め
	}
	
	

}
