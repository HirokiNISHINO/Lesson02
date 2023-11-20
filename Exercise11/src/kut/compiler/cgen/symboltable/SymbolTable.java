package kut.compiler.cgen.symboltable;

import java.util.HashMap;
import java.util.Vector;

public class SymbolTable {
	
	/**
	 * 
	 */
	public HashMap<String, String> 	mapGlobalVariableToMemoryAddressLabel;

	/**
	 * 
	 */
	public SymbolTable() {
		this.resetGlobalVariablesDeclaration();
	}

	/**
	 * 
	 */
	public void resetGlobalVariablesDeclaration() {
		mapGlobalVariableToMemoryAddressLabel = new HashMap<String, String>();
	}
	
	
	/**
	 * @param id
	 * @return
	 */
	public String getMemorryAddressLabelOfGlobalVariable(String id) {
		if (mapGlobalVariableToMemoryAddressLabel.containsKey(id) == false) {
			return null;
		}
		return mapGlobalVariableToMemoryAddressLabel.get(id);
	}
	
	/**
	 * @param id
	 */
	public void declareGlobalVariable(String id) {
		//既にグローバル変数として宣言されていたら何もしない。
		if (mapGlobalVariableToMemoryAddressLabel.containsKey(id)){
			return;
		}
		
		//初めての宣言であれば、ラベルを生成しmapに保存。
		String lbl = this.generateGlobalVariableMemoryAddressLabel(id);
		mapGlobalVariableToMemoryAddressLabel.put(id, lbl);
	}
	
	/**
	 * @param id
	 * @return
	 */
	private String generateGlobalVariableMemoryAddressLabel(String id) {
		return "global_variable#" + id;
	}
	
	
	/**
	 * @return
	 */
	public Vector<String> getAllMemoryAddressLabels(){
		Vector<String> labels = new Vector<String>();
		
		labels.addAll(mapGlobalVariableToMemoryAddressLabel.values());
		return labels;
	}
	
	/**
	 * 
	 */
	public TypeOfId getTypeOfId(String id) {
		if (mapGlobalVariableToMemoryAddressLabel.containsKey(id)) {
			return TypeOfId.GlobalVariable;
		}
		
		return TypeOfId.UNKNOWN;
	}
	
}
