package app.hub;

import java.io.IOException;

import app.ServerStart;
import netgame.common.Hub;
import pkgException.DeckException;
import pkgException.HandException;
import pkgGame.Action;

import pkgCore.GamePlay;
import pkgCore.Player;
import pkgCore.Table;



public class GameHub extends Hub {

	private ServerStart mainApp;
	private Table HubTable = null;
	private Game HubGame = null;
	

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
			HubTable = new Table();

		if (message instanceof Action) {
			Action act = (Action) message;

			switch (act.geteAct()) {
			case PlayerJoinApp:
				Player p = act.getPlayer(); 
				this.mainApp.AddPlayerToApp(p);
								break;
			case AttemptMove:
				resetOutput();
				// Attempt Move
				break;
			case StartGame:
				HubGame = new Game();
				for (Player HubTablePlayer: this.HubTable.getPlayers())
				{
					HubGame.AddPlayer(HubTablePlayer);
				}
				try {
					HubGame.StartGame();
				} catch (DrawException e) {
					// TODO Auto-generated catch block
					//Test
					e.printStackTrace();
				} catch (GameException e) {
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