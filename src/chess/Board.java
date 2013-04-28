package chess;

import java.util.HashSet;
import java.util.Set;

import chess.pieces.Color;
import chess.pieces.Piece;
import chess.pieces.PieceType;

public class Board {
	private Set<Piece> pieces = new HashSet<Piece>();

	public Set<Piece> getPieces() {
		return pieces;
	}

	public void kill(Piece piece) {
		pieces.remove(piece);
	}

	public Piece getPieceAtLocation(Location location) {
		for (Piece piece : pieces) {
			if (piece.getLocation().isAt(location)) {
				return piece;
			}
		}
		return null;
	}

	public Piece getKing(Color color) {
		for (Piece piece : pieces) {
			if (piece.getColor() == color && piece.getType() == PieceType.King) {
				return piece;
			}
		}
		System.out.println("Warning!! For some reason, the " + color + " king doesn't exist on the board");
		return null;
	}

	public Board clone() {
		Board board = new Board();
		for (Piece piece : pieces) {
			board.getPieces().add(piece.clone());
		}
		return board;
	}
}
