package mule.graph;

import mule.graph.analyzer.RouteAnalyzer;
import mule.graph.analyzer.ShortPathAnalyzer;
import mule.graph.model.IGraph;
import mule.graph.model.INode;

public class GraphFacade {
	public int getRouteDistance (IGraph g, INode... route) {
		return new RouteAnalyzer().compile(g).getRouteDistance(route);
	}

	/**
	 * Returns the # of possible trips between two nodes with a number of stops that is less or equals to the max stops
	 * specified.
	 */
	public int getNumberOfTripsWithMaxStops (IGraph g, INode from, INode to, int maxStops) {
		return new RouteAnalyzer().getRouteAlternatives(g, from, to, maxStops, RouteAnalyzer.AccumType.STOPS, RouteAnalyzer.ControlType.AS_MUCH_AS);
	}

	/**
	 * Returns the # of possible trips between two nodes with a number of stops that is equals to the stops specified.
	 */
	public int getNumberOfTripsWithStops (IGraph g, INode from, INode to, int stops) {
		return new RouteAnalyzer().getRouteAlternatives(g, from, to, stops, RouteAnalyzer.AccumType.STOPS, RouteAnalyzer.ControlType.EXACT);
	}

	/**
	 * Returns the # of possible trips between two nodes with a weight (length) that is less than the length specified.
	 */
	public int getNumberOfTripsWithLength (IGraph g, INode from, INode to, int length) {
		return new RouteAnalyzer().getRouteAlternatives(g, from, to, length, RouteAnalyzer.AccumType.LENGTH, RouteAnalyzer.ControlType.LESS_THAN);
	}

	public int getShortestRouteLength (IGraph g, INode from, INode to) {
		ShortPathAnalyzer ga = new ShortPathAnalyzer();
		ga.compile(g, from);
		return ga.getDistancesTo(to);
	}
}
