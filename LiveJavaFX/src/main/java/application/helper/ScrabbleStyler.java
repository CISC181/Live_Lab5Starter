package application.helper;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import pkgGame.Board;
import pkgGame.Space;

public class ScrabbleStyler {

	private int iPuzzleBorder = 5;
	private int iCellBorder = 1;

	private Board board;

	public ScrabbleStyler(Board b) {
		this.board = b;
	}

	/**
	 * iGetTop - Figure out the width of the 'top' part of the cell
	 * 
	 * @param s
	 * @return
	 */
	private int iGetTop(Space s) {
		if (board.bFirstRow(s)) {
			return iPuzzleBorder;
		}
		return iCellBorder;
	}

	/**
	 * iGetBottom - Figure out the width of the 'bottom' part of the cell
	 * 
	 * @param s
	 * @return
	 */
	private int iGetBottom(Space s) {
		if (board.bLastRow(s)) {
			return iPuzzleBorder;
		} else
			return iCellBorder;
	}

	/**
	 * iGetLeft - Figure out the width of the 'left' part of the cell
	 * 
	 * @param s
	 * @return
	 */
	private int iGetLeft(Space s) {
		if (board.bFirstCol(s)) {
			return iPuzzleBorder;
		} else
			return iCellBorder;
	}

	/**
	 * iGetRight - Figure out the width of the 'right' part of the cell
	 * 
	 * @param c
	 * @return
	 */
	private int iGetRight(Space s) {
		if (board.bLastCol(s)) {
			return iPuzzleBorder;		
		} else
			return iCellBorder;
	}

	/**
	 * getStyle - Figure out the styling of the cell (color, cell border)
	 * 
	 * @param s
	 * @return
	 */
	public String getStyle(Space s) {
		String strStyle = "-fx-background-color: black, white; ";

		strStyle += String.format("-fx-background-insets: 0, %1$s %2$s %3$s %4$s;", iGetTop(s), iGetRight(s),
				iGetBottom(s), iGetLeft(s));

		return strStyle;
	}

	public static ColumnConstraints getGenericColumnConstraint(int iCellSize) {
		ColumnConstraints colCon = new ColumnConstraints();
		colCon.setHgrow(Priority.NEVER); // This means the column will never grow, even if you re-size the scene
		colCon.halignmentProperty().set(HPos.CENTER); // Center the stuff you add to the column
		colCon.setMinWidth(iCellSize); // Set the width of the column

		return colCon;
	}

	public static RowConstraints getGenericRowConstraint(int iCellSize) {
		// RowConstraint is a generic rule... how every row in the grid should behave
		RowConstraints rowCon = new RowConstraints();
		rowCon.setMinHeight(iCellSize); // Set the height of the row
		rowCon.setVgrow(Priority.NEVER); // This means the row will never grow, even if you re-size the scene
		rowCon.valignmentProperty().set(VPos.CENTER); // Center the stuff added to the row
		return rowCon;
	}

	public static Pane getRedPane() {
		Pane p = new Pane();
		String strStyle = "-fx-background-color: #AA0000; ";
		p.setStyle(strStyle);
		return p;
	}

	public static void RemoveGridStyling(GridPane gp) {
		Platform.runLater(() -> {
			for (Node child : gp.getChildren()) {
				Integer column = GridPane.getColumnIndex(child);
				Integer row = GridPane.getRowIndex(child);

				ScrabbleCell sc = (ScrabbleCell) child;
				sc.getStyleClass().clear();
			}
		});
	}

}