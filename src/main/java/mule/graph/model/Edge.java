package mule.graph.model;

public class Edge {
	private Node	from;
	private Node	to;
	private int		weigth;

	public Edge (Node from, Node to, int weigth) {
		super();
		this.from = from;
		this.to = to;
		this.weigth = weigth;
	}

	public Node getFrom () {
		return this.from;
	}

	public void setFrom (Node from) {
		this.from = from;
	}

	public Node getTo () {
		return this.to;
	}

	public void setTo (Node to) {
		this.to = to;
	}

	public int getWeigth () {
		return this.weigth;
	}

	public void setWeigth (int weigth) {
		this.weigth = weigth;
	}

	@Override
	public String toString () {
		return this.from + ":" + this.to + "(" + this.weigth + ")";
	}
}
