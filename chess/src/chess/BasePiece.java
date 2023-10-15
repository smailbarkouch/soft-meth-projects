package chess;

import java.util.ArrayList;

abstract class BasePiece {
    ReturnPiece.PieceType pieceType;

    public BasePiece(ReturnPiece.PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public abstract boolean canDoMove(Board board, int oldX, int oldY, int newX, int newY);
    public abstract boolean checkAndDoMove(Board board, int oldX, int oldY, int newX, int newY);

    public boolean isMoveBasicallyViable(Board board, int oldX, int oldY, int newX, int newY) {
        BasePiece piece = board.spaces[newX][newY].piece;
        boolean isLocTheSame = oldX == newX && oldY == newY;

        return ((piece == null && !isLocTheSame) ||
                (piece != null && piece.isWhite() && !this.isWhite() && !isLocTheSame));
    }

    public boolean isConsumablePieceAtLoc(Board board, int newX, int newY) {
        return board.spaces[newX][newY].piece != null && board.spaces[newX][newY].piece.isWhite() != isWhite();
    }

    public void doMove(Board board, int oldX, int oldY, int newX, int newY) {
        board.spaces[oldX][oldY].piece = null;
        board.spaces[newX][newY].piece = this;
    }

    public boolean isWhite() {
        return pieceType == ReturnPiece.PieceType.WK || pieceType == ReturnPiece.PieceType.WR || pieceType == ReturnPiece.PieceType.WQ || pieceType == ReturnPiece.PieceType.WP || pieceType == ReturnPiece.PieceType.WN || pieceType == ReturnPiece.PieceType.WB;
    }

    public boolean doesNotPassPerpAheadOfPiece(Board board, int oldX, int oldY, int newX, int newY) {
        int negativeComponent = newX == oldX && newY > oldY ? -1 :
                (newY == oldY && newX > oldX ? -1 : 1);

        for(int i = 1; i < Math.abs(oldX - newX + oldY - newY); i++) {
            if(board.spaces[newX == oldX ? newX : (newX + (negativeComponent * i))][newY == oldY ? newY : (newY + (negativeComponent * i))].piece != null) {
                return false;
            }
        }

        return true;
    }

    public boolean doesNotPassDiaAheadOfPiece(Board board, int oldX, int oldY, int newX, int newY) {
        int xNegativeComponent = newX > oldX ? 1 : -1;
        int yNegativeComponent = newY > oldY ? 1 : -1;

        for(int i = 1; i < Math.abs(oldX - newX); i++) {
            if(board.spaces[oldX + xNegativeComponent * i][oldY + yNegativeComponent * i].piece != null) {
                return false;
            }
        }

        return true;
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

class Board {
    Space[][] spaces = new Space[8][8];
    boolean isWhiteTurn = true;

    public Board() {
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                spaces[x][y] = new Space(x, y);
            }
        }

        for(int i = 0; i < 2; i++) {
            spaces[0][i * 7].piece = new Rook(i == 0);
            spaces[1][i * 7].piece = new Knight(i == 0);
            spaces[2][i * 7].piece = new Bishop(i == 0);
            spaces[3][i * 7].piece = new Queen(i == 0);
            spaces[4][i * 7].piece = new King(i == 0);
            spaces[5][i * 7].piece = new Bishop(i == 0);
            spaces[6][i * 7].piece = new Knight(i == 0);
            spaces[7][i * 7].piece = new Rook(i == 0);
        }

        for(int i = 0; i < 8; i++) {
            spaces[i][1].piece = new Pawn(true);
        }

        for(int i = 0; i < 8; i++) {
            spaces[i][6].piece = new Pawn(true);
        }
    }

    public ReturnPlay tryMove(String move) {
        ReturnPlay play = new ReturnPlay();
        play.piecesOnBoard = getPiecesOnBoard();

        if(move.equals("resign")) {
            if(isWhiteTurn) {
                play.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
            } else {
                play.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
            }

            return play;
        }


        String[] moveParts = move.split(" ");
        if(moveParts.length != 2 || (moveParts[0].length() != 2 || moveParts[1].length() != 2)) {
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

        if(piece.checkAndDoMove(this, oldX, oldY, newX, newY)) {
            play.piecesOnBoard = getPiecesOnBoard();
            return play;
        }

        play.message = ReturnPlay.Message.ILLEGAL_MOVE;
        return play;
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
