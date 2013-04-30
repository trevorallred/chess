package chess;

import chess.pieces.Piece;

public class BoardPrinter {

	public static String print(Board board) {
		String output = "\n";
		for (int y = 9; y >= 0; y--) {
			output = printSideNumbers(output, y);
			for (File x : File.values()) {
				if (y == 0 || y == 9) {
					output += x;
				} else {
					Piece piece = board.getPieceAtLocation(new Location(x, y));
					if (piece != null) {
						output += piece.toString();
					} else {
						output += "-";
					}
				}
				output += "\t";
			}
			output = printSideNumbers(output, y);
			output += "\n";
		}
		return output;
	}

	private static String printSideNumbers(String output, int y) {
		if (y > 0 && y < 9) {
			output += y;
		}
		output += "\t";
		return output;
	}
}
