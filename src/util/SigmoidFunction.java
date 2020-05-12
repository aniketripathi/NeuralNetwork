package util;

public final class SigmoidFunction {

	/*
	 * The formula for sigmoid function is f(x) = 1 /( 1 + exp(-x) )
	 * 
	 * This class calculates the value of function based on approximation methods
	 * Index at 0 corresponds to x = 0, i = 1 corresponds to x = 0 + STEP_VALUE and
	 * so on The function follows the property f(-x) = 1 - f(x)
	 */

	private static final int SIZE = 6001;
	private static double mapping[] = setMapping(SIZE);
	private static final double STEP_VALUE = 0.001;
	private static final double X_MAX = (SIZE - 1) * STEP_VALUE;
	private static final double X_MIN = -(SIZE - 1) * STEP_VALUE;
	private static final double VALUE_AT_ZERO = 0.5;
	private static final double Y_MAX = 1.0;
	private static final double Y_MIN = 0.0;

	/*
	 * To initialize the mapping of sigmoid function
	 * 
	 * 
	 */
	private static double[] setMapping(int size) {
		double[] mapping = new double[size];
		double x = 0;
		for (int i = 0; i < size; i++) {
			mapping[i] = 1 / (1 + Math.exp(-x));
			x = x + STEP_VALUE;
		}
		return mapping;
	}

	public static double get(double x) {
		double y = VALUE_AT_ZERO;
		if (DoubleComparison.isSmaller(x, X_MIN)) {
			y = Y_MIN;
		} else if (DoubleComparison.isLarger(x, X_MAX)) {
			y = Y_MAX;
		}

		else if (DoubleComparison.isLarger(x, 0)) {
			y = mapping[(int) Math.round(x / STEP_VALUE)];
		}

		else if (DoubleComparison.isSmaller(x, 0)) {
			y = 1 - mapping[(int) Math.round(-x / STEP_VALUE)];
		} else {
			y = VALUE_AT_ZERO;
		}

		return y;
	}

	public static double getDerivative(double x) {
		return (get(x) * (1 - get(x)));
	}

	public static double getDerivativeAtOutput(double x) {
		return x * (1 - x);
	}

}
