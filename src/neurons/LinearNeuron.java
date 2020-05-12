package neurons;

import util.ScaleFunction;

public class LinearNeuron extends AbstractNeuron {

	private ScaleFunction function;

	public LinearNeuron(int size, double bias, boolean randomWeights) {
		super(size, bias, randomWeights);
		function = new ScaleFunction();
	}

	public LinearNeuron(int size, double bias, double weight) {
		super(size, bias, weight);
		function = new ScaleFunction();
	}

	public LinearNeuron(int size, double bias) {
		super(size, bias);
		function = new ScaleFunction();
	}

	public double getScale() {
		return function.getScale();
	}

	public void setScale(double scale) {
		function.setScale(scale);
	}

	public double getDerivative() {
		return function.getDerivative();
	}

	@Override
	protected double activationFunction(double weightedSum) {
		return function.get(weightedSum);
	}

	@Override
	public double getDerivativeAtOutput(double output2) {
		return this.getDerivative();
	}

}
