package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import application.controller.ClientStartController;
import application.controller.GameBorderController;
import application.controller.ScrabbleController;
//import app.controller.GameBorderController;
//import app.controller.SudokuController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import netgame.common.Client;
import pkgGame.Game;
import pkgGame.Player;
import pkgGame.Action;
import pkgGame.Board;
import pkgGame.Table;
import pkgUtility.PropertyUtil;
import pkgeNum.eAction;

public class Scrabble extends Application {

	private static Properties props = null;
	private GameClient gClient = null;
	private Stage primaryStage;
	private GameBorderController GBC = null;
	private ScrabbleController SC = null;
	private BorderPane GameBorderPane = null;
	private BorderPane ScrabblePane = null;

	private Player ScrabblePlayer;
	
 


	public static void main(String[] args) {
		PropertyUtil properties = new PropertyUtil();	
		try {
			props = properties.getPropertyFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		showServer(primaryStage);
//		HandleRoot();
	}

	public Player getScrabblePlayer() {
		return ScrabblePlayer;
	}

	/**
	 * @author BRG
	 * @version Lab #6
	 * @since Lab #6
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

			loader = new FXMLLoader(getClass().getResource("/Client/app/view/ClientStart.fxml"));
			BorderPane ClientServerOverview = (BorderPane) loader.load();
			Scene scene = new Scene(ClientServerOverview);
			primaryStage.setScene(scene);
			ClientStartController controller = loader.getController();
			controller.setMainApp(this);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void HandleRoot() {
		// Parent root;
		try {

			FXMLLoader loader = new FXMLLoader();
			loader = new FXMLLoader(getClass().getResource("/Client/app/view/GameBorder.fxml"));
			GameBorderPane = (BorderPane) loader.load();
			
			//GameBorderPane.setMinHeight(1400);
			//GameBorderPane.setMinWidth(1200);
			primaryStage.setMaximized(true);
			Scene scene = new Scene(GameBorderPane);

			
			primaryStage.setScene(scene);
			GBC = loader.getController();
			GBC.setMainApp(this);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ShowScrabble() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader = new FXMLLoader(getClass().getResource("/Client/app/view/ScrabbleBoard.fxml"));
			ScrabblePane = (BorderPane) loader.load();

			//ScrabblePane.prefHeight(500);
			//ScrabblePane.prefWidth(500);
			//ScrabblePane.setMinHeight(600);
			

			GameBorderPane.setCenter(ScrabblePane);
			SC = loader.getController();
			SC.setGame(this);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void StartClient(String strComputerName, int iPort, String strPlayerName) {
		try {
			gClient = new GameClient(strComputerName, iPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ScrabblePlayer = new Player(strPlayerName, gClient.getID());
		HandleRoot();
		ShowScrabble();
		
		//////////////////////////////////Action act = new Action(eAction.PlayerJoinApp,ScrabblePlayer, ScrabblePlayer);
		//Action act = new Action(eAction.TableState, this.getAppPlayer());		
		////////////////////////messageSend(act);
		
		System.out.println(ScrabblePlayer.getClientID());
	}

 
	/**
	 * @author BRG
	 * @version Lab #6
	 * @since Lab #6
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
	
	private class GameClient extends Client {

		public GameClient(String hubHostName, int hubPort) throws IOException {
			super(hubHostName, hubPort);
		}

		/**
		 * @author BRG
		 * @version Lab #6
		 * @since Lab #6
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
		 * @version Lab #6
		 * @since Lab #6
		 * 
		 * messageReceived - A message received from the Client is
		 * sent by the Hub.  
		 * The following messgaes will be handled...
		 * 
		 * GamePlay - call HandleGamePlayMessage
		 * Table - call HandleTableMessage
		 * 
		 * Anything else will be ignored
		 * 
		 * Call the appropriate controller methods 
		 */
		@Override
		protected void messageReceived(final Object message) {
			Platform.runLater(() -> {
				System.out.println("Message Received.  The message: " + message);

				if (message instanceof String) {
					System.out.println("Message Received from hub " + message);
				}
				else if (message instanceof ArrayList)
				{
				}
				else if (message instanceof Object) {
					
				}
				else if (message instanceof Table) {

				}

			});
		}

		
		
		/**
		 * @author BRG
		 * @version Lab #6
		 * @since Lab #6
		 * 
		 * serverShutdown - run this when shutdown is detected.
		 * 
		 * Server is going to send any "Action" message to the Hub.
		 */
		
		protected void serverShutdown(String message) {

			Platform.runLater(() -> {
				Platform.exit();
				System.exit(0);
			});
		}

	}

}