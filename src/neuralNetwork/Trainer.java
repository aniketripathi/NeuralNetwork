package neuralNetwork;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import neurons.AbstractNeuron;
import util.MnistDataLoader;
import util.MutableDouble;

public class Trainer {

	String imagePath;
	String labelPath;
	
	public Trainer() {
		imagePath = System.getProperty("user.dir") + "\\data\\train-images-idx3-ubyte\\train-images.idx3-ubyte";
		labelPath = System.getProperty("user.dir") + "\\data\\train-labels-idx1-ubyte\\train-labels.idx1-ubyte";		
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getLabelPath() {
		return labelPath;
	}

	
	public double getLearningRate() {
		return 0.03;
	}
	
	/*
	 * MnistDataLoader should have already loaded the data using load() save the
	 * network to file after training
	 * 
	 */
	public void backPropagationTrain1(NeuralNetwork network, MnistDataLoader dl, boolean saveToFile, String filePath)
			throws IOException {
		double startTime = System.currentTimeMillis();

		double learningRate = getLearningRate();
		Random random = new Random(System.currentTimeMillis());
		int sampleSize = 10000;
		for (int i = 0; i < sampleSize; i++) {

			// Get the example randomly
			int randomEx = random.nextInt(dl.getSize());
			int xLabel = dl.getLabel(randomEx);
			double xImage[] = dl.getDoubleImage(randomEx);

			// Get the output i.e fire the network for the label
			double[] output = network.fire(xImage);

			var error = new Object() {
				double error_ol[];
				double error_il[];
				NeuralLayer<?> il, ol;
			};
			error.ol = network.getLayer(network.getSize()-1);
			error.error_ol = new double[network.getOutputSize()];
			error.error_il = new double[network.getLayer(network.getSize() - 1).getInputSize()];
			// get the error w.r.t to output
			IntStream.range(0, error.error_ol.length).forEach((k) -> {
				AbstractNeuron n = error.ol.getNeuron(k);
				error.error_ol[k] = (output[k] - ((xLabel == k) ? 1 : 0)) * n.getDerivativeAtOutput(n.getOutput());
			});
			
			for (int l = network.getSize() - 1; l >= 1; l--) {

				error.ol = network.getLayer(l);
				error.il = network.getLayer(l - 1);
				
			//	Arrays.stream(error.error_ol).forEach((x)->System.out.print(x+" , "));System.out.println();
				
				IntStream.range(0, error.il.getSize()).forEach((j) -> {
					AbstractNeuron n = error.il.getNeuron(j);
					error.error_il[j] = IntStream.range(0, error.ol.getSize()).collect(MutableDouble::new,
							(result, k) -> result
									.add(error.ol.getNeuron(k).getWeight(j) * n.getDerivativeAtOutput(n.getOutput()) * error.error_ol[k]),
							(r1, r2) -> r1.add(r2)).get();
				});

				// Update weights and biases
				IntStream.range(0, error.ol.getSize()).forEach((j) -> {
					AbstractNeuron n = error.ol.getNeuron(j);
					IntStream.range(0, error.il.getSize()).forEach((k) -> {
						n.setWeight(k,
								n.getWeight(k) - learningRate * error.il.getNeuron(k).getOutput() * error.error_ol[j]);
					});
					n.setBias(n.getBias() - learningRate * error.error_ol[j]);
				});
				
				error.error_ol = error.error_il;
				error.error_il = new double[error.il.getInputSize()];
			}
			
			
			// Update For the first layer where inputs are actual inputs and not neurons
			error.ol = network.getLayer(0);

			IntStream.range(0, error.ol.getSize()).forEach((j) -> {
				AbstractNeuron n = error.ol.getNeuron(j);
				IntStream.range(0, network.getInputSize()).forEach((k) -> {
					n.setWeight(k, n.getWeight(k) - learningRate * xImage[k] * error.error_ol[j]);
				//	System.out.println(n.getWeight(k)+" , "+xImage[k]+" , " + error.error_ol[j]);
				});
				n.setBias(n.getBias() - learningRate * error.error_ol[j]);
			});
			// System.out.println();

			// System.out.println("Trained for sample "+ i);

		} // Sample size loop ends

		double endTime = System.currentTimeMillis();
		System.out.println("Training time in seconds= " + (endTime - startTime) / 1000);

		if (saveToFile && filePath != null) {
			network.writeToFile(new File(filePath).toPath());
		}
	}


}
