package mule;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

import static org.junit.Assert.*;

import mule.graph.loader.GraphLoader;
import mule.graph.model.IGraph;

public class GraphLoaderTest {

	@Test
	public void load () throws FileNotFoundException {
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
}
