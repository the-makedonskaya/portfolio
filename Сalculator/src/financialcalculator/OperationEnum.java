package financialcalculator;

import java.util.HashMap;
import java.util.Map;

public enum OperationEnum {

	PLUS("+") {
		@Override
		public Double apply(Double left, Double right) {
			return Double.sum(left, right);
		}
	}, 
	MINUS("-") {
		@Override
		public Double apply(Double left, Double right) {
			return left - right;
		}
	}, 
	MULTIPLY("*") {
		@Override
		public Double apply(Double left, Double right) {
			return left * right;
		}
	},
	DIVIDE("/") {
		@Override
		public Double apply(Double left, Double right) {
			return left / right;
		}
	},	
	COSINUS("cos") {
		@Override
		public Double apply(Double left, Double right) {
			return Math.cos(right);
		}	
	},
	TANGENT("tan") {
		@Override
		public Double apply(Double left, Double right) {
			return Math.tan(right);
		}	
	},
	COTANGENT("sin") {
		@Override
		public Double apply(Double left, Double right) {
			return Math.sin(right) ;
		}	
	};
	
	static Map<String, OperationEnum> helper = new HashMap<String, OperationEnum>();
	
	private String operationSign;

	private OperationEnum(final String sign) {
        this.operationSign = sign;
    }	
	
	static {
		for (OperationEnum op : values()) {
			helper.put(op.operationSign, op);
		}
	}

	public static OperationEnum getBySign(String sign) {
		return helper.get(sign);
	}

	@Override
	public String toString() {
		return operationSign;
	}
	
	abstract public Double apply(Double left, Double right);
}
