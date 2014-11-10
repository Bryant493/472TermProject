package project;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Agent2048 extends JPanel
{
	private Game2048 game;

	public Agent2048(Game2048 givenGame)
	{
		this.game = givenGame;
		game.setFocusable(true);
	}

	public void runAI() throws InterruptedException
	{
		game.resetGame();

		for (int i = 0; i < 1000000000; i++)
		{
			if (i % 10 == 0)
			{
				game.up();
				game.right();
				game.left();
				game.down();
				game.repaint();
				Thread.sleep(1000); 
			}
		}
	}

	public static void main(String args[]) throws InterruptedException
	{
		Game2048 twentyFortyEight = new Game2048();
		Agent2048 agent = new Agent2048(twentyFortyEight);
		JFrame game = new JFrame();
		game.setTitle("2048 Game");
		game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game.setSize(340, 400);
		game.setResizable(false);
		game.setVisible(true);
		game.add(twentyFortyEight);
		game.setLocationRelativeTo(null);
		agent.runAI();
	}
}
