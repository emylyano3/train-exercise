package mule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import static org.junit.Assert.*;

import mule.graph.loader.GraphLoader;
import mule.graph.model.IGraph;

public class GraphLoaderTest {

	@Test
	public void load_FILE () throws FileNotFoundException {
		IGraph g = new GraphLoader().loadGraph(new File(getClass().getResource("/mulevania.graph").getFile()));
		assertNotNull(g);
		assertNotNull(g.getEdges());
		assertFalse(g.getEdges().isEmpty());
		assertEquals(9, g.getEdges().size());
		assertFalse(g.getNodes().isEmpty());
		assertEquals(5, g.getNodes().size());
		assertNotNull(g.getNode("A"));
		assertEquals("A", g.getNode("A").getName());
	}

	@Test
	public void load_STRING () throws IOException {
		InputStream is = getClass().getResourceAsStream("/mulevania.graph");
		byte[] bytes = new byte[is.available()];
		is.read(bytes);
		IGraph g = new GraphLoader().loadGraph(new String(bytes));
		assertNotNull(g);
		assertNotNull(g.getEdges());
		assertFalse(g.getEdges().isEmpty());
		assertEquals(9, g.getEdges().size());
		assertFalse(g.getNodes().isEmpty());
		assertEquals(5, g.getNodes().size());
		assertNotNull(g.getNode("A"));
		assertEquals("A", g.getNode("A").getName());
	}

	@Test
	public void load_BYTES () throws IOException {
		InputStream is = getClass().getResourceAsStream("/mulevania.graph");
		byte[] bytes = new byte[is.available()];
		is.read(bytes);
		IGraph g = new GraphLoader().loadGraph(bytes);
		assertNotNull(g);
		assertNotNull(g.getEdges());
		assertFalse(g.getEdges().isEmpty());
		assertEquals(9, g.getEdges().size());
		assertFalse(g.getNodes().isEmpty());
		assertEquals(5, g.getNodes().size());
		assertNotNull(g.getNode("A"));
		assertEquals("A", g.getNode("A").getName());
	}
}
