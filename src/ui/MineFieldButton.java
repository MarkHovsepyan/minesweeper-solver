package ui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import bean.Square;

public class MineFieldButton extends JButton {
	
	private Square associatedSquare;
	
	public Square getAssociatedSquare() {
		return associatedSquare;
	}

	public void setAssociatedSquare(Square associatedSquare) {
		this.associatedSquare = associatedSquare;
	}

	public MineFieldButton() {
	}

	public MineFieldButton(Icon icon) {
		super(icon);
	}

	public MineFieldButton(String text) {
		super(text);
	}

	public MineFieldButton(Action a) {
		super(a);
	}

	public MineFieldButton(String text, Icon icon) {
		super(text, icon);
	}

}
