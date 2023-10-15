package chess;

class Pawn extends BasePiece {

    public Pawn(boolean isWhite) {
        super(isWhite ? ReturnPiece.PieceType.WP : ReturnPiece.PieceType.BP);
    }

    @Override
    public boolean canDoMove(Board board, int oldX, int oldY, int newX, int newY) {
        int movementComponent = isWhite() ? 1 : -1;
        boolean isJumpViable = (oldY == 1 && movementComponent == 1) || (oldY == 6 && movementComponent == -1);

        return isMoveBasicallyViable(board, oldX, oldY, newX, newY) && (((newY - oldY == movementComponent && oldX == newX) && board.spaces[newX][newY].piece == null) || // move forward 1
                (isJumpViable && newY - oldY == 2 * movementComponent) || // jump forward 2 from starting
                (Math.abs(newX - oldX) == 1 && newY - oldY == movementComponent && isConsumablePieceAtLoc(board, newX, newY)));
    }
}
