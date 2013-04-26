package chess.pieces;

import java.util.HashSet;
import java.util.Iterator;
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

		switch (piece.getType()) {
		case King:
			for (Direction direction : Direction.values()) {
				nextMoves.add(moveOneSpace(piece, direction));
			}
			break;
		case Queen:
			for (Direction direction : Direction.values()) {
				nextMoves.addAll(move(piece, direction));
			}
			break;
		case Bishop:
			for (Direction direction : Direction.getBishopDirections()) {
				nextMoves.addAll(move(piece, direction));
			}
			break;
		case Rook:
			for (Direction direction : Direction.getRookDirections()) {
				nextMoves.addAll(move(piece, direction));
			}
			break;
		case Knight:
			// TODO
			nextMoves.add(new Location(piece.getLocation(), 1, 2));
			break;
		case Pawn:
			int direction = (piece.getColor() == Color.White ? 1 : -1);
			if (nextMoves.add(new Location(piece.getLocation(), 0, 1 * direction))) {
				nextMoves.add(new Location(piece.getLocation(), 0, 2 * direction));
			}
			break;
		}

		removeInvalidMoves(nextMoves);

		return nextMoves;
	}

	private void removeInvalidMoves(Set<Location> nextMoves) {
		Iterator<Location> iterator = nextMoves.iterator();
		while (iterator.hasNext()) {
			Location location = iterator.next();
			if (location == null || !location.isValid()) {
				iterator.remove();
			}
		}
	}

	private Set<Location> move(Piece piece, Direction direction) {
		return move(piece, direction, true);
	}

	private Location moveOneSpace(Piece piece, Direction direction) {
		Location nextLocation = new Location(piece.getLocation(), direction.x, direction.y);
		if (pieceAtTargetIsFriendly(piece, nextLocation)) {
			return null;
		}
		return nextLocation;
	}

	private boolean pieceAtTargetIsFriendly(Piece piece, Location nextLocation) {
		return whatIsAtLocation(piece.getColor(), nextLocation) == PieceTypeAtLocation.Friend;
	}

	private Set<Location> move(Piece piece, Direction direction, boolean keepMoving) {
		Set<Location> nextMoves = new HashSet<Location>();

		Location nextLocation = new Location(piece.getLocation(), direction.x, direction.y);
		if (!nextLocation.isValid()) {
			return nextMoves;
		}

		switch (whatIsAtLocation(piece.getColor(), nextLocation)) {
		case Empty:
			nextMoves.add(nextLocation);
			if (keepMoving) {
				piece = piece.clone();
				piece.setLocation(nextLocation);
				nextMoves.addAll(move(piece, direction));
			}
			break;
		case Friend:
			// Target is on the same team. Can't kill your team mate.
			break;
		case Foe:
			nextMoves.add(nextLocation);
			break;
		}

		return nextMoves;
	}

	private PieceTypeAtLocation whatIsAtLocation(Color friendColor, Location location) {
		Piece target = board.getPieceAtLocation(location);

		if (target == null) {
			System.out.println("No piece at " + location);
			return PieceTypeAtLocation.Empty;
		}

		if (friendColor == target.getColor()) {
			System.out.println("Team mate " + target + " at " + location);
			return PieceTypeAtLocation.Friend;
		} else {
			System.out.println("Opponent " + target + " at " + location);
			return PieceTypeAtLocation.Foe;
		}
	}

	private enum PieceTypeAtLocation {
		Empty, Friend, Foe;
	}

	private enum Direction {
		Up(0, 1), Down(0, -1), Right(1, 0), Left(-1, 0), TopRight(1, 1), TopLeft(-1, 1), BottomRight(1, -1), BottomLeft(
				-1, -1);
		int x = 0;
		int y = 0;

		private Direction(int x, int y) {
			this.x = x;
			this.y = y;
		}

		static Set<Direction> getBishopDirections() {
			Set<Direction> directions = new HashSet<Direction>();
			directions.add(Direction.TopLeft);
			directions.add(Direction.TopRight);
			directions.add(Direction.BottomLeft);
			directions.add(Direction.BottomRight);
			return directions;
		}

		static Set<Direction> getRookDirections() {
			Set<Direction> directions = new HashSet<Direction>();
			directions.add(Direction.Up);
			directions.add(Direction.Down);
			directions.add(Direction.Right);
			directions.add(Direction.Left);
			return directions;
		}
	}
}
