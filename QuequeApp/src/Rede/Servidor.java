package Rede;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import Projecto.Mensagem;

public class Servidor {

	private ServerSocket svsocket;
	private Map<String, HandleClient> clients;
	private ServerResource sv;
	private long ligouH;

	// Liga aos clientes e cria as suas threads
	private void start() {
		System.out.println("A ligar");

		try {
			svsocket = new ServerSocket(8080);
			clients = new HashMap<String, HandleClient>();
			ligouH = System.currentTimeMillis();
			sv = new ServerResource(clients);
			new ServerInfoThread(sv, this).start();

			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					notifyUsers();

				}
			}, 0, 5000);

			while (!svsocket.isClosed()) {

				new ServerHandler(sv).start();

				Socket cliente = svsocket.accept();
				HandleClient clientThread = new HandleClient(cliente, sv, this);
				clients.put(clientThread.getNome(), clientThread);
				newClientOnline(clientThread.getNome());
				System.out.println("Ligado a: " + clientThread.getNome());

			}
			svsocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Map<String, HandleClient> getClients() {
		return clients;
	}

	public void newClientOnline(String name) {
		for (String s : clients.keySet()) {
			clients.get(s).getOutThread().sendInfo(new Info(name, ClientState.ONLINE));
			clients.get(name).getOutThread().sendInfo(new Info(s, ClientState.ONLINE));
		}
	}

	public void notifyUsers() {
		for (String s : clients.keySet()) {
			for (String c : clients.keySet()) {
				clients.get(s).getOutThread().sendInfo(new Info(c, ClientState.ONLINE));
			}
		}
	}

	public void newClientOffline(Info info) {
		for (String s : clients.keySet()) {
			clients.get(s).getOutThread().sendInfo(info);

		}
	}

	public long upTime() {
		long time = System.currentTimeMillis() - ligouH;
		time = (time / 1000);
		return time;
	}

	public void shutdown() {
		for (String s : clients.keySet()) {
			for (String c : clients.keySet()) {
				clients.get(s).getOutThread().sendInfo(new Info(c, ClientState.OFFLINE));
			}
		}
		System.exit(0);
	}

	public static void main(String[] args) {
		new Servidor().start();
	}

}
