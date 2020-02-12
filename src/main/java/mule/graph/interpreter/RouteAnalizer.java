package mule.graph.interpreter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import mule.graph.NoSuchRouteException;
import mule.graph.model.Edge;
import mule.graph.model.IGraph;
import mule.graph.model.Node;

public class RouteAnalizer {
	private Map<Node, Map<Node, Integer>> matrix;

	public RouteAnalizer compile (IGraph g) {
		init(g);
		fill(g);
		return this;
	}

	public int getRouteDistance (Node... route) {
		int distance = 0;
		for (int i = 0; i < route.length - 1; ++i) {
			if (this.matrix.get(route[i]).get(route[i + 1]) == -1) {
				throw new NoSuchRouteException(Arrays.toString(route));
			} else {
				distance += this.matrix.get(route[i]).get(route[i + 1]);
			}
		}
		return distance;
	}

	private void init (IGraph g) {
		this.matrix = new HashMap<Node, Map<Node, Integer>>(g.getNodes().size());
		for (Node n : g.getNodes()) {
			this.matrix.put(n, new HashMap<>(g.getNodes().size()));
			for (Node m : g.getNodes()) {
				this.matrix.get(n).put(m, -1);
			}
		}
	}

	private void fill (IGraph g) {
		for (Edge e : g.getEdges()) {
			this.matrix.get(e.getFrom()).put(e.getTo(), e.getWeigth());
		}
	}
}
