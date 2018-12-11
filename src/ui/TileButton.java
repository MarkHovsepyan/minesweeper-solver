package ui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import minesweeper.Tile;

public class TileButton extends JButton {
	
	private Tile associatedSquare;
	
	public Tile getAssociatedTile() {
		return associatedSquare;
	}

	public void setAssociatedTile(Tile associatedSquare) {
		this.associatedSquare = associatedSquare;
	}

	public TileButton() {
	}

	public TileButton(Icon icon) {
		super(icon);
	}

	public TileButton(String text) {
		super(text);
	}

	public TileButton(Action a) {
		super(a);
	}

	public TileButton(String text, Icon icon) {
		super(text, icon);
	}

}
