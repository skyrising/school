package de.skyrising.school.graphs.ice;

public class DirectRoute implements Comparable<DirectRoute>
{
	private Node from;
	private Node to;
	
	public DirectRoute(Node from, Node to) {
		this.from = from;
		this.to = to;
	}
	
	public Node getFrom() {
		return from;
	}
	
	public Node getTo() {
		return to;
	}
	
	public int getDistance() {
		return from.getConnectedTo(to);
	}
	
	@Override
	public int compareTo(DirectRoute o)
	{
		return o.getDistance()-getDistance();
	}
}
