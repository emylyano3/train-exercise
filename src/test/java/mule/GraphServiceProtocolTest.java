package mule;

import java.io.IOException;

import org.junit.Test;

import static mule.graph.network.GraphServiceProtocol.*;
import static org.junit.Assert.*;

import mule.graph.network.GraphServiceProtocol;

public class GraphServiceProtocolTest {

	private GraphServiceProtocol protocol = new GraphServiceProtocol();

	@Test
	public void processInput_INVALID_ROUTE () throws IOException {
		String result = this.protocol.processInput(CMD_ROUTE_DISTANCE + " " + GraphTestUtils.getGraphString() + " A B D");
		assertNotNull(result);
		assertEquals("The specified route does not exist [A, B, D]", result);
	}

	@Test
	public void processInput_ROUTE_DISTANCE () throws IOException {
		String result = this.protocol.processInput(CMD_ROUTE_DISTANCE + " " + GraphTestUtils.getGraphString() + " A B C");
		assertNotNull(result);
		assertEquals("The length of the route is 9", result);
	}

	@Test
	public void processInput_UNKNOWN () throws IOException {
		String result = this.protocol.processInput("-xxx" + " " + GraphTestUtils.getGraphString() + " A B C");
		assertNotNull(result);
		assertTrue(result.contains("Unknown command -xxx"));
	}

	@Test
	public void processInput_NULL () throws IOException {
		String result = this.protocol.processInput(null);
		assertNotNull(result);
		assertTrue(result.contains("Params must not be empty"));
	}

	@Test
	public void processInput_EMPTY () throws IOException {
		String result = this.protocol.processInput("");
		assertNotNull(result);
		assertTrue(result.contains("Params must not be empty"));
	}

	@Test
	public void processInput_WRONG () throws IOException {
		String result = this.protocol.processInput("-xxx");
		assertNotNull(result);
		assertEquals("Invalid params -xxx", result);
	}
}
