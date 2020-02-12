package mule.graph.model;

import java.util.Set;

public interface IGraph {

	Set<Edge> getEdges ();

	Set<Edge> getEdges (Node n);

	void setEdges (Set<Edge> edges);

	void addEdge (Edge edge);

	Node getNode (String name);

	Set<Node> getNodes ();

	void setNodes (Set<Node> nodes);
}
