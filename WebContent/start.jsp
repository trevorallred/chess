<%@page import="chess.*"%>
<% Board board = BoardBuilder.startNew(); %><%= JsonUtils.toJson(board) %>