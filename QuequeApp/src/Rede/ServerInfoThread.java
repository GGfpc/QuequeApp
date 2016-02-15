package Rede;

import java.util.Scanner;

import Projecto.Mensagem;
import Projecto.MensagemDeGrupo;
import Projecto.MensagemIndividual;

public class ServerInfoThread extends Thread {

	Servidor server;
	ServerResource sv;
	Scanner in;

	public ServerInfoThread(ServerResource sv, Servidor server) {
		this.sv = sv;
		this.server = server;
		in = new Scanner(System.in);
		System.out.println("Comandos disponiveis : \n users - Lista utilizadores ligados"
				+ "\n uptime - Lista há quanto tempo está ligado o Servidor"
				+ "\n msgs - Lista mensagens na fila"
				+ "\n shutdown - Desliga servidor");
		
	}

	@Override
	public void run() {
		int i = 0;
		
		while (true) {
			
			String comando = in.nextLine();
			switch (comando) {
			case "users":
				System.out.println("Utilizadores ligados: " + server.getClients().size() + "\n");
				for (String s : server.getClients().keySet()) {
					System.out.println(s + "\n");
				}
				break;

			case "uptime":
				System.out.println("Ligado há: " + server.upTime() + " segundos");
				System.out.println("Enviou: " + sv.getDeliveredMsgs() + " mensagens");
				break;
				
			case "msgs":
				if (sv.getMsgQueueGeral().isEmpty()) {
					System.out.println("Não há mensagens");
				} else {
					for (Mensagem m : sv.getMsgQueueGeral()) {
						if (m instanceof MensagemDeGrupo) {
							System.out.println("From: " + m.getUser().getNome() + " To: " + ((MensagemDeGrupo) m).getGrupo() + "\n"
									+ m.getConteudo());
						} else if (m instanceof MensagemIndividual) {
							System.out.println("From: " + m.getUser().getNome() + " To: " + ((MensagemIndividual) m).getSender()
									+ "\n" + m.getConteudo());
						}
					}
				}
				break;

			case "shutdown":
				server.shutdown();
				break;

			}
			
		}
	}

}
