package pkgCore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import pkgEnum.eCardDestination;
import pkgEnum.eDrawCount;
import pkgEnum.eRank;
import pkgEnum.eSuit;
import pkgException.DeckException;
import pkgException.HandException;

public class GamePlay {

	private Rule Rle;
	private HashMap<UUID, Player> GamePlayers = new HashMap<UUID, Player>();
	private HashMap<UUID, HandPoker> GameHand = new HashMap<UUID, HandPoker>();
	private ArrayList<Card> CommonCards = new ArrayList<Card>();
	private Deck GameDeck;

	private CardDraw currentDraw;
	private eDrawCount currentDrawCount;

	public GamePlay(Rule rle) {

		this.Rle = rle;
		GameDeck = new Deck();
	}

	/**
	 * GamePlay - Create an instance of GamePlay. For every player in the table, add
	 * them to the game Set the GameDeck.
	 * 
	 * @param t
	 * @param rle
	 */
	public GamePlay(Table t, Rule rle) {

		this.Rle = rle;
		for (var p : t.getTablePlayer()) {
			this.GamePlayers.put(p.getPlayerID(), p);
		}
		GameDeck = new Deck();
	}

	public void AddPlayer(Player p) {
		this.GamePlayers.put(p.getPlayerID(), p);
	}

	/**
	 * Draw - Draw a card from the game's deck and assign it to a given player
	 * 
	 * @author BRG
	 * @version Lab #3
	 * @since Lab #3
	 * 
	 * @param p
	 * @param CD
	 * @throws DeckException
	 * @throws HandException
	 */
	public void Draw(Player p, CardDraw CD) throws DeckException, HandException {

		for (int crdCnt = 0; crdCnt < CD.getCardCount().getCardCount(); crdCnt++) {
			if (CD.getCardDestination() == eCardDestination.COMMON) {
				CommonCards.add(GameDeck.Draw());
			} else {
				GameHand.get(p.getPlayerID()).Draw(GameDeck).ScoreHand();
			}
		}
	}

	public void Draw() throws DeckException, HandException {

		for (int crdCnt = 0; crdCnt < currentDraw.getCardCount().getCardCount(); crdCnt++) {
			if (currentDraw.getCardDestination() == eCardDestination.COMMON) {
				CommonCards.add(GameDeck.Draw());
			} else {
				for (Map.Entry<UUID, Player> mapElement : GamePlayers.entrySet()) {
					UUID key = mapElement.getKey();

					// Adding some bonus marks to all the students
					Player p = mapElement.getValue();
					GameHand.get(p.getPlayerID()).Draw(GameDeck).ScoreHand();
				}
			}

		}
		switch (currentDrawCount)
		{
		case FIRST:
				currentDrawCount = eDrawCount.SECOND;
				break;
		case SECOND:
			currentDrawCount = eDrawCount.THIRD;
			break;
		case THIRD:
			currentDrawCount = eDrawCount.FOURTH;
			break;
		case FOURTH:
			currentDrawCount = eDrawCount.FIFTH;
			break;
		}
	}

	/**
	 * @author BRG
	 * @version Lab #3
	 * @since Lab #3
	 * 
	 *        getCommonCards - returns the common cards for the game. There's a bit
	 *        of a cheat- return 'jokers' for cards that are not yet dealt. If there
	 *        are supposed to be 5 community cards, and in the current state of the
	 *        game there are 3, return the three + two jokers.
	 * 
	 * @return - list of community cards.
	 */
	public ArrayList<Card> getCommonCards() {

		return CommonCards;
	}

	/**
	 * @author BRG
	 * @version Lab #3
	 * @since Lab #3
	 * 
	 *        setCommonCards - setter for CommonCards. Be careful! You should only
	 *        use this method in reflections for testing!!
	 * 
	 * @return - list of community cards.
	 */
	private GamePlay setCommonCards(ArrayList<Card> commonCards) {
		CommonCards.clear();
		CommonCards = (ArrayList<Card>) commonCards.clone();
		return this;
	}

	/**
	 * @author BRG
	 * @version Lab #3
	 * @since Lab #3
	 * 
	 *        setPlayerHand - Only used for unit testing.
	 * 
	 * @return - current instance of GamePlay
	 */
	private GamePlay setPlayerHand(UUID PlayerID, HandPoker HP) {
		try {
			HP.ScoreHand();
		} catch (HandException e) {
			// There's no way this can fail, you're sending in cards.
			e.printStackTrace();
		}
		this.GameHand.put(PlayerID, HP);
		return this;
	}

	/**
	 * @author BRG
	 * @version Lab #3
	 * @since Lab #3 GetGamePlayer - return the Player object for a given PlayerID
	 * @param PlayerID - ID for the Player
	 * @return - Player object
	 */
	private Player GetGamePlayer(UUID PlayerID) {
		return this.GamePlayers.get(PlayerID);
	}

	/**
	 * @author BRG
	 * @version Lab #3
	 * @since Lab #3
	 * 
	 *        GetPlayersHand - return the Hand in the GameHand hashmap for a given
	 *        player
	 * @param player
	 * @return
	 */
	public HandPoker GetPlayersHand(Player player) {
		return GameHand.get(player.getPlayerID());
	}

	/**
	 * @author BRG
	 * @version Lab #3
	 * @since Lab #3
	 * 
	 *        getRle - Get the rule for the game. It's set in the constructor
	 * @return
	 */
	public Rule getRle() {
		return Rle;
	}

	/**
	 * @author BRG
	 * @version Lab #3
	 * @since Lab #3
	 * 
	 *        StartGame - Create a new HandPoker for each player, put it in the
	 *        GameHand map, execute the first Draw
	 * 
	 * @throws DeckException
	 * @throws HandException
	 */
	public void StartGame() throws DeckException, HandException {

		// Looping through the HashMap
		for (Map.Entry<UUID, Player> mapElement : GamePlayers.entrySet()) {
			UUID key = mapElement.getKey();

			// Adding some bonus marks to all the students
			Player value = mapElement.getValue();

			HandPoker hp = new HandPoker(value, this);
			GameHand.put(key, hp);

			currentDraw = this.Rle.getCardDraw(eDrawCount.FIRST);

			// Draw(value, this.Rle.getCardDraw(eDrawCount.FIRST));
		}
	}

	/**
	 * @author BRG
	 * @version Lab #4
	 * @since Lab #3
	 * 
	 *        WinningHand - Will return the Winning Hand. Have to handle all
	 *        possible hands
	 * 
	 * @return - HandPoker
	 * @throws HandException
	 */
	public HandPoker WinningHand() throws HandException {
		ArrayList<HandPoker> GameHands = new ArrayList<HandPoker>();

		ArrayList<HandPoker> PossibleGameHands = new ArrayList<HandPoker>();

		for (Map.Entry<UUID, HandPoker> mapElement : this.GameHand.entrySet()) {
			GameHands.add(mapElement.getValue());
		}

		for (HandPoker hp : GameHands) {
			PossibleGameHands.addAll(hp.GetPossibleHands(this));
		}

		for (HandPoker hp : PossibleGameHands) {
			hp.ScoreHand();
		}

		PossibleGameHands.sort(HandPoker.hpComparator);

		return GameHands.get(0);
	}

	/**
	 * @author BRG
	 * @version Lab #3
	 * @since Lab #3
	 * 
	 *        WinningPlayer - Will return the Winning Player
	 * 
	 * @return Player
	 * @throws HandException
	 */
	public ArrayList<Player> WinningPlayer() throws HandException {
		HandPoker hp = this.WinningHand();
		HandScorePoker HSP = hp.getHSP();
		ArrayList<Player> WinningPlayers = new ArrayList<Player>();

		for (Map.Entry<UUID, HandPoker> mapElement : this.GameHand.entrySet()) {
			HandPoker hpItem = mapElement.getValue();
			if (hpItem.getHSP().equals(HSP)) {
				WinningPlayers.add(GamePlayers.get(mapElement.getKey()));
			}

		}
		return WinningPlayers;
	}

}