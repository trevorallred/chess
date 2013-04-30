<%@page import="chess.*"%>
<% Board board = BoardBuilder.build().addAll().getBoard(); %><%= JsonUtils.toJson(board) %>