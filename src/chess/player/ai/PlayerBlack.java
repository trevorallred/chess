package chess.player.ai;

import java.util.ArrayList;
import java.util.List;

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
		int scoreBefore = score(board);

		List<Move> bestMoves = new ArrayList<Move>();
		int bestScore = -100000;
		for (Piece piece : board.getPieces()) {
			if (isFriend(piece)) {
				for (Location target : mover.getNextMoves(piece)) {
					try {
						System.out.println("Simulating moves for " + piece + " to " + target);
						Move move = new Move(piece.getLocation(), target);
						Board simulation = simulateMovingPiece(move);
						System.out.println(BoardPrinter.print(simulation));
						int damage = scoreBefore - score(simulation);

						if (damage > bestScore) {
							bestScore = damage;
							bestMoves.clear();
						}

						if (damage == bestScore) {
							bestMoves.add(move);
						}

					} catch (Exception e) {
					}
				}
			}
		}
		return pickBestMove(bestMoves);
	}

	private Board simulateMovingPiece(Move move) throws Exception {
		Board simulation = board.clone();
		PieceMover simulator = new PieceMover(simulation);
		simulator.move(move);
		return simulation;
	}

	private static Move pickBestMove(List<Move> bestMoves) {
		if (bestMoves.size() == 0)
			return null;
		if (bestMoves.size() == 1)
			return bestMoves.get(0);
		int randomMove = (int) Math.floor(Math.random() * bestMoves.size());
		return bestMoves.get(randomMove);
	}

	private boolean isFriend(Piece piece) {
		return piece.getColor() == myColor;
	}

	private static int score(Board board) {
		return score(board, Color.White) - score(board, Color.Black);
	}

	static public int score(Board board, Color color) {
		int score = 0;

		PieceMover mover = new PieceMover(board);

		if (mover.isChecked(color)) {
			score -= 100;
		}
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
			return 40;
		case Queen:
			return 9;
		case Bishop:
			return 3;
		case Rook:
			return 5;
		case Knight:
			return 3;
		case Pawn:
			return 1;
		}
		return 1;
	}

}
