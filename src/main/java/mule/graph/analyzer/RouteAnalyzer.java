package mule.graph.analyzer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import mule.graph.exception.NoSuchRouteException;
import mule.graph.model.Edge;
import mule.graph.model.IGraph;
import mule.graph.model.INode;
import mule.graph.model.Node;

public class RouteAnalyzer {
	private Map<Node, Map<Node, Integer>> matrix;

	public RouteAnalyzer compile (IGraph g) {
		init(g);
		fill(g);
		return this;
	}

	public int getRouteDistance (INode... route) {
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

	public int getRouteAlternatives (IGraph g, INode from, INode to, int limit, CounterType accumType, Predicate<Control> control) {
		return find(g, from, to, 0, 0, limit, accumType, control);
	}

	private int find (IGraph g, INode n, INode toFind, int matches, int current, int limit, CounterType accumType, Predicate<Control> control) {
		if (current <= limit) {
			for (Edge e : g.getEdges(n)) {
				if (e.getTo().equals(toFind) && control.test(new Control(e, current, limit))) {
					++matches;
				}
				matches = find(g, e.getTo(), toFind, matches, getCurrent(current, e, accumType), limit, accumType, control);
			}
		}
		return matches;
	}

	private int getCurrent (int current, Edge e, CounterType accumType) {
		return CounterType.STOPS.equals(accumType) ? current + 1 : current + e.getWeigth();
	}

	public static class Control {
		private Edge	edge;
		private int		current;
		private int		limit;

		public Edge getEdge () {
			return this.edge;
		}

		public void setEdge (Edge edge) {
			this.edge = edge;
		}

		public int getCurrent () {
			return this.current;
		}

		public void setCurrent (int current) {
			this.current = current;
		}

		public int getLimit () {
			return this.limit;
		}

		public void setLimit (int limit) {
			this.limit = limit;
		}

		public Control (Edge e, int current, int limit) {
			super();
			this.edge = e;
			this.current = current;
			this.limit = limit;
		}
	}

	public enum ControlType {
		AS_MUCH_AS,
		EXACT,
		LESS_THAN
	}

	public enum CounterType {
		STOPS,
		LENGTH
	}
}
