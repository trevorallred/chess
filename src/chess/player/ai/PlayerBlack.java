package chess.player.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chess.Board;
import chess.BoardPrinter;
import chess.Location;
import chess.pieces.Color;
import chess.pieces.Piece;
import chess.pieces.PieceMover;
import chess.player.Move;
import chess.player.Player;

public class PlayerBlack implements Player {

	private Board board;
	private Color myColor = Color.Black;

	public Move suggestMove(Board board, Color color) {
		this.board = board;
		this.myColor = color;
		return startAnalysis();
	}

	private Move startAnalysis() {
		System.out.println(BoardPrinter.print(board));
		PieceMover mover = new PieceMover(board);
		int whiteBefore = score(board, Color.White);
		// int blackBefore = score(board, Color.Black);

		Map<String, Move> moves = new HashMap<String, Move>();
		List<Move> bestMoves = new ArrayList<Move>();
		int bestScore = -100000;
		for (Piece piece : board.getPieces()) {
			if (isFriend(piece)) {
				for (Location target : mover.getNextMoves(piece)) {
					try {
						System.out.println("Simulating moves for " + piece + " to " + target);
						Move move = new Move(piece.getLocation(), target);
						Board simulation = board.clone();
						PieceMover simulator = new PieceMover(simulation);
						simulator.move(move);
						int whiteAfter = score(simulation, Color.White);
						// int blackAfter = score(simulation, Color.Black);

						int damage = whiteBefore - whiteAfter;
						if (damage > bestScore) {
							bestScore = damage;
							bestMoves.clear();
						}
						if (damage == bestScore) {
							bestMoves.add(move);
						}
						System.out.println("damageDone = " + damage);

					} catch (Exception e) {
					}
				}
			}
		}
		if (bestMoves.size() == 0)
			return null;
		// TODO Randomize
		return bestMoves.get(0); 
	}

	private boolean isFriend(Piece piece) {
		return piece.getColor() == myColor;
	}

	static public int score(Board board, Color color) {
		int score = 0;

		// if (board.isCheckMated(color)) {
		// return -1;
		// }
		for (Piece piece : board.getPieces()) {
			if (color == piece.getColor()) {
				score += getPieceValue(piece);
			}
		}
		return score;
	}

	static private int getPieceValue(Piece piece) {
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
