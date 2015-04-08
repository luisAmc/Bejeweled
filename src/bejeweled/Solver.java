package bejeweled;

import java.util.ArrayList;

public class Solver {
	private Board board;
	
	public Solver (Board board) {
            this.board = board;
	}
	
	public void Solve() {
            for (int row = 1; row < 7; row++) {
                for (int col = 1; col < 7; col++) {
                    if (!(row == 0 || col == 0 || row == 7 || col == 7)) {
                        try {
                        
                            if ((row < 3 || row > 4) || (col < 3 || col > 4)) {
                                board.swapTile(board.getTileAt(row, col), innerBorder(row, col));
                            } else {
                                board.swapTile(board.getTileAt(row, col), coreBoard(row, col));
                            }
                            
                        } catch (Exception ex) {
                            System.err.println("[" + row + ", " + col + "]");
                            ex.printStackTrace();
                        }
                    }
                }
            }
	}
	
	private Tile innerBorder(int row, int col) {
            Tile tile = board.getTileAt(row, col);
            Tile[] diagNeighbors = {board.getTileAt(row - 1, col - 1),
                                board.getTileAt(row - 1, col + 1),
                                board.getTileAt(row + 1, col - 1),
                                board.getTileAt(row + 1, col + 1)};
            
            if (checkNeighborsGem(tile, diagNeighbors[0], diagNeighbors[1]))
                return board.getTileAt(row - 1, col);
            else if (checkNeighborsGem(tile, diagNeighbors[2], diagNeighbors[3]))
                return board.getTileAt(row + 1, col);
            else if (checkNeighborsGem(tile, diagNeighbors[1], diagNeighbors[3]))
                return board.getTileAt(row, col + 1);
            else 
                return board.getTileAt(row, col - 1);
	}
        
        private Tile coreBoard(int row, int col) {
            Tile tile = board.getTileAt(row, col);
            
            boolean up = checkNeighborsGem(tile, board.getTileAt(row - 2, col), board.getTileAt(row - 3, col));
            boolean upL = checkNeighborsGem(tile, board.getTileAt(row - 1, col - 1), board.getTileAt(row - 1, col - 2));
            boolean upR = checkNeighborsGem(tile, board.getTileAt(row - 1, col + 1), board.getTileAt(row - 1, col + 2));
            
            boolean left = checkNeighborsGem(tile, board.getTileAt(row, col - 2), board.getTileAt(row, col - 3));
            boolean leftU = checkNeighborsGem(tile, board.getTileAt(row - 1, col - 1), board.getTileAt(row - 2, col - 1));
            boolean leftD = checkNeighborsGem(tile, board.getTileAt(row + 1, col - 1), board.getTileAt(row + 2, col - 1));
            
            boolean right = checkNeighborsGem(tile, board.getTileAt(row, col + 2), board.getTileAt(row, col + 3));
            boolean rightU = checkNeighborsGem(tile, board.getTileAt(row - 1, col + 1), board.getTileAt(row - 2, col + 1));
            boolean rightD = checkNeighborsGem(tile, board.getTileAt(row + 1, col + 1), board.getTileAt(row + 2, col + 1));
            
            boolean down = checkNeighborsGem(tile, board.getTileAt(row + 2, col), board.getTileAt(row + 3, col));
            boolean downL = checkNeighborsGem(tile, board.getTileAt(row + 1, col - 1), board.getTileAt(row + 1, col - 2));
            boolean downR = checkNeighborsGem(tile, board.getTileAt(row + 1, col + 1), board.getTileAt(row + 1, col + 2));
            
            if (up || upL || upR)
                return board.getTileAt(row - 1, col);
            else if (down || downL || downR)
                return board.getTileAt(row + 1, col);
            else if (left || leftU || leftD)
                return board.getTileAt(row, col - 1);
            else 
                return board.getTileAt(row, col + 1);
        }
	
	private boolean checkNeighborsGem(Tile t1, Tile t2, Tile t3) {
            return ((t1.id == t2.id) && (t2.id == t3.id));
	}
}
