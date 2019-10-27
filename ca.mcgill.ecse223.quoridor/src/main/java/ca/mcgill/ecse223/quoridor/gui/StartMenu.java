package ca.mcgill.ecse223.quoridor.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartMenu extends Stage {
	public StartMenu()
	{
		try
		{
			System.out.print("Hello");
			this.setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("StartMenu.fxml"))));
		}
		catch (IOException ex)
		{
			System.err.println(ex.getMessage());
		}
	}
}
