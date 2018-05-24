/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import controllz.mmi.data.SceneDocument;
import controllz.mmi.elements.Alphanumeric;
import controllz.mmi.elements.SceneElement;
import controllz.mmi.elements.SceneElementType;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import rs.prosmart.hostkonal.HostKonal;
import rs.prosmart.hostkonal.model.FxAlphanumeric2;

/**
 *
 * @author Sinisa
 */
public class HostKonalFX extends Application {
    private TableView<SceneElement> table;
	private ImageView imageView;
	private Group group;
	private Group shapeLayer;
	private VBox root;
        private TextField addressField;
    @Override
    public void start(Stage primaryStage) {
    try {
        primaryStage.setTitle("Host for Konal objects");			
        root = new VBox();		
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(1));
        Menu fileMenu = createFileMenu(primaryStage);
        MenuBar mBar = new MenuBar(fileMenu);	
        HBox addressBox = new HBox();
        addressField = new TextField();
        addressField.minWidth(200);
        addressField.autosize();
        Button browseButton = new Button("Browse");
        browseButton.setOnAction(e -> {
            try {
                String fileUrl = this.addressField.getText();
                SceneDocument doc = new SceneDocument();
                doc.open(fileUrl);
                BufferedImage buffImage = doc.getBackground();
                Image fxImg = SwingFXUtils.toFXImage(buffImage, null);
                imageView.setImage(fxImg);
                imageView.setStyle("-fx-padding-left: 0px");

                ObservableList<SceneElement> elements = FXCollections.observableArrayList(doc.getElements());
                elements.forEach(element -> {
                    if(element.getElementType().equals(SceneElementType.ALPHANUMERIC))
                    {
                        //FxAlphanumeric fxalpha = new FxAlphanumeric((Alphanumeric) element);
                        FxAlphanumeric2 fxalpha = new FxAlphanumeric2((Alphanumeric) element);
                        shapeLayer.getChildren().add(fxalpha);
                    }

                });

//                                Image img = new Image(fileUrl, false);
//                                imageView.setImage(img);
            } catch (IOException ex) {
                Logger.getLogger(HostKonal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(HostKonal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

            addressBox.getChildren().addAll(addressField, browseButton);
            addressBox.setHgrow(addressField, Priority.ALWAYS);

//			table = new TableView();
//			TableColumn<SceneElement, String> idCol = new TableColumn<>("ID");
//			idCol.setCellValueFactory(new PropertyValueFactory<SceneElement, String>("id"));
//			TableColumn<SceneElement, String> typeCol = new TableColumn("Element Type");
//			typeCol.setCellValueFactory(new PropertyValueFactory<SceneElement, String>("elementType"));
//			table.getColumns().addAll(idCol, typeCol);
//			table.getItems().clear();
//			root.setCenter(table);

            group = new Group();
            imageView = new ImageView();
            //imageView.setPreserveRatio(true);
            //imageView.setSmooth(true);
            group.getChildren().add(imageView);
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add(group);
            AnchorPane.setLeftAnchor(group, 0.0);
            AnchorPane.setTopAnchor(group, 0.0);
            root.getChildren().addAll(mBar, addressBox, anchorPane);			

            shapeLayer = new Group();
            group.getChildren().add(shapeLayer);

            Scene scene = new Scene(root,400,400);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch(Exception e) {
                e.printStackTrace();
        }
    }
	
    /**
     * Create File menu.
     * @param primaryStage
     * @return File menu
     */
    private Menu createFileMenu(Stage primaryStage)
    {
            Menu fileMenu = new Menu("File");

            // Open file item
            MenuItem fOpen = new MenuItem("Open");
            wireupFileMenuItem(fOpen, primaryStage);

            // Exit app item
            MenuItem exit = new MenuItem("Exit");
            exit.setOnAction(e -> {
                    primaryStage.close();
            });

            fileMenu.getItems().addAll(fOpen, exit);

            return fileMenu;
    }
	
    /*
     * Adds handlers for File menu.
     */
    private void wireupFileMenuItem(MenuItem fileMenu, Stage primaryStage) {
        FileChooser  fileChooser = new FileChooser();
        fileChooser.setTitle("Izaberi scenu");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Scene files", "*.scn")
        );

        fileMenu.setOnAction(e -> {
            List<File> list = fileChooser.showOpenMultipleDialog(primaryStage);			
            if(list != null)
            {
                shapeLayer.getChildren().clear();
                for(File file : list)
                {
                    String url;
                    try {
                        url = file.toURI().toURL().toString();
                        SceneDocument doc = new SceneDocument();
                        doc.open(file.getAbsolutePath());					
                        BufferedImage buffImage = doc.getBackground();
                        Image fxImg = SwingFXUtils.toFXImage(buffImage, null);
                        imageView.setImage(fxImg);
                        imageView.setStyle("-fx-padding-left: 0px");

                        ObservableList<SceneElement> elements = FXCollections.observableArrayList(doc.getElements());
                        elements.forEach(element -> {
                                if(element.getElementType().equals(SceneElementType.ALPHANUMERIC))
                                {
                                        //FxAlphanumeric fxalpha = new FxAlphanumeric((Alphanumeric) element);
                                        FxAlphanumeric2 fxalpha = new FxAlphanumeric2((Alphanumeric) element);
                                        shapeLayer.getChildren().add(fxalpha);
                                }

                        });

                        //group.setScaleX(0.7);
                        //group.setScaleY(0.7);

                    } catch (MalformedURLException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                    } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                    } catch (SAXException ex) {
                        Logger.getLogger(HostKonal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
