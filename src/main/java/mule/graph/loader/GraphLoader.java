package mule.graph.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import mule.graph.model.Edge;
import mule.graph.model.Graph;
import mule.graph.model.IGraph;
import mule.graph.model.Node;

public class GraphLoader implements IGraphLoader {

	private static final int	WEIGTH_INDEX	= 2;
	private static final int	TO_INDEX		= 1;
	private static final int	FROM_INDEX		= 0;
	private static final String	EDGE_SPLITTER	= ",";
	private static final String	GRAPH_SPLITTER	= ";";

	private Map<String, Node>	nodes = new HashMap<String, Node>();

	@Override
	public IGraph loadGraph (File graphFile) {
		try (InputStream is = new FileInputStream(graphFile)) {
			byte[] bytes = new byte[is.available()];
			is.read(bytes);
			String graphDesc = new String(bytes);
			String[] edges = graphDesc.trim().split(GRAPH_SPLITTER);
			Graph g = new Graph();
			for (String edge : edges) {
				String[] parts = edge.trim().split(EDGE_SPLITTER);
				if (parts.length < 3) {
					throw new RuntimeException(
						String.format("Invalid graph data in file %s. Edge bad formatted %s", graphFile.getPath(), Arrays.toString(parts)));
				}
				g.addEdge(buildEdge(parts));
			}
			g.setNodes(new HashSet<>(this.nodes.values()));
			return g;
		} catch (IOException e) {
			throw new RuntimeException(String.format("File %s could not be loaded.", graphFile.getPath()), e);
		}
	}

	private Edge buildEdge (String[] parts) {
		return new Edge(buildNode(parts[FROM_INDEX]), new Node(parts[TO_INDEX]), Integer.parseInt(parts[WEIGTH_INDEX]));
	}

	private Node buildNode (String name) {
		if (!this.nodes.containsKey(name)) {
			this.nodes.put(name, new Node(name));
		}
		return this.nodes.get(name);
	}
}
