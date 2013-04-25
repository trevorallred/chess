package chess;

import java.util.HashSet;
import java.util.Set;

import chess.pieces.Bishop;
import chess.pieces.Color;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Piece;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

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
		Piece piece;
		String type = data.substring(0, 1);

		if ("K".equals(type)) {
			piece = new King(color);
		} else if ("Q".equals(type)) {
			piece = new Queen(color);
		} else if ("B".equals(type)) {
			piece = new Bishop(color, File.a);
		} else if ("R".equals(type)) {
			piece = new Rook(color, File.a);
		} else if ("N".equals(type)) {
			piece = new Knight(color, File.a);
		} else {
			piece = new Pawn(color, File.a);
		}
		File file = File.valueOf(data.substring(1, 2));
		int y = Integer.parseInt(data.substring(2, 3));
		piece.setLocation(new Location(file, y));
		return piece;
	}

	private static Set<Piece> addPieces(Color color) {
		Set<Piece> pieces = new HashSet<Piece>();
		pieces.add(new King(color));
		pieces.add(new Queen(color));
		pieces.add(new Rook(color, File.a));
		pieces.add(new Rook(color, File.h));
		pieces.add(new Bishop(color, File.c));
		pieces.add(new Bishop(color, File.f));
		pieces.add(new Knight(color, File.b));
		pieces.add(new Knight(color, File.g));
		for (File x : File.values()) {
			pieces.add(new Pawn(color, x));
		}
		return pieces;
	}

	public static Board move(String json) throws Exception {
		return JsonUtils.fromJson(json);
	}
}
