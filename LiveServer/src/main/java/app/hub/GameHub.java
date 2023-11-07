package app.hub;

import java.io.IOException;

import app.ServerStart;
import netgame.common.Hub;
import pkgException.DeckException;
import pkgException.HandException;
import pkgCore.Action;

import pkgCore.GamePlay;
import pkgCore.Player;
import pkgCore.Rule;
import pkgCore.Table;
import pkgEnum.eGame;



public class GameHub extends Hub {

	private ServerStart mainApp;
	private Table HubTable = null;
	private GamePlay HubGame = null;
 

	public void setMainApp(ServerStart mainApp) {
		this.mainApp = mainApp;
	}
	
	public GameHub(int port) throws IOException {
		super(port);
		this.setAutoreset(true);
	}

	@Override
	public void messageReceived(int ClientID, Object message) {

		if (HubTable == null)
			HubTable = new Table("PokerTable");

		if (message instanceof Action) {
			Action act = (Action) message;

			switch (act.geteAct()) {
			case PlayerJoinApp:
				Player p = act.getPlayer(); 
				this.mainApp.AddPlayerToApp(p);
								break;
			case StartGame:
				Rule r = new Rule(eGame.FiveStud);
				HubGame = new GamePlay(r);
				
				for (Player HubTablePlayer: this.HubTable.getTablePlayer())
				{
					HubGame.AddPlayer(HubTablePlayer);
				}
				try {
					HubGame.StartGame();
				} catch (DeckException e) {
					// TODO Auto-generated catch block
					//Test
					e.printStackTrace();
				} catch (HandException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				resetOutput();
				
			default:
				break;
			}
		}
	}

}