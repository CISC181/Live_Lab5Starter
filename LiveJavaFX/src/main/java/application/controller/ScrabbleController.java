package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Scrabble;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.GridPane;
import pkgEnum.eAction;
import pkgCore.Action;
import pkgCore.GamePlay;
import pkgCore.Player;

public class ScrabbleController implements Initializable {

	private Scrabble scrabble;
	
	public Scrabble getGame() {
		return scrabble;
	}

	public void setGame(Scrabble scrabble) {
		this.scrabble = scrabble;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void btnStartGame(ActionEvent event)
	{
		
	}

	@FXML
	private void btnUndo_Click(ActionEvent event)
	{
		
	}
	
	@FXML
	private void btnRedo_Click(ActionEvent event)
	{
		
	}
	@FXML
	private void btnMakeMove(ActionEvent event) {
		
	}
	

	
//	private Image GetImage(Letter letter) {
//		Image img = new Image(getClass().getResource("/img/" + letter.getChLetter() + ".png").toString());
//		return img;
//	}
}
