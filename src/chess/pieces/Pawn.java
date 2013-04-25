package chess.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.Board;
import chess.File;
import chess.Location;

public class Pawn extends BasePiece implements Piece {
	public Pawn(Color color, File x) {
		super(color, x, true);
		value = 2;
	}

	private Pawn(Color color, Location location) {
		super(color, location);
	}

	protected void setDefaults() {
		this.value = 2;
	}

	public Set<Location> getNextMoves(Board board) {
		Set<Location> nextMoves = new HashSet<Location>();
		int direction = (color == Color.White ? 1 : -1);

		addMove(nextMoves, 0, 1 * direction);
		addMove(nextMoves, 0, 2 * direction);
		return nextMoves;
	}

	public String toString() {
		return "P";
	}

	public Pawn clone() {
		return new Pawn(color, location);
	}
}
