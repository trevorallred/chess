package chess.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.Board;
import chess.File;
import chess.Location;

public class King extends BasePiece implements Piece {

	public King(Color color) {
		super(color, File.e, false);
	}

	private King(Color color, Location location) {
		super(color, location);
	}

	protected void setDefaults() {
		this.value = 0;
	}

	public Set<Location> getNextMoves(Board board) {
		Set<Location> nextMoves = new HashSet<Location>();
		
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				addMove(nextMoves, x, y);
			}
		}
		
		return nextMoves;
	}

	public String toString() {
		return "K";
	}

	public King clone() {
		return new King(color, location);
	}
}
