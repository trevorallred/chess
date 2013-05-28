package chess.engine;

public enum File {
    a, b, c, d, e, f, g, h;

    public File move(int x) {
        int ordinal = this.ordinal() + x;
        if (ordinal < 0)
            return null;
        if (ordinal >= File.values().length)
            return null;
        return File.values()[ordinal];
    }

    public int getX() {
        return ordinal() + 1;
    }

}
