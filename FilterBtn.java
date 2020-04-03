/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gursimar_hehar_project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author navde
 */
public class FilterBtn {

    Button btnSearch;
    TextField txtSearch;

    public Button getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(Button btnSearch) {
        this.btnSearch = btnSearch;
    }

    public TextField getTxtSearch() {
        return txtSearch;
    }

    public void setTxtSearch(TextField txtSearch) {
        this.txtSearch = txtSearch;
    }

    public void showResult() {

        Stage stage = new Stage();
        stage.setTitle("Corona Virus");
        stage.initModality(Modality.APPLICATION_MODAL);

        Label lblCountry = new Label("Country: ");

        txtSearch = new TextField();
        btnSearch = new Button("Search");

        FlowPane panSearch = new FlowPane(10, 10, lblCountry, txtSearch);

        VBox panRoot = new VBox(20);
        panRoot.setPadding(new Insets(10, 10, 10, 10));
        panRoot.setAlignment(Pos.CENTER);
        panRoot.getChildren().addAll(panSearch, btnSearch);

        Scene scene = new Scene(panRoot);
        scene.getStylesheets().add(FilterBtn.class.getResource("css.css").toExternalForm());
        Image imgLogo = new Image("gursimar_hehar_project/covid.png");
        stage.getIcons().add(imgLogo);
        stage.setScene(scene);
        stage.showAndWait();

    }

    public FilterBtn(Button btnSearch, TextField txtSearch) {
        this.btnSearch = btnSearch;
        this.txtSearch = txtSearch;
    }

}
