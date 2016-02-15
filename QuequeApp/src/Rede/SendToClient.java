package Rede;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import Projecto.Grupo;
import Projecto.Mensagem;

public class SendToClient extends Thread {

	String nome;
	Socket socket;
	ObjectOutputStream outCliente;
	LinkedList<Mensagem> queue;
	HandleClient cl;

	public SendToClient(Socket socket, ObjectOutputStream out, String nome,
			LinkedList<Mensagem> queue, HandleClient cl) throws IOException {
		this.socket = socket;
		outCliente = out;
		this.nome = nome;
		this.queue = queue;
		this.cl = cl;
	}
	
	public void sendInfo(Info info){
		try {
			outCliente.writeObject(info);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (!isInterrupted()) {
				Mensagem msg = cl.getFirst();
				if (msg != null) {
					outCliente.writeObject(msg);
					queue.removeFirst();
				}
			}
		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
