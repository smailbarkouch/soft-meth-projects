package chess;

import java.util.ArrayList;

class Board {
    Space[][] spaces = new Space[8][8];
    boolean isWhiteTurn = true;
    boolean isGameOver = false;

    public Board() {
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                spaces[x][y] = new Space(x, y);
            }
        }

        for(int i = 0; i < 2; i++) {
            spaces[0][i * 7].piece = new Rook(i == 0);
//            spaces[1][i * 7].piece = new Knight(i == 0);
//            spaces[2][i * 7].piece = new Bishop(i == 0);
            spaces[3][i * 7].piece = new Queen(i == 0);
            spaces[4][i * 7].piece = new King(i == 0);
//            spaces[5][i * 7].piece = new Bishop(i == 0);
//            spaces[6][i * 7].piece = new Knight(i == 0);
            spaces[7][i * 7].piece = new Rook(i == 0);
        }

        for(int i = 0; i < 8; i++) {
//            spaces[i][1].piece = new Pawn(true);
        }

        for(int i = 0; i < 8; i++) {
//            spaces[i][6].piece = new Pawn(false);
        }
    }

    public ReturnPlay tryMove(String move) {
        ReturnPlay play = new ReturnPlay();
        play.piecesOnBoard = getPiecesOnBoard();

        if(isGameOver) {
            return play;
        }

        if(move.equals("resign")) {
            if(isWhiteTurn) {
                play.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
            } else {
                play.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
            }

            return play;
        }

        String[] moveParts = move.split(" ");
        boolean isDraw = moveParts.length == 3 && moveParts[2].equals("draw?");
        boolean isProm = moveParts.length == 3
                && (moveParts[2].equals("R")
                || moveParts[2].equals("B")
                || moveParts[2].equals("N")
                || moveParts[2].equals("Q"));

        if((moveParts.length != 2 && !isDraw && !isProm) || (moveParts[0].length() != 2 || moveParts[1].length() != 2)) {
            play.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return play;
        }

        int oldX = pieceFileToX(String.valueOf(moveParts[0].charAt(0)));
        int oldY = Character.getNumericValue(moveParts[0].charAt(1)) - 1;

        int newX = pieceFileToX(String.valueOf(moveParts[1].charAt(0)));
        int newY = Character.getNumericValue(moveParts[1].charAt(1)) - 1;

        if(!isPosInValidRange(oldX, oldY) || !isPosInValidRange(newX, newY)) {
            play.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return play;
        }

        BasePiece piece = spaces[oldX][oldY].piece;

        if(piece == null) {
            play.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return play;
        }

        if(piece.checkAndDoMove(this, oldX, oldY, newX, newY, moveParts.length == 3 ? moveParts[2] : "")) {
            play.piecesOnBoard = getPiecesOnBoard();
            isWhiteTurn = !isWhiteTurn;

//            System.out.println("compromises " + (isWhiteTurn ? "white" : "black") +  " king: " + isMoveCompromisingKing(isWhiteTurn) + " isCheckmate: " + isCheckmate(isWhiteTurn, newX, newY));

            play.message = isDraw ? ReturnPlay.Message.DRAW : (isMoveCompromisingKing(isWhiteTurn) ? (isCheckmate(isWhiteTurn, newX, newY) ? (isWhiteTurn ? ReturnPlay.Message.CHECKMATE_BLACK_WINS : ReturnPlay.Message.CHECKMATE_WHITE_WINS) : ReturnPlay.Message.CHECK) : null);
            isGameOver = play.message != ReturnPlay.Message.CHECK && play.message != null;

            return play;
        }

        play.message = ReturnPlay.Message.ILLEGAL_MOVE;
        return play;
    }

    public boolean isMoveCompromisingKing(boolean isWhite) {
        Space king = null;

        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                if(spaces[x][y].piece != null && spaces[x][y].piece instanceof King && spaces[x][y].piece.isWhite() == isWhite) {
                    king = spaces[x][y];
                }
            }
        }

        if(king == null) {
//            System.out.println("no king on field?!");
            return false;
        }

        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                if(spaces[x][y].piece != null && spaces[x][y].piece.canDoMove(this, x, y, king.x, king.y)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isCheckmate(boolean isWhite, int concernX, int concernY) {
        Space king = null;

        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                if(spaces[x][y].piece != null && spaces[x][y].piece instanceof King && spaces[x][y].piece.isWhite() == isWhite) {
                    king = spaces[x][y];
                }
            }
        }

        if(king == null) {
//            System.out.println("isCheckmate: no king on field?!");
            return false;
        }

        // check if king can escape threat (or capture threat themselves)
        for(int x = -1; x < 2; x++) {
            for(int y = -1; y < 2; y++) {
                if(king.piece.checkFutureMoveViable(this, king.x, king.y, king.x + x, king.y + y)) {
                    return false;
                }
                System.out.printf("checking %d %d", king.x + x, king.y + y);
            }
        }

        // check if some team piece can capture threat
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                if(spaces[x][y].piece != null && spaces[x][y].piece.isWhite() == isWhite) {
                    if(spaces[x][y].piece.canDoMove(this, x, y, concernX, concernY)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public ArrayList<ReturnPiece> getPiecesOnBoard() {
        ArrayList<ReturnPiece> piecesOnBoard = new ArrayList<ReturnPiece>();

        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                if(spaces[x][y].piece != null) {
                    ReturnPiece returnPiece = new ReturnPiece();
                    returnPiece.pieceRank = y + 1;
                    returnPiece.pieceFile = xToPieceFile(x);
                    returnPiece.pieceType = spaces[x][y].piece.pieceType;

                    piecesOnBoard.add(returnPiece);
                }
            }
        }

        return piecesOnBoard;
    }

    public ReturnPiece.PieceFile xToPieceFile(int x) {
        return switch (x) {
            case 0 -> ReturnPiece.PieceFile.a;
            case 1 -> ReturnPiece.PieceFile.b;
            case 2 -> ReturnPiece.PieceFile.c;
            case 3 -> ReturnPiece.PieceFile.d;
            case 4 -> ReturnPiece.PieceFile.e;
            case 5 -> ReturnPiece.PieceFile.f;
            case 6 -> ReturnPiece.PieceFile.g;
            case 7 -> ReturnPiece.PieceFile.h;
            default -> null;
        };
    }

    public int pieceFileToX(String pieceFile) {
        return switch(pieceFile) {
            case "a" -> 0;
            case "b" -> 1;
            case "c" -> 2;
            case "d" -> 3;
            case "e" -> 4;
            case "f" -> 5;
            case "g" -> 6;
            case "h" -> 7;
            default -> -1;
        };
    }

    public boolean isPosInValidRange(int x, int y) {
        return (x >= 0 && x <= 7) && (y >= 0 && y <= 7);
    }
}

class Space {
    int x;
    int y;

    BasePiece piece;

    public Space(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
