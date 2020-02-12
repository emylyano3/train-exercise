package mule;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import mule.graph.GraphFacade;
import mule.graph.loader.GraphLoader;
import mule.graph.model.IGraph;

public class GraphFacadeTest {

	private GraphFacade		facade	= new GraphFacade();
	private static IGraph	graph;

	@BeforeClass
	public static void loadGraph () {
		graph = new GraphLoader().loadGraph(new File(GraphFacadeTest.class.getResource("/mulevania.graph").getFile()));
		assertNotNull(graph);
		assertNotNull(graph.getEdges());
		assertFalse(graph.getEdges().isEmpty());
		assertEquals(9, graph.getEdges().size());
	}

	@Test
	public void getShortestRouteLength () {
		assertEquals(9, this.facade.getShortestRouteLength(graph, graph.getNode("A"), graph.getNode("C")));
		assertEquals(9, this.facade.getShortestRouteLength(graph, graph.getNode("B"), graph.getNode("B")));
	}
}
