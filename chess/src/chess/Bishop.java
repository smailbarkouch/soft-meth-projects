package chess;

class Bishop extends BasePiece {
    public Bishop(boolean isWhite) {
        super(isWhite ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB);
    }

    @Override
    public boolean canDoMove(Board board, int oldX, int oldY, int newX, int newY) {
        return isMoveBasicallyViable(board, oldX, oldY, newX, newY) && (Math.abs(newX - oldX) == Math.abs(newY - oldY) && doesNotPassDiaAheadOfPiece(board, oldX, oldY, newX, newY));
    }
}
