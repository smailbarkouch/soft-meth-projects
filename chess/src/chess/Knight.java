package chess;

class Knight extends BasePiece {
    public Knight(boolean isWhite) {
        super(isWhite ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK);
    }

    @Override
    public boolean checkAndDoMove(Board board, int oldX, int oldY, int newX, int newY) {
        if(!isMoveBasicallyViable(board, oldX, oldY, newX, newY)) {
            return false;
        }

        int diffX = Math.abs(newX - oldX);
        int diffY = Math.abs(newY - oldY);

        if((diffX == 1 && diffY == 2) || (diffX == 2 && diffY == 1)) {
            doMove(board, oldX, oldY, newX, newY);
            return true;
        }


        return false;
    }
}