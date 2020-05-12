package util;

public final class ScaleFunction {

	private double scale;

	public ScaleFunction() {
		scale = (1.0/8);
	}

	public ScaleFunction(double scale) {
		this.scale = scale;
	}

	public double get(double x) {
		return scale * x;
	}

	public double getDerivative() {
		return scale;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

}
