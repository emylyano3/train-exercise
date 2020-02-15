package mule.graph.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class GraphClient {

	public static void main (String... args) throws UnknownHostException, IOException {
		if (args.length < 2) {
			System.err.println("Enter the host and port parameters");
			System.exit(1);
		}
		new GraphClient().launch(args[0], Integer.parseInt(args[1]));
	}

	private void launch (String host, int port) {
		try (
			Socket socket = new Socket(host, port);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String fromServer, fromUser;
			while ((fromServer = in.readLine()) != null) {
				System.out.println("Server: " + fromServer);
				if (fromServer.equals("Bye.")) {
					break;
				}
				fromUser = stdIn.readLine();
				if (fromUser != null) {
					System.out.println("Client: " + fromUser);
					out.println(fromUser);
				}
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + host);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + host);
			System.exit(1);
		}
	}
}
