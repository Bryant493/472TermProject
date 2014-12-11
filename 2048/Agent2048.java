import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * AI Agent that controls a 2048 game with an expectimax algorithm in order to
 * win every time!
 * 
 * @author Bryant
 */
public class Agent2048 extends JPanel
{

	private static final long serialVersionUID = 1L;

	/**
	 * 2048 game to be manipulated
	 */
	private Game2048 game;

	private final int LEFT = 0;

	private final int RIGHT = 1;

	private final int UP = 2;

	private final int DOWN = 3;
	
	private final String strategy;

	/**
	 * Constructs an Agent 2048 object with a given 2048 game
	 */
	public Agent2048(Game2048 givenGame, String strategy)
	{
		this.game = givenGame;
		game.setFocusable(true);
		this.strategy = strategy;
	}

	/**
	 * Runs the AI Agent by selecting moves and performing them by generating a
	 * game tree and calculating the hValues based on an expectimax algorithm
	 * 
	 * @throws InterruptedException
	 */
	public void runAI(int depth) throws InterruptedException
	{
		Node current = new Node(game.getMyTiles(), strategy);
		double hVal = 0;
		buildTree(current, true, depth);
		while (!game.myLose)
		{
			hVal = expectiMiniMax(current, depth, true);
			for (Node n : current.getChildren())
			{
				// find the child with the calculated expectiMiniMaxValue
				if (n.getHVal() == hVal)
				{
					int direction = n.getDirection();
					if (direction == LEFT)
					{
						game.left();
						break;
					}
					else if (direction == RIGHT)
					{
						game.right();
						break;
					}
					else if (direction == UP)
					{
						game.up();
						break;
					}
					else if (direction == DOWN)
					{
						game.down();
						break;
					}
				}
			}
			game.repaint();
			current = new Node(game.getMyTiles(), strategy);
			buildTree(current, true, depth);
		}
	}

	/**
	 * Find the next node to choose with the best hVal using a recursive
	 * expectimax algorithm
	 * 
	 * @param node
	 *            - start node
	 * @param depth
	 *            - how far down the tree this algorithm should go
	 * @param value
	 *            - the hVal of the node
	 * @param max
	 *            - which level of the tree we are currently on (i.e Max or
	 *            Expect)
	 * @return value
	 */
	public double expectiMiniMax(Node node, int depth, boolean max)
	{
		double value;
		if (depth == 0 || node.getChildren().size() == 0)
		{
			value = node.getHVal();
		}

		else if (!max)
		{
			value = 0;
			// Expect next node
			for (Node child : node.getChildren())
			{
				value += child.getProb() * expectiMiniMax(child, depth - 1, true);
			}
			node.setHVal(value);
		}

		else
		{
			value = -Double.MAX_VALUE;
			// Max of next node
			for (Node child : node.getChildren())
			{
				value = Math.max(value, expectiMiniMax(child, depth - 1, false));
			}
			node.setHVal(value);
		}
		return value;
	}

	public void buildTree(Node start, boolean max, int depth)
	{
		if (depth == 0)
		{
			return;
		}

		if (max)
		{
			Node left = new Node(game.whatIfLeft(start.getData()), strategy);
			left.setDirection(LEFT);
			if (!left.equals(start))
			{
				start.getChildren().add(left);
			}
			Node right = new Node(game.whatIfRight(start.getData()), strategy);
			right.setDirection(RIGHT);
			if (!right.equals(start))
			{
				start.getChildren().add(right);
			}
			Node up = new Node(game.whatIfUp(start.getData()), strategy);
			up.setDirection(UP);
			if (!up.equals(start))
			{
				start.getChildren().add(up);
			}
			Node down = new Node(game.whatIfDown(start.getData()), strategy);
			down.setDirection(DOWN);
			if (!down.equals(start))
			{
				start.getChildren().add(down);
			}

			for (int i = 0; i < start.getChildren().size(); i++)
			{
				buildTree(start.getChildren().get(i), false, depth - 1);
			}
		}

		else
		{
			Node possible = new Node(game.cloneTiles(start.getData()), strategy);
			// count the number of empty tiles
			int numEmpty = 0;
			for (Tile t : possible.getData())
			{
				if (t.isEmpty())
				{
					numEmpty++;
				}
			}

			for (Tile t : possible.getData())
			{
				if (t.value == 0)
				{

					t.value = 2;
					Node nodeWithAdded2 = new Node(game.cloneTiles(possible.getData()), strategy);
					nodeWithAdded2.setProb(0.9 * (1 / (double) numEmpty));
					start.getChildren().add(nodeWithAdded2);
					if (!weLost(nodeWithAdded2))
					{
						buildTree(nodeWithAdded2, true, depth - 1);
					}
					t.value = 4;
					Node nodeWithAdded4 = new Node(game.cloneTiles(possible.getData()), strategy);
					nodeWithAdded4.setProb(0.1 * (1 / (double) numEmpty));
					start.getChildren().add(nodeWithAdded4);
					if (!weLost(nodeWithAdded4))
					{
						buildTree(nodeWithAdded4, true, depth - 1);
					}
					t.value = 0;
				}
			}

		}
	}

	private boolean tileArraysAreEqual(Tile[] arr1, Tile[] arr2)
	{
		for (int i = 0; i < 16; i++)
		{
			if (!arr1[i].equals(arr2[i]))
			{
				return false;
			}
		}
		return true;
	}

	private boolean weLost(Node node)
	{
		if (tileArraysAreEqual(game.whatIfLeft(node.getData()), node.getData()) && tileArraysAreEqual(game.whatIfRight(node.getData()), node.getData()) && tileArraysAreEqual(game.whatIfUp(node.getData()), node.getData()) && tileArraysAreEqual(game.whatIfDown(node.getData()), node.getData()))
		{
			return true;
		}
		return false;
	}

	private boolean weWon(Node node)
	{
		for (Tile t : node.getData())
		{
			if (t.equals(new Tile(2048)))
			{
				return true;
			}
		}
		return false;
	}

	public static void printTiles(Tile[] tiles)
	{
		for (Tile t : tiles)
		{
			System.out.print(t.value);
		}
		System.out.println("\n");
	}

	public static void main(String args[]) throws InterruptedException
	{
		Game2048 twentyFortyEight = new Game2048();
		
		String strategy = args[0];
		int depth = Integer.parseInt(args[1]);
		
		Agent2048 agent = new Agent2048(twentyFortyEight, strategy);
		
		JFrame game = new JFrame();
		game.setTitle("2048 Game");
		game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game.setSize(340, 400);
		game.setResizable(false);
		game.setVisible(true);
		game.add(twentyFortyEight);
		game.setLocationRelativeTo(null);

		agent.runAI(depth);

		int max = twentyFortyEight.getMyTiles()[0].value;
		for (Tile t : twentyFortyEight.getMyTiles())
		{
			if (t.value > max)
			{
				max = t.value;
			}
		}
		System.out.println(max);
		System.out.println((double) (stop - start) / 1000000000);
		long maxBytes = Runtime.getRuntime().maxMemory();
		System.out.println(maxBytes);
	}
}
