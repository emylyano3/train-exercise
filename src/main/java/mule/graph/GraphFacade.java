package mule.graph;

import mule.graph.interpreter.GraphAnalyzer;
import mule.graph.model.IGraph;
import mule.graph.model.Node;

public class GraphFacade {
	public int getRouteDistance (IGraph g, Node... route) {
		GraphAnalyzer ga = new GraphAnalyzer();
		ga.compile(g, route[0]);
		int distance = 0;
		for (Node node : route) {
			distance += ga.getDistancesTo(node);
		}
		return distance;
	}

	public int getNumberOfTripsWithMaxStops (IGraph g, Node from, Node to, int maxStops) {
		return 0;
	}

	public int getNumberOfTripsWithStops (IGraph g, Node from, Node to, int stops) {
		return 0;
	}

	public int getShortestRouteLength (IGraph g, Node from, Node to) {
		GraphAnalyzer ga = new GraphAnalyzer();
		ga.compile(g, from);
		return ga.getDistancesTo(to);
	}

	public int getNumberOfTripsWithLength (IGraph g, Node from, Node to, int length) {
		return 0;
	}
}
