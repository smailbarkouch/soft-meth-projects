package chess;

import java.util.ArrayList;

class ReturnPiece {
	static enum PieceType {WP, WR, WN, WB, WQ, WK, 
		            BP, BR, BN, BB, BK, BQ};
	static enum PieceFile {a, b, c, d, e, f, g, h};
	
	PieceType pieceType;
	PieceFile pieceFile;
	int pieceRank;  // 1..8
	public String toString() {
		return ""+pieceFile+pieceRank+":"+pieceType;
	}
	public boolean equals(Object other) {
		if (other == null || !(other instanceof ReturnPiece)) {
			return false;
		}
		ReturnPiece otherPiece = (ReturnPiece)other;
		return pieceType == otherPiece.pieceType &&
				pieceFile == otherPiece.pieceFile &&
				pieceRank == otherPiece.pieceRank;
	}
}

class ReturnPlay {
	enum Message {ILLEGAL_MOVE, DRAW, 
				  RESIGN_BLACK_WINS, RESIGN_WHITE_WINS, 
				  CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS, 
				  STALEMATE};
	
	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;
}

public class Chess {
	
	enum Player { white, black }
	
	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
	public static ReturnPlay play(String move) {

		/* FILL IN THIS METHOD */
		
		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
		return null;
	}
	
	
	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		/* FILL IN THIS METHOD */
		ArrayList<ReturnPiece> pieces = new ArrayList<>();
		//white pawns
        for(ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()){
            ReturnPiece whitePawn = new ReturnPiece();
			whitePawn.pieceType = ReturnPiece.PieceType.WP;
			whitePawn.pieceFile = file;
			whitePawn.pieceRank = 2;  

			pieces.add(whitePawn);
        }
		//black pawns
		for(ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()){
            ReturnPiece blackPawn = new ReturnPiece();
			blackPawn.pieceType = ReturnPiece.PieceType.BP;
			blackPawn.pieceFile = file;
			blackPawn.pieceRank = 7;  

			pieces.add(blackPawn);
        }
		//white rooks
		for(ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()){
			if(file == ReturnPiece.PieceFile.a || file == ReturnPiece.PieceFile.h){
				ReturnPiece whiteRook = new ReturnPiece();
				whiteRook.pieceType = ReturnPiece.PieceType.WR;
				whiteRook.pieceFile = file;
				whiteRook.pieceRank = 1; 
				pieces.add(whiteRook);
			}	
		}
		//black rooks
		for(ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()){
			if(file == ReturnPiece.PieceFile.a || file == ReturnPiece.PieceFile.h){
				ReturnPiece blackRook = new ReturnPiece();
				blackRook.pieceType = ReturnPiece.PieceType.BR;
				blackRook.pieceFile = file;
				blackRook.pieceRank = 8; 
				pieces.add(blackRook);
			}	
		}
		//white knights
		for(ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()){
			if(file == ReturnPiece.PieceFile.b || file == ReturnPiece.PieceFile.g){
				ReturnPiece whiteKnight = new ReturnPiece();
				whiteKnight.pieceType = ReturnPiece.PieceType.WN;
				whiteKnight.pieceFile = file;
				whiteKnight.pieceRank = 1; 
				pieces.add(whiteKnight);
			}	
		}
		//black knights
		for(ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()){
			if(file == ReturnPiece.PieceFile.b || file == ReturnPiece.PieceFile.g){
				ReturnPiece blackKnight = new ReturnPiece();
				blackKnight.pieceType = ReturnPiece.PieceType.BN;
				blackKnight.pieceFile = file;
				blackKnight.pieceRank = 8; 
				pieces.add(blackKnight);
			}	
		}
		//white bishops
		for(ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()){
			if(file == ReturnPiece.PieceFile.c || file == ReturnPiece.PieceFile.f){
				ReturnPiece whiteBishop = new ReturnPiece();
				whiteBishop.pieceType = ReturnPiece.PieceType.WB;
				whiteBishop.pieceFile = file;
				whiteBishop.pieceRank = 1; 
				pieces.add(whiteBishop);
			}	
		}
		//black bishops
		for(ReturnPiece.PieceFile file : ReturnPiece.PieceFile.values()){
			if(file == ReturnPiece.PieceFile.c || file == ReturnPiece.PieceFile.f){
				ReturnPiece blackBishop = new ReturnPiece();
				blackBishop.pieceType = ReturnPiece.PieceType.WB;
				blackBishop.pieceFile = file;
				blackBishop.pieceRank = 8; 
				pieces.add(blackBishop);
			}	
		}
		//white queen
		ReturnPiece whiteQueen = new ReturnPiece();
		whiteQueen.pieceType = ReturnPiece.PieceType.WQ;
		whiteQueen.pieceFile = ReturnPiece.PieceFile.d;
		whiteQueen.pieceRank = 1;  
		pieces.add(whiteQueen);

		//black queen
		ReturnPiece blackQueen = new ReturnPiece();
		blackQueen.pieceType = ReturnPiece.PieceType.BQ;
		blackQueen.pieceFile = ReturnPiece.PieceFile.d;
		blackQueen.pieceRank = 8;  
		pieces.add(blackQueen);

		//white king
		ReturnPiece whiteKing = new ReturnPiece();
		whiteKing.pieceType = ReturnPiece.PieceType.WK;
		whiteKing.pieceFile = ReturnPiece.PieceFile.e;
		whiteKing.pieceRank = 1;  
		pieces.add(whiteKing);

		//black queen
		ReturnPiece blackKing = new ReturnPiece();
		blackKing.pieceType = ReturnPiece.PieceType.BK;
		blackKing.pieceFile = ReturnPiece.PieceFile.e;
		blackKing.pieceRank = 8;  
		pieces.add(blackKing);

        PlayChess.printBoard(pieces);
	}
}
