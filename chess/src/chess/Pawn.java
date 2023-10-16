package chess;

class Pawn extends BasePiece {

    public Pawn(boolean isWhite) {
        super(isWhite ? ReturnPiece.PieceType.WP : ReturnPiece.PieceType.BP);
    }

    @Override
    public boolean canDoMove(Board board, int oldX, int oldY, int newX, int newY) {
        int movementComponent = isWhite() ? 1 : -1;

        return isMoveBasicallyViable(board, oldX, oldY, newX, newY) && (((newY - oldY == movementComponent && oldX == newX) && board.spaces[newX][newY].piece == null) || // move forward 1
                (this.isInOGPos && newY - oldY == 2 * movementComponent && newX == oldX && board.spaces[oldX][oldY + movementComponent].piece == null && board.spaces[newX][newY].piece == null) || // jump forward 2 from starting
                (Math.abs(newX - oldX) == 1 && newY - oldY == movementComponent && (isConsumablePieceAtLoc(board, newX, newY))));
    }
}
