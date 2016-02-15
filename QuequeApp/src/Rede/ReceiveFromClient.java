package Rede;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import Projecto.Mensagem;

public class ReceiveFromClient extends Thread {

	Socket socket;
	ObjectInputStream inCliente;
	ServerResource sv;
	Servidor servidor;
	String nome;

	public ReceiveFromClient(Socket socket, ObjectInputStream in,
			ServerResource sv, String nome, Servidor servidor) throws IOException {
		this.socket = socket;
		inCliente = in;
		this.sv = sv;
		this.nome = nome;
		this.servidor = servidor;
	}

	@Override
	public void run() {
		Mensagem msg;
		boolean exit = false;
		try {
			while (true) {
				//Recebe uma mensagem
				Object obj = inCliente.readObject();
				if(obj instanceof Info){
					Info info = (Info) obj;
					if(info.state.equals(ClientState.OFFLINE)){
						servidor.newClientOffline(info);
						break;
					}
				}
				//Se for do tipo mensagem envia para o server
				if(obj instanceof Mensagem){
					msg = (Mensagem) obj;
					sv.addMsg(msg);
					System.out.println(msg);
				}
				
				
			}
			//Se sair do while remove o contacto do server
			sv.removeContacto(nome);
			

		} catch (ClassNotFoundException e) {
			System.out.println("parou no class");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("parou no IO");
			e.printStackTrace();
		}
		

	}
	

}
