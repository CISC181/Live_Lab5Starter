package pkgCore;

import java.io.Serializable;

import pkgEnum.eAction;

public class Action implements Serializable {

 
	private static final long serialVersionUID = 1L;
	private eAction eAct;
	private Player Player;
	private Object payload;
	public Action(eAction eAct, Object payload, Player player) {
		super();
		this.eAct = eAct;
		this.payload = payload;
		this.Player = player;
	}
	public eAction geteAct() {
		return eAct;
	}
	public Object getPayload() {
		return payload;
	}
	public Player getPlayer() {
		return Player;
	}
	
}
