package Rede;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import Projecto.Contacto;
import Projecto.Grupo;
import Projecto.Mensagem;
import Projecto.MensagemDeGrupo;
import Projecto.MensagemIndividual;

public class ServerResource {

	private Map<String, HandleClient> clients;
	private LinkedList<Mensagem> msgQueueGeral;
	private int deliveredMsgs;

	public ServerResource(Map<String, HandleClient> clients) {
		this.clients = clients;
		msgQueueGeral = new LinkedList<>();
		deliveredMsgs = 0;
	}

	public synchronized void addMsg(Mensagem m) {
		msgQueueGeral.add(m);
		notify();
	}
	
	public LinkedList<Mensagem> getMsgQueueGeral() {
		return msgQueueGeral;
	}

	public synchronized void distribute() throws InterruptedException {
		while (msgQueueGeral.isEmpty()) {
			wait();
		}
		LinkedList<Mensagem> toRemove = new LinkedList<>();

		for (Mensagem m : msgQueueGeral) {

			// Envia a mensagem para a messagebox do contacto e envia para a do
			// utilizador para notificar
			if (m instanceof MensagemDeGrupo) {

				for (Contacto c : ((MensagemDeGrupo) m).getContactosAEnviar()) {
					
					if (clients.containsKey(c.getNome()) && !((MensagemDeGrupo) m).getJaEnviado().contains(c)) {
						clients.get(c.getNome()).addMessages(m);
						((MensagemDeGrupo) m).getJaEnviado().add(c);
						deliveredMsgs++;
						if (!((MensagemDeGrupo) m).isAlreadySent()) {
							if (clients.containsKey(m.getUser().getNome())) {
							clients.get(m.getUser().getNome()).addMessages(m);
								((MensagemDeGrupo) m).setAlreadySent(true);
							}
						}

					}
				}

				if (((MensagemDeGrupo) m).getContactosAEnviar().size() == ((MensagemDeGrupo) m).getJaEnviado().size()) {
					toRemove.add(m);
				}

			} else if (m instanceof MensagemIndividual) {
				if (clients.containsKey(((MensagemIndividual) m).getSender().getNome())) {
					clients.get(((MensagemIndividual) m).getSender().getNome()).addMessages(m);
					clients.get(m.getUser().getNome()).addMessages(m);
					toRemove.add(m);
					deliveredMsgs++;
				}
			}

		}
		// Remove da queue todas as mensagens que foram enviadas
		msgQueueGeral.removeAll(toRemove);
	}

	public void removeContacto(String nome) {
		clients.remove(nome);
		System.out.println(clients);

	}

	
	public int getDeliveredMsgs() {
		return deliveredMsgs;
	}
}
