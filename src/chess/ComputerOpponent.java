package chess;

public class ComputerOpponent {

	private Board currentState;

	public ComputerOpponent(Board board) {
		this.currentState = board;
	}

	public void calculateNextMove() {
		try {
			int randomFile = (int) Math.floor(Math.random() * 8);
			File file = File.values()[randomFile];
			currentState.move(new Location(file, 7), new Location(file, 5));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
