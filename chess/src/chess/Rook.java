package chess;

class Rook extends BasePiece {

    public Rook(boolean isWhite) {
        super(isWhite ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR);
    }

    @Override
    public boolean canDoMove(Board board, int oldX, int oldY, int newX, int newY) {
        return isMoveBasicallyViable(board, oldX, oldY, newX, newY) && (((newX != oldX && newY == oldY) || (newX == oldX && newY != oldY)) && doesNotPassPerpAheadOfPiece(board, oldX, oldY, newX, newY));
    }
}