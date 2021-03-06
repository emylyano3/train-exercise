package mule.graph.analyzer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
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

	/**
	 * @throws NoSuchRouteException
	 */
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

	/**
	 * Calculates how many different alternatives exist to go from one node no another inside a graph in a certain
	 * number of steps.
	 *
	 * @param limit
	 *            The number of steps
	 * @param odometer
	 *            The function to calculate the trip length.
	 * @param match
	 *            Evaluates the condition in which the route is valid.
	 */
	public int getRouteAlternatives (IGraph g, INode from, INode to, int startAt, int limit, Function<Control, Integer> odometer, Predicate<Control> match) {
		return find(g, from, to, 0, startAt, limit, odometer, match);
	}

	private int find (IGraph g, INode n, INode toFind, int matches, int length, int limit, Function<Control, Integer> odometer, Predicate<Control> match) {
		if (length <= limit) {
			for (Edge e : g.getEdges(n)) {
				if (e.getTo().equals(toFind) && match.test(new Control(e, length, limit))) {
					++matches;
				}
				matches = find(g, e.getTo(), toFind, matches, odometer.apply(new Control(e, length, limit)), limit, odometer, match);
			}
		}
		return matches;
	}

	public static class Control {
		private final Edge	edge;
		private final int	tripLength;
		private final int	limit;

		public Edge getEdge () {
			return this.edge;
		}

		public int getTripLength () {
			return this.tripLength;
		}

		public int getLimit () {
			return this.limit;
		}

		public Control (Edge e, int tripLength, int limit) {
			super();
			this.edge = e;
			this.tripLength = tripLength;
			this.limit = limit;
		}
	}
}
