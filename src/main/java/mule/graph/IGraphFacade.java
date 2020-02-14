package mule.graph;

import mule.graph.model.IGraph;
import mule.graph.model.INode;

public interface IGraphFacade {

	/**
	 * Returns the distance of a route that is conformed by a series of nodes
	 */
	int getRouteDistance (IGraph g, INode... route);

	/**
	 * Returns the # of possible trips between two nodes with a number of stops that is less or equals to the max stops
	 * specified.
	 */
	int getNumberOfTripsWithMaxStops (IGraph g, INode from, INode to, int maxStops);

	/**
	 * Returns the # of possible trips between two nodes with a number of stops that is equals to the stops specified.
	 */
	int getNumberOfTripsWithStops (IGraph g, INode from, INode to, int stops);

	/**
	 * Returns the # of possible trips between two nodes with a weight (length) that is less than the length specified.
	 */
	int getNumberOfTripsWithLength (IGraph g, INode from, INode to, int length);

	/**
	 * Returns the length of the shortest route between two nodes.
	 */
	int getShortestRoute (IGraph g, INode from, INode to);
}
