package chess.engine;

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
        if (type == PieceType.Pawn) {
            if (location.getY() == color.getLastRow()) {
                type = PieceType.Queen;
            }
        }
    }

    public Piece clone() {
        return new Piece(type, color, location);
    }

    public String toString() {
        return color.toString().charAt(0) + type.getInitial();
    }
}
