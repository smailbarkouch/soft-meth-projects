package chess;

class Queen extends BasePiece {

    public Queen(boolean isWhite) {
        super(isWhite ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ);
    }

    @Override
    public boolean canDoMove(Board board, int oldX, int oldY, int newX, int newY) {
//        System.out.printf("checking queen moves: %b && ((%d == %d && %b) || (((%b && %b) || (%b && %b)) && %b)%n",
//                isMoveBasicallyViable(board, oldX, oldY, newX, newY),
//                Math.abs(newX - oldX), Math.abs(newY - oldY),
//                doesNotPassDiaAheadOfPiece(board, oldX, oldY, newX, newY),
//                newX != oldX,
//                newY == oldY,
//                newX == oldX,
//                newY != oldY,
//                doesNotPassPerpAheadOfPiece(board, oldX, oldY, newX, newY));

        return isMoveBasicallyViable(board, oldX, oldY, newX, newY) && ((Math.abs(newX - oldX) == Math.abs(newY - oldY) && doesNotPassDiaAheadOfPiece(board, oldX, oldY, newX, newY))
                || (((newX != oldX && newY == oldY) || (newX == oldX && newY != oldY)) && doesNotPassPerpAheadOfPiece(board, oldX, oldY, newX, newY)));
    }
}