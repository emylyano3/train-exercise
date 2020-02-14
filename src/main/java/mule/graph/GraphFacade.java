package mule.graph;

import mule.graph.analyzer.RouteAnalyzer;
import mule.graph.analyzer.ShortPathAnalyzer;
import mule.graph.model.IGraph;
import mule.graph.model.INode;

public class GraphFacade implements IGraphFacade {

	@Override
	public int getRouteDistance (IGraph g, INode... route) {
		return new RouteAnalyzer().compile(g).getRouteDistance(route);
	}

	@Override
	public int getNumberOfTripsWithMaxStops (IGraph g, INode from, INode to, int maxStops) {
		return new RouteAnalyzer().getRouteAlternatives(g, from, to, 1, maxStops, (c) -> c.getTripLength() + 1, (c) -> c.getTripLength() <= c.getLimit());
	}

	@Override
	public int getNumberOfTripsWithStops (IGraph g, INode from, INode to, int stops) {
		return new RouteAnalyzer().getRouteAlternatives(g, from, to, 1, stops, (c) -> c.getTripLength() + 1, (c) -> c.getTripLength() == c.getLimit());
	}

	@Override
	public int getNumberOfTripsWithLength (IGraph g, INode from, INode to, int length) {
		return new RouteAnalyzer()
			.getRouteAlternatives(g,
				from,
				to,
				0,
				length,
				(c) -> c.getTripLength() + c.getEdge().getWeigth(),
				(c) -> c.getTripLength() + c.getEdge().getWeigth() < c.getLimit());
	}

	@Override
	public int getShortestRoute (IGraph g, INode from, INode to) {
		return new ShortPathAnalyzer().compile(g, from).getDistancesTo(to);
	}
}
