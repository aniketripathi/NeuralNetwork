package neuralNetwork;

import java.util.ArrayList;
import java.util.stream.IntStream;

import neurons.AbstractNeuron;

public class GroupedNeuralLayer<T extends AbstractNeuron> {
	
	static class Group<T> {
		private int startIndex;		//inclusive
		private int endIndex;		//exclusive
		private ArrayList<T> neurons;
		
		public Group(int start, int end, int groupSize) {
			this.startIndex = start;
			this.endIndex = end;
			this.neurons = new ArrayList<T>(groupSize);
			
		}
		
		public Group(int start, int end) {
			this.startIndex = start;
			this.endIndex = end;
			this.neurons = new ArrayList<T>(20);
			
		}
		
		
		public T getNeuron(int index){
			return neurons.get(index);
		}
		
		public Group<T> addNeuron(T neuron) {
			neurons.add(neuron);
			return this;
		}
		
		public Group<T> addNeuron(T neuron, int index) {
			neurons.add(index,neuron);
			return this;
		}
		
		public int getSize() {
			return neurons.size();
		}
		
		public int getStart() {
			return this.startIndex;
		}
		
		public int getEnd() {
			return this.endIndex;
		}
		
		public int getInputSize() {
			return (getEnd() - getStart());
		}
		
		@Override
		public String toString() {
			return ("("+startIndex+","+endIndex+")");
		}
		
	}
	
	
	
	
	private ArrayList<Group<T>> groups;
	
	/*
	 * totalGroups is not fixed. You can add more groups as it is stored in ArrayList.
	 * Be very careful when working with this type of layer
	 * Ensure that all the input weights of neurons are in order w.r.t to previous layer
	 */
	public GroupedNeuralLayer(int totalGroups) {
		groups = new ArrayList<Group<T>>(totalGroups);
	}
	
	/*
	 * First add group(s) to the layer and ensure that 
	 * start of group 0 is the 1st neuron of previous layer
	 * end - 1 of last group is the last neuron of previous layer
	 * 
	 * Groups can contain neurons or neurons can be added to groups later
	 */
	public GroupedNeuralLayer<T> addGroup(int start, int end, int groupSize){
		groups.add(new Group<T>(start, end, groupSize));
		return this;
	}
	
	public GroupedNeuralLayer<T> addGroup(Group<T> group){
		groups.add(group);
		return this;
	}
	
	public GroupedNeuralLayer<T> add(T neuron, int groupIndex) {
		assert(neuron.getInputSize() == groups.get(groupIndex).getInputSize());
		groups.get(groupIndex).addNeuron(neuron);
		return this;
	}
	
	public Group<T> getGroup(int index) {
		return groups.get(index);
	}
	
	public T getNeuron(int group, int i) {
		return getGroup(group).getNeuron(i);
	}

	public int getTotalGroups() {
		return groups.size();
	}
	
	/*
	 * Avoid using it within loop
	 * Use total groups instead
	 */
	public int getTotalNeurons() {
		int sum = 0;
		for(Group<T> g: groups) {
			sum += g.getSize();
		}
		return sum;
	}
	
	
	/*
	 * The combined output is created from all groups
	 */
	public double[] fire(double inputs[]) {
		double output[] = new double[this.getTotalNeurons()];
		var obj = new Object(){ int outputIndex = 0; };
		for(int i = 0; i < this.getTotalGroups(); i++) {
			Group<T> g = groups.get(i);
			IntStream.range(0, g.getSize()).parallel().forEach(
				(j) -> {
					output[obj.outputIndex + j] = g.getNeuron(j).fire(inputs, g.startIndex, g.endIndex);
				});
			obj.outputIndex += g.getSize();
			
			}

		return output;
		}

	/*
	 * Fires the layer sequentially.
	 * 
	 */
	public double[] serialFire(double inputs[]) {
		double output[] = new double[this.getTotalNeurons()];
		var obj = new Object(){ int outputIndex = 0; };
		for(int i = 0; i < this.getTotalGroups(); i++) {
			Group<T> g = groups.get(i);
			IntStream.range(0, g.getSize()).sequential().forEach(
				(j) -> {
					output[obj.outputIndex + j] = g.getNeuron(j).fire(inputs, g.startIndex, g.endIndex);
				});
			obj.outputIndex += g.getSize();
			
			}

		return output;
	}
}
