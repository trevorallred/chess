package chess.pieces;

public enum Color {
	Black, White;

	public int getFirstRow() {
		return (this == White ? 1 : 8);
	}

	public int getSecondRow() {
		return (this == White ? 2 : 7);
	}

	public int getLastRow() {
		return (this == Black ? 1 : 8);
	}
}
