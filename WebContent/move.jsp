<%@page import="chess.*"%>
<%
	String[] values = request.getParameterValues("json");
	String json = null;
	if (values != null && values.length > 0) {
		json = values[0];
	}

	Board board = BoardBuilder.move(json);
	ComputerOpponent opponent = new ComputerOpponent(board);
	opponent.calculateNextMove();
%><%=JsonUtils.toJson(board)%>