package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Scrabble;
import application.helper.ScrabbleCell;
import application.helper.ScrabbleStyler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.GridPane;
import pkgGame.Space;
import pkgeNum.eAction;
import pkgGame.Action;
import pkgGame.Board;
import pkgGame.Game;
import pkgGame.Letter;
import pkgGame.Move;
import pkgGame.Player;

public class ScrabbleController implements Initializable {

	private int iCellSize = 45;
	private Scrabble scrabble;
	private Board ScrabbleBoard = null;
	
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
		
		Player p = this.getGame().getScrabblePlayer();
		Move m = new Move(ScrabbleBoard, this.getGame().getScrabblePlayer());		
		Action act = new Action(eAction.AttemptMove, m, p);
		//Action act = new Action(eAction.StartGamePoker, this.mainApp.getAppPlayer());
		
		this.getGame().messageSend(act);
	}
	
	private GridPane BuildScrabbleGrid(Board board) {

		ScrabbleStyler ss = new ScrabbleStyler(board);
		GridPane gridPaneSudoku = new GridPane();
		gridPaneSudoku.setCenterShape(true);

		gridPaneSudoku.setMaxWidth(iCellSize * board.getPuzzle().length);
		gridPaneSudoku.setMaxHeight(iCellSize * board.getPuzzle().length);

		for (int iCol = 0; iCol < board.getPuzzle().length; iCol++) {
			gridPaneSudoku.getColumnConstraints().add(ScrabbleStyler.getGenericColumnConstraint(iCellSize));
			gridPaneSudoku.getRowConstraints().add(ScrabbleStyler.getGenericRowConstraint(iCellSize));

			for (int iRow = 0; iRow < board.getPuzzle().length; iRow++) {

				// The image control is going to be added to a StackPane, which can be centered

				ScrabbleCell paneTarget = new ScrabbleCell(new Space(iRow, iCol));

				if (board.getPuzzle()[iRow][iCol].getLetter() != null) {
					ImageView iv = new ImageView(GetImage(board.getPuzzle()[iRow][iCol].getLetter()));
					//paneTarget.getCell().setiCellValue(board.getPuzzle()[iRow][iCol]);
					paneTarget.getChildren().add(iv);
				}

				paneTarget.getStyleClass().clear(); // Clear any errant styling in the pane
				paneTarget.setStyle(ss.getStyle(new Space(iRow, iCol))); // Set the styling.

				paneTarget.setOnMouseClicked(e -> {
					System.out.println(paneTarget.getCell().getLetter());
				});
				gridPaneSudoku.add(paneTarget, iCol, iRow); // Add the pane to the grid
			}
		}
		return gridPaneSudoku;
	}
	
	private Image GetImage(Letter letter) {
		Image img = new Image(getClass().getResource("/img/" + letter.getChLetter() + ".png").toString());
		return img;
	}
}
