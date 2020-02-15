package mule.graph.network;

import java.util.Arrays;

import mule.graph.GraphFacade;
import mule.graph.IGraphFacade;
import mule.graph.exception.NoSuchRouteException;
import mule.graph.loader.GraphLoader;
import mule.graph.loader.IGraphLoader;
import mule.graph.model.IGraph;
import mule.graph.model.INode;

public class GraphServiceProtocol {

	private static final String	CMD_SHORTEST_ROUTE			= "-sr";
	private static final String	CMD_TRIPS_WITH_LENGTH		= "-twl";
	private static final String	CMD_TRIPS_WITH_STOPS		= "-tws";
	private static final String	CMD_TRIPS_WITH_MAX_STOPS	= "-twms";
	public static final String	CMD_ROUTE_DISTANCE			= "-rd";

	private IGraphFacade		graphFacade					= new GraphFacade();
	private IGraphLoader		graphLoader					= new GraphLoader();

	public String processInput (String input) {
		if (input == null || input.isEmpty()) {
			return "Params must not be empty";
		}
		String[] chunks = input.split(" ");
		if (chunks.length < 2) {
			return "Invalid params " + input;
		}
		switch (chunks[0]) {
			case CMD_ROUTE_DISTANCE:
				IGraph g = this.graphLoader.loadGraph(chunks[1]);
				INode[] nodes = new INode[chunks.length - 2];
				for (int i = 0; i < nodes.length; i++) {
					nodes[i] = g.getNode(chunks[i + 2]);
				}
				try {
					return "The length of the route is " + this.graphFacade.getRouteDistance(g, nodes);
				} catch (NoSuchRouteException e) {
					return "The specified route does not exist " + Arrays.toString(nodes);
				}
			case CMD_TRIPS_WITH_MAX_STOPS:
				return "getNumberOfTripsWithMaxStops not implemented";
			case CMD_TRIPS_WITH_STOPS:
				return "getNumberOfTripsWithStops not implemented";
			case CMD_TRIPS_WITH_LENGTH:
				return "getNumberOfTripsWithLength not implemented";
			case CMD_SHORTEST_ROUTE:
				return "getShortestRoute not implemented";
			default:
				return new StringBuilder()
					.append("Unknown command ")
					.append(chunks[0])
					.append(". Available commands are: ")
					.append(CMD_ROUTE_DISTANCE)
					.append(" ")
					.append(CMD_SHORTEST_ROUTE)
					.append(" ")
					.append(CMD_TRIPS_WITH_LENGTH)
					.append(" ")
					.append(CMD_TRIPS_WITH_MAX_STOPS)
					.append(" ")
					.append(CMD_TRIPS_WITH_STOPS)
					.toString();
		}
	}
}
