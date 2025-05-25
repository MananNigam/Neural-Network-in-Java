public class Network {

    int size;

    Layer[] layers;

    public Network(int size, int[] layerSizes) {
        this.size = size;
        this.layers = new Layer[size];

//        System.out.println(size);

        for(int i = 0; i < size; i++){
            layers[i] = new Layer();

            layers[i].setSize(layerSizes[i]);
            if(i >= 1) {
                layers[i].initializeLayer(layerSizes[i - 1]);
            }
        }
    }

    public Layer inputLayer(double[] data){
        Layer inputLayer = new Layer();
        inputLayer.setSize(784);
        inputLayer.setNeurons(data);

        return inputLayer;
    }

}
