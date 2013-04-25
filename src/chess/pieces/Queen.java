package chess.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.Board;
import chess.File;
import chess.Location;

public class Queen extends BasePiece implements Piece {

	public Queen(Color color) {
		super(color, File.d, false);
	}

	private Queen(Color color, Location location) {
		super(color, location);
	}

	protected void setDefaults() {
		this.value = 27;
	}

	public Set<Location> getNextMoves(Board board) {
		Set<Location> nextMoves = new HashSet<Location>();
		for (int x = -8; x <= 8; x++) {
			addMove(nextMoves, x, 0);
		}
		for (int y = -8; y <= 8; y++) {
			addMove(nextMoves, 0, y);
		}

		return nextMoves;
	}

	public String toString() {
		return "Q";
	}

	public String toUnicode() {
		return "&#9813;";
	}

	public Queen clone() {
		return new Queen(color, location);
	}
}
