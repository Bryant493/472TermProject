package project;

import java.util.ArrayList;
import java.util.Arrays;

public class Node
{
	private Tile[] data;
	private Node parent;
	private ArrayList<Node> children;
	private int hVal;

	public Node(Tile[] data)
    {
		this.data = data;
       	children = new ArrayList<Node>();
    }

	public void setParent(Node parent)
	{
		this.parent = parent;
	}

	public ArrayList<Node> getChildren()
	{
		return children;
	}

	public Node getParent()
	{
		return parent;
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
		return Arrays.equals(this.getData(), other.getData());
	}
}