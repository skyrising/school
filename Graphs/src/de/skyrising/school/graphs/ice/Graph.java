package de.skyrising.school.graphs.ice;

import java.util.*;

public class Graph
{
	private ArrayList<Node> nodes = new ArrayList<Node>();
	
	public Graph()
	{
		
	}
	
	public ArrayList<Node> getNodes()
	{
		return nodes;
	}
	
	public void addNode(Node n)
	{
		nodes.add(n);
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(Node n : nodes)
			sb.append(n).append('\n');
		return sb.toString();
	}
	
	public List<Node> getRoute(Node from, Node to) {
		return getRoute(from, to, Integer.MAX_VALUE);
	}
	
	public List<Node> getRoute(Node from, Node to, int shorterThan) {
		ArrayList<Node> route = new ArrayList<Node>();
		List<Node> shortest = null;
		int dist = shorterThan;
		for(Node n : nodes) {
			if(n.equals(to)) {
				route.add(n);
				break;
			}else {
				int disttemp = 0;
				List<Node> r2 = getRoute(n, to);
				Node last = null;
				for(Node n1 : r2)
					if(last == null)
						last = n1;
					else {
						disttemp+=last.getConnectedTo(n1);
						last = n1;
					}
				if(disttemp<dist) {
					shortest = r2;
				}
			}
		}
		if(shortest != null)
			route.addAll(shortest);
		return route;
	}
	
	
	
	
}
