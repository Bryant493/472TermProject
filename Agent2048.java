package project;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * AI Agent that controls a 2048 game with an 
 * expectimax algorithm in order to win every time!
 * 
 * @author Bryant
 */
public class Agent2048 extends JPanel
{
	/**
	 * 2048 game to be manipulated
	 */
	private Game2048 game;

	/**
	 * Constructs an Agent 2048 object with a given 2048 game
	 */
	public Agent2048(Game2048 givenGame)
	{
		this.game = givenGame;
		game.setFocusable(true);
	}

	/**
	 * Runs the AI Agent by selecting moves and performing them by generating a game tree and 
	 * calculating the hValues based on an expectimax algorithm
	 * @throws InterruptedException
	 */
	public void runAI(int depth) throws InterruptedException
	{
		Node current = new Node(game.getMyTiles());
		int hVal = 0;
		buildTree(current, true, depth);
		while(true)
		{
			hVal = expectiMiniMax(current, depth, true);
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
					else if(i == 1)
					{
						game.right();
						//move to this node in tree
						current = current.getChildren().get(i);
						break;
					}
					//up
					else if(i == 2)
					{
						game.up();
						//move to this node in tree
						current = current.getChildren().get(i);
						break;
					}
					//down
					else if(i == 3)
					{
						game.down();
						//move to this node in tree
						current = current.getChildren().get(i);
						break;
					}
				}
			}
			game.repaint();
			game.addTile(game.getMyTiles());
			//follow down the tree
			//current = findCurrent(current, new Node(game.getMyTiles()));
			current = new Node(game.getMyTiles());
			game.repaint();
			if(game.myWin)
			{
				break;
			}
			buildTree(current, true, depth);
		}
	}	
	
	/**
	 * Find the next node to choose with the best hVal using a recursive expectimax algorithm
	 * @param node - start node
	 * @param depth - how far down the tree this algorithm should go
	 * @param value - the hVal of the node
	 * @param max - which level of the tree we are currently on (i.e Max or Expect)
	 * @return value
	 */
	public int expectiMiniMax(Node node, int depth, boolean max)
	{	
		int value;
		double prob=0; 	//probability that the child tile is added 
		if(depth == 0 || node.getChildren().size() == 0)
		{
			value=node.getHVal();
		}
		
		else if(!max)
		{
			value = 0;
			//Expect next node
			for(Node child : node.getChildren())
			{
				value += prob * expectiMiniMax(child, depth - 1, true);
			}
			node.setHVal(value);
		}
		
		else
		{
			value = 0;
			//Max of next node
			for(Node child : node.getChildren())
			{
				value= Math.max(value, expectiMiniMax(child, depth-1, max));
			}
			node.setHVal(value);
		}
		return value;
	}
	
	public void buildTree(Node start, boolean max, int depth)
	{
		if(depth == 0)
		{
			return;
		}
		
		if(max)
		{
			Node left = new Node(game.whatIfLeft(start.getData()));
			if(!left.equals(start))
			{
				start.getChildren().add(left);
			}
			Node right = new Node(game.whatIfRight(start.getData()));
			if(!right.equals(start))
			{
				start.getChildren().add(right);
			}
			Node up = new Node(game.whatIfUp(start.getData()));
			if(!up.equals(start))
			{
				start.getChildren().add(up);
			}
			Node down = new Node(game.whatIfDown(start.getData()));
			if(!down.equals(start))
			{
				start.getChildren().add(down);
			}
			
			for(int i = 0; i < start.getChildren().size(); i++)
			{
				//for each, check if we have won the game, if we haven't then continue building
				if(!weWon(start.getChildren().get(i)))
				{
					buildTree(start.getChildren().get(i), false, depth - 1);
				}
			}
		}
		
		else
		{
			Node possible = new Node(game.cloneTiles(start.getData()));
			
			for(int i = 0; i < possible.getData().length; i++)
			{
				if(possible.getData()[i].value == 0)
				{
					
					possible.getData()[i].value = 2;
					Node nodeWithAdded2 = new Node(game.cloneTiles(possible.getData()));
					start.getChildren().add(nodeWithAdded2);
					if(!weLost(nodeWithAdded2))
					{
						buildTree(new Node(game.cloneTiles(possible.getData())), true, depth - 1);
					}	
					possible.getData()[i].value = 4;
					Node nodeWithAdded4 = new Node(game.cloneTiles(possible.getData()));
					start.getChildren().add(nodeWithAdded4);
					if(!weLost(nodeWithAdded4))
					{
						buildTree(new Node(game.cloneTiles(possible.getData())), true, depth - 1);
					}	
					possible.getData()[i].value = 0;
				}
			}
			
		}
	}
	
	private boolean weLost(Node node) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean weWon(Node node) {
		// TODO Auto-generated method stub
		return false;
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
		
		JFrame game = new JFrame();
		game.setTitle("2048 Game");
		game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game.setSize(340, 400);
		game.setResizable(false);
		game.setVisible(true);
		game.add(twentyFortyEight);
		game.setLocationRelativeTo(null);
		
		agent.runAI(6);
	}
}

