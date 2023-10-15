package chess;

class Bishop extends BasePiece {
    public Bishop(boolean isWhite) {
        super(isWhite ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB);
    }

    @Override
    public boolean checkAndDoMove(Board board, int oldX, int oldY, int newX, int newY) {
        if(!isMoveBasicallyViable(board, oldX, oldY, newX, newY)) {
            return false;
        }

        if(Math.abs(newX - oldX) == Math.abs(newY - oldY) && doesNotPassDiaAheadOfPiece(board, oldX, oldY, newX, newY)) {
            doMove(board, oldX, oldY, newX, newY);

            return true;
        }

        return false;
    }


}
