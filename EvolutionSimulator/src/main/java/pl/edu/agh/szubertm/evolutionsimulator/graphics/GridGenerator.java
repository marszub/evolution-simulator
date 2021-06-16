package pl.edu.agh.szubertm.evolutionsimulator.graphics;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

public class GridGenerator {
    public static ColumnConstraints column(double percent) {
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(percent);
        return column;
    }

    public static RowConstraints row(double percent) {
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(percent);
        return row;
    }
}
