<%@page import="chess.GameController"%>
<%
	String[] values = request.getParameterValues("json");
	String json = null;
	if (values != null && values.length > 0) {
		json = values[0];
	}
	GameController controller = new GameController(json);
%><%=controller.getBoard()%>