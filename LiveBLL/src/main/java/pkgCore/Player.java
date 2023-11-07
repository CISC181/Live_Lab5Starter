package pkgCore;

import java.io.Serializable;
import java.util.UUID;

public class Player implements Serializable {
 
	private static final long serialVersionUID = 1L;
	
	private UUID PlayerID;
	private String PlayerName;
	private int ClientID;
	private String PlayerPosition;
	
	public Player(UUID playerID, String playerName) {
		super();
		PlayerID = playerID;
		PlayerName = playerName;
	}
		
	public Player(String playerName) {
		this.PlayerID = UUID.randomUUID();
		PlayerName = playerName;
	}
	
	public Player(String playerName,  int iClientID) {
		this.PlayerID = UUID.randomUUID();
		PlayerName = playerName;
		this.ClientID = iClientID;
	}

	public String getPlayerName() {
		return PlayerName;
	}

	public void setPlayerName(String playerName) {
		PlayerName = playerName;
	}

	public UUID getPlayerID() {
		return PlayerID;
	}
	
	public int getClientID() {
		return ClientID;
	}

	public String getPlayerPosition() {
		return PlayerPosition;
	}

	public void setPlayerPosition(String playerPosition) {
		PlayerPosition = playerPosition;
	}
	
	
}