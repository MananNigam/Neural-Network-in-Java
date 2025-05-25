public class Layer {

    private int size;
    private double[] neurons;
    private double[][] weights;
    private double[] biases;

    public double[] getNeurons() {
        return neurons;
    }

    public void setNeurons(double[] neurons) {
        this.neurons = neurons;
    }

    public double[][] getWeights() {
        return weights;
    }

    public void setWeights(double[][] weights) {
        this.weights = weights;
    }

    public double[] getBiases() {
        return biases;
    }

    public void setBiases(double[] biases) {
        this.biases = biases;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }



    public void initializeLayer(int prevLayerSize){
        this.weights = new double[prevLayerSize][size];
        this.biases = new double[size];

        for(int i = 0; i < prevLayerSize; i++){
            for(int j = 0; j < size; j++){
                weights[i][j] = 1;
                biases[j] = 0;
            }
        }
    }
}
