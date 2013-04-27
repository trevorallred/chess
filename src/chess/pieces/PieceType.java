package chess.pieces;

public enum PieceType {
	King, Queen, Bishop, Knight("N"), Rook, Pawn;

	private String initial;

	private PieceType() {
		this.toString().substring(0, 1);
	}

	private PieceType(String initial) {
		this.initial = initial;
	}

	public String getInitial() {
		if (initial == null)
			return this.toString().substring(0, 1);
		return initial;
	}

	public static PieceType getType(String initial) {
		for (PieceType type : PieceType.values()) {
			if (type.getInitial().equals(initial))
				return type;
		}
		return null;
	}

}
