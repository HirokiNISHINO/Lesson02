package kut.compiler.parser.ast;

import kut.compiler.cgen.CodeGenerator;
import kut.compiler.cgen.symboltable.TypeOfId;
import kut.compiler.error.SemanticErrorException;
import kut.compiler.tokenizer.Token;

/**
 * @author hnishino
 *
 */
public class AstIdentifier extends AstNode
{
	private Token t;
	
	/**
	 * 
	 */
	public AstIdentifier(Token t) {
		this.t = t;
	}
	
	/**
	 * @return
	 */
	public String getIdentifier() {
		return t.getLexeme();
	}
	
	
	/**
	 *
	 */
	@Override
	public String getTreeString(int indent) {
		return this.getIndentedStringWithCR(indent, "Identifier:" + this.getIdentifier());
	}

	/**
	 * @param gen
	 */
	public void beforeCGEN(CodeGenerator gen) throws SemanticErrorException {
		
		//TODO: CodeGeneratorのgetTypeOfIdメソッド識別子を渡し呼び出す。TypeOfId.GlobalVariable
		//TODO: （記号表にグローバル変数として登録されていることを示す）が返り値でない場合は、
		//TODO: SemanticErrorExceptionを発生させよ。そうでなければ何もしないでよい（意味解析上問題がなかったため）。
	}
	
	/**
	 *
	 */
	@Override
	public void cgen(CodeGenerator gen) {
		//TODO: グローバル変数に割り当てられたメモリ領域から値を読みだしraxに書きコードを生成せよ。
		//TODO: CodeGeneratorのgetGlobalVariableMemoryAddressLabelメソッドに、識別子の文字列を渡し、
		//TODO: グローバル変数のメモリ・アドレスへのラベル名を得て使用すれば良い。　q
		//TODO: アドレスから値を取り出す際、x86_64のコードでは [rel ラベル名] の形でアクセスしなければいけないことに注意せよ。
	}
	
	

}
