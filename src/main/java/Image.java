public class Image {

    private double[] data;
    private int label;

    public double[] getData() {
        return data;
    }

    public int getLabel() {
        return label;
    }

    public Image(double[] data, int label) {
        this.data = data;
        this.label = label;
    }

    @Override
    public String toString() {
        String s = label + "\n";
        for(double i : data){
            s += i + ",";
        }

        return s;
    }
}
