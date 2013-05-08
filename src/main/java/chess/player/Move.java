package chess.player;

import chess.Location;

public class Move {
	final private Location from;
	final private Location to;

	public Move(Location from, Location to) {
		this.from = from;
		this.to = to;
	}

	public Location getFrom() {
		return from;
	}

	public Location getTo() {
		return to;
	}

	public String toString() {
		return from.toString() + "->" + to.toString();
	}

	public boolean equals(Move obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		
		Move o = (Move) obj;
		if (!o.getFrom().equals(this.getFrom()))
			return false;
		return o.getTo().equals(this.getTo());
	}
}
