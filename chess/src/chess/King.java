package chess;

class King extends BasePiece {

    public King(boolean isWhite) {
        super(isWhite ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK);
    }

    @Override
    public boolean canDoMove(Board board, int oldX, int oldY, int newX, int newY) {
        return false;
    }

    @Override
    public boolean checkAndDoMove(Board board, int oldX, int oldY, int newX, int newY) {
        if(!isMoveBasicallyViable(board, oldX, oldY, newX, newY)) {
            return false;
        }

        int diffX = Math.abs(newX - oldX);
        int diffY = Math.abs(newY - oldY);

        if(diffX == 1 && (diffY == 1 || diffY == 0) || diffY == 1 && diffX == 0) {
            doMove(board, oldX, oldY, newX, newY);

            return true;
        }

        return false;
    }
}