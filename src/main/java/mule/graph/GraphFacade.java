package mule.graph;

import mule.graph.interpreter.RouteAnalizer;
import mule.graph.interpreter.ShortPathAnalyzer;
import mule.graph.model.IGraph;
import mule.graph.model.Node;

public class GraphFacade {
	public int getRouteDistance (IGraph g, Node... route) {
		return new RouteAnalizer().compile(g).getRouteDistance(route);
	}

	public int getNumberOfTripsWithMaxStops (IGraph g, Node from, Node to, int maxStops) {
		return 0;
	}

	public int getNumberOfTripsWithStops (IGraph g, Node from, Node to, int stops) {
		return 0;
	}

	public int getShortestRouteLength (IGraph g, Node from, Node to) {
		ShortPathAnalyzer ga = new ShortPathAnalyzer();
		ga.compile(g, from);
		return ga.getDistancesTo(to);
	}

	public int getNumberOfTripsWithLength (IGraph g, Node from, Node to, int length) {
		return 0;
	}
}
