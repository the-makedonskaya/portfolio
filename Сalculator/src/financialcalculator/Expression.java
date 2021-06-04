package financialcalculator;

import java.util.ArrayList;
import java.util.Collections;

public class Expression {
	
	private Expression expLeft;
	
	private Expression expRight;
	
	private OperationEnum operation;
	
	private Double result;
	
	private String message;
		
	public Expression() {
	}
	
	
	public Expression(String str) {
		str = str.replace(',', '.');
		if (isCorrectExp(str)) {
			str = getExpWithoutBrackets(str);
			int operatorIndex = getCurrentOperatorIndex(str);
			if (operatorIndex > 0 && operatorIndex < (str.length() - 1)) {
				this.operation = OperationEnum.getBySign(Character.toString(str.charAt(operatorIndex)));
				this.expLeft = new Expression(str.substring(0, operatorIndex));
				this.expRight = new Expression(str.substring(operatorIndex + 1));
			} else {
				if (operatorIndex > 0) {
					str = str.substring(0, operatorIndex);
				}
				this.result = Double.valueOf(str);
			}
			if (this.getResult() == null) {
				this.result = this.operation.apply(this.expLeft.getResult(), this.expRight.getResult());
			}
		} else {
			this.message = "syntax error!";
		}
	}

	private int getCurrentOperatorIndex(String str) {
		String signs;
		if (str.contains("+") || str.contains("-")) {
			signs = "+-";
		} else {
			signs = "*/";
		}
		
		ArrayList<Integer> list = new ArrayList<>();
		
		for (char sign : signs.toCharArray()) {
			list.add(str.lastIndexOf(sign));
		}
		
		return Collections.max(list);		
	}

	public Double getResult() {
		return result;
	}
	
	public String getMessage() {
		return message;
	}
	
	private boolean isCorrectExp(String str) {
		if (str.isEmpty()) {
			return false;
		}
		int leftBrackets = 0;
		int rightBrackets = 0;
		for (int i = 0; i < str.length(); i++) {
			switch (str.charAt(i)) {
			case '(':
				leftBrackets++;
				break;
			case ')':
				rightBrackets++;
				break;
			case '.':
				if (i < str.length() - 1) {
					if (str.charAt(i) == str.charAt(i + 1))
						return false;
				}
				break;
			}
		}

		if (leftBrackets == rightBrackets) {
			return true;
		}

		return false;
	}
	

	
	
	public String getExpWithoutBrackets(String str) {		
		if (!str.contains("(")) {
			return str;
		}
		
		int index1 = str.indexOf("(");
		int index2 = str.indexOf(")");
		boolean bool = true;

		while (bool) {
			index2--;
			char c = str.charAt(index2);
			if (c == str.charAt(index1)) {
				bool = false;
				index1 = index2;
			}		
		}
		index2 = str.indexOf(")");
		String result = Double.toString(new Expression(str.substring(index1 + 1, index2)).getResult());

		if (index1 >= 3 && str.contains("cos") || str.contains("can") || str.contains("sin")) {
			int start = index1 - 3;
			int end = index1 ;
			char[] dst = new char[end - start];
			str.getChars(start, end, dst, 0);
			String operator = new String(dst);
			OperationEnum operation = OperationEnum.getBySign(operator);
			Double newResult = operation.apply(1.1, Double.valueOf(result));
			result = Double.toString(newResult);
			str = str.replace(str.substring(start, end), "");
			index1 = index1 - 3;
			index2 = index2 - 3;
		}
		str = str.replace(str.substring(index1, index2 + 1), result);
		return getExpWithoutBrackets(str);
	}

}
