import isel.leic.pg.Console;

import java.awt.event.KeyEvent;

public class InLine {
	public static final int N = 3;           // Number of pieces on a winning line. (3,4,5...)
    private static final int                 // Console dimensions
            VIEW_LINES = Board.VIEW_LINES + Piece.HEIGHT + 1 , VIEW_COLS = Board.VIEW_COLS + 2;
    // The game state
    private static boolean player = false;    // Current player (false- 1 , true- 2)
    private static Piece piece;               // Current piece (not placed on the board)

    /**
     * Start method of game
     * @param args Not used.
     */
    public static void main(String[] args) {
		init();
        run();        // Loop of the game
		terminate();
	}

	private static void init() {
		Console.open("PG In Line ("+N+')', VIEW_LINES, VIEW_COLS);
		//Console.exit(true);				 // Uncomment to enable exit closing the console
        startGame();
	}

    private static void startGame() {
        Board.drawGrid();				    // Draw initial board
        piece = new Piece(player, Board.DIM_COLS/2);  // Create first piece
    }

    private static final String GAME_OVER_TXT = "GAME OVER";
	
	private static void terminate() {
		Console.cursor(VIEW_LINES/2, (VIEW_COLS-GAME_OVER_TXT.length())/2);
		Console.color(Console.RED, Console.YELLOW);
		Console.print(GAME_OVER_TXT);		// Message GAME OVER
		while (Console.isKeyPressed()) ;	// Wait if any key is pressed
		Console.waitKeyPressed(5000);		// Wait 5 seconds for any key
		Console.close();					// Close Console window
	}

	private static void run() {
        int key;           // Current key
		do {
            key = Console.getKeyPressed();  // Read a key
            if (!piece.stepFall())          // Try move the piece down
                stopFall();                 // Fix the piece
 			if (key!=Console.NO_KEY)        // A key was pressed ?
				action(key);	            // Do action for the key
		} while (key != KeyEvent.VK_ESCAPE);  // Key to abort game.
	}

    private static void stopFall() {
	    piece.fix();                        // Fix current piece
        player = !player;                   // Swap player
        piece = new Piece(player, piece.getColumn()); // Create other piece
    }

    private static void action(int key) {
		switch (key) {
            case KeyEvent.VK_LEFT:
                piece.jumpLeftColumn();
                break;
            case KeyEvent.VK_RIGHT:
                piece.jumpRightColumn();
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_DOWN:
                piece.startFall();
                break;
		}
        while (Console.isKeyPressed(key)) ;  // Wait to release key
    }
}
