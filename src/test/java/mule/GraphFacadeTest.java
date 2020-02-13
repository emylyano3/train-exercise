package mule;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import mule.graph.GraphFacade;
import mule.graph.exception.NoSuchRouteException;
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
	 * 6. The number of trips starting at C and ending at C with a maximum of 3 stops. In the sample data below, there
	 * are two such trips: C-D-C (2 stops). and C-EB-C (3 stops).
	 */
	@Test
	public void getRouteAlternatives_CC_STOPS () {
		assertEquals(2, this.facade.getNumberOfTripsWithMaxStops(graph, graph.getNode("C"), graph.getNode("C"), 3));
	}

	/**
	 * 7. The number of trips starting at A and ending at C with exactly 4 stops. In the sample data below, there are
	 * three such trips: A to C (via B,C,D); A to C(via D,C,D); and A to C (via D,E,B).
	 */
	@Test
	public void getRouteAlternatives_AC_STOPS () {
		assertEquals(3, this.facade.getNumberOfTripsWithStops(graph, graph.getNode("A"), graph.getNode("C"), 4));
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

	/**
	 * 10. The number of different routes from C to C with a distance of less than 30. In the sample data, the trips
	 * are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
	 */
	@Test
	public void getRouteAlternatives_CC_WEIGHT () {
		assertEquals(7, this.facade.getNumberOfTripsWithLength(graph, graph.getNode("C"), graph.getNode("C"), 30));
	}
}
