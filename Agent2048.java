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
		Node current = new Node(game.getMyTiles());
		buildTree(current);
		int hVal = 0;
		hVal = expectiMiniMax(current, 10, hVal, true);
		while(!game.myWin)
		{
			for(int i = 0; i < current.getChildren().size(); i++)
			{
				//find the child with the calculated expectiMiniMaxValue
				if(current.getChildren().get(i).getHVal() == hVal)
				{
					// left
					if(i == 0)
					{
						game.left();
						//move to this node in tree
						current = current.getChildren().get(i);
						break;
					}
					//right
					else if(i == 2)
					{
						game.right();
						//move to this node in tree
						current = current.getChildren().get(i);
						break;
					}
					//up
					else if(i == 3)
					{
						game.up();
						//move to this node in tree
						current = current.getChildren().get(i);
						break;
					}
					//down
					else if(i == 4)
					{
						game.down();
						//move to this node in tree
						current = current.getChildren().get(i);
						break;
					}
				}
			}
			//add random tile
			game.addTile(false, null);
			//follow down the tree
			current = findCurrent(current, new Node(game.getMyTiles()));
		}
	}	
	
	//find the next node to choose with the best hVal
	public int expectiMiniMax(Node node, int depth, int value, boolean max)
	{	
		if(depth == 0 || node.getChildren().size() == 0)
		{
			return node.getHVal();
		}
		
		else if(max == false)
		{
			value = 0;
			//Expect next node
			for(Node child : node.getChildren())
			{
				//not sure if correct
				value += node.getHVal() * expectiMiniMax(child, depth - 1, value, true);
			}
		}
		
		else if(max == true)
		{
			value = Integer.MAX_VALUE;
			//Max of next node
			for(Node child : node.getChildren())
			{
				//not sure if correct
				value = node.getHVal() > expectiMiniMax(child, depth - 1, value, false) ? node.getHVal() : child.getHVal();
			}
		}
		return value;
	}
	
	public Node buildTree(Node start)
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
	
	private Node findCurrent(Node current, Node next)
	{
		for(Node t : current.getChildren())
		{
			if(t.equals(next))
			{
				return t;
			}
		}
		return null;
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
