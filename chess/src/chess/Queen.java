package chess;

class Queen extends BasePiece {

    public Queen(boolean isWhite) {
        super(isWhite ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ);
    }

    @Override
    public boolean checkAndDoMove(Board board, int oldX, int oldY, int newX, int newY) {
        if(!isMoveBasicallyViable(board, oldX, oldY, newX, newY)) {
            return false;
        }

        if((Math.abs(newX - oldX) == Math.abs(newY - oldY) && doesNotPassDiaAheadOfPiece(board, oldX, oldY, newX, newY))
                || ((newX != oldX && newY == oldY) || (newX == oldX && newY != oldY)) && doesNotPassPerpAheadOfPiece(board, oldX, oldY, newX, newY)) {
            doMove(board, oldX, oldY, newX, newY);

            return true;
        }

        return false;
    }
}