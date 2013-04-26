package chess;

import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import chess.pieces.Color;
import chess.pieces.Piece;
import chess.pieces.PieceType;

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
		json.put(PIECE_TYPE, piece.getType().toString());
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
		PieceType type = PieceType.valueOf((String) pieceObj.get("type"));
		Color color = Color.valueOf((String) pieceObj.get("color"));
		File file = File.valueOf(locationObj.substring(0, 1));
		int y = Integer.parseInt(locationObj.substring(1, 2));
		Location location = new Location(file, y);
		return new Piece(type, color, location);
	}
}
