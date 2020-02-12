package mule.graph.model;

import java.util.HashSet;
import java.util.Set;

/**
 * The local commuter railroad services a number of towns in Mulevania. Because of monetary concerns, all of the tracks
 * are 'one-way.' That is, a route from Tomcata to Esby does not imply the existence of a route from Esby to Tomcata. In
 * fact, even if both of these routes do happen to exist, they are distinct and are not necessarily the same distance!
 * The purpose of this problem is to help the railroad provide its customers with information about the routes. In
 * particular, you will compute the distance along a certain route, the number of different routes between two towns,
 * and the shortest route between two towns.
 *
 * A directed graph where a node represents a town and an edge represents a route between two towns. The weighting of
 * the edge represents the distance between the two towns. A given route will never appear more than once, and for a
 * given route, the starting and ending town will not be the same town.
 *
 * Routes one way</br>
 * Routes not repeated</br>
 * Routes don't loop</br>
 *
 */
public class Graph implements IGraph {
	private Set<Edge>		edges;
	private Set<Node>		nodes;

	public Graph () {
		super();
		this.edges = new HashSet<>();
	}

	@Override
	public Set<Edge> getEdges () {
		return this.edges;
	}

	@Override
	public Set<Edge> getEdges (Node n) {
		Set<Edge> result = new HashSet<>();
		for (Edge e : this.edges) {
			if (e.getFrom().equals(n)) {
				result.add(e);
			}
		}
		return result;
	}

	@Override
	public void setEdges (Set<Edge> edges) {
		this.edges = edges;
	}

	@Override
	public void addEdge (Edge edge) {
		this.edges.add(edge);
	}

	@Override
	public Set<Node> getNodes () {
		return this.nodes;
	}

	@Override
	public Node getNode (String name) {
		for (Node n : this.nodes) {
			if (n.getName().equals(name)) {
				return n;
			}
		}
		return null;
	}

	@Override
	public void setNodes (Set<Node> nodes) {
		this.nodes = nodes;
	}

}
