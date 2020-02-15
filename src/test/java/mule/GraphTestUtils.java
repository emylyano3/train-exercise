package mule;

import java.io.IOException;
import java.io.InputStream;

import mule.graph.loader.GraphLoader;
import mule.graph.model.IGraph;

public class GraphTestUtils {

	public static IGraph getGraph () throws IOException {
		InputStream is = GraphTestUtils.class.getResourceAsStream("/mulevania.graph");
		byte[] bytes = new byte[is.available()];
		is.read(bytes);
		return new GraphLoader().loadGraph(new String(bytes));
	}

	public static String getGraphString () throws IOException {
		InputStream is = GraphTestUtils.class.getResourceAsStream("/mulevania.graph");
		byte[] bytes = new byte[is.available()];
		is.read(bytes);
		return new String(bytes);
	}
}
