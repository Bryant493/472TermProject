package project;

import java.util.ArrayList;

public class Tree
{
	private Tile[] data;
	private Tree parent;
	private ArrayList<Tree> children;
	private int hVal;

	public Tree()
    {
       	children = new ArrayList<Tree>();
    }

	public void setParent(Tree parent)
	{
		this.parent = parent;
	}

	public ArrayList<Tree> getChildren()
	{
		return children;
	}

	public Tree getParent()
	{
		return parent;
	}

	public void addChild(Tree node)
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
}