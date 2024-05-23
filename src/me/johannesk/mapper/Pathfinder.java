package me.johannesk.mapper;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Optional;


public class Pathfinder extends Application {
    private static final String MAP_URL = "europa.gif";
    private final Button findPath = new Button("Find Path");
    private final Button showConnection = new Button("Show Connection");
    private final Button newPlace = new Button("New Place");
    private final Button newConnection = new Button("New Connection");

    private final BorderPane root = new BorderPane();
    private final StackPane centerPane = new StackPane();
    private final Scene scene = new Scene(root);
    private  ImageView imageView;
    private Boolean loadedMap = false;
    @Override
    public void start(Stage stage) throws Exception {
        VBox vBox = new VBox();
        //Menu
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem newMap = new MenuItem("New Map");
        fileMenu.getItems().addAll(newMap);
        menuBar.getMenus().addAll(fileMenu);
        vBox.getChildren().add(menuBar);
        newMap.setOnAction(ev ->
            setBackground(MAP_URL));
       //Toolbar
        HBox toolBar = new HBox();
        toolBar.setSpacing(5);
        toolBar.getChildren().addAll(findPath,showConnection,newPlace,newConnection);
        vBox.getChildren().add(toolBar);
        //Eventhandlers till knapparna
        newPlace.setOnMouseClicked(ev -> {
            scene.setCursor(Cursor.CROSSHAIR);
            newPlace.setDisable(true);

            centerPane.setOnMouseClicked(sceneEv -> {
                double xPos = sceneEv.getX();
                double yPos = sceneEv.getY();
                if (loadedMap) {
                    TextInputDialog dialog = new TextInputDialog("Name");
                    dialog.setWidth(400);
                    dialog.setHeaderText(null);
                    dialog.setContentText("Name of place");
                    Optional<String> answer = dialog.showAndWait();
                    if (answer.isPresent()){
                        System.out.println("Marking");
                        Label label = new Label();
                        label.setText(answer.get());
                        label.setMinSize(300,300);
                        Circle circle = new Circle(20, Color.RED);
                        circle.relocate(xPos, yPos);
                        centerPane.getChildren().add(circle);
                        label.setTranslateX(xPos);
                        label.setTranslateY(yPos);
                        centerPane.getChildren().add(label);
                           // root.getChildren().add(circle);
                        System.out.println("Clicked coords are (X,Y) " + xPos + "," + yPos + " vs " + label.getLayoutX() + "," + label.getLayoutY() );
                    }

                }
                newPlace.setDisable(false);
                 scene.setCursor(Cursor.DEFAULT);
            });
        });

        //Scene
        root.setTop(vBox);
        //setBackground("");
        stage.setScene(scene);
        stage.show();
    }
    private void setBackground(String url){
        try {
            InputStream is =  getClass().getResourceAsStream(url);
            Image image = new Image(is );
            imageView = new ImageView(image);
            imageView.setFitHeight(image.getRequestedHeight());
            imageView.setFitWidth(image.getRequestedWidth());
            centerPane.getChildren().add(imageView);

            root.setCenter(centerPane);
            loadedMap = true;
        } catch (NullPointerException e){
            System.out.println("Could not find imnage at " + url);
        }
    }
}
