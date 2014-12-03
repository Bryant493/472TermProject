package project;

import java.util.ArrayList;
import java.util.Collections;

public class HeuristicCalc
{

	static boolean horizontal;
	static final int LEFT = 0;
	static final int RIGHT = 1;
	static final int UP = 2;
	static final int DOWN = 3;
	
	public static int getHeuristic(Tile[] tiles)
	{
		int[] highestEdge = getHighestEdgeRow(tiles);
		int score = 0;
		score += gradientScore(tiles);
//		score += snakeScore(tiles);
//		score += orderlinessScore(highestEdge);
//		score += edgeScore(tiles);
//		score += topBlockScore(tiles);
//		score += openBlocksScore(tiles);
//		score += monotonicScore(tiles);
//		score += highestBlockScore(tiles);
//		score += medianBlockScore(tiles);

		return score;
	}
	
	private static int gradientScore(Tile[] tiles) {
		int[] gradientTopLeft = new int[] {
				3, 2, 1, 0,
				2, 1, 0, -1,
				1, 0, -1, -2,
				0, -1, -2, -3
		};
		int[] gradientTopRight = new int[] {
				0, 1, 2, 3,
				-1, 0, 1, 2,
				-2, -1, 0, 1,
				-3, -2, -1, 0
		};
		int[] gradientBottomRight = new int[] {
				-3, -2, -1, 0,
				-2, -1, 0, 1,
				-1, 0, 1, 2,
				0, 1, 2, 3
		};
		int[] gradientBottomLeft = new int[] {
				0, -1, -2, -3,
				1, 0, -1, -2,
				2, 1, 0, -1,
				3, 2, 1, 0
		};
		
		int topLeftScore = 0;
		int topRightScore = 0;
		int bottomLeftScore = 0;
		int bottomRightScore = 0;
		for (int i = 0; i < 16; i++)
		{
			topLeftScore += tiles[i].value * gradientTopLeft[i];
			topRightScore += tiles[i].value * gradientTopRight[i];
			bottomLeftScore += tiles[i].value * gradientBottomLeft[i];
			bottomRightScore += tiles[i].value * gradientBottomRight[i];
		}
		int maxTop = Math.max(topLeftScore, topRightScore);
		int maxBottom = Math.max(bottomLeftScore, bottomRightScore);
		return Math.max(maxTop, maxBottom);
	}
	private static int combinableScore(Tile[] tiles) {
		// TODO: complete this method
		int score = 0;
		for(int i = 0; i < 4; i++) {
			int prevVal = 0;
			for(int j = 0; j < 4; j++) {
			}
		}
		return 0;
	}
	
	private static int topBlockScore(Tile[] tiles) {
		int total = 0;
		int topRow = 0;
		for (int i = 0; i < 16; i++)
		{
			total += tiles[i].value;
			if(i < 4) {
				topRow += tiles[i].value;
			}
		}
		return (int)((double) topRow / total * 100);
	}
	
	private static int medianBlockScore(Tile[] tiles) {
		ArrayList<Integer> tileValues = new ArrayList<Integer>();
		for (int i = 0; i < tiles.length; i++)
		{
			int tileValue = tiles[i].value;
			if (tileValue > 0)
			{
				tileValues.add(tileValue);
			}
		}
		
		Collections.sort(tileValues);
		
		int median;
		
		int numValues = tileValues.size();
		if (numValues == 0)
		{
			// don't worry about case where numValues == 0 because it is an impossible board position
			median = (tileValues.get(numValues / 2) + tileValues.get(numValues / 2 - 1)) / 2;
		}
		else
		{
			median = tileValues.get(numValues / 2);
		}
		
		// since 2^16 is the maximum block, maxMedian = (2^7 + 2^8) / 2
		int maxMedian = 192; 
		
		return (int) ((double) median / maxMedian * 100);
	}

	private static int highestBlockScore(Tile[] tiles)
	{
		int max = 0;
		for (int i = 0; i < 16; i++)
		{
			if (tiles[i].value > max)
			{
				max = tiles[i].value;
			}
		}

		// treat 65536 block (2^16) as the highest possible block
		int percent = (int) ((double) max / 65536 * 100);

		return Math.max(100, percent);
	}
	
	private static int snakeScore(Tile[] tiles)
	{
		int score = 0;
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if(j % 2 == 0) {
					
				}
				
			}
		}
		score += (tiles[12].value <= tiles[13].value) ? (8) : (0);
		score += (tiles[13].value <= tiles[14].value) ? (8) : (0);
		score += (tiles[14].value <= tiles[13].value) ? (8) : (0);
		score += (tiles[15].value <= tiles[11].value) ? (8) : (0);
		
		score += (tiles[11].value <= tiles[10].value) ? (4) : (0);
		score += (tiles[10].value <= tiles[9].value) ? (4) : (0);
		score += (tiles[9].value <= tiles[8].value) ? (4) : (0);
		score += (tiles[8].value <= tiles[4].value) ? (4) : (0);
		
		score += (tiles[4].value <= tiles[5].value) ? (2) : (0);
		score += (tiles[5].value <= tiles[6].value) ? (2) : (0);
		score += (tiles[6].value <= tiles[7].value) ? (2) : (0);
		score += (tiles[7].value <= tiles[3].value) ? (2) : (0);
		
		score += (tiles[3].value <= tiles[2].value) ? (1) : (0);
		score += (tiles[2].value <= tiles[1].value) ? (1) : (0);
		score += (tiles[1].value <= tiles[0].value) ? (1) : (0);
		
		return (int) ((double)score * 100);
	}

	private static int monotonicScore(Tile[] tiles)
	{
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
		return (int) ((double) numMonotonic / 8 * 100);
	}

	private static Tile[] getRow(int rowNum, Tile[] tiles)
	{
		Tile[] row = new Tile[4];
		for (int x = 0; x < 4; x++)
		{
			row[x] = tiles[x + rowNum * 4];
		}
		return row;
	}

	private static Tile[] getCol(int colNum, Tile[] tiles)
	{
		Tile[] col = new Tile[4];
		for (int y = 0; y < 4; y++)
		{
			col[y] = tiles[colNum + y * 4];
		}
		return col;
	}

	private static boolean isMonotonic(Tile[] line)
	{
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

	private static int openBlocksScore(Tile[] tiles)
	{
		int numOpenBlocks = 0;
		for (int i = 0; i < 16; i++)
		{
			if (tiles[i].value == 0)
			{
				numOpenBlocks++;
			}
		}
		
		// total numOpenBlocks is 16
		return (int) ((double) numOpenBlocks / 16 * 200);
	}

	private static int edgeScore(Tile[] tiles)
	{
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
		return (int) (((double) outerSquareSum / allSquareSum) * 100);
	}

	private static int orderlinessScore(int[] highestEdge)
	{
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
		
		return (int) ((double) score / 3 * 100);
	}

	private static int[] getHighestEdgeRow(Tile[] tiles)
	{
		int[] sums = new int[4];
		int[][] edges = new int[4][4];
		
		for (int i = 0; i < 4; i++)
		{
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
