package chess.pieces;

import java.util.Set;

import chess.Board;
import chess.Location;

public class Piece {
	private Color color;
	private Location location;
	private PieceType type;

	public Piece(PieceType type, Color color, Location location) {
		this.type = type;
		this.color = color;
		this.location = location;
	}

	public PieceType getType() {
		return type;
	}

	public Color getColor() {
		return color;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	// TODO remove this method
	public Set<Location> getNextMoves(Board board) {
		return new PieceMover(board).getNextMoves(this);
	}

	public Piece clone() {
		return new Piece(type, color, location);
	}
}
