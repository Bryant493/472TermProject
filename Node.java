package project;

import java.util.ArrayList;
import java.util.Arrays;

public class Node
{
	private Tile[] data;
	private ArrayList<Node> children;
	private int hVal;
	private double prob;

	public Node(Tile[] data)
    {
		this.data = data;
       	children = new ArrayList<Node>();
       	hVal = HeuristicCalc.getHeuristic(data);
    }

	@Override
	public String toString()
	{
		String ret = "";
		for(int i = 0; i < 16; i++)
		{
			ret += data[i] + ", ";
			if(i == 3 || i == 7 || i == 11 || i == 15)
			{
				ret = ret.substring(0, ret.length() - 2) + "\n";
			}
		}
		return ret;
	}

	public ArrayList<Node> getChildren()
	{
		return children;
	}

	public void addChild(Node node)
	{
		children.add(node);
	}
	
	public void setData(Tile[] data)
	{
		this.data = data;
	}
	
	public Tile[] getData()
	{
		return data;
	}
	
	public int getHVal()
	{
		return hVal;
	}
	
	public void setHVal(int hVal)
	{
		this.hVal = hVal;
	}
	
	public boolean equals(Node other)
	{
		for(int i = 0; i < this.getData().length; i++)
		{
			if(!(this.getData()[i].equals(other.getData()[i])))
			{
				return false;
			}
		}
		return true;
	}

	public double getProb() {
		return prob;
	}

	public void setProb(double prob) {
		this.prob = prob;
	}
}