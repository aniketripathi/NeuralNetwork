package neuralNetwork;

import java.util.List;

import util.MnistReader;

public class MnistDataLoader {

	/*
	 * Each image is 28 * 28 pixel box Pixels are from 0 to 255. 0 means white and
	 * 255 means black
	 */

	public static final int PIXELS = 28;

	private int[] labels;
	private List<int[][]> imageList;
	private double[][] images;

	public MnistDataLoader() {
		labels = null;
		imageList = null;
	}

	public void load(String imagePath, String labelPath) {
		labels = MnistReader.getLabels(labelPath);
		imageList = MnistReader.getImages(imagePath);
		// Convert all the images into double format
		images = new double[imageList.size()][PIXELS * PIXELS];
		for (int i = 0; i < images.length; i++) {
			for (int j = 0; j < PIXELS; j++) {
				for (int k = 0; k < PIXELS; k++) {
					images[i][j * PIXELS + k] = getPixelInDouble(imageList.get(i)[j][k]);
				}

			}
		}

	}

	public int[][] getImage(int i) {
		return imageList.get(i);
	}

	public double[] getDoubleImage(int i) {
		return images[i];
	}

	public int getLabel(int i) {
		return labels[i];
	}

	public int getNumberOfImages() {
		return imageList.size();
	}

	public int getNumberOfLabels() {
		return labels.length;
	}

	public double getPixelInDouble(int pixelValue) {
		return ((double) pixelValue / 255);
	}
}
