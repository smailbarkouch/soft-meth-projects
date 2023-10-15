package chess;

class Rook extends BasePiece {

    public Rook(boolean isWhite) {
        super(isWhite ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR);
    }

    @Override
    public boolean checkAndDoMove(Board board, int oldX, int oldY, int newX, int newY) {
        if(!isMoveBasicallyViable(board, oldX, oldY, newX, newY)) {
            return false;
        }

        if(((newX != oldX && newY == oldY) || (newX == oldX && newY != oldY)) && doesNotPassPerpAheadOfPiece(board, oldX, oldY, newX, newY)) {
            doMove(board, oldX, oldY, newX, newY);

            return true;
        }

        return false;
    }


}