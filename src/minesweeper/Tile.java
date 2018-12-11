package minesweeper;

public class Tile {

	private int noOfMinesAround;
	private boolean enabled;
	private boolean flagged;
	private int locX, locY;
	private boolean marked;

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	Tile(int x, int y) {
		noOfMinesAround = -1;
		enabled = true;
		flagged = false;
		locX = x;
		locY = y;
	}

	public int getNoOfMinesAround() {
		return noOfMinesAround;
	}

	void setNoOfMinesAround(int noOfMinesAround) {
		this.noOfMinesAround = noOfMinesAround;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}

	@Override
	public String toString() {
		return "<" + locX + " , " + locY + "> Number of Mines Around : " + noOfMinesAround;
	}

	@Override
	public int hashCode() {
		return noOfMinesAround;
	}

	public int getLocX() {
		return locX;
	}

	public int getLocY() {
		return locY;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tile) {
			Tile otherTile = (Tile) obj;
			return otherTile.getLocX() == locX && otherTile.getLocY() == locY;
		}
		return false;
	}

}
