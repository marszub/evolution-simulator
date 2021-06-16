package pl.edu.agh.szubertm.evolutionsimulator.graphics;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.io.FileReader;

public class JsonReader{
    public final int parallelSimulations;
    public final int width;
    public final int height;
    public final double jungleRatio;
    public final int numberOfAnimals;
    public final int startEnergy;
    public final int moveEnergy;
    public final int plantEnergy;

    public JsonReader(String fileName){
        String filePath = System.getProperty("user.dir") + File.separator + fileName;
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = (JSONObject) jsonParser.parse(new FileReader(filePath));
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Exception while reading json.");
            alert.setContentText(e.getMessage());
            alert.setOnHidden(a -> Platform.exit());
            alert.showAndWait();
        }
        parallelSimulations = ((Long) jsonObject.get("parallelSimulations")).intValue();
        width = ((Long) jsonObject.get("width")).intValue();
        height = ((Long) jsonObject.get("height")).intValue();
        jungleRatio = (Double) jsonObject.get("jungleRatio");
        numberOfAnimals = ((Long) jsonObject.get("numberOfAnimals")).intValue();
        startEnergy = ((Long) jsonObject.get("startEnergy")).intValue();
        moveEnergy = ((Long) jsonObject.get("moveEnergy")).intValue();
        plantEnergy = ((Long) jsonObject.get("plantEnergy")).intValue();
    }
}
