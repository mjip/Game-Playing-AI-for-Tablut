package student_player;

import boardgame.Move;
import boardgame.Player;
import coordinates.Coord;
import coordinates.Coordinates;
import tablut.RandomTablutPlayer;
import tablut.GreedyTablutPlayer;
import tablut.TablutBoardState;
import tablut.TablutBoardState.Piece;
import tablut.TablutMove;
import tablut.TablutPlayer;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.List;
import java.util.Random;

/** A player file submitted by a student. */
public class StudentPlayer extends TablutPlayer {
    Random rand = new Random(System.currentTimeMillis());
	boolean openingStrat = true;
	int opponent;
	int player;
	int turn;

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260686859");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    
    // strat inspo:
    // http://tafl.cyningstan.com/page/30/general-strategy-for-the-attackers
    // http://tafl.cyningstan.com/page/34/defending-the-king-and-aiding-his-escape
    public Move chooseMove(TablutBoardState boardState) {

    	MyTools bs = new MyTools();
    	
    	long start = System.currentTimeMillis();

        // set player/opponent
        opponent = boardState.getOpponent();
        player = (opponent + 1) % 2;
    	
        TablutMove bestMove = (TablutMove) boardState.getRandomMove();
    	turn = boardState.getTurnNumber();
    	System.out.println("Turn number: " + turn);
        /////////////////////////////////////////////////////////////////////////
        ///////////////////////// MUSCOVITE STRATS //////////////////////////////
        /////////////////////////////////////////////////////////////////////////
        if (player == TablutBoardState.MUSCOVITE){
        	
        	////////////////////////////////////////////////////////////////////
        	// 						OPENING STRATEGY 						  //
        	////////////////////////////////////////////////////////////////////
        	if(openingStrat){
        		// take all initial black pieces,
        		// choose one at random and move it clockwise on the diagonal
        		if(turn <= 6){
        			Coord[] ends = {Coordinates.get(1,4), Coordinates.get(4,1), Coordinates.get(4,7), Coordinates.get(7,4)};
        			Coord[] cwRotatedEnds = {Coordinates.get(1,7), Coordinates.get(1,1), Coordinates.get(7,7), Coordinates.get(7,1)};
        			Coord[] ccwRotatedEnds = {Coordinates.get(1,1), Coordinates.get(7,1), Coordinates.get(1,7), Coordinates.get(7,7)};
        			int randInd = rand.nextInt(4);
        			
        			// if king moved out of centre before opening strat is finished, prioritize blocking his path
        			if(boardState.getKingPosition().x != 4 || boardState.getKingPosition().y != 4){
        				int kingQuad = bs.getQuadrant(boardState.getKingPosition());
        				switch(kingQuad){
        					case 5:
        						if(boardState.isLegal(new TablutMove(cwRotatedEnds[1], cwRotatedEnds[0], player))){
        							return new TablutMove(cwRotatedEnds[1], cwRotatedEnds[0], player);
        						} else if(boardState.isLegal(new TablutMove(ends[0], cwRotatedEnds[0], player))){
        							return new TablutMove(ends[0], cwRotatedEnds[0], player);
        						} else if(boardState.isLegal(new TablutMove(ends[2], cwRotatedEnds[0], player))){
        							return new TablutMove(ends[2], cwRotatedEnds[0], player);
        						} else if(boardState.isLegal(new TablutMove(ends[2], cwRotatedEnds[2], player))){
        							return new TablutMove(ends[2], cwRotatedEnds[2], player);
        						} else if(boardState.isLegal(new TablutMove(ends[3], cwRotatedEnds[2], player))){
        							return new TablutMove(ends[3], cwRotatedEnds[2], player);
        						} else if(boardState.isLegal(new TablutMove(cwRotatedEnds[3], cwRotatedEnds[2], player))){
        							return new TablutMove(cwRotatedEnds[3], cwRotatedEnds[2], player);
        						} else { // king can't be blocked, execute regular gameplay
        							openingStrat = false;
        						}
        						break;
        					case 6:
        						if(boardState.isLegal(new TablutMove(cwRotatedEnds[3], ccwRotatedEnds[0], player))){
        							return new TablutMove(cwRotatedEnds[3], ccwRotatedEnds[0], player);
        						} else if(boardState.isLegal(new TablutMove(ends[1], ccwRotatedEnds[0], player))){
        							return new TablutMove(ends[1], ccwRotatedEnds[0], player);
        						} else if(boardState.isLegal(new TablutMove(ends[0], ccwRotatedEnds[0], player))){
        							return new TablutMove(ends[0], ccwRotatedEnds[0], player);
        						} else if(boardState.isLegal(new TablutMove(ends[0], ccwRotatedEnds[2], player))){
        							return new TablutMove(ends[0], ccwRotatedEnds[2], player);
        						} else if(boardState.isLegal(new TablutMove(ends[2], ccwRotatedEnds[2], player))){
        							return new TablutMove(ends[2], ccwRotatedEnds[2], player);
        						} else if(boardState.isLegal(new TablutMove(cwRotatedEnds[2], ccwRotatedEnds[2], player))){
        							return new TablutMove(cwRotatedEnds[2], ccwRotatedEnds[2], player);
        						} else { // king can't be blocked, execute regular gameplay
        							openingStrat = false;
        						}
        						break;
        					case 7:
        						if(boardState.isLegal(new TablutMove(cwRotatedEnds[0], cwRotatedEnds[1], player))){
        							return new TablutMove(cwRotatedEnds[0], cwRotatedEnds[1], player);
        						} else if(boardState.isLegal(new TablutMove(ends[0], cwRotatedEnds[1], player))){
        							return new TablutMove(ends[0], cwRotatedEnds[1], player);
        						} else if(boardState.isLegal(new TablutMove(ends[1], cwRotatedEnds[1], player))){
        							return new TablutMove(ends[1], cwRotatedEnds[1], player);
        						} else if(boardState.isLegal(new TablutMove(ends[1], cwRotatedEnds[3], player))){
        							return new TablutMove(ends[1], cwRotatedEnds[3], player);
        						} else if(boardState.isLegal(new TablutMove(ends[3], cwRotatedEnds[3], player))){
        							return new TablutMove(ends[3], cwRotatedEnds[3], player);
        						} else if(boardState.isLegal(new TablutMove(cwRotatedEnds[2], cwRotatedEnds[3], player))){
        							return new TablutMove(cwRotatedEnds[2], cwRotatedEnds[3], player);
        						} else { // king can't be blocked, execute regular gameplay
        							openingStrat = false;
        						}
        						break;
        					case 8:
        						if(boardState.isLegal(new TablutMove(cwRotatedEnds[1], cwRotatedEnds[3], player))){
        							return new TablutMove(cwRotatedEnds[1], cwRotatedEnds[3], player);
        						} else if(boardState.isLegal(new TablutMove(ends[1], cwRotatedEnds[3], player))){
        							return new TablutMove(ends[1], cwRotatedEnds[3], player);
        						} else if(boardState.isLegal(new TablutMove(ends[3], cwRotatedEnds[3], player))){
        							return new TablutMove(ends[3], cwRotatedEnds[3], player);
        						} else if(boardState.isLegal(new TablutMove(ends[3], cwRotatedEnds[2], player))){
        							return new TablutMove(ends[3], cwRotatedEnds[2], player);
        						} else if(boardState.isLegal(new TablutMove(ends[2], cwRotatedEnds[2], player))){
        							return new TablutMove(ends[2], cwRotatedEnds[2], player);
        						} else if(boardState.isLegal(new TablutMove(cwRotatedEnds[0], cwRotatedEnds[2], player))){
        							return new TablutMove(cwRotatedEnds[0], cwRotatedEnds[2], player);
        						} else { // king can't be blocked, execute regular gameplay
        							openingStrat = false;
        						}
        						break;
        					default:
        						break;
        				}
        			}
        			
        			// if piece is not legal, find one that is. but also if you hit a max count (nothing is legal) exit your opening strategy and play a move normally
        			int count = 0;
        			while (!boardState.isLegal(new TablutMove(ends[randInd], cwRotatedEnds[randInd], player)) && count < 20){
        				randInd = rand.nextInt(4);
        				count++;
        			}        		
        			if(count == 20){
        				openingStrat = false;
        			} else {
        				return new TablutMove(ends[randInd], cwRotatedEnds[randInd], player);
        			}
        		} else {
        			openingStrat = false;
        		}

        	}
			////////////////////////////////////////////////////////////////////
			// 					REGULAR GAMEPLAY STRATEGY 					  //
			////////////////////////////////////////////////////////////////////
        	
        	// similar to greedy strategy, get all moves
            List<TablutMove> options = boardState.getAllLegalMoves();
            int numOfOpponentPieces = boardState.getNumberPlayerPieces(opponent);
            TreeMap<Integer, TablutMove> moveQual = new TreeMap<Integer, TablutMove>();
            TreeMap<Integer, TablutMove> manDistMoves = new TreeMap<Integer, TablutMove>();
            Coord kingPos = boardState.getKingPosition();
            int bestManDist = 1000;
            
            for (TablutMove move : options) {
                TablutBoardState cloneBoard = (TablutBoardState) boardState.clone();
                cloneBoard.processMove(move);
                int newNumOfOpponentPieces = cloneBoard.getNumberPlayerPieces(opponent);

                // check 1) can you eat the king, and do it right away
                if (cloneBoard.getWinner() == player) {
                    return move;
                }
                // check 2) can you get beside the king
                else if(Coordinates.getNeighbors(kingPos).contains(move.getEndPosition())){
                	moveQual.put(2, move);
                }
                // check 3 + 4) can you eat the king's neighbours/any other pieces
                else if (newNumOfOpponentPieces < numOfOpponentPieces) {
                	boolean betterMove = false;
                	List<Coord> kingNeighbours = Coordinates.getNeighbors(kingPos);
                	for (Coord kingNeighboursNeighbours : kingNeighbours){
                		if(Coordinates.getNeighbors(kingNeighboursNeighbours).contains(move.getEndPosition())){
                			moveQual.put(3, move);
                			betterMove = true;
                		}
                	}
                	if (!betterMove){
                		moveQual.put(4, move);
                	}
                }
                // otherwise: evaluate move by basic manhattan distance heuristic (you want to block the king's path/enclose him in a circle)
                else {
                	int currentManDist = bs.getMuscHeuristic(move, kingPos);
                	if (currentManDist < bestManDist){
                		manDistMoves.put(1000 + currentManDist, move);
                		bestManDist = currentManDist;
                	}
                } 
            }
            
            // returns best move according to move quality ranking/manhattan distance ranking
            try {
            	return moveQual.get(moveQual.firstKey());
            } catch (Exception NoSuchElementException){
            	return manDistMoves.get(manDistMoves.firstKey());
            }
        }
        /////////////////////////////////////////////////////////////////////////
        ///////////////////////// SWEDE STRATS //////////////////////////////////
        /////////////////////////////////////////////////////////////////////////
        else if (player == TablutBoardState.SWEDE){
        	
        	////////////////////////////////////////////////////////////////////
        	// 						OPENING STRATEGY 						  //
        	////////////////////////////////////////////////////////////////////
        	if(openingStrat){
        		// if first turns
        		if(turn == 0){
        			// determine where the opponent moved their first piece,
        			// move in opposite quadrant
        			HashSet<Coord> newOpponentSet = boardState.getOpponentPieceCoordinates();
        			HashSet<Coord> startOpponentSet = new HashSet<>();
        			startOpponentSet.add(Coordinates.get(0, 3));
        			startOpponentSet.add(Coordinates.get(0, 4));
        			startOpponentSet.add(Coordinates.get(0, 5));
        			startOpponentSet.add(Coordinates.get(1, 4));
        			startOpponentSet.add(Coordinates.get(3, 0));
        			startOpponentSet.add(Coordinates.get(4, 0));
        			startOpponentSet.add(Coordinates.get(5, 0));
        			startOpponentSet.add(Coordinates.get(4, 1));
        			startOpponentSet.add(Coordinates.get(4, 7));
        			startOpponentSet.add(Coordinates.get(3, 8));
        			startOpponentSet.add(Coordinates.get(4, 8));
        			startOpponentSet.add(Coordinates.get(5, 8));
        			startOpponentSet.add(Coordinates.get(8, 5));
        			startOpponentSet.add(Coordinates.get(8, 4));
        			startOpponentSet.add(Coordinates.get(8, 3));
        			startOpponentSet.add(Coordinates.get(7, 4));
        			
        			newOpponentSet.removeAll(startOpponentSet);
        			int quadOfOpp = bs.getQuadrant(newOpponentSet.iterator().next());
        		    
        		    // move in opposite direction
        		    if (quadOfOpp == 1){
        		    	return new TablutMove(Coordinates.get(6, 4), Coordinates.get(6, 1), player);
        		    } else if(quadOfOpp == 2){
        		    	return new TablutMove(Coordinates.get(4, 6), Coordinates.get(7, 6), player);
        		    } else if(quadOfOpp == 3){
        		    	return new TablutMove(Coordinates.get(2, 4), Coordinates.get(2, 7), player);
        		    } else if(quadOfOpp == 4){
        		    	return new TablutMove(Coordinates.get(4, 2), Coordinates.get(1, 2), player);
        		    }
        		} else if(turn == 1){
        			// determine your own last move using center of mass and complete the followup move to form a 'tunnel'
        			HashSet<Coord> newPlayerSet = boardState.getPlayerPieceCoordinates();
        			HashSet<Coord> startPlayerSet = new HashSet<Coord>();
        			startPlayerSet.add(Coordinates.get(4, 4));
        			startPlayerSet.add(Coordinates.get(4, 5));
        			startPlayerSet.add(Coordinates.get(4, 6));
        			startPlayerSet.add(Coordinates.get(4, 3));
        			startPlayerSet.add(Coordinates.get(4, 2));
        			startPlayerSet.add(Coordinates.get(2, 4));
        			startPlayerSet.add(Coordinates.get(3, 4));
        			startPlayerSet.add(Coordinates.get(5, 4));
        			startPlayerSet.add(Coordinates.get(6, 4));
        			
        			newPlayerSet.removeAll(startPlayerSet);
        			int quadOfPlayer = bs.getQuadrant(newPlayerSet.iterator().next());
        			
        		    // move in same quadrant
        		    if (quadOfPlayer == 1){
        		    	if (boardState.isLegal(new TablutMove(Coordinates.get(4, 5), Coordinates.get(1, 5), player))){
        		    		return new TablutMove(Coordinates.get(4, 5), Coordinates.get(1, 5), player);
        		    	}
        		    } else if(quadOfPlayer == 2){
        		    	if (boardState.isLegal(new TablutMove(Coordinates.get(5, 4), Coordinates.get(5, 7), player))){
        		    		return new TablutMove(Coordinates.get(5, 4), Coordinates.get(5, 7), player);
        		    	}
        		    } else if(quadOfPlayer == 3){
        		    	if (boardState.isLegal(new TablutMove(Coordinates.get(4, 3), Coordinates.get(7, 3), player))){
        		    		return new TablutMove(Coordinates.get(4, 3), Coordinates.get(7, 3), player);
        		    	}
        		    } else if(quadOfPlayer == 4){
        		    	if (boardState.isLegal(new TablutMove(Coordinates.get(5, 4), Coordinates.get(5, 7), player))){
        		    		return new TablutMove(Coordinates.get(5, 4), Coordinates.get(5, 7), player);
        		    	}
        		    } else {
        		    	openingStrat = false;
        		    }
        		} else {
        			openingStrat = false;
        		}
        	}
        	
    		////////////////////////////////////////////////////////////////////
    		// 					REGULAR GAMEPLAY STRATEGY 					  //
    		////////////////////////////////////////////////////////////////////
        	
        	// similar to greedy move, but different checks/heuristic
            List<TablutMove> options = boardState.getAllLegalMoves();
            int minNumOfOpponentPieces = boardState.getNumberPlayerPieces(opponent);
            TreeMap<Integer, TablutMove> moveQual = new TreeMap<Integer, TablutMove>();
            TreeMap<Integer, TablutMove> manDistMoves = new TreeMap<Integer, TablutMove>();
            Coord kingPos = boardState.getKingPosition();
            int bestManDist = 1000;
            
            // check 1) try to move king to winning position, else try to move king closer to corner (only if safe)
            int minDistance = Coordinates.distanceToClosestCorner(kingPos);
            for (TablutMove move : boardState.getLegalMovesForPosition(kingPos)) {
                TablutBoardState cloneBoard = (TablutBoardState) boardState.clone();
                cloneBoard.processMove(move);
                
                if (cloneBoard.getWinner() == player) {
                    return move;
                }
                
            	List<Coord> kingNeighbours = Coordinates.getNeighbors(move.getEndPosition());
                boolean safe = true;
            	for (Coord spaces : kingNeighbours){
            		if((cloneBoard.getPieceAt(spaces) == Piece.BLACK) || (Coordinates.isCorner(spaces))){
            			safe = false;
            		}
            	}
            	
            	if (safe){
            		int moveDistance = Coordinates.distanceToClosestCorner(move.getEndPosition());
                    if (moveDistance < minDistance) {
                        minDistance = moveDistance;
                        moveQual.put(2, move);
                    }
            	}
            }

            for (TablutMove move : options) {
                TablutBoardState cloneBoard = (TablutBoardState) boardState.clone();
                cloneBoard.processMove(move);
                int newNumOfOpponentPieces = cloneBoard.getNumberPlayerPieces(opponent);

                // check 3) if we can capture any of the opponent's pieces, try
                if (newNumOfOpponentPieces < minNumOfOpponentPieces) {
                    minNumOfOpponentPieces = newNumOfOpponentPieces;
                    moveQual.put(3, move);
                    break;
                }
                
                // check 4) move our pieces out to corners more, based on manhattan distance heuristic
            	int currentManDist = bs.getSwedeHeuristic(cloneBoard, move, kingPos);
            	if (currentManDist < bestManDist){
            		manDistMoves.put(1000 + currentManDist, move);
            		bestManDist = currentManDist;
            	}
            }

            // returns best move according to move quality ranking/manhattan distance ranking
            try {
            	return moveQual.get(moveQual.firstKey());
            } catch (Exception NoSuchElementException){
            	return manDistMoves.get(manDistMoves.firstKey());
            }
        }


        // Return your move to be processed by the server.
        long end = System.currentTimeMillis();
        System.out.println("It took: " + (end-start) + " ms to choose a move.");
        return bestMove;
    }
    
    // For Debugging purposes only.
    public static void main(String[] args) {
        TablutBoardState b = new TablutBoardState();
        Player muscovite = new GreedyTablutPlayer("GreedyMusc");
        muscovite.setColor(TablutBoardState.MUSCOVITE);

        //Player swede = new RandomTablutPlayer("RandomSwede");
        //swede.setColor(TablutBoardState.SWEDE);
        //
        Player swede = new StudentPlayer();
        swede.setColor(TablutBoardState.SWEDE);
        // ((StudentPlayer) swede).rand = new Random(4);

       // Player muscovite = new StudentPlayer();
        //muscovite.setColor(TablutBoardState.MUSCOVITE);

        Player player = muscovite;
        while (!b.gameOver()) {
            Move m = player.chooseMove(b);
            b.processMove((TablutMove) m);
            player = (player == muscovite) ? swede : muscovite;
            System.out.println("\nMOVE PLAYED: " + m.toPrettyString());
            b.printBoard();
        }
        System.out.println(TablutMove.getPlayerName(b.getWinner()) + " WIN!");
    }
}