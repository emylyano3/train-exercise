package mule.graph.analyzer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import mule.graph.NoSuchRouteException;
import mule.graph.model.Edge;
import mule.graph.model.IGraph;
import mule.graph.model.Node;

public class RouteAnalyzer {
	private Map<Node, Map<Node, Integer>> matrix;

	public RouteAnalyzer compile (IGraph g) {
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

	public int getRouteAlternatives (IGraph g, Node from, Node to, int limit, AccumType accumType, ControlType controlType) {
		return find(g, from, to, 0, 0, limit, accumType, controlType);
	}

	private int find (IGraph g, Node n, Node toFind, int matches, int current, int limit, AccumType accumType, ControlType controlType) {
		MatchControl ct = getCutControl(controlType);
		for (Edge e : g.getEdges(n)) {
			if (e.getTo().equals(toFind) && ct.applies(current, limit)) {
				++matches;
			}
			if (current < limit) {
				matches = find(g, e.getTo(), toFind, matches, getCurrent(current, e, accumType), limit, accumType, controlType);
			}
		}
		return matches;
	}

	private int getCurrent (int current, Edge e, AccumType accumType) {
		return AccumType.STOPS.equals(accumType) ? current + 1 : current + e.getWeigth();
	}

	interface MatchControl {

		boolean applies (int current, int control);
	}

	class ExactControl implements MatchControl {

		@Override
		public boolean applies (int current, int control) {
			return current == control - 1;
		}
	}

	class AsMuchAsControl implements MatchControl {

		@Override
		public boolean applies (int current, int control) {
			return current < control;
		}
	}

	class LessThanControl implements MatchControl {

		@Override
		public boolean applies (int current, int control) {
			return current < control - 1;
		}
	}

	private MatchControl getCutControl (ControlType ct) {
		switch (ct) {
			case AS_MUCH_AS:
				return new AsMuchAsControl();
			case LESS_THAN:
				return new LessThanControl();
			case EXACT:
			default:
				return new ExactControl();
		}
	}

	public enum ControlType {
		AS_MUCH_AS,
		EXACT,
		LESS_THAN
	}

	public enum AccumType {
		STOPS,
		LENGTH
	}
}
