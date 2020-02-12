package mule.graph.interpreter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mule.graph.model.Edge;
import mule.graph.model.IGraph;
import mule.graph.model.Node;

public class GraphAnalyzer {

	// Usaremos un vector para guardar las distancias del nodo salida al resto
	private Map<Node, Integer>	distances	= new HashMap<>();
	// vector de boleanos para controlar los vértices de los que ya tenemos la distancia mínima
	private Set<Node>			checked		= new HashSet<>();

	public GraphAnalyzer compile (IGraph g, Node source) {
		for (Node node : g.getNodes()) {
			Edge r;
			if ((r = getNodesRelation(g, source, node)) != null) {
				this.distances.put(node, r.getWeigth());
			} else {
				this.distances.put(node, -1);
			}
		}
		this.checked.add(source);
		while (this.checked.size() < g.getNodes().size()) {
			Node v = getNextNode();
			for (Edge e : g.getEdges(v)) {
				this.distances.put(e.getTo(), getShortestDistance(e));
			}
			this.checked.add(v);
		}
		return this;
	}

	private int getShortestDistance (Edge e) {
		if (this.distances.get(e.getTo()) == -1 && this.distances.get(e.getFrom()) == -1) {
			return -1;
		} else if (this.distances.get(e.getTo()) == -1) {
			return this.distances.get(e.getFrom())  + e.getWeigth();
		} else {
			return this.distances.get(e.getTo());
		}
	}

	private Edge getNodesRelation (IGraph g, Node from, Node to) {
		for (Edge e : g.getEdges(from)) {
			if (e.getTo().equals(to)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * @returns the first node from the distance check map that has not been checked
	 */
	private Node getNextNode () {
		for (Node n : this.distances.keySet()) {
			if (!this.checked.contains(n)) {
				return n;
			}
		}
		return null;
	}

	public Map<Node, Integer> getDistances () {
		return this.distances;
	}

	public Integer getDistancesTo (Node n) {
		return this.distances.get(n) != null ? this.distances.get(n) : -1;
	}

}
