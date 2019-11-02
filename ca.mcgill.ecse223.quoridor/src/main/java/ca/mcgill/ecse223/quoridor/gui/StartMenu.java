package ca.mcgill.ecse223.quoridor.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartMenu extends Stage {
	
    public StartMenu() {
        
	BorderPane root = new BorderPane();
	Text text = new Text("Quoridor");
	text.setFill(Color.BLACK);
    text.setStyle("-fx-font: 24 arial;");
	
	VBox buttons = new VBox();
	buttons.setSpacing(10);
	buttons.setPadding(new Insets(5, 5, 30, 5));
	Button button = new Button("Start New Game");
	Button button2 = new Button("Load Game");
	
	buttons.getChildren().addAll(button, button2);
    
    buttons.setAlignment(Pos.CENTER);
    
    //start new game
    button.setOnMouseClicked(e -> {
    ChooseUsername();
    });
    
    //load game
    button2.setOnMouseClicked(e -> {
    	
    });
    
    root.setCenter(text);
    root.setBottom(buttons);
	
	Scene scene = new Scene(root,300, 300);
	
	this.setTitle("Hello World!");
    this.setScene(scene);
	
	}
    
    public void ChooseUsername() {
    	Pane pane = new Pane();
    	
    	Text text = new Text("White Player");
    	Text text2 = new Text("Black Player");
    	
    	text.setFill(Color.BLACK);
        text.setStyle("-fx-font: 16 arial;");
        text2.setFill(Color.BLACK);
        text2.setStyle("-fx-font: 16 arial;");
    	
    	text.setLayoutX(40);
    	text.setLayoutY(40);
    	
    	text2.setLayoutX(40);
    	text2.setLayoutY(175);
    	
    	HBox box = new HBox();
    	box.setSpacing(5);
    	box.setPadding(new Insets(10, 10, 15, 10));
    	HBox box2 = new HBox();
    	box2.setSpacing(5);
    	box2.setPadding(new Insets(10, 10, 25, 10));
    	
    	box.setLayoutX(100);
    	box.setLayoutY(100);
    	box2.setLayoutX(100);
    	box2.setLayoutY(225);
    	
    	Text text3 = new Text("Choose a username: ");
    	Text text4 = new Text("Choose a username: ");
    	
    	ComboBox username = new ComboBox();
    	username.setLayoutX(150);
    	username.setLayoutY(100);
    	username.setEditable(true);
    	
    	ComboBox username2 = new ComboBox();
    	username2.setLayoutX(150);
    	username2.setLayoutY(225);
    	username2.setEditable(true);
    	username2.setDisable(true);
    	
    	box.getChildren().addAll(text3, username);
    	box2.getChildren().addAll(text4, username2);
    	
    	try {
    		File file = new File(getClass().getClassLoader().getResource("Usernames.txt").getFile());
			Scanner input = new Scanner(file);
			while(input.hasNext())
			username.getItems().add(input.nextLine());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	pane.getChildren().addAll(text, text2, box, box2);
    	
    	Scene scene2 = new Scene(pane);
    	this.setScene(scene2);
    }
    //Scene scene;
	//Scene scene2;	
	
	
	/*public StartMenu()
	{
		try
		{	
			scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("StartMenu.fxml")));
			scene2 = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("SelectUsernameMenu.fxml")));
			this.setScene(scene);s
		}
		catch (IOException ex)
		{
			System.err.println(ex.getMessage());
		}
		primary
	}
	
	@FXML
	public void StartNewGame(MouseEvent event) {
		this.setScene(scene2);
	}
	
	@FXML
	public void LoadGame(MouseEvent event) {
		//traian yee
	}
	
	@FXML
	public void WhitePlayerChooseUsername(MouseEvent event) {
		
	}
	
	@FXML
	public void BlackPlayerChooseUsername(MouseEvent event) {
		
	}*/
}
