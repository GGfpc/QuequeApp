package Rede;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import Projecto.Mensagem;

public class HandleClient {

	private String nome;
	private LinkedList<Mensagem> mensagens;
	private ReceiveFromClient inThread;
	private SendToClient outThread;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	//Guarda as threads para o cliente no servidor e a caixa de mensagens a enviar para este cliente
	
	public HandleClient(Socket socket, ServerResource sv, Servidor servidor) throws IOException,
			ClassNotFoundException {
		this.socket = socket;
		mensagens = new LinkedList<>();
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		nome = (String) in.readObject();
		inThread = new ReceiveFromClient(socket, in, sv,nome,servidor);
		outThread = new SendToClient(socket, out, nome, mensagens, this);
		inThread.start();
		outThread.start();

	}
	public Socket getSocket() {
		return socket;
	}
	
	public ReceiveFromClient getInThread() {
		return inThread;
	}

	public String getNome() {
		return nome;
	}

	public SendToClient getOutThread() {
		return outThread;
	}
	//Adiciona uma mensagem à fila e notifica a threadOut
	public synchronized void addMessages(Mensagem m) {
		mensagens.add(m);
		notify();
	}

	//Obtem a primeira mensagem da fila para enviar e fica em espera se estiver vazia
	public synchronized Mensagem getFirst() {
		try {
			while (mensagens.isEmpty()) {
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return mensagens.getFirst();
	}

	//retira a primeira mensagem da fila
	public synchronized void removeFirst() {
		mensagens.removeFirst();
	}

}
