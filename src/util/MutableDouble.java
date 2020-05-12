package util;

public class MutableDouble {
	private double result;

	public MutableDouble() {
		result = 0;
	}

	public MutableDouble(double value) {
		result = value;
	}

	public double get() {
		return result;
	}

	public void set(double value) {
		this.result = value;
	}

	public void add(double x) {
		result += x;
	}

	public void subtract(double x) {
		result -= x;
	}

	public void multiply(double x) {
		result *= x;
	}

	public void divide(double x) {
		result /= x;
	}

	public MutableDouble add(MutableDouble x) {
		this.add(x.get());
		return this;
	}

	public MutableDouble multiply(MutableDouble x) {
		this.multiply(x.get());
		return this;
	}

}
