package chess.pieces;

import chess.Board;
import chess.Location;
import chess.player.Move;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PieceMover {
    Logger logger = LoggerFactory.getLogger(PieceMover.class);
    private Board board;

    public PieceMover(Board board) {
        this.board = board;
    }

    public void move(Move move) throws Exception {
        Piece attacker = board.getPieceAtLocation(move.getFrom());
        if (attacker == null) {
            throw new Exception("No piece found on board at location " + move.getFrom());
        }
        Piece defender = board.getPieceAtLocation(move.getTo());
        board.kill(defender);
        attacker.setLocation(move.getTo());
    }

    private boolean canPieceMoveTo(Piece piece, Location toLocation) {
        Set<Location> nextMoves = getNextMoves(piece);
        for (Location location : nextMoves) {
            if (toLocation.equals(location))
                return true;
        }
        return nextMoves.contains(toLocation);
    }

    public Map<Piece, Set<Location>> getNextMoves(Color color) {
        Map<Piece, Set<Location>> allNextMoves = new HashMap<Piece, Set<Location>>();
        for (Piece piece : board.getPieces()) {
            if (piece.getColor() == color) {
                Set<Location> nextMoves = getNextMovesNotResultingInCheckmate(piece);
                if (nextMoves.size() > 0) {
                    allNextMoves.put(piece, nextMoves);
                }
            }
        }
        return allNextMoves;
    }

    public Set<Location> getNextMoves(Piece piece) {
        logger.debug("Getting moves for {}", piece);
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
                addKnightMove(piece, nextMoves, 1, 2);
                addKnightMove(piece, nextMoves, 1, -2);
                addKnightMove(piece, nextMoves, -1, 2);
                addKnightMove(piece, nextMoves, -1, -2);
                addKnightMove(piece, nextMoves, 2, 1);
                addKnightMove(piece, nextMoves, 2, -1);
                addKnightMove(piece, nextMoves, -2, 1);
                addKnightMove(piece, nextMoves, -2, -1);
                break;
            case Pawn:
                moveForward(piece, nextMoves);
                attackWithPawn(piece, nextMoves, Direction.TopRight);
                attackWithPawn(piece, nextMoves, Direction.TopLeft);
                break;
        }

        removeInvalidMoves(piece, nextMoves);

        return nextMoves;
    }

    private void addKnightMove(Piece piece, Set<Location> nextMoves, int x, int y) {
        Location nextLocation = new Location(piece.getLocation(), x, y);
        if (whatIsAtLocation(piece.getColor(), nextLocation) == PieceTypeAtLocation.Friend) {
            return;
        }
        nextMoves.add(nextLocation);
    }

    private void attackWithPawn(Piece piece, Set<Location> nextMoves, Direction direction) {
        int directionX = direction.getX(piece.getColor());
        int directionY = direction.getY(piece.getColor());
        Location nextLocation = new Location(piece.getLocation(), directionX, directionY);
        if (whatIsAtLocation(piece.getColor(), nextLocation) == PieceTypeAtLocation.Foe) {
            nextMoves.add(nextLocation);
        }
    }

    private void moveForward(Piece piece, Set<Location> nextMoves) {
        int directionY = Direction.Up.getY(piece.getColor());
        Location nextLocation = new Location(piece.getLocation(), 0, directionY);
        if (whatIsAtLocation(piece.getColor(), nextLocation) == PieceTypeAtLocation.Empty) {
            if (nextMoves.add(nextLocation)) {
                if (piece.getLocation().getY() == piece.getColor().getSecondRow()) {
                    Location secondLocation = new Location(piece.getLocation(), 0, 2 * directionY);
                    if (whatIsAtLocation(piece.getColor(), secondLocation) == PieceTypeAtLocation.Empty) {
                        nextMoves.add(secondLocation);
                    }
                }
            }
        }
    }

    private void removeInvalidMoves(Piece piece, Set<Location> nextMoves) {
        logger.debug("Found {} possible moves for {}...determining which if any are invalid", nextMoves.size(), piece);
        Iterator<Location> iterator = nextMoves.iterator();
        while (iterator.hasNext()) {
            Location location = iterator.next();
            if (location == null || !location.isValid()) {
                logger.debug("  removing move since it's out of bounds");
                iterator.remove();
            }
        }
    }

    public Set<Location> getNextMovesNotResultingInCheckmate(Piece piece) {
        Set<Location> nextMoves = getNextMoves(piece);
        Iterator<Location> iterator = nextMoves.iterator();
        while (iterator.hasNext()) {
            Location location = iterator.next();
            if (isKingCheckMatedAfterMove(piece, location)) {
                logger.debug("  King can't move to {} since it would be in Checkmate", location);
                iterator.remove();
            }
        }
        return nextMoves;
    }

    private boolean isKingCheckMatedAfterMove(Piece piece, Location location) {
        logger.debug("  Simulating move {} to {}", piece, location);
        Board simulation = board.clone();
        PieceMover mover = new PieceMover(simulation);
        Move move = new Move(piece.getLocation(), location);
        try {
            mover.move(move);
        } catch (Exception e) {
            return true;
        }
        return mover.isChecked(piece.getColor());
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
            // System.out.println("No piece at " + location);
            return PieceTypeAtLocation.Empty;
        }

        if (friendColor == target.getColor()) {
            // System.out.println("Team mate " + target + " at " + location);
            return PieceTypeAtLocation.Friend;
        } else {
            // System.out.println("Opponent " + target + " at " + location);
            return PieceTypeAtLocation.Foe;
        }
    }

    public boolean isChecked(Color color) {
        logger.debug("  Is the {} King Checkmated?", color);
        Piece king = board.getKing(color);
        if (king == null) {
            logger.debug("  The king can't be in check since it's not on the board");
            return false;
        }
        for (Piece piece : board.getPieces()) {
            if (color != piece.getColor()) {
                if (canPieceMoveTo(piece, king.getLocation())) {
                    return true;
                }
            }
        }
        logger.debug("  It's still safe");
        return false;
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

        int getX(Color color) {
            return color == Color.White ? x : x * -1;
        }

        int getY(Color color) {
            return color == Color.White ? y : y * -1;
        }
    }

}
