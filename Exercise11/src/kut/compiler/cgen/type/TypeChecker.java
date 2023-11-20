package kut.compiler.cgen.type;

public class TypeChecker {

	/**
	 * @param typename
	 * @return
	 */
	public static boolean isValidType(String typename) {
		if ("int".equals(typename)) {
			return true;
		}
		return false;
	}

}
