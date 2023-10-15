package chess;

class Knight extends BasePiece {
    public Knight(boolean isWhite) {
        super(isWhite ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN);
    }

    @Override
    public boolean canDoMove(Board board, int oldX, int oldY, int newX, int newY) {
        int diffX = Math.abs(newX - oldX);
        int diffY = Math.abs(newY - oldY);

        return isMoveBasicallyViable(board, oldX, oldY, newX, newY) && ((diffX == 1 && diffY == 2) || (diffX == 2 && diffY == 1));
    }
}