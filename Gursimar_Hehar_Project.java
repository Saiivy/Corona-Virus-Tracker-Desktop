package gursimar_hehar_project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.net.ssl.HttpsURLConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Gursimar Singh Hehar This class fetches data from corona api and
 * displays the stats.
 */
public class Gursimar_Hehar_Project extends Application {

    //Nodes corresponding to the stats..
    private Label lblGlobalStats;
    private Label lblCountriesAtDanger;
    private Label lblDailyStats;
    private ListView lstCountriesAtDanger;
    private Button btnSearchCountry;
    private Button btnCasesByCountry;
    private Button btnAffectedCountries;
    private Button btnCompareData;
    private TextField txtArea;
    private String userInput;
    ArrayList<Data> dataList;
    Data data;
    BarChart barChart;
    XYChart.Series compareChart;
    CategoryAxis xAxis;
    NumberAxis yAxis;
    PieChart pieChart;

    @Override
    public void start(Stage stage) throws Exception {
        StringBuilder stringBuilder = null;

        //Api Url
        String apiLink = "https://api.covid19api.com/summary";
        try {
            //creating connection with api now
            URL url = new URL(apiLink);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            //Reading data from api
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            Error.display("Error while reading data from Internet");
        }

        //parsing the api data
        JSONParser parser = new JSONParser();
        JSONObject objRoot = (JSONObject) parser.parse(stringBuilder.toString());
        JSONArray arrayRoot = (JSONArray) objRoot.get("Countries");

        //data that we want from api
        String country = "";
        long totalDeaths = 0;
        long newRecovered = 0;
        long totalRecovered = 0;
        long newConfirmed = 0;
        long newDeaths = 0;
        long totalCases = 0;

        //Creating a arraylist to store data
        dataList = new ArrayList<>();

        //looping through the array and finding the data,finnally assigning them to above data-fields
        for (int i = 0; i < arrayRoot.size(); i++) {
            //getting through the whole array to find the details we want
            JSONObject dataIn = (JSONObject) arrayRoot.get(i);
            country = (String) dataIn.get("Country");
            totalCases = (long) dataIn.get("TotalConfirmed");
            newRecovered = (long) dataIn.get("NewRecovered");
            newDeaths = (long) dataIn.get("NewDeaths");
            totalCases = (long) dataIn.get("TotalConfirmed");
            newConfirmed = (long) dataIn.get("NewConfirmed");
            totalDeaths = (long) dataIn.get("TotalDeaths");

            //Creating instance of data class and storing data object in arraylist
            data = new Data(country, totalDeaths, newRecovered, totalRecovered, newConfirmed, newDeaths, totalCases);

            //adding instance to arrayList
            dataList.add(data);
        }

        //Setting Label
        lblCountriesAtDanger = new Label("Most Endangered Countries:");
        //Creating new ListView
        lstCountriesAtDanger = new ListView();

        //We are also creating a bar-graph to compare cases between top countires of covid-19
        //Giving x-axis the label of country  
        xAxis = new CategoryAxis();
        xAxis.setLabel("Country");
        //Giving x-axis the label of total cases 
        yAxis = new NumberAxis();
        yAxis.setLabel("Total Cases");
        //creating a bar graph with two axix
        barChart = new BarChart(xAxis, yAxis);
        barChart.setId("bar");
        btnCompareData = new Button("Compare Data");

        //Also we will create a piechart to show the affected population..
        pieChart = new PieChart();
        pieChart.setId("piechart");
        //now that we have got the stats and stored them in arraylist.. we will make a gui and graph to display these..
        //looping through our arraylist to get list of vulnerable countries 
        for (Data countries : dataList) {

            //now lets add data to piechart
            ObservableList<PieChart.Data> pieChartData
                    = FXCollections.observableArrayList(
                            new PieChart.Data("Population Affected", countries.getTotalCases()));
            pieChart.setData(pieChartData);

            if (countries.getTotalCases() >= 20000) {
                //Created a new-chart and stored data
                compareChart = new XYChart.Series<>();
                compareChart.getData().add(new XYChart.Data<>(countries.getCountryName(), countries.getTotalCases() / 100));
                //add data to barchart
                barChart.getData().add(compareChart);
                //display data on listview
                lstCountriesAtDanger.getItems().addAll(countries.getCountryName());

            }
        }

        btnCompareData.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //Here we just call the method to display chart when button is pressed
                actionCompareData(barChart);

            }
        });

        //Setting totals cases of covid to this label
        lblGlobalStats = new Label(GlobalStats.display().toString());
        //Setting daily cases of covid to this label
        lblDailyStats = new Label(DailyStats.display().toString());

        //corona virus image
        ImageView imgCorona = new ImageView();
        imgCorona.setImage(new Image("gursimar_hehar_project/stagePic.png"));
        imgCorona.setFitHeight(200);
        imgCorona.setFitWidth(300);

        //Creating button to show country specific results
        btnCasesByCountry = new Button("Show Countrwise Stats");
        btnCasesByCountry.setPrefWidth(300);
        //adding action event
        btnCasesByCountry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //Here we just call the method
                countryWiseStats();

            }
        });
        //Creating button for affected Countries
        btnAffectedCountries = new Button("List Affected Countries");
        btnAffectedCountries.setPrefWidth(300);
        btnAffectedCountries.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //Here we just call the method
                countryWiseStats();

            }
        });

        VBox panButton = new VBox(5);
        panButton.setAlignment(Pos.CENTER);
        panButton.getChildren().addAll(btnCasesByCountry, btnAffectedCountries);

        //Creating gridpane and adding all nodes to it
        GridPane root = new GridPane();

        // Setting Alignment of nodes in GridPane
        GridPane.setHalignment(lblGlobalStats, HPos.CENTER);
        GridPane.setHalignment(lblDailyStats, HPos.CENTER);

        //setting padding
        root.setPadding(new Insets(10, 10, 0, 10));
        //Setting vgap and hgap
        root.setVgap(10);
        root.setHgap(10);
        //finally setting all nodes to gridpane
        root.add(imgCorona, 0, 0);
        root.add(lblGlobalStats, 3, 0);
        root.add(panButton, 2, 2);
        root.add(lblDailyStats, 3, 3);
        root.add(lblCountriesAtDanger, 0, 3);
        root.add(lstCountriesAtDanger, 0, 4);
        root.add(btnCompareData, 0, 5);
        root.add(pieChart, 3, 4);

        //creating scene
        Scene scene = new Scene(root, 1250, 900);
        //adding stylesheet
        scene.getStylesheets().add("gursimar_hehar_project/styles.css");

        //Setting Icon
        Image imgLogo = new Image("gursimar_hehar_project/covid.png");
        stage.getIcons().add(imgLogo);

        //showing scene on stage
        stage.setScene(scene);
        stage.show();

    }

    /**
     * main method to launch the app
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void countryWiseStats() {
        //A listView to Display Data
        ListView lstCountryWiseStats = new ListView();
        lstCountryWiseStats.setPrefWidth(1000);
        lstCountryWiseStats.setPrefHeight(1000);

        //A button that be use to filter data
        Button btnFilter = new Button("Filter");

        //A textfield to let user enter the data of country that they want
        TextField userInput = new TextField();
        userInput.setText("Enter Country name Here To Filter Data");

        //Looping through saved data and printing each stat   
        for (Data countryWiseStats : dataList) {
            lstCountryWiseStats.getItems().add(countryWiseStats.toString() + "\n");
        }

        //Now according to userinput we filter the data
        //Checking if user entered correct 
        btnFilter.setOnAction(event -> {
            //looping again through array , to check if we have the countryname stored somewhere in list or not       
            for (Data filter : dataList) {
                //Also convert both userinput and stored countryname to lowecase
                //so that whatever user types we can match it easily
                String countryName = filter.getCountryName().toLowerCase();
                //Covert text input to lowercase
                String refinedUserInput = userInput.getText().toLowerCase();
                //now if it matches we display the results
                if (refinedUserInput.matches(countryName)) {
                    //if it matches we delete all elements from listview and display userchoice.
                    lstCountryWiseStats.getItems().clear();
                    lstCountryWiseStats.getItems().add(filter.toString());
                }

            }

        });

        //A gridpane to add nodes to it      
        GridPane pane = new GridPane();
        //adding nodes to gridpane
        pane.add(lstCountryWiseStats, 0, 0);
        pane.add(userInput, 0, 1);
        pane.add(btnFilter, 0, 2);

        Scene scene = new Scene(pane, 1000, 800);
        scene.getStylesheets().add("gursimar_hehar_project/styles.css");

        Stage stage = new Stage();
        Image imgLogo = new Image("gursimar_hehar_project/covid.png");
        stage.getIcons().add(imgLogo);
        stage.setScene(scene);
        stage.show();
    }

    public void actionCompareData(BarChart chart) {
        StackPane pane = new StackPane();
        pane.getChildren().add(chart);
        Scene s1 = new Scene(pane, 900, 900);
        s1.getStylesheets().add("gursimar_hehar_project/styles.css");
        Stage s = new Stage();
        //Setting Icon
        Image imgLogo = new Image("gursimar_hehar_project/covid.png");
        s.getIcons().add(imgLogo);
        s.setScene(s1);
        s.show();

    }

}
