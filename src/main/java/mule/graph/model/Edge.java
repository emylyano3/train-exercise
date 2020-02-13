package mule.graph.model;

public class Edge {
	private INode	from;
	private Node	to;
	private int		weigth;

	public Edge (INode from, Node to, int weigth) {
		super();
		this.from = from;
		this.to = to;
		this.weigth = weigth;
	}

	public INode getFrom () {
		return this.from;
	}

	public void setFrom (INode from) {
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
