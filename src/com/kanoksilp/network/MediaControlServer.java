/*
 *         Java Media Player (MP3) Project
 * -------------------------------------------------
 *  Author: Kanoksilp Jindadoungrut, Sci 5733652723
 *  Course: [2301260] PROGRAMMING TECHNIQUES / 2014
 * -------------------------------------------------
 */

package com.kanoksilp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

import static com.kanoksilp.util.TestingUtil.err;
import static com.kanoksilp.util.TestingUtil.out;

/**
 * @author Kanoksilp
 */
public class MediaControlServer implements Runnable {

	private Thread thread;
	private String threadName;

	private int portNumber = 10010;
	
	private ServerLogger logger;
	private CommandListener listener;
	private ServerStatusWatcher watcher;
	
	public enum ServerStatus {
		RUNNING_CONNECTED, RUNNING_NOT_CONNECTED, NOT_RUNNING
	}

	private ServerStatus status = ServerStatus.NOT_RUNNING;

	public MediaControlServer(String threadName, int portNumber) {
		this.threadName = threadName;
		this.portNumber = portNumber;
		out("[new] MediaControlServer(threadName=" + threadName
			+ ",portNumber=" + portNumber + ")");
	}

	public ServerStatus getStatus() {
		return status;
	}

	private void setStatus(ServerStatus status) {
		this.status = status;
		this.watcher.updateServerStatus(status);
	}

	public void setServerLogger(ServerLogger serverLogger) {
		this.logger = serverLogger;
		if (this.logger == null) {
			this.logger = new ServerLoggerDefault();
		}
	}
	
	public void setCommandListener(CommandListener listener) {
		this.listener = listener;
		if (this.listener == null) {
			this.listener = new CommandListenerDefault();
		}
	}
	
	public void setServerStatusWatcher(ServerStatusWatcher watcher) {
		this.watcher = watcher;
		if (this.watcher == null) {
			this.watcher = new ServerStatusWatcherDefault();
		}
	}


	@Override
	public void run() {
		try {
			startServer();
		} catch (IOException e) {
			err("@MediaControlServer : could not start server:\n\t" + e);
		}
	}

	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	//private PrintWriter out;
	private BufferedReader in;

	private void startServer() throws IOException {
		logger.appendln("starting server...");
		out("[SERVER] starting server...");

		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			setStatus(ServerStatus.NOT_RUNNING);
			logger.appendln("error: could not listen on port " + portNumber);
			err("@MediaControlServer : could not listen on port " + portNumber + ":\n\t" + e);
			return;
		}
		
		setStatus(ServerStatus.RUNNING_NOT_CONNECTED);
		
		logger.appendln("server started.\nwaiting for connection..."
				+ "\n----------------------------------------------------"
				+ "\n\tIP ADDRESS = " + Inet4Address.getLocalHost().getHostAddress()
				+ "\n\tLISTENING PORT = " + portNumber
				+ "\n----------------------------------------------------");
		out("[SERVER] waiting for connection on port " + portNumber + "...");

		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			setStatus(ServerStatus.NOT_RUNNING);
			err("@MediaControlServer : failed accepting socket:\n\t" + e);
			return;
		}

		setStatus(ServerStatus.RUNNING_CONNECTED);
		logger.appendln("connection successful.\nwaiting for command...");
		out("[SERVER] connection successful, waiting for command...");

		//out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));

		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			sendCommand(inputLine);
			//out.println(inputLine);
		}

		logger.appendln("remote disconnected.\nauto stop server...");
		out("[SERVER] remote disconnected. auto stop server...");
		
		stop();
	}


	private void sendCommand(String commandText) {
		
		logger.appendln("[input] " + commandText);
		out("[SERVER] remote command received: " + commandText);
		
		listener.receiveCommand(commandText);
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
		}
	}

	public void stop() {
		logger.appendln("stopping server...");
		out("[SERVER] stopping server...");
		try {
			//if (out != null) out.close();
			in.notify();
			if (in != null) in.close();
			if (clientSocket != null) clientSocket.close();
			if (serverSocket != null) serverSocket.close();
		} catch (Exception e) {
			err("@MediaControlServer : exception thrown while stopping server:\n\t" + e);
		} finally {
			setStatus(ServerStatus.NOT_RUNNING);
			logger.appendln("server stopped.");
			out("[SERVER] server stopped.");
		}
	}

	private static class ServerLoggerDefault implements ServerLogger {


		@Override
		public void append(String log) {
			System.out.print("ServerLoggerDefault.append() : " + log);
		}

		@Override
		public void appendln(String line) {
			System.out.println("ServerLoggerDefault.appendln() : " + line);
		}
	}
	
	private static class CommandListenerDefault implements CommandListener {

		@Override
		public void receiveCommand(String cmd) {
			System.out.println("CommandListenerDefault.receiveCommand() : " + cmd);
		}
		
	}

	private static class ServerStatusWatcherDefault implements ServerStatusWatcher {

		@Override
		public void updateServerStatus(ServerStatus status) {
			System.out.println("ServerStatusWatcherDefault.updateServerStatus() : " + status);
		}
		
	}
	
}
