package kut.compiler.parser;

import kut.compiler.cgen.type.TypeChecker;
import kut.compiler.error.SyntaxErrorException;
import kut.compiler.parser.ast.AstBinOp;
import kut.compiler.parser.ast.AstGVarDec;
import kut.compiler.parser.ast.AstIntLiteral;
import kut.compiler.parser.ast.AstNode;
import kut.compiler.parser.ast.AstPrint;
import kut.compiler.parser.ast.AstPrintln;
import kut.compiler.parser.ast.AstProgram;
import kut.compiler.tokenizer.Token;
import kut.compiler.tokenizer.Tokenizer;

/**
 * @author hnishino
 *
 */
public class Parser {

	AstNode 	rootNode	;
	Tokenizer	tokenizer	;

	/**
	 * 
	 */
	public Parser(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
		this.rootNode = null;
	}

	/**
	 * @throws SyntaxErrorException
	 */
	public void parse() throws SyntaxErrorException
	{
		tokenizer.tokenize();
		this.rootNode = parseProgram();
	}
	
	/**
	 * @throws SyntaxErrorException
	 */
	public AstNode parseProgram() throws SyntaxErrorException
	{
		AstProgram program = new AstProgram();
		
		while(true) {
			Token token = this.tokenizer.peekToken();
			if (token.getKlazz() == Token.EOF) {
				break;
			}
			AstNode stmt = this.parseStmt();	
			program.addStatement(stmt);
		}
		
		Token token = this.tokenizer.peekToken();
		if (token.getKlazz() != Token.EOF) {
			throw new SyntaxErrorException("expecting an EOF token, but found :" + token);
		}
				
		return program;
	}
	
	
	/**
	 * 
	 */
	public AstNode parseStmt() throws SyntaxErrorException 
	{
		AstNode stmt = null;
		
		Token token = this.tokenizer.peekToken();
		
		if (token.getKlazz() == Token.PRINT) {
			stmt = this.parsePrintStmt();
		}
		else if (token.getKlazz() == Token.PRINTLN) {
			stmt = this.parsePrintlnStmt();
		}
		else if (token.getKlazz() == Token.GLOBAL) {
			stmt = this.parseGlobalVarDecStmt();
		}
		//上に該当しなかったら式文として扱う。
		else {
			stmt = this.parseExprStmt();
		}
		
		return stmt;
	}
	
	
	/**
	 * @return
	 * @throws SyntaxErrorException
	 */
	public AstNode parseExprStmt() throws SyntaxErrorException
	{
		AstNode expr = this.parseExpr();
		
		Token t = this.tokenizer.peekToken();
		if (t.getKlazz() != ';') {
			throw new SyntaxErrorException("expecting a ';' token, but found :" + t);
		}
		this.tokenizer.consumeToken();
		
		return expr;
	}
	
	/**
	 * @return
	 */
	public String getTreeString() {
		if (this.rootNode == null) {
			return "";
		}
		return this.rootNode.getTreeString(0);
	}
	
	
	/**
	 * @return
	 */
	public AstNode getRootNode() {
		return this.rootNode;
	}
	
	/**
	 * @return
	 * @throws SyntaxErrorException
	 */
	public AstNode parseGlobalVarDecStmt() throws SyntaxErrorException
	{
		//TODO: 下記に"global int 変数名;"の形で　変数宣言を行う構文解析を実装せよ。
		//TODO: 型名のトークンを得た際には、TypeChecker.isValidTypeメソッドに、そのトークンの語彙素の文字列を
		//TODO: 渡し、有効な型名(int)でなければ文法エラーを発生させよ。
		//TODO: 戻り値となるのはAstGVarDecのインスタンスである。
	}

	/**
	 * @return
	 * @throws SyntaxErrorException
	 */
	public AstNode parsePrintlnStmt() throws SyntaxErrorException
	{
		Token t = this.tokenizer.peekToken();
		if (t.getKlazz() != Token.PRINTLN) {
			throw new SyntaxErrorException("expecting a 'println' token, but found :" + t);
		}
		AstPrintln printlnNode = new AstPrintln(t);
		this.tokenizer.consumeToken();
		
		t = this.tokenizer.peekToken();
		if (t.getKlazz() != '(') {
			throw new SyntaxErrorException("expecting a '(' token, but found :" + t);
		}
		this.tokenizer.consumeToken();
		
		
		t = this.tokenizer.peekToken();
		if (t.getKlazz() != ')') {
			while(true) {
				t = this.tokenizer.peekToken();

				AstNode n = parseExpr();
				printlnNode.addParam(n);

				t = this.tokenizer.peekToken();
				if (t.getKlazz() == ')') {
					break;
				}
				if (t.getKlazz() != ',') {
					throw new SyntaxErrorException("expecting a ')' | ',' token, but found :" + t);
				}
				this.tokenizer.consumeToken();
			}
		}
		t = this.tokenizer.peekToken();
		if (t.getKlazz() != ')') {
			throw new SyntaxErrorException("expecting a ')' token, but found :" + t);
		}
		this.tokenizer.consumeToken();
		
		t = this.tokenizer.peekToken();
		if (t.getKlazz() != ';') {
			throw new SyntaxErrorException("expecting a ';' token, but found :" + t);
		}
		this.tokenizer.consumeToken();
		
		return printlnNode;
	}
	
	/**
	 * @return
	 * @throws SyntaxErrorException
	 */
	public AstNode parsePrintStmt() throws SyntaxErrorException
	{
		Token t = this.tokenizer.peekToken();
		if (t.getKlazz() != Token.PRINT) {
			throw new SyntaxErrorException("expecting a 'print' token, but found :" + t);
		}
		AstPrint printNode = new AstPrint(t);
		this.tokenizer.consumeToken();
		
		t = this.tokenizer.peekToken();
		if (t.getKlazz() != '(') {
			throw new SyntaxErrorException("expecting a '(' token, but found :" + t);
		}
		this.tokenizer.consumeToken();
		
		
		t = this.tokenizer.peekToken();
		if (t.getKlazz() != ')') {
			while(true) {
				t = this.tokenizer.peekToken();

				AstNode n = parseExpr();
				printNode.addParam(n);

				t = this.tokenizer.peekToken();
				if (t.getKlazz() == ')') {
					break;
				}
				if (t.getKlazz() != ',') {
					throw new SyntaxErrorException("expecting a ')' | ',' token, but found :" + t);
				}
				this.tokenizer.consumeToken();
			}
		}
		t = this.tokenizer.peekToken();
		if (t.getKlazz() != ')') {
			throw new SyntaxErrorException("expecting a ')' token, but found :" + t);
		}
		this.tokenizer.consumeToken();
		
		t = this.tokenizer.peekToken();
		if (t.getKlazz() != ';') {
			throw new SyntaxErrorException("expecting a ';' token, but found :" + t);
		}
		this.tokenizer.consumeToken();
		
		return printNode;
	}
	/**
	 * @return
	 * @throws SyntaxErrorException
	 */
	public AstNode parseExpr() throws SyntaxErrorException
	{
		return this.parseAdditiveExpr();
	}
	
	/**
	 * @return
	 * @throws SyntaxErrorException
	 */
	public AstNode parseAdditiveExpr() throws SyntaxErrorException
	{
		AstNode lhs = this.parseMultiplicativeExpr();
		
		while(true) {
			Token token = this.tokenizer.peekToken();

			if (token.getKlazz() != '+' && token.getKlazz() != '-') {
				break;		
			}
			this.tokenizer.consumeToken();
			
			AstNode rhs = this.parseMultiplicativeExpr();
			lhs = new AstBinOp(token, lhs, rhs);
		}
		
		return lhs;
	}
	
	/**
	 * @return
	 * @throws SyntaxErrorException
	 */
	public AstNode parseMultiplicativeExpr() throws SyntaxErrorException
	{
		AstNode lhs = this.parsePrimaryExpression();
		
		while(true) {
			Token token = this.tokenizer.peekToken();
			
			if (token.getKlazz() != '*' && token.getKlazz() != '/' && token.getKlazz() != '%') {
				break;		
			}
			this.tokenizer.consumeToken();
			
			AstNode rhs = this.parsePrimaryExpression();
			lhs = new AstBinOp(token, lhs, rhs);
		}
		
		return lhs;
	}
	
	/**
	 * @return
	 * @throws SyntaxErrorException
	 */
	public AstNode parsePrimaryExpression() throws SyntaxErrorException {
		Token token = this.tokenizer.peekToken();
		
		if (token.getKlazz() == '(') {
			return this.parseParenthesesExpr();
		}
		
		
		return this.parseIntLiteral();
	}
	
	/**
	 * @return
	 * @throws SyntaxErrorException
	 */
	public AstNode parseParenthesesExpr() throws SyntaxErrorException {
		Token token = this.tokenizer.peekToken();
		if (token.getKlazz() != '(') {
			throw new SyntaxErrorException("expecting a '(' token, but found :" + token);
		}
		this.tokenizer.consumeToken();
		
		AstNode expr = this.parseExpr();

		token = this.tokenizer.peekToken();
		if (token.getKlazz() != ')') {
			throw new SyntaxErrorException("expecting a ')' token, but found :" + token);
		}
		this.tokenizer.consumeToken();

		return expr;
	}
	/**
	 * @return
	 */
	public AstIntLiteral parseIntLiteral() throws SyntaxErrorException 
	{
		Token token = this.tokenizer.peekToken();
				
		token = this.tokenizer.peekToken();
		if (token.getKlazz() != Token.INT_LITERAL) {
			throw new SyntaxErrorException("expecting an INT_LITERAL token, but found :" + token);
		}
		this.tokenizer.consumeToken();
						
		return new AstIntLiteral(token);
	}
}
