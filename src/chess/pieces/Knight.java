package chess.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.Board;
import chess.File;
import chess.Location;

public class Knight extends BasePiece implements Piece {

	public Knight(Color color, File x) {
		super(color, x, false);
	}

	protected void setDefaults() {
		this.value = 8;
	}

	public Set<Location> getNextMoves(Board board) {
		Set<Location> nextMoves = new HashSet<Location>();
		addMove(nextMoves, 1, 2);
		addMove(nextMoves, 2, 1);
		addMove(nextMoves, 1, -2);
		addMove(nextMoves, 2, -1);
		addMove(nextMoves, -1, 2);
		addMove(nextMoves, -2, 1);
		addMove(nextMoves, -1, -2);
		addMove(nextMoves, -2, -1);

		return nextMoves;
	}
	
	public String toString() {
		return "N";
	}

	public String toUnicode() {
		return "&#9816;";
	}

	public Knight clone() {
		return new Knight(color, location.getX());
	}
}
