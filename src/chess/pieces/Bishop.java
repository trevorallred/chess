package chess.pieces;

import chess.File;
import chess.Location;

public class Bishop extends BasePiece implements Piece {

	public Bishop(Color color, File x) {
		super(color, x, false);
	}

	private Bishop(Color color, Location location) {
		super(color, location);
	}

	protected void setDefaults() {
		this.value = 7;
	}

	public String toString() {
		return "B";
	}

	public Bishop clone() {
		return new Bishop(color, location);
	}
}
