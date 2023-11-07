package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import app.controller.ServerStartController;
import app.hub.GameHub;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import netgame.common.Client;
//import pkgGame.Action;
import pkgCore.Action;
import pkgCore.Player;

public class ServerStart extends Application {

	private Stage primaryStage;
	private GameHub gHub = null;
	private GameClient gClient = null;
	private ServerStartController controller = null;
	private HashMap<UUID, Player> appPlayers = new HashMap<UUID, Player>();

	/**
	 * @author BRG
	 * @version Lab #9
	 * @since Lab #9
	 * 
	 *        main - entry point for the application
	 * 
	 * @author BRG
	 *
	 */
	public static void main(String[] args) {

		launch(args);
	}

	/**
	 * @author BRG
	 * @version Lab #9
	 * @since Lab #9
	 * 
	 *        start - override 'start' in application, set the primarystage
	 * 
	 * @author BRG
	 *
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		showServer(primaryStage);

	}

	/**
	 * @author BRG
	 * @version Lab #9
	 * @since Lab #9
	 * 
	 *        showServer - show the server form
	 * 
	 * @author BRG
	 *
	 */
	public void showServer(Stage primaryStage) {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();

			loader = new FXMLLoader(getClass().getResource("/Server/app/view/ServerStart.fxml"));
			BorderPane ClientServerOverview = (BorderPane) loader.load();
			Scene scene = new Scene(ClientServerOverview);
			primaryStage.setScene(scene);
			controller = loader.getController();
			controller.setMainApp(this);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author BRG
	 * @version Lab #9
	 * @since Lab #9
	 * 
	 *        StartServer - Start the server socket.
	 * 
	 * @author BRG
	 *
	 */
	public void StartServer(boolean bStartHub, String strComputerName, int iPort) {
		if (bStartHub) {
			try {
				gHub = new GameHub(iPort);
				gHub.setMainApp(this);

			} catch (Exception e) {
				System.out.println("Error: Can't listen on port " + iPort);
				return;
			}
		}
		try {
			gClient = new GameClient(strComputerName, iPort);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author BRG
	 * @version Lab #9
	 * @since Lab #9
	 * 
	 *        messageSend - Call this method to send a message (Client to Hub)
	 * 
	 * @author BRG
	 *
	 */
	public void messageSend(final Object message) {
		System.out.println("Sending message from MainApp");
		gClient.messageSend(message);
	}

	public void AddPlayerToApp(Player p) {
		appPlayers.put(p.getPlayerID(), p);
	}

	public ArrayList<Player> GetAppPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();

		for (Entry<UUID, Player> entry : appPlayers.entrySet())
		{
			players.add(entry.getValue());
		}

		return players;
	}

	/**
	 * @author BRG
	 * @version Lab #9
	 * @since Lab #9
	 * 
	 *        stop - Kill the server socket if client stops program.
	 * 
	 * @author BRG
	 *
	 */
	@Override
	public void stop() throws Exception {
		gClient.serverShutdown("Client Exit");
		super.stop();
	}

	/**
	 * @author BRG
	 * @version Lab #9
	 * @since Lab #9
	 * 
	 *        GameClient - Private inner class to extend Client class
	 * 
	 * @author BRG
	 *
	 */
	private class GameClient extends Client {

		public GameClient(String hubHostName, int hubPort) throws IOException {
			super(hubHostName, hubPort);
		}

		/**
		 * @author BRG
		 * @version Lab #9
		 * @since Lab #9
		 * 
		 *        messageSend - Send a message (Hub to client)
		 * 
		 * @author BRG
		 *
		 */
		protected void messageSend(Object message) {
			resetOutput();
			super.send(message);
		}

		/**
		 * @author BRG
		 * @version Lab #9
		 * @since Lab #9
		 * 
		 *        messageReceived will get an Object message... it's up to you to
		 *        determine what should happen to that the message.
		 * 
		 *        Server is going to send any "Action" message to the Hub.
		 */
		@Override
		protected void messageReceived(final Object message) {
			Platform.runLater(() -> {
				System.out.println("Message Received.  The message: " + message);

				if (message instanceof String) {
					System.out.println("Message Received from hub " + message);
				}

				else if (message instanceof Action) {
					Action act = (Action) message;
					gHub.messageReceived(((Action) message).getPlayer().getClientID(), message);
				}

			});
		}

		/**
		 * @author BRG
		 * @version Lab #9
		 * @since Lab #9
		 * 
		 *        serverShutdown - run this when shutdown is detected.
		 * 
		 *        Server is going to send any "Action" message to the Hub.
		 */

		protected void serverShutdown(String message) {

			Platform.runLater(() -> {
				Platform.exit();
				System.exit(0);
			});
		}

	}

}