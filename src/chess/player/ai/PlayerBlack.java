package chess.player.ai;

import chess.Board;
import chess.Location;
import chess.pieces.Color;
import chess.pieces.Piece;
import chess.pieces.PieceMover;
import chess.player.Move;
import chess.player.Player;

public class PlayerBlack implements Player {

	private Board board;

	public Move suggestMove(Board board) {
		this.board = board;
		return startAnalysis();
	}

	private Move startAnalysis() {
		PieceMover mover = new PieceMover(board);
		for (Piece piece : board.getPieces()) {
			if (isFriend(piece)) {
				for (Location target : mover.getNextMoves(piece)) {
					return new Move(piece.getLocation(), target);
				}
			}
		}
		return null;
	}

	private boolean isFriend(Piece piece) {
		return piece.getColor() == Color.Black;
	}

	public int score(Color color) {
		int score = 0;
		if (board.isCheckMated(color)) {
			return -1;
		}
		for (Piece piece : board.getPieces()) {
			if (color == piece.getColor()) {
				score += getPieceValue(piece);
			}
		}
		return score;
	}

	private int getPieceValue(Piece piece) {
		switch (piece.getType()) {
		case King:
			return 7;
		case Queen:
			return 27;
		case Bishop:
			return 7;
		case Rook:
			return 14;
		case Knight:
			return 8;
		}
		return 2;
	}

}
