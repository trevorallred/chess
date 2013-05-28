var CHESS = new Object();

$(document).ready(function () {
    CHESS.board.div = $("#board");

    $(document).on("click", "div.moveable", function (e) {
        CHESS.board.choose(this);
    });

    $(document).on("click", "div.destination", function (e) {
        CHESS.board.move(this);
    });

    $("#startGame").click(function () {
        CHESS.board.startNew();
    });

    CHESS.board.draw();
    CHESS.board.startNew();
});

CHESS.board = {
    div: null,
    round: 0,
    myTurn: true,
    piece_to_move: null,
    pieces: {},
    drawScore: function () {
        var color = CHESS.myTurn ? "White" : "Black";
        var html = "<div class='space " + color + "Piece'><div class='piece'>" + CHESS.unicode("King") + "</div></div>";
        $("#turn").html(html);
        $("#round").html(CHESS.board.round);
    },
    draw: function () {
        var toggle = false;
        for (y = 8; y >= 1; y--) {
            for (x = 1; x <= 8; x++) {
                var file = String.fromCharCode(x + 96);
                spaceID = file + y;
                var piece = CHESS.board.pieces[spaceID];
                var moveable = (piece != null && piece.color == "White");

                var html = "<div id='" + spaceID + "'";
                html += " class='space " + (toggle ? "light" : "dark")
                    + (moveable ? " moveable " : " ")
                    + (piece == null ? "" : piece.color + "Piece") + "'";
                html += " title='" + spaceID + "'";
                html += ">";
                if (piece != null) {
                    // console.log(piece);
                    html += "<div class='piece'>" + piece.unicode + "</div>";
                }
                html += "</div>";
                $("#" + spaceID).remove();
                CHESS.board.div.append(html);
                toggle = !toggle;
            }
            toggle = !toggle;
        }
        CHESS.board.drawScore();
    },
    load: function (data) {
        CHESS.board.pieces = {};

        $.each(data, function (key, piece) {
            piece.unicode = CHESS.unicode(piece.type);
            CHESS.board.pieces[piece.location.name] = piece;
        });
        CHESS.board.draw();
    },
    startNew: function () {
        if (CHESS.board.round > 1 && !confirm("Press OK to start a new game")) {
            return;
        }
        $.getJSON('start.jsp', function (data) {
            CHESS.board.round = 1;
            CHESS.board.load(data);
            CHESS.myTurn = true;
            CHESS.board.drawScore();
        });
    },
    choose: function (div) {
        if (!CHESS.myTurn) {
            return;
        }
        $("div").removeClass("selected");
        $("div").removeClass("destination");
        $(div).addClass("selected");

        var spaceID = $(div).attr('id');
        CHESS.board.piece_to_move = CHESS.board.pieces[spaceID];

        $.each(CHESS.board.piece_to_move.next_moves, function (key, target) {
            $("#" + target).addClass("destination");
        });
    },
    move: function (div) {
        CHESS.board.round++;
        CHESS.myTurn = false;
        CHESS.board.drawScore();

        $("div").removeClass("selected");
        $("div").removeClass("destination");
        var spaceID = $(div).attr('id');
        // console.log("Move " + CHESS.board.piece_to_move.location.name + " -> " + spaceID);

        var data = {
            "board": CHESS.board.pieces,
            "move": {
                "from": CHESS.board.piece_to_move.location.name,
                "to": spaceID
            }
        };

        $.ajax({
            type: "POST",
            dataType: "json",
            url: 'move.jsp',
            data: {
                json: JSON.stringify(data)
            },
            success: function (data) {
                CHESS.myTurn = true;
                CHESS.board.piece_to_move = null;
                CHESS.board.load(data);
            }
        });

        // console.log($(div).children());
        var starting = $("#" + CHESS.board.piece_to_move.location.name).offset();
        var ending = $(div).offset();
        var move_animation = {};
        move_animation.top = (ending.top - starting.top) + "px";
        move_animation.left = (ending.left - starting.left) + "px";

        $("#" + CHESS.board.piece_to_move.location.name).children().animate(move_animation, 1000, "linear");
    }
};

CHESS.unicode = function (type) {
    switch (type) {
        case "King":
            return "&#9818;";
        case "Queen":
            return "&#9819;";
        case "Rook":
            return "&#9820;";
        case "Bishop":
            return "&#9821;";
        case "Knight":
            return "&#9822;";
        case "Pawn":
            return "&#9823;";
        default:
            return "&nbsp;";
    }
};
