package neuralNetwork;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

import neurons.LinearNeuron;
import neurons.SigmoidNeuron;

public class Main {

	static String filePath = System.getProperty("user.dir") + "\\data\\network.txt";
	static String filePath2 = System.getProperty("user.dir") + "\\data\\network1.txt";
	public static void run() throws IOException {
		int inputSize = 28 * 28;
		int output = 10;
		int hidden = 49;
		double b = 0;
		NeuralNetwork nn = new NeuralNetwork(inputSize);

		var nl1 = new NeuralLayer<SigmoidNeuron>(hidden, inputSize);
		var nl2 = new NeuralLayer<SigmoidNeuron>(output, hidden);

		for (int i = 0; i < hidden; i++) {
			nl1.add(new SigmoidNeuron(inputSize, b, true));
		}	
		
		for (int i = 0; i < output; i++) {
			nl2.add(new SigmoidNeuron(hidden, b, true));
		}

		nn.addLayer(nl1).addLayer(nl2);
		MnistDataLoader ld = new MnistDataLoader();
			train(nn,ld,20);
		
	}

	public static void train(NeuralNetwork nn, MnistDataLoader dl, int epochs) {
		
		
		Trainer tr = new Trainer();
		dl.load(tr.imagePath, tr.labelPath);
		IntStream.range(0, epochs).forEach((i) -> {
			try {
				System.out.println("Epoch " + i);
				tr.backPropagationTrain(nn, dl, true, filePath2);
				System.out.println();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		});
	
	}
	
	public static void test(NeuralNetwork nn, MnistDataLoader dl) throws IOException {
				
		Tester ts = new Tester();
		dl.load(ts.getImagePath(), ts.getLabelPath());
		
		ts.test(nn, dl);
	}

	public static void no() throws IOException {
		int inputSize = 28 * 28;
		int output = 10;
		int hidden = 49;
		double b = -7;
		NeuralNetwork nn = new NeuralNetwork(inputSize);

		var nl1 = new NeuralLayer<SigmoidNeuron>(hidden, inputSize);
		var nl2 = new NeuralLayer<SigmoidNeuron>(output, hidden);

		for (int i = 0; i < hidden; i++) {
			var n = new SigmoidNeuron(inputSize, b, 0);
			nl1.add(n);
				Random random = new Random(System.currentTimeMillis());
				for(int k = 16*i; k < 16*(i+1); k++){
						n.setWeight(k,random.nextDouble());
				}
		}	
		
		for (int i = 0; i < output; i++) {
			nl2.add(new SigmoidNeuron(hidden, 0, true));
		}

		nn.addLayer(nl1).addLayer(nl2);
		MnistDataLoader ld = new MnistDataLoader();
			
		nn.writeToFile(new File(filePath2).toPath());
		train(nn,ld,5);
			test(nn, ld);
	}
	
	public static void main(String args[]) throws IOException {
		//run();
		no();
	}
}
