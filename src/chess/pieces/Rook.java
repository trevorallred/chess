package chess.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.Board;
import chess.File;
import chess.Location;

public class Rook extends BasePiece implements Piece {

	public Rook(Color color, File x) {
		super(color, x, false);
	}

	private Rook(Color color, Location location) {
		super(color, location);
	}

	protected void setDefaults() {
		this.value = 14;
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
		return "R";
	}

	public Rook clone() {
		return new Rook(color, location);
	}
}
