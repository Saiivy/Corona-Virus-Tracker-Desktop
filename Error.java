package gursimar_hehar_project;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Gursimar Singh Hehar This class represents an error for incorrect
 * value
 */
public class Error {

    public static void display(String error) {

        //ALert if user enters incorret value
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();

    }
}
