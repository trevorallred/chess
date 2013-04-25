package chess;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import chess.pieces.Color;
import chess.pieces.Piece;

public class Board {
	private Color nextMove = Color.White;
	private Set<Piece> pieces = new HashSet<Piece>();

	public Set<Piece> getPieces() {
		return pieces;
	}

	public void move(Location fromLocation, Location toLocation) throws Exception {
		Piece piece = getPieceAtLocation(fromLocation);
		if (piece == null) {
			throw new Exception("No piece found on board at location " + fromLocation);
		}
		if (!canPieceMoveTo(piece, toLocation)) {
			// throw new Exception("Not a valid move to " + toLocation);
		}
		piece.setLocation(toLocation);
	}

	public Piece getPieceAtLocation(Location location) {
		for (Piece piece : pieces) {
			if (piece.getLocation().isAt(location)) {
				return piece;
			}
		}
		return null;
	}

	private boolean canPieceMoveTo(Piece piece, Location toLocation) {
		Set<Location> nextMoves = piece.getNextMoves(this);
		for (Location location : nextMoves) {
			if (toLocation.equals(location))
				return true;
		}
		return nextMoves.contains(toLocation);
	}

	public Board clone() {
		Board board = new Board();
		board.nextMove = this.nextMove;

		for (Piece piece : pieces) {
			board.getPieces().add(piece.clone());
		}
		return board;
	}

	public Color getNextMove() {
		return nextMove;
	}

	public Map<Piece, Set<Location>> getNextMoves() {
		Map<Piece, Set<Location>> allNextMoves = new HashMap<Piece, Set<Location>>();
		for (Piece piece : pieces) {
			if (piece.getColor() == nextMove) {
				Set<Location> nextMoves = piece.getNextMoves(this);
				if (nextMoves.size() > 0) {
					allNextMoves.put(piece, nextMoves);
				}
			}
		}
		return allNextMoves;
	}

	public Piece getKing(Color color) {
		for (Piece piece : pieces) {
			if (piece.getColor() == color) {
				// TODO only return the King
				return piece;
			}
		}
		// This shouldn't happen in a real game
		return null;
	}
}
