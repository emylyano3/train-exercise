package mule.graph.network;

import java.io.IOException;
import java.net.ServerSocket;

public class GraphServer {

	public static void main (String... args) throws IOException {
		if (args.length < 1) {
			System.out.println("Enter the port parameter");
			System.exit(1);
		}
		new GraphServer().launch(Integer.parseInt(args[0]));
	}

	public void launch (int port) throws IOException {
		System.out.println("Graph Server Started");
		try (ServerSocket ss = new ServerSocket(port)) {
			while (true) {
				new Thread(new GraphService(ss.accept())).start();
				System.out.println("New client accepted");
			}
		}
	}
}
