package neuralNetwork;

import java.io.IOException;
import java.util.Arrays;

import util.DoubleComparison;

public class Tester {
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

	public void test(NeuralNetwork network, MnistDataLoader dl) throws IOException {

		double error = 0;
		int correct = 0, incorrect = 0;

		int totalSample = dl.getNumberOfLabels();
		for (int i = 0; i < totalSample; i++) {
			int label = dl.getLabel(i);
			double image[] = dl.getDoubleImage(i);

			double output[] = network.fire(image);
			if (DoubleComparison.isLarger(output[label], 0.9)) {
				correct++;
			} else
				incorrect++;
			error += (1 - output[label]) * (1 - output[label]);
			 //Arrays.stream(output).forEach(x->System.out.print(x+", "));System.out.println();
			// StringBuilder sr = new StringBuilder();
			// sr.append(" correct= ").append(correct).append(" incorrect=").append(incorrect).append(" Answer= ").append(label).append(" Error= ").append(error);
			// System.out.println(sr.toString());

		}
		System.out.println(" Correct = " + correct + "/" + (totalSample));
	}

}
