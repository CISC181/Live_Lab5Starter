package application.helper;

import java.io.Serializable;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import pkgGame.Space;

public class ScrabbleCell extends StackPane implements Serializable {

	private Space space;

	public ScrabbleCell(Space c) {
		this.space = c;
	}

	public Space getCell() {
		return space;
	}
	
	public void setCell(Space c)
	{
		
	}

}