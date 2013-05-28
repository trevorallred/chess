package chess;

import org.json.simple.JSONObject;

import chess.pieces.Color;
import chess.pieces.PieceMover;
import chess.player.Move;
import chess.player.ai.Player;

public class GameController {

	private Board board;
	private PieceMover mover;
	private String errorMessage;
	private Player opponent;

	public GameController(String json) {
		// We could inject the Opponent Player here
		opponent = new Player(Color.Black);
		try {
			board = JsonUtils.fromJson(json);
			// TODO Should we try to check if the move is valid here? PieceMover.canPieceMoveTo()
			mover = new PieceMover(board);
			opponentTurn();
		} catch (Exception e) {
			board = null;
			errorMessage = e.getMessage();
		}
	}

	private void opponentTurn() throws Exception {
		Move move = opponent.suggestMove(board);
		mover.move(move);
	}

	@SuppressWarnings("unchecked")
	public String getBoard() {
		if (board == null) {
			JSONObject json = new JSONObject();
			json.put("message", errorMessage);
			return json.toString();
		}
		return JsonUtils.toJson(board).toString();
	}
}
