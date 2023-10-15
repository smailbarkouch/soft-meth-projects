package chess;

class Pawn extends BasePiece {

    public Pawn(boolean isWhite) {
        super(isWhite ? ReturnPiece.PieceType.WP : ReturnPiece.PieceType.BP);
    }

    @Override
    public boolean checkAndDoMove(Board board, int oldX, int oldY, int newX, int newY) {
        if(!isMoveBasicallyViable(board, oldX, oldY, newX, newY)) {
            return false;
        }

        if((newY - oldY == 1 && oldX == newX) || // move forward 1
                (oldY == 1 && newY - oldY == 2) || // jump forward 2 from starting
                (Math.abs(newX - oldX) == 1 && newY - oldY == 1 && isConsumablePieceAtLoc(board, newX, newY))) { // jump to the side
            doMove(board, oldX, oldY, newX, newY);

            return true;
        }

        return false;
    }
}
