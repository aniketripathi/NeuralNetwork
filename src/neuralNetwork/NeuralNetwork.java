package neuralNetwork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import neurons.AbstractNeuron;

public class NeuralNetwork {

	private List<NeuralLayer<? extends AbstractNeuron>> layers;
	private int inputSize;

	public NeuralNetwork(int inputSize) {
		this.layers = new ArrayList<NeuralLayer<?>>();
		this.inputSize = inputSize;
	}

	// Number of layers
	public int getSize() {
		return layers.size();
	}

	public int getInputSize() {
		return inputSize;
	}

	public int getOutputSize() {
		int outSize = 0;
		if (layers.size() > 0) {
			outSize = layers.get(layers.size() - 1).getSize();
		}
		return outSize;
	}

	public NeuralNetwork addLayer(NeuralLayer<?> layer) {
		layers.add(layer);
		return this;
	}

	public NeuralLayer<?> getLayer(int i) {
		return layers.get(i);
	}

	public double[] fire(double inputs[]) {

		double output[] = inputs;
		if (inputs.length < inputSize) {
			throw new IllegalArgumentException("Less number of inputs.");
		} else {
			ListIterator<NeuralLayer<?>> iterator = layers.listIterator();
			while (iterator.hasNext()) {
				output = iterator.next().fire(output);
			}
		}
		return output;
	}

	public void writeToFile(Path path) throws IOException {

		if (!Files.exists(path)) {
			Files.createFile(path);
		}
		if (Files.isWritable(path)) {
			Files.write(path, writeToList(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		} else
			throw new IOException("File is not writable.");

	}

	private int getTotalLines() {
		int totalLines = 1; // first line to write network layer parameters
		totalLines += layers.size(); // lines to write parameters for each layer

		for (int i = 0; i < layers.size(); i++) { // to store weights
			totalLines += layers.get(i).getSize();
		}
		return totalLines;

	}

	private ArrayList<String> writeToList() {

		ArrayList<String> data = new ArrayList<String>(getTotalLines());

		StringBuilder sb = new StringBuilder();

		// Line 1 network layer parameters - input_size;total_layers;output_size
		sb.append(this.getInputSize()).append(",").append(layers.size()).append(",").append(this.getOutputSize());
		data.add(sb.toString());

		// Layer i parameters followed by its weight for each neuron
		// Layer i parameter - input_size;total_neurons
		for (int i = 0; i < layers.size(); i++) {
			NeuralLayer<?> layer = layers.get(i);
			data.add(layer.getInputSize() + "," + layer.getSize());

			for (int j = 0; j < layer.getSize(); j++) {
				AbstractNeuron n = layer.getNeuron(j); // [bias,w2,w3,...]
				sb.setLength(0);
				sb.append(n.getBias());
				for (int k = 0; k < n.getInputSize(); k++) {
					sb.append(",").append(n.getWeight(k));
				}
				data.add(sb.toString());
			}

		}

		return data;
	}

	public void loadFromFile(Path path) throws IOException {

		try {
			List<String> data = readFromFile(path);
			if (data != null) {

				cleanList(data);

				ListIterator<String> iterator = data.listIterator();
				// get neural network parameters
				String networkParam[] = iterator.next().split(",");
				int totalLayers = Integer.parseInt(networkParam[1]);
				// nn = new NeuralNetwork(Integer.parseInt(networkParam[0]));

				// for each layer
				for (int i = 0; i < totalLayers; i++) {
					String layerParam[] = iterator.next().split(",");
					NeuralLayer<?> nl = this.getLayer(i);
					for (int j = 0; j < nl.getSize(); j++) {
						try (Scanner sc = new Scanner(iterator.next()).useDelimiter(",")) {
							AbstractNeuron neuron = nl.getNeuron(j);
							neuron.setBias(sc.nextDouble()); // set size and bias

							// Only the first k weights will saved, rest will take default values
							int k = 0;
							while (sc.hasNextDouble()) {
								assert (k < neuron.getInputSize());
								neuron.setWeight(k, sc.nextDouble());
								k++;
							}
						}
					}

				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			System.err.println("Cannot load file. File is corrupt.");
		}
	}

	/*
	 * Removes all whitespaces and non visible characters
	 */
	private static void cleanList(List<String> data) {

		for (int i = 0; i < data.size(); i++) {
			data.set(i, data.get(i).replaceAll("\\s+", ""));
		}
	}

	private static List<String> readFromFile(Path path) throws IOException {

		List<String> data = null;
		if (Files.exists(path) && Files.isReadable(path)) {
			data = Files.readAllLines(path);
		} else
			throw new IOException("File does not exist or is not readable.");

		return data;
	}

}
