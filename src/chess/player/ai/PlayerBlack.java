package chess.player.ai;

import chess.Board;
import chess.Location;
import chess.pieces.Color;
import chess.pieces.Piece;
import chess.player.Move;
import chess.player.Player;

public class PlayerBlack implements Player {

	private Board board;

	public Move suggestMove(Board board) {
		this.board = board;
		
		// TODO Create AI logic
		
		return new Move(new Location("a2"), new Location("a4"));
	}

	public int score(Color color) {
		int score = 0;
		if (isCheckMated(color)) {
			return -1;
		}
		for (Piece piece : board.getPieces()) {
			if (color == piece.getColor()) {
				score += 1;
			}
		}
		return score;
	}

	// Should we move this to the Board class?
	private boolean isCheckMated(Color color) {
		if (!isChecked(color))
			return false;

		Piece myKing = board.getKing(color);
		return myKing.getNextMoves(board).size() == 0;
	}

	private boolean isChecked(Color color) {
		Piece myKing = board.getKing(color);
		for (Piece piece : board.getPieces()) {
			if (color != piece.getColor()) {
				if (canPieceMoveTo(piece, myKing.getLocation())) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean canPieceMoveTo(Piece piece, Location location) {
		// TODO Auto-generated method stub
		return false;
	}
}
