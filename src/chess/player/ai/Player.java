package chess.player.ai;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chess.Board;
import chess.Location;
import chess.pieces.Color;
import chess.pieces.Piece;
import chess.pieces.PieceMover;
import chess.player.Move;

public class Player {

	Logger logger = LoggerFactory.getLogger(Player.class);

	private Board board;
	private Color color = Color.Black;
	private int intelligence = 3;

	public Player(Color color) {
		this.color = color;
	}

	public Player(Color color, int intelligence) {
		this.color = color;
		this.intelligence = intelligence;
	}

	public Move suggestMove(Board board) {
		this.board = board;
		return startAnalysis();
	}

	private Move startAnalysis() {
		logger.debug("Starting Analysis for {} with Level {} Intelligence", color, intelligence);
		board.log();
		PieceMover mover = new PieceMover(board);
		int scoreBefore = score(board);

		Player opponent = new Player(color.getOpposite(), intelligence - 1);

		List<Move> bestMoves = new ArrayList<Move>();
		int bestScore = -100000;
		for (Piece piece : board.getPieces()) {
			if (isFriend(piece)) {
				for (Location target : mover.getNextMoves(piece)) {
					try {
						logger.debug("Simulating moves for {} to {}", piece, target);
						int damage = 0;
						Move move = new Move(piece.getLocation(), target);
						Board simulation;
						{
							simulation = board.clone();
							PieceMover simulator = new PieceMover(simulation);
							simulator.move(move);
							if (simulator.isChecked(color)) {
								logger.debug("The {} King can't move into or remain in Check", color);
								break;
							}
							simulation.log();
							damage = scoreBefore - score(simulation);
						}

						if (intelligence > 0) {
							Move counterMove = opponent.suggestMove(simulation);
							Board counterSimulation = simulation.clone();
							PieceMover simulator = new PieceMover(counterSimulation);
							simulator.move(counterMove);
							damage = scoreBefore - score(counterSimulation);
						}
						logger.debug("Calculated a change in score of {}", damage);

						if (damage > bestScore) {
							bestScore = damage;
							bestMoves.clear();
						}

						if (damage == bestScore) {
							bestMoves.add(move);
						}

					} catch (Exception e) {
						logger.debug(e.getMessage());
					}
				}
			}
		}
		return pickBestMove(bestMoves);
	}

	private Move pickBestMove(List<Move> bestMoves) {
		if (bestMoves.size() == 0) {
			logger.warn("Failed to find any possible moves for {}", color);
			return null;
		}
		if (bestMoves.size() == 1) {
			logger.info("Returning best move for {}: {}", color, bestMoves.get(0));
			return bestMoves.get(0);
		}
		int randomMove = (int) Math.floor(Math.random() * bestMoves.size());
		logger.info("Returning random move for {}: {}", color, bestMoves.get(randomMove));
		return bestMoves.get(randomMove);
	}

	private boolean isFriend(Piece piece) {
		return piece.getColor() == color;
	}

	private int score(Board board) {
		int score = score(board, Color.White) - score(board, Color.Black);
		return color == Color.White ? score : score * -1;
	}

	static private int score(Board board, Color color) {
		int score = 0;

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
