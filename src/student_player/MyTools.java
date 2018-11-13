package student_player;

import coordinates.Coord;
import tablut.TablutBoardState;
import tablut.TablutMove;

public class MyTools {

    public int getQuadrant(Coord c){
    	int quad = -1;
    	if (c.x == 4 && c.y == 4){
    		quad = 0;
    	} else if(c.x == 4 && c.y < 4){
    		quad = 7;
    	} else if(c.x == 4 && c.y > 4){
    		quad = 5;
    	} else if(c.x < 4 && c.y == 4){
    		quad = 6;
    	} else if(c.x > 4 && c.y == 4){
    		quad = 8;
    	} else if(c.x < 4 && c.y < 4){
    		quad = 2;
    	} else if(c.x < 4 && c.y > 4){
    		quad = 1;
    	} else if(c.x > 4 && c.y > 4){
    		quad = 4;
    	} else if(c.x > 4 && c.y < 4){
    		quad = 3;
    	}
    	return quad;
    }
    
    public int getMuscHeuristic(TablutMove playerMove, Coord kingPiece){
    	int dist = 0;
    	
    	int ogDist = kingPiece.distance(playerMove.getStartPosition());
    	int endDist = kingPiece.distance(playerMove.getEndPosition());
    	
    	// the closer it is to closing in on half the distance, the better manhattanDistance it returns
    	dist = 2 * Math.abs(endDist - (ogDist/2));
    	
    	return dist;
    }
    
    public int getSwedeHeuristic(TablutBoardState boardState, TablutMove playerMove, Coord kingPiece){
    	int dist;
    	
    	// try to get as close to king as possible, but try to enclose quadrant (stay closer to origin lines) if possible
    	// if you're in a neighbouring qudrant that's better than nothing
    	int kingQuad = this.getQuadrant(kingPiece);
    	int playerQuad = this.getQuadrant(playerMove.getEndPosition());
    	
    	if (kingQuad == playerQuad){
    		if((playerMove.getEndPosition().x > 2) && (playerMove.getEndPosition().y > 2) && playerQuad == 2){
    			dist = 50;
    		} else if((playerMove.getEndPosition().x > 2) && (playerMove.getEndPosition().y < 6) && playerQuad == 1){
    			dist = 50;
    		} else if((playerMove.getEndPosition().x < 6) && (playerMove.getEndPosition().y > 2) && playerQuad == 3){
    			dist = 50;
    		} else if((playerMove.getEndPosition().x < 6) && (playerMove.getEndPosition().y < 6) && playerQuad == 4){
    			dist = 50;
    		} else {
    			dist = 100;
    		}
    	} else if (((kingQuad % 4) + 1 == playerQuad) || (((kingQuad - 2) % 4) + 1 == playerQuad)){
    		dist = 200;
    	} else {
    		dist = 300;
    	}
    	
    	return dist;
    }
}
