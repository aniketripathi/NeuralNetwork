package neuralNetwork;

import util.DoubleComparison;
import util.MnistDataLoader;

public class Tester {
	
	
	 static class Checker {
		private double error;
		private double actualOutput;
		private double expectedOutput;
		
		public Checker(double expectedOutput, double actualOutput, double error){
			this.expectedOutput = expectedOutput;
			this.actualOutput = actualOutput;
			this.error = error;
		}
		
		public boolean isCorrect() {
			return DoubleComparison.isEqual(this.actualOutput, this.expectedOutput);
		}
		
		public double getExpectedOutput() {
			return this.expectedOutput;
		}
		
		public double getActualOutput() {
			return this.actualOutput;
		}
		
		public double getError() {
			return this.error;
		}
		
		// (Expected output, actual output, error)
		public String toString() {
			return ("("+this.expectedOutput+","+this.actualOutput+","+this.error+")");
					
		}
		
	}
	
	
	
	private String imagePath;
	private String labelPath;

	public Tester() {
		imagePath = System.getProperty("user.dir") + "\\data\\t10k-images-idx3-ubyte\\t10k-images.idx3-ubyte";
		labelPath = System.getProperty("user.dir") + "\\data\\t10k-labels-idx1-ubyte\\t10k-labels.idx1-ubyte";
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getLabelPath() {
		return labelPath;
	}
	
	
	
	
	public Checker[] test(NeuralNetwork network, MnistDataLoader dl) {

		int totalSample = dl.getSize();
		Checker[] result = new Checker[totalSample];
		for (int i = 0; i < totalSample; i++) {
			int label = dl.getLabel(i);
			double image[] = dl.getDoubleImage(i);
			double output[] = network.fire(image);
			
			int max = 0;
			double error = 0;
			for(int j = 0; j < 10; j++) {
				if(DoubleComparison.isLarger(output[j], output[max]))
					max = j;
				if(j == label) 
					error += Math.abs(1 - output[j]);
				
				else
					error += Math.abs(output[j]);
			}
			error /= 10;
			result[i] = new Checker(label, max, error);
				}
		return result;
	}
	
	public static void printTestResult(boolean individualResult, Checker[] results) {
		
		double error = 0;
		int correct = 0;
			for(Checker i: results) {
				if(individualResult) {		
					System.out.println(results);
				}
				error += i.getError();
				correct += (i.isCorrect())?1:0;
			}
			error /= results.length;
			System.out.println("Correct = "+ correct + "/" + results.length + ". Average error = "+error);
		
	}
}
