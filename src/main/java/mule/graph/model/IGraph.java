package mule.graph.model;

import java.io.Serializable;
import java.util.Set;

public interface IGraph extends Serializable {

	Set<Edge> getEdges ();

	Set<Edge> getEdges (INode n);

	void setEdges (Set<Edge> edges);

	void addEdge (Edge edge);

	INode getNode (String name);

	Set<Node> getNodes ();

	void setNodes (Set<Node> nodes);
}
