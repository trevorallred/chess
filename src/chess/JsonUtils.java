package chess;

import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import chess.pieces.Bishop;
import chess.pieces.Color;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Piece;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

@SuppressWarnings("unchecked")
public class JsonUtils {
	private static final String PIECE_COLOR = "color";
	private static final String PIECE_TYPE = "type";
	private static final String PIECE_LOCATION = "location";
	private static final String PIECE_LOCATION_NAME = "name";
	private static final String PIECE_LOCATION_X = "x";
	private static final String PIECE_LOCATION_Y = "y";
	private static final String PIECE_NEXT_MOVES = "next_moves";
	private static final String MOVE = "move";
	private static final String MOVE_FROM = "from";
	private static final String MOVE_TO = "to";

	public static JSONArray toJson(Board board) {
		Map<Piece, Set<Location>> nextMoves = board.getNextMoves();
		JSONArray json = new JSONArray();
		for (Piece piece : board.getPieces()) {
			json.add(toJsonPiece(piece, nextMoves.get(piece)));
		}
		return json;
	}

	private static JSONObject toJsonPiece(Piece piece, Set<Location> nextMoves) {
		JSONObject json = new JSONObject();
		json.put(PIECE_COLOR, piece.getColor().toString());
		json.put(PIECE_TYPE, piece.getClass().getSimpleName());
		json.put(PIECE_LOCATION, toJsonLocation(piece.getLocation()));
		if (piece.getColor() == Color.White) {
			json.put(PIECE_NEXT_MOVES, toJson(nextMoves));
		}
		return json;
	}

	private static JSONArray toJson(Set<Location> nextMoves) {
		JSONArray json = new JSONArray();
		if (nextMoves != null) {
			for (Location location : nextMoves) {
				json.add(location.toString());
			}
		}
		return json;
	}

	private static JSONObject toJsonLocation(Location location) {
		JSONObject json = new JSONObject();
		json.put(PIECE_LOCATION_NAME, location.toString());
		json.put(PIECE_LOCATION_X, location.getX().ordinal() + 1);
		json.put(PIECE_LOCATION_Y, location.getY());
		return json;
	}

	public static Board fromJson(String json) throws Exception {
		JSONObject jsonObj = (JSONObject) JSONValue.parse(json);
		JSONObject jsonBoard = (JSONObject) jsonObj.get("board");
		Board board = new Board();
		for (Object locationObj : jsonBoard.keySet()) {
			JSONObject pieceObj = (JSONObject) jsonBoard.get(locationObj);
			Piece piece = createPiece(pieceObj, (String) locationObj);
			board.getPieces().add(piece);
		}
		JSONObject move = (JSONObject) jsonObj.get(MOVE);
		Location fromLocation = new Location((String) move.get(MOVE_FROM));
		Location toLocation = new Location((String) move.get(MOVE_TO));
		board.move(fromLocation, toLocation);
		return board;
	}

	private static Piece createPiece(JSONObject pieceObj, String locationObj) {
		Piece piece;

		String type = (String) pieceObj.get("type");
		Color color = Color.valueOf((String) pieceObj.get("color"));

		if ("King".equals(type)) {
			piece = new King(color);
		} else if ("Queen".equals(type)) {
			piece = new Queen(color);
		} else if ("Bishop".equals(type)) {
			piece = new Bishop(color, File.a);
		} else if ("Rook".equals(type)) {
			piece = new Rook(color, File.a);
		} else if ("Knight".equals(type)) {
			piece = new Knight(color, File.a);
		} else {
			piece = new Pawn(color, File.a);
		}
		File file = File.valueOf(locationObj.substring(0, 1));
		int y = Integer.parseInt(locationObj.substring(1, 2));
		piece.setLocation(new Location(file, y));

		System.out.println("Created " + piece.toString() + " at " + piece.getLocation());
		return piece;
	}
}
