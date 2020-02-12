package mule;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import mule.graph.GraphFacade;
import mule.graph.NoSuchRouteException;
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

	/**
	 * 1. The distance of the route A-B-C.
	 */
	@Test
	public void getRouteDistance_ABC () {
		assertEquals(9, this.facade.getRouteDistance(graph, graph.getNode("A"), graph.getNode("B"), graph.getNode("C")));
	}

	/**
	 * 2. The distance of the route A-D.
	 */
	@Test
	public void getRouteDistance_AD () {
		assertEquals(5, this.facade.getRouteDistance(graph, graph.getNode("A"), graph.getNode("D")));
	}

	/**
	 * 3. The distance of the route A-D-C.
	 */
	@Test
	public void getRouteDistance_ADC () {
		assertEquals(13, this.facade.getRouteDistance(graph, graph.getNode("A"), graph.getNode("D"), graph.getNode("C")));
	}

	/**
	 * 4. The distance of the route A-E-B-C-D.
	 */
	@Test
	public void getRouteDistance_AEBCD () {
		assertEquals(22,
			this.facade.getRouteDistance(graph, graph.getNode("A"), graph.getNode("E"), graph.getNode("B"), graph.getNode("C"), graph.getNode("D")));
	}

	/**
	 * 5. The distance of the route A-E-D.
	 */
	@Test(expected = NoSuchRouteException.class)
	public void getRouteDistance_AED () {
		this.facade.getRouteDistance(graph, graph.getNode("A"), graph.getNode("E"), graph.getNode("D"));
	}

	/**
	 * 8. The length of the shortest route (in terms of distance to travel) from A to C.
	 */
	@Test
	public void getShortestRouteLength_AC () {
		assertEquals(9, this.facade.getShortestRouteLength(graph, graph.getNode("A"), graph.getNode("C")));
	}

	/**
	 * 9. The length of the shortest route (in terms of distance to travel) from B to B.
	 */
	@Test
	public void getShortestRouteLength_BB () {
		assertEquals(9, this.facade.getShortestRouteLength(graph, graph.getNode("B"), graph.getNode("B")));
	}
}
