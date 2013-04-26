package chess;

import java.util.HashSet;
import java.util.Set;

import chess.pieces.Color;
import chess.pieces.Piece;
import chess.pieces.PieceType;

public class BoardBuilder {

	public static Board startNew() {
		Board board = new Board();
		board.getPieces().addAll(addPieces(Color.White));
		board.getPieces().addAll(addPieces(Color.Black));
		return board;
	}

	public static Board build(String whitePieces, String blackPieces) {
		Board board = new Board();
		board.getPieces().addAll(parsePieces(whitePieces, Color.White));
		board.getPieces().addAll(parsePieces(blackPieces, Color.Black));
		return board;
	}

	private static Set<Piece> parsePieces(String pieceData, Color color) {
		Set<Piece> pieces = new HashSet<Piece>();
		if (pieceData == null || pieceData.isEmpty())
			return pieces;

		for (String singlePieceData : pieceData.split(",")) {
			pieces.add(createPiece(singlePieceData, color));
		}
		return pieces;
	}

	private static Piece createPiece(String data, Color color) {
		PieceType type = PieceType.valueOf(data.substring(0, 1));
		File file = File.valueOf(data.substring(1, 2));
		int y = Integer.parseInt(data.substring(2, 3));
		Location location = new Location(file, y);
		return new Piece(type, color, location);
	}

	private static Set<Piece> addPieces(Color color) {
		int row1 = (color == Color.White ? 1 : 8);
		int row2 = (color == Color.White ? 2 : 7);
		
		Set<Piece> pieces = new HashSet<Piece>();
		pieces.add(new Piece(PieceType.Rook, color, new Location(File.a, row1)));
		pieces.add(new Piece(PieceType.Knight, color, new Location(File.b, row1)));
		pieces.add(new Piece(PieceType.Bishop, color, new Location(File.c, row1)));
		pieces.add(new Piece(PieceType.Queen, color, new Location(File.d, row1)));
		pieces.add(new Piece(PieceType.King, color, new Location(File.e, row1)));
		pieces.add(new Piece(PieceType.Bishop, color, new Location(File.f, row1)));
		pieces.add(new Piece(PieceType.Knight, color, new Location(File.g, row1)));
		pieces.add(new Piece(PieceType.Rook, color, new Location(File.h, row1)));
		for (File x : File.values()) {
			pieces.add(new Piece(PieceType.Pawn, color, new Location(x, row2)));
		}
		return pieces;
	}

	public static Board move(String json) throws Exception {
		return JsonUtils.fromJson(json);
	}
}
