package ui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import minesweeper.bean.Square;

public class MineFieldButton extends JButton {
	
	private Square associatedSquare;
	
	
	
	public Square getAssociatedSquare() {
		return associatedSquare;
	}

	public void setAssociatedSquare(Square associatedSquare) {
		this.associatedSquare = associatedSquare;
	}

	public MineFieldButton() {
		// TODO Auto-generated constructor stub
	}

	public MineFieldButton(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	public MineFieldButton(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public MineFieldButton(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public MineFieldButton(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

}
