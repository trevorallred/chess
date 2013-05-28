package chess.engine;

public class Location implements Comparable<Location> {
    final private File x;
    final private int y;

    public Location(String location) {
        if (location.length() != 2) {
            throw new RuntimeException("Location(String) must be 2 characters");
        }
        x = File.valueOf(location.substring(0, 1));
        y = Integer.parseInt(location.substring(1, 2));
    }

    public Location(int x, int y) {
        this.x = File.values()[x - 1];
        this.y = y;
    }

    public Location(File x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location(Location location, int moveX, int moveY) {
        this.x = location.x.move(moveX);
        this.y = location.y + moveY;
    }

    public File getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAt(File x, int y) {
        return (this.x == x && this.y == y);
    }

    public boolean isAt(Location location) {
        return isAt(location.x, location.y);
    }

    public boolean isValid() {
        if (x == null)
            return false;
        if (y < 1 || y > 8)
            return false;
        return true;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "off board";
        }
        return x.toString() + y;
    }

    @Deprecated
    /**
     * Use new Location(location, moveX, moveY)
     */
    public Location move(int moveX, int moveY) {
        return new Location(this, moveX, moveY);
    }

    public boolean equals(Location obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        return (this.x == obj.x && this.y == obj.y);
    }

    public int compareTo(Location o) {
        if (this.y != o.y)
            return this.y - o.y;

        return this.x.ordinal() - o.x.ordinal();
    }
}
