package project;

public class HeuristicCalc {

	static boolean horizontal;
	static final int LEFT = 0;
	static final int RIGHT = 1;
	static final int UP = 2;
	static final int DOWN = 3;
	
	public static int getHeuristic(Tile[] tiles) {
		int[] highestEdge = getHighestEdgeRow(tiles);
		int score = orderlinessScore(highestEdge);
		score += edgeScore(tiles);
		score += openBlocksScore(tiles);
		score += monotonicScore(tiles);
		score += highestBlockScore(tiles);

		return score;
	}
	
	private static int highestBlockScore(Tile[] tiles) {
		int max = 0;
		for (int i = 0; i < 16; i++)
		{
			if(tiles[i].value > max) {
				max = tiles[i].value;
			}
		}

		// treat 65536 block (2^16) as the highest possible block
		int percent = (int) (max / 65536 * 100);

		return Math.max(100, percent);
	}

	private static int monotonicScore(Tile[] tiles) {
		int numMonotonic = 0;
		for (int i = 0; i < 4; i++)
		{
			Tile[] row = getRow(i, tiles);
			Tile[] col = getCol(i, tiles);
			if (isMonotonic(row))
			{
				numMonotonic++;
			}
			if (isMonotonic(col))
			{
				numMonotonic++;
			}
		}
		
		// maximum numMonotonic is 8 (rows + columns)
		return (int) ((double)numMonotonic / 8 * 100);
	}

	private static Tile[] getRow(int rowNum, Tile[] tiles) {
		Tile[] row = new Tile[4];
		for (int x = 0; x < 4; x++)
		{
			row[x] = tiles[x + rowNum * 4];
		}
		return row;
	}

	private static Tile[] getCol(int colNum, Tile[] tiles) {
		Tile[] col = new Tile[4];
		for (int y = 0; y < 4; y++)
		{
			col[y] = tiles[colNum + y * 4];
		}
		return col;
	}

	private static boolean isMonotonic(Tile[] line) {
		for (int i = 1; i < 3; i++)
		{
			boolean increasingBefore = line[i - 1].value < line[i].value;
			boolean increasingAfter = line[i].value < line[i + 1].value;
			if (increasingBefore != increasingAfter)
			{
				return false;
			}
		}
		return true;
	}

	private static int openBlocksScore(Tile[] tiles) {
		int numOpenBlocks = 0;
		for (int i = 0; i < 16; i++)
		{
			if (tiles[i].value == 0)
			{
				numOpenBlocks++;
			}
		}
		
		// total numOpenBlocks is 16
		return (int) ((double)numOpenBlocks / 16 * 100);
	}

	private static int edgeScore(Tile[] tiles) {
		int outerSquareSum = 0;
		int allSquareSum = 0;
		for (int y = 0; y < 4; y++)
		{
			for (int x = 0; x < 4; x++)
			{
				int tileVal = tiles[x + y * 4].value;
				allSquareSum += tileVal;
				if (y == 0 || y == 3 || x == 0 || x == 3)
				{
					outerSquareSum += tileVal;
				}
			}
		}
		return (int) (((double) allSquareSum / outerSquareSum) * 100);
	}

	private static int orderlinessScore(int[] highestEdge) {
		int score = 0;
		
		for (int i = 1; i < 3; i++)
		{
			boolean increasingBefore = highestEdge[i-1] < highestEdge[i];
			boolean increasingNow = highestEdge[i] < highestEdge[i+1];
			if ((increasingBefore && increasingNow) || 
				(!increasingBefore && !increasingNow)) 
			{
				++score;
			}
		}
		
		return (int) ((double)score / 3 * 100);
	}

	private static int[] getHighestEdgeRow(Tile[] tiles) {
		int[] sums = new int[4];
		int[][] edges = new int[4][4];
		
		for (int i = 0; i < 4; i++) {
			// Tile (x, y) is located at tiles[x + y * 4]
			edges[LEFT][i] = tiles[0 + i * 4].value;
			edges[RIGHT][i] = tiles[3 + i * 4].value;
			edges[UP][i] = tiles[i + 0 * 4].value;
			edges[DOWN][i] = tiles[i + 3 * 4].value;
			
			sums[LEFT] += edges[LEFT][i];
			sums[RIGHT] += edges[RIGHT][i];
			sums[UP] += edges[UP][i];
			sums[DOWN] += edges[DOWN][i];
		}
		
		int max = Integer.MIN_VALUE;
		int maxi = 0;
		for (int i = 0; i < 4; i++)
		{
			if (sums[i] > max)
			{
				max = sums[i];
				maxi = i;
			}
		}
		
		return edges[maxi];
	}
}
