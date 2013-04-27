package chess.player;

import chess.Board;
import chess.pieces.Color;

public interface Player {
	Move suggestMove(Board board, Color black);
}
