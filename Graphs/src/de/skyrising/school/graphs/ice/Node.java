package de.skyrising.school.graphs.ice;

import java.util.*;

public class Node
{
	private Map<Node,Integer> connected = new HashMap<Node,Integer>();
	private String name;
	
	public Node(String name)
	{
		this.name = name;
	}
	
	public Collection<Node> getConnected()
	{
		return connected.keySet();
	}
	
	public boolean isConnectedTo(Node n)
	{
		return connected.containsKey(n);
	}
	
	public int getConnectedTo(Node n)
	{
		return connected.get(n);
	}
	
	public Node addConnected(Node n, int time)
	{
		if(!isConnectedTo(n))
			connected.put(n, time);
		return this;
	}
	
	public Node addConnectedBoth(Node n, int time)
	{
		addConnected(n, time);
		n.addConnected(this, time);
		return this;
	}
	
	public String getName()
	{
		return name;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(name).append('[');
		int i = 0;
		for(Node n : connected.keySet()) {
			if(i>0)sb.append(',');
			sb.append(n.getName()).append(':').append(connected.get(n));
			i++;
		}
		return sb.append(']').toString();
	}
}
