package de.skyrising.school.graphs.ice;

public class Test
{

	public static void ice()
	{
		Graph g = new Graph();
		Node b = new Node("Berlin");
		Node dd = new Node("Dresden");
		Node ef = new Node("Erfurt");
		Node f = new Node("Frankfurt");
		Node h = new Node("Hannover");
		Node hb = new Node("Bremen");
		Node hh = new Node("Hamburg");
		Node k = new Node("Köln");
		Node ks = new Node("Kassel");
		Node l = new Node("Leipzig");
		Node m = new Node("München");
		Node n = new Node("Nürnberg");
		Node s = new Node("Stuttgart");
		Node wü = new Node("Würzburg");

		b.addConnectedBoth(h, 260).addConnectedBoth(hh, 280)
				.addConnectedBoth(l, 180);
		dd.addConnectedBoth(l, 140);
		ef.addConnectedBoth(f, 270).addConnectedBoth(ks, 140)
				.addConnectedBoth(l, 170).addConnectedBoth(n, 260)
				.addConnectedBoth(wü, 300);
		f.addConnectedBoth(k, 190).addConnectedBoth(ks, 190)
				.addConnectedBoth(s, 200).addConnectedBoth(wü, 130);
		h.addConnectedBoth(hb, 120).addConnectedBoth(hh, 150)
				.addConnectedBoth(ks, 240);
		hb.addConnectedBoth(hh, 110);
		m.addConnectedBoth(n, 160).addConnectedBoth(s, 210);
		n.addConnectedBoth(wü, 110);

		g.addNode(b);
		g.addNode(dd);
		g.addNode(ef);
		g.addNode(f);
		g.addNode(h);
		g.addNode(hb);
		g.addNode(hh);
		g.addNode(k);
		g.addNode(ks);
		g.addNode(l);
		g.addNode(m);
		g.addNode(n);
		g.addNode(s);
		g.addNode(wü);

		System.out.println(g.getRoute(m, n));
	}

	public static void main(String[] args)
	{

	}
}
