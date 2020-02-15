package mule.graph.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class GraphService implements Runnable {

	private static int				clientId;

	private final Socket			client;
	private GraphServiceProtocol	protocol	= new GraphServiceProtocol();

	public GraphService (Socket client) {
		super();
		this.client = client;
	}

	@Override
	public void run () {
		try (
			PrintWriter out = new PrintWriter(new OutputStreamWriter(this.client.getOutputStream()), true)) {
			BufferedReader in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			out.println("Welcome client " + ++GraphService.clientId);
			String input;
			while ((input = in.readLine()) != null) {
				out.println(this.protocol.processInput(input));
			}
		} catch (IOException e) {
			System.err.println("I/O Error");
		}
	}

	public Socket getClient () {
		return this.client;
	}
}
