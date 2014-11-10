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

		for (int i = 0; i < 100; i++)
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
	
	//get next move (basic outline)
	public Tree expectMiniMax(Tree node, int depth)
	{	
		if(depth == 0 || node.getChildren().size() == 0)
		{
			//return node;
		}
		
		else if(depth % 2 == 0)
		{
			//Expect next node
			for(Tree child : node.getChildren())
			{
				//move = null;
			}
		}
		
		else if(depth % 2 != 0)
		{
			//Max of next node
			for(Tree child : node.getChildren())
			{
				//not sure if correct
				//move = node.getHVal() > expectMiniMax(child, depth - 1, move).getHVal() ? node : child;
			}
		}
		return null;
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
