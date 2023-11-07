package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Poker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import pkgCore.Action;
import pkgCore.Table;
import pkgEnum.eAction;

public class PokerController implements Initializable  {

	private Poker poker;
	
	public Poker getGame() {
		return poker;
	}

	public void setGame(Poker _poker) {
		this.poker = _poker;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void btnSit(ActionEvent event)
	{
		Button b = (Button)event.getSource();
		
		String btnID = b.getId();
		
		this.getGame().getPlayer().setPlayerPosition(btnID);
		
		
		Action a = new Action(eAction.Sit, this.getGame().getPlayer(),null);
		this.getGame().messageSend(a);
		
	}
	
	
	@FXML
	private void btnStartGame(ActionEvent event)
	{
		//Action a = new Action(eAction.StartGame, )

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
	
	public void HandleTable(Table CurrentTable)
	{
		//	Handle buttons for all positions
		//	Loop around players in table, if instance of player
		//	is me,show leave button
		//	
	}
	
}
