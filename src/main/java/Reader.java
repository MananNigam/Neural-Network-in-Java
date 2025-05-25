import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    private final int rows = 28;
    private final int cols = 28;

    public List<Image> readData(String path){
        List<Image> images = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] lineItems = line.split(",");

                double[] data = new double[rows * cols];
                int label = Integer.parseInt(lineItems[0]);

                for(int i = 0; i < rows * cols; i++){
                    data[i] = (double) Integer.parseInt(lineItems[i+1]);
                }

                images.add(new Image(data, label));
            }
        } catch (Exception e){

        }

        return images;
    }
}
