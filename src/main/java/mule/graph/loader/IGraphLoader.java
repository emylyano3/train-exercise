package mule.graph.loader;

import java.io.File;

import mule.graph.model.IGraph;

public interface IGraphLoader {
	IGraph loadGraph (File graphFile);

	IGraph loadGraph (String graph);

	IGraph loadGraph (byte[] graph);
}
