package project;

import java.util.Arrays;

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
		int hVal = 0;
		Tree start = new Tree(game.getMyTiles());
		buildTree(start);
		
		while(!game.myWin)
		{
			hVal = expectiMiniMax(start, 10, hVal);
			for(int i = 0; i < start.getChildren().size(); i++)
			{
				//find the child with the calculated expectiMiniMaxValue
				if(start.getChildren().get(i).getHVal() == hVal)
				{
					// left
					if(i == 0)
					{
						game.left();
						start = start.getChildren().get(i);
						break;
					}
					//right
					else if(i == 2)
					{
						game.right();
						start = start.getChildren().get(i);
						break;
					}
					//up
					else if(i == 3)
					{
						game.up();
						start = start.getChildren().get(i);
						break;
					}
					//down
					else if(i == 4)
					{
						game.down();
						start = start.getChildren().get(i);
						break;
					}
				}
			}
			//must now add the random tile
			//game.addTile();
		}
		
//		game.resetGame();
//
//		for (int i = 0; i < 100; i++)
//		{
//			if (i % 10 == 0)
//			{
//				game.up();
//				game.repaint();
//				Thread.sleep(250); 
//				game.right();
//				game.repaint();
//				Thread.sleep(250); 
//				game.left();
//				game.repaint();
//				Thread.sleep(250); 
//				game.down();
//				game.repaint();
//				Thread.sleep(250); 
//			}
//		}
	}	
	
	//find the next node to choose with the best hVal
	public int expectiMiniMax(Tree node, int depth, int value)
	{	
		if(depth == 0 || node.getChildren().size() == 0)
		{
			return node.getHVal();
		}
		
		else if(depth % 2 == 0)
		{
			value = 0;
			//Expect next node
			for(Tree child : node.getChildren())
			{
				//not sure if correct
				value += node.getHVal() * expectiMiniMax(child, depth - 1, value);
			}
		}
		
		else if(depth % 2 != 0)
		{
			value = Integer.MAX_VALUE;
			//Max of next node
			for(Tree child : node.getChildren())
			{
				//not sure if correct
				value = node.getHVal() > expectiMiniMax(child, depth - 1, value) ? node.getHVal() : child.getHVal();
			}
		}
		return value;
	}
	
	public Tree buildTree(Tree start)
	{
		return start;
	}
	
	public static void printTiles(Tile[] tiles)
	{
		for(Tile t : tiles)
		{
			System.out.print(t.value);
		}
		System.out.println("\n");
	}

	public static void main(String args[]) throws InterruptedException
	{
		Game2048 twentyFortyEight = new Game2048();
		Agent2048 agent = new Agent2048(twentyFortyEight);
		
//		printTiles(twentyFortyEight.getMyTiles());
//		printTiles(twentyFortyEight.whatIfRight(Arrays.copyOf(twentyFortyEight.getMyTiles(), twentyFortyEight.getMyTiles().length)));
//		printTiles(twentyFortyEight.whatIfLeft(Arrays.copyOf(twentyFortyEight.getMyTiles(), twentyFortyEight.getMyTiles().length)));
//		printTiles(twentyFortyEight.getMyTiles());
		
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
