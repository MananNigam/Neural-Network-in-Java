import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Image> trainImages = new Reader().readData("E:\\Documents\\Java Projects\\NumberThonk\\src\\main\\resources\\mnist_train.csv");
        int[] layerSizes = {784, 10, 10};
        Network network = new Network(layerSizes.length, layerSizes);

        int iterations = 100;
        for(int i = 0; i < iterations; i++){
            double cost = 0;
            for(Image image: trainImages){
                cost = cost(network, image);
                backPropagation(network, image, 0.1);
            }
            System.out.println("Iteration: " + (i+1));
            System.out.println("Cost: " + cost);
        }
    }

    public static double[] iterate(Network network, Image image) {
        network.layers[0] = network.inputLayer(image.getData());
        for(int j = 1; j < network.size; j++){
            Layer layer = network.layers[j];
            layer.setNeurons(add(dot(layer.getWeights(), network.layers[j-1].getNeurons(), layer.getSize()),
                    layer.getBiases()));
        }

        return network.layers[network.size-1].getNeurons();
    }

    public static double cost(Network network, Image image){
        double[] predictions = ReLU(iterate(network, image));
        double error = 0.0;

//        System.out.println(Arrays.toString(predictions));

        for(int i = 0; i < predictions.length; i++){
            if(i == image.getLabel()){
                error += (1 - predictions[i]) * (1 - predictions[i]);
            } else {
                error += predictions[i] * predictions[i];
            }
        }

        return error / predictions.length;
    }

    public static void backPropagation(Network network, Image image, double learningRate){
        List<Double>[] d_k = new List[network.size];
        for(int l = network.size - 1; l >= 0; l--){
            d_k[l] = new ArrayList<>();
            Layer layer = network.layers[l];

            for(int k = 0; k < layer.getSize(); k++){
                double[] layerNeurons = layer.getNeurons();

                if(l == network.size - 1){
                    double a_k = ReLU(layerNeurons)[k];
                    double y_k = image.getLabel() == k ? 1 : 0;
                    d_k[l].add(k, (a_k - y_k) * RelUDerivative(layerNeurons)[k]);
                } else {
                    double[] M = Tdot(network.layers[l+1].getWeights(), d_k[l+1]);
                    d_k[l].add(k, M[k] * RelUDerivative(layerNeurons)[k]);
                }
            }

            if(l != network.size - 1){
                double[][] weights = network.layers[l + 1].getWeights();
                for (int i = 0; i < weights.length; i++) {
                    for (int j = 0; j < weights[0].length; j++) {
                        weights[i][j] = weights[i][j] - learningRate * d_k[l + 1].get(j) * ReLU(layer.getNeurons())[i];
                    }
                }
                network.layers[l+1].setWeights(weights);

                double[] biases = network.layers[l + 1].getBiases();
                for (int i = 0; i < biases.length; i++){
                    biases[i] = biases[i] - learningRate * d_k[l+1].get(i);
                }
                network.layers[l+1].setBiases(biases);
            }
        }
    }

    public static double[] dot(double[][] weights, double[] prevNeurons, int size){
        double[] unbiasedNeuron = new double[size];
//        System.out.println(size);

        for(int i = 0; i < size; i++){
            double sum = 0.0;

            for(int j = 0; j < prevNeurons.length; j++){
                sum += (weights[j][i] * prevNeurons[j]);
            }
            unbiasedNeuron[i] = sum;
        }

        return unbiasedNeuron;
    }

    public static double[] Tdot(double[][] M1, List<Double> M2){
        double[] M = new double[M1.length];
        double[] M2_ = new double[M2.size()];

        for(int i = 0; i < M2_.length; i++){
            M2_[i] = M2.get(i);
        }

        for(int i = 0; i < M1.length; i++){
            double sum = 0;

            for(int j = 0; j < M1[0].length; j++){
                sum += M1[i][j] * M2_[j];
            }
            M[i] = sum;
        }

        return M;
    }

    public static double[] add(double[] M1, double[] M2){
        double[] M = new double[M1.length];

        for(int i = 0; i < M1.length; i++){
            M[i] = M1[i] + M2[i];
        }

        return M;
    }

    public static double[] ReLU(double[] neurons){
        double sum = 0;
        double[] activatedNeurons = new double[neurons.length];

        for(double val : neurons){
            sum += val;
        }

        for(int i = 0; i < neurons.length; i++){
            double normalizedNeuron = neurons[i] / sum;
            activatedNeurons[i] = normalizedNeuron > 0 ? normalizedNeuron : 0;
        }

        return activatedNeurons;
    }

    public static double[] RelUDerivative(double[] neurons){
        double[] derivative = new double[neurons.length];

        for(int i = 0; i < neurons.length; i++){
            derivative[i] = neurons[i] > 0 ? 1 : 0;
        }

        return derivative;
    }
}
