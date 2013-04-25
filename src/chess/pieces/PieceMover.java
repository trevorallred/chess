package chess.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.Board;
import chess.Location;

public class PieceMover {
	private Board board;

	public PieceMover(Board board) {
		this.board = board;
	}

	public Set<Location> getNextMoves(Piece piece) {
		Set<Location> nextMoves = new HashSet<Location>();
		for (Diagonally direction : Diagonally.values()) {
			// Find the piece and make a copy so we don't actually move it
			// around
			nextMoves.addAll(move(piece, direction));
		}
		return nextMoves;
	}

	private Set<Location> move(Piece piece, Diagonally direction) {
		Set<Location> nextMoves = new HashSet<Location>();
		Location location = piece.getLocation();
		Location newLocation = null;
		switch (direction) {
		case TopRight:
			newLocation = new Location(location, 1, 1);
			break;
		case TopLeft:
			newLocation = new Location(location, -1, 1);
			break;
		case BottomRight:
			newLocation = new Location(location, 1, -1);
			break;
		case BottomLeft:
			newLocation = new Location(location, -1, -1);
			break;
		default:
			newLocation = new Location(location, 0, 0);
		}

		if (!newLocation.isValid()) {
			return nextMoves;
		}

		Piece pieceAtNewLocation = board.getPieceAtLocation(newLocation);

		if (pieceAtNewLocation == null) {
			System.out.println("No piece at " + newLocation);
			nextMoves.add(newLocation);
			// Keep moving
			piece = piece.clone();
			piece.setLocation(newLocation);
			nextMoves.addAll(move(piece, direction));
		} else if (piece.getColor() != pieceAtNewLocation.getColor()) {
			System.out.println("Opponent " + pieceAtNewLocation + " at " + newLocation);
			// Don't keep moving
			// An opponent is there but you can move there
			nextMoves.add(newLocation);
		} else {
			// Target is on the same team. Can't kill your team mate.
			System.out.println("Team mate " + pieceAtNewLocation + " at " + newLocation);
		}
		return nextMoves;
	}

	private enum Diagonally {
		TopRight, TopLeft, BottomRight, BottomLeft;
	}

}
