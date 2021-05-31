package financialcalculator;

public class Ñurrency {
	
	private static final double DOLLAR = 0.8;
	
	private static final double EURO = 1.5;

	public double getDOLLAR() {
		return DOLLAR;
	}

	public double getEURO() {
		return EURO;
	}

	public static double toDollar(double eur) {
		 return eur * EURO;
	}
	
	public static double toEuro(double dollar) {
		return dollar * DOLLAR;
	}
	public static void main(String[] args) {
	
		System.out.println(toDollar(toEuro(10.00) + 5.00));
		
	}
}
