package chess.pieces;

import java.util.Set;

import chess.Board;
import chess.File;
import chess.Location;

public class BasePiece implements Piece {
	protected Color color;
	protected Location location;
	protected int value;

	protected BasePiece(Color color, Location location) {
		setDefaults();
	}

	protected BasePiece(Color color, File x, boolean isPawn) {
		setDefaults();
		this.color = color;
		int y = 0;
		if (isPawn) {
			y = (this.color == Color.White ? 2 : 7);
		} else {
			// All other pieces
			y = (this.color == Color.White ? 1 : 8);
		}
		this.location = new Location(x, y);
	}

	protected void setDefaults() {
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

	public Set<Location> getNextMoves(Board board) {
		return new PieceMover(board).getNextMoves(this);
	}
	
	public Piece clone() {
		return new BasePiece(color, location);
	}

	protected void addMove(Set<Location> nextMoves, int x, int y) {
		if (x == 0 && y == 0)
			return;

		Location newLocation = new Location(location, x, y);
		if (newLocation.isValid())
			nextMoves.add(newLocation);
	}
}
