<%@page import="chess.*"%>
<%@ page import="chess.engine.Board" %>
<%@ page import="chess.engine.BoardBuilder" %>
<% Board board = BoardBuilder.build().addAll().getBoard(); %><%= JsonUtils.toJson(board) %>