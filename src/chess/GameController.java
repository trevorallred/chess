package chess;

import org.json.simple.JSONObject;

import chess.player.Move;
import chess.player.Player;
import chess.player.ai.PlayerBlack;

public class GameController {

	private Board board;
	private String errorMessage;
	private Player opponent;

	public GameController(String json) {
		// We could inject the Opponent Player here
		opponent = new PlayerBlack();
		try {
			board = BoardBuilder.move(json);
			opponentTurn();
		} catch (Exception e) {
			board = null;
			errorMessage = e.getMessage();
		}
	}

	private void opponentTurn() throws Exception {
		Move move = opponent.suggestMove(board);
		board.move(move.getFrom(), move.getTo());
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
