package chess;

import static org.junit.Assert.assertEquals;

import org.json.simple.JSONValue;
import org.junit.Ignore;
import org.junit.Test;

public class JsonUtilsTest {
	@Test
	@Ignore
	public void testBoardJson() throws Exception {
		Board board = BoardBuilder.startNew();
		String expectedString = "{\"f7\":\"B:P\",\"f8\":\"B:B\",\"h8\":\"B:R\",\"b2\":\"W:P\",\"d8\":\"B:Q\","
				+ "\"b7\":\"B:P\",\"d7\":\"B:P\",\"b8\":\"B:N\",\"d1\":\"W:Q\",\"d2\":\"W:P\",\"b1\":\"W:N\","
				+ "\"a1\":\"W:R\",\"a2\":\"W:P\",\"f1\":\"W:B\",\"h7\":\"B:P\",\"h1\":\"W:R\",\"f2\":\"W:P\","
				+ "\"a7\":\"B:P\",\"h2\":\"W:P\",\"a8\":\"B:R\",\"g7\":\"B:P\",\"g8\":\"B:N\",\"e8\":\"B:K\","
				+ "\"e7\":\"B:P\",\"c8\":\"B:B\",\"c7\":\"B:P\",\"e1\":\"W:K\",\"e2\":\"W:P\",\"c1\":\"W:B\","
				+ "\"c2\":\"W:P\",\"g2\":\"W:P\",\"g1\":\"W:N\"}";
		assertEquals(JSONValue.parse(expectedString), JsonUtils.toJson(board));
	}
}
