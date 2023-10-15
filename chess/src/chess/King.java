package chess;

class King extends BasePiece {

    public King(boolean isWhite) {
        super(isWhite ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK);
    }

    @Override
    public boolean canDoMove(Board board, int oldX, int oldY, int newX, int newY) {
        int diffX = Math.abs(newX - oldX);
        int diffY = Math.abs(newY - oldY);

        boolean regMovement = ((diffX == 1 && (diffY == 1 || diffY == 0) || diffY == 1 && diffX == 0));

        return isMoveBasicallyViable(board, oldX, oldY, newX, newY) && regMovement; // don't check for castling cause we do it in the basepiece
    }
}