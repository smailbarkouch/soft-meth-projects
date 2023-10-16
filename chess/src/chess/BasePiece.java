package chess;

abstract class BasePiece {
    ReturnPiece.PieceType pieceType;
    boolean isInOGPos = true;

    public BasePiece(ReturnPiece.PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public abstract boolean canDoMove(Board board, int oldX, int oldY, int newX, int newY);
    public boolean checkAndDoMove(Board board, int oldX, int oldY, int newX, int newY, String prom) {
        if(board.isWhiteTurn != board.spaces[oldX][oldY].piece.isWhite()) {
//            System.out.println("this why 1");
            return false;
        }

        if(oldX == newX && oldY == newY) {
            return false;
        }

        if(doEmpassantIfViable(board, oldX, oldY, newX, newY)) {
            return true;
        }

        if(doCastlingMoveIfViable(board, oldX, oldY, newX, newY)) {
            return true;
        }

        if(canDoMove(board, oldX, oldY, newX, newY)) {
            BasePiece oldPiece = board.spaces[oldX][oldY].piece;
            BasePiece newPiece = board.spaces[newX][newY].piece;

            setPiece(board, oldX, oldY, null);
            setPiece(board, newX, newY, oldPiece);

            if(board.isMoveCompromisingKing(board.isWhiteTurn)) {
//                System.out.println("this why 2");
                setPiece(board, oldX, oldY, oldPiece);
                setPiece(board, newX, newY, newPiece);

                return false;
            }

            promoteIfViable(board, newX, newY, prom);
            isInOGPos = false;

            return true;
        }

//        System.out.println("this why 3");

        return false;
    }

    public boolean checkFutureMoveViable(Board board, int oldX, int oldY, int newX, int newY) {
        if(board.isWhiteTurn != board.spaces[oldX][oldY].piece.isWhite()) {
            return false;
        }

        if(oldX == newX && oldY == newY) {
            return false;
        }

        if(canDoMove(board, oldX, oldY, newX, newY)) {
            BasePiece oldPiece = board.spaces[oldX][oldY].piece;
            BasePiece newPiece = board.spaces[newX][newY].piece;

            setPiece(board, oldX, oldY, null);
            setPiece(board, newX, newY, oldPiece);

            boolean checks = board.isMoveCompromisingKing(board.isWhiteTurn);

            setPiece(board, oldX, oldY, oldPiece);
            setPiece(board, newX, newY, newPiece);

            return !checks;
        }

        return false;
    }

    public boolean isMoveBasicallyViable(Board board, int oldX, int oldY, int newX, int newY) {
        if(newX < 0 || newX > 7 || newY < 0 || newY > 7) {
            return false;
        }

        BasePiece piece = board.spaces[newX][newY].piece;
        boolean isLocTheSame = oldX == newX && oldY == newY;


        return ((piece == null && !isLocTheSame) ||
                (piece != null && piece.isWhite() != this.isWhite() && !isLocTheSame));
    }

    public boolean isConsumablePieceAtLoc(Board board, int newX, int newY) {
        return board.spaces[newX][newY].piece != null && board.spaces[newX][newY].piece.isWhite() != isWhite();
    }

    public void setPiece(Board board, int x, int y, BasePiece piece) {
        board.spaces[x][y].piece = piece;
    }

    public boolean isWhite() {
        return pieceType == ReturnPiece.PieceType.WK || pieceType == ReturnPiece.PieceType.WR || pieceType == ReturnPiece.PieceType.WQ || pieceType == ReturnPiece.PieceType.WP || pieceType == ReturnPiece.PieceType.WN || pieceType == ReturnPiece.PieceType.WB;
    }

    public boolean doesNotPassPerpAheadOfPiece(Board board, int oldX, int oldY, int newX, int newY) {
        int negativeComponent = newX == oldX && newY > oldY ? -1 :
                (newY == oldY && newX > oldX ? -1 : 1);

        for(int i = 1; i < Math.abs(oldX - newX + oldY - newY); i++) {
            int x = newX == oldX ? newX : (newX + (negativeComponent * i));
            int y = newY == oldY ? newY : (newY + (negativeComponent * i));

            if(x < 0 || x > 7 || y < 0 || y > 7) {
                continue;
            }

            if(board.spaces[x][y].piece != null) {
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

    public boolean checkArbMovementHasNoCheck(Board board, int oldX, int oldY, int newX, int newY) {
        BasePiece oldPiece = board.spaces[oldX][oldY].piece;
        BasePiece newPiece = board.spaces[newX][newY].piece;

        setPiece(board, oldX, oldY, null);
        setPiece(board, newX, newY, oldPiece);

        boolean checks = board.isMoveCompromisingKing(board.isWhiteTurn);

        setPiece(board, oldX, oldY, oldPiece);
        setPiece(board, newX, newY, newPiece);

        return !checks;
    }

    public boolean isValidCastlingMove(Board board, int oldX, int oldY, int newX, int newY) {
        BasePiece lRook = board.isWhiteTurn ? board.spaces[0][0].piece : board.spaces[0][7].piece;
        BasePiece rRook = board.isWhiteTurn ? board.spaces[7][0].piece : board.spaces[7][7].piece;

//        System.out.printf("checking queen moves: %b && %b && ((%b && %b && %b && %b && %b && %b) || (%b && %b && %b && %b && %b && %b))",
//                this instanceof King,
//                isInOGPos,
//                newX == 6,
//                lRook instanceof Rook,
//                lRook.isInOGPos,
//                !board.isMoveCompromisingKing(board.isWhiteTurn),
//                checkFutureMoveViable(board, oldX, oldY, 5, newY),
//                checkArbMovementHasNoCheck(board, oldX, oldY, 6, newY),
//                newX == 2,
//                rRook instanceof Rook,
//                rRook.isInOGPos,
//                !board.isMoveCompromisingKing(board.isWhiteTurn),
//                checkFutureMoveViable(board, oldX, oldY, 3, newY),
//                checkArbMovementHasNoCheck(board, oldX, oldY, 2, newY));


        return (this instanceof King) && isInOGPos &&
                ((newX == 6 &&
                        lRook instanceof Rook &&
                        lRook.isInOGPos &&
                        !board.isMoveCompromisingKing(board.isWhiteTurn) &&
                        checkFutureMoveViable(board, oldX, oldY, 5, newY) &&
                        checkArbMovementHasNoCheck(board, oldX, oldY, 6, newY) &&
                        board.spaces[6][oldY].piece == null)
                        || (newX == 2 &&
                        rRook instanceof Rook &&
                        rRook.isInOGPos &&
                        !board.isMoveCompromisingKing(board.isWhiteTurn) &&
                        checkFutureMoveViable(board, oldX, oldY, 3, newY) &&
                        checkArbMovementHasNoCheck(board, oldX, oldY, 2, newY) &&
                        board.spaces[2][oldY].piece == null));
    }

    public boolean doCastlingMoveIfViable(Board board, int oldX, int oldY, int newX, int newY) {
        if(isValidCastlingMove(board, oldX, oldY, newX, newY)) {
            if(newX == 2) {
                board.spaces[3][oldY].piece = board.spaces[0][oldY].piece;
                board.spaces[0][oldY].piece = null;

                board.spaces[2][oldY].piece = board.spaces[4][oldY].piece;
                board.spaces[4][oldY].piece = null;

                board.spaces[3][oldY].piece.isInOGPos = false;
                board.spaces[2][oldY].piece.isInOGPos = false;
            } else {
                board.spaces[5][oldY].piece = board.spaces[7][oldY].piece;
                board.spaces[7][oldY].piece = null;

                board.spaces[6][oldY].piece = board.spaces[4][oldY].piece;
                board.spaces[4][oldY].piece = null;

                board.spaces[5][oldY].piece.isInOGPos = false;
                board.spaces[6][oldY].piece.isInOGPos = false;
            }

            return true;
        }

        return false;
    }

    public boolean doEmpassantIfViable(Board board, int oldX, int oldY, int newX, int newY) {
        if(isEmpassantViable(board, oldX, oldY, newX, newY)) {
            BasePiece pawn = board.spaces[oldX][oldY].piece;
            BasePiece destroyed = board.spaces[newX][newY - (isWhite() ? 1 : -1)].piece;

            board.spaces[oldX][oldY].piece = null;
            board.spaces[newX][newY - (isWhite() ? 1 : -1)].piece = null;
            board.spaces[newX][newY].piece = pawn;

            if(board.isMoveCompromisingKing(isWhite())) {
                board.spaces[oldX][oldY].piece = pawn;
                board.spaces[newX][newY - (isWhite() ? 1 : -1)].piece = destroyed;
                board.spaces[newX][newY].piece = null;

                return false;
            }

            return true;
        }

        return false;
    }

    public boolean isEmpassantViable(Board board, int oldX, int oldY, int newX, int newY) {
        int negativeComponent = isWhite() ? 1 : -1;
        BasePiece maybePawn = board.spaces[oldX][oldY].piece;
        BasePiece maybeCapturedPawn = board.spaces[newX][newY - negativeComponent].piece;

        return newY - oldY == negativeComponent
                && Math.abs(newX - oldX) == 1
                && board.spaces[newX][newY].piece == null
                && maybePawn instanceof Pawn
                && maybeCapturedPawn instanceof Pawn
                && board.spaces[newX][newY - negativeComponent].piece != null
                && board.spaces[newX][newY - negativeComponent].piece.isWhite() != isWhite()
                && newY == (isWhite() ? 5 : 2);
    }

    public void promoteIfViable(Board board, int newX, int newY, String prom) {
        if((newY == 7 && isWhite() || newY == 0 && !isWhite()) && board.spaces[newX][newY].piece instanceof Pawn) {
            switch (prom) {
                case "", "Q" -> board.spaces[newX][newY].piece = new Queen(isWhite());
                case "R" -> board.spaces[newX][newY].piece = new Rook(isWhite());
                case "N" -> board.spaces[newX][newY].piece = new Knight(isWhite());
                case "B" -> board.spaces[newX][newY].piece = new Bishop(isWhite());
                default -> System.out.println("ERROR");
            }
        }
    }
}
