package Rede;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import GUI.Utilizador;
import GUI.ViewGUI;
import Projecto.Contacto;
import Projecto.Grupo;
import Projecto.Mensagem;
import Projecto.MensagemDeGrupo;
import Projecto.MensagemIndividual;

public class Client {

	private Utilizador user;
	private Socket socket;
	private ObjectOutputStream out;
	private ViewGUI view;
	private ThreadInputCliente in;

	public Client(Utilizador user) {
		this.user = user;
	}

	// Recebe uma mensagem e envia para o servidor
	public void enviaParaServ(Mensagem m) {
		try {
			out.writeObject(m);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Liga o cliente ao server e inicia as threads
	public void ligaAServ() {
		try {
			socket = new Socket("localhost", 8080);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ThreadInputCliente(socket);
			in.start();

			if (socket.isConnected()) {
				out.writeObject(user.getNome());
				view.setOnline(true);
			}
			
		} catch (IOException e) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ligaAServ();
		}

	}

	private class ThreadInputCliente extends Thread {
		Socket socket;
		ObjectInputStream in;
		boolean close = false;

		public ThreadInputCliente(Socket socket) {
			this.socket = socket;
			try {
				in = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch blockCCl
				e.printStackTrace();
			}
		}

		@Override
		public void run() {

			try {
				Mensagem msg;
				while (true) {
					Object obj = in.readObject();

					if (obj instanceof Mensagem) {
						msg = (Mensagem) obj;
						if (msg.getUser().getNome().equals(user.getNome())) {
							sentReceipt(msg);
						} else {
							receiveMessage(msg);
						}
					} else if (obj instanceof Info) {
						Info info = (Info) obj;
					
						if (user.containsContacto(info.user)) {
							if (info.state.equals(ClientState.ONLINE)) {
								user.getContacto(info.user).setOnline(true);
							} else if (info.state.equals(ClientState.OFFLINE)) {
								if (info.user.equals(user.getNome())) {
									break;
								}
								user.getContacto(info.user).setOnline(false);

							}

							view.getList().repaint();
							System.out.println(user.getContacto(info.user).isOnline());
						}
					}
				}
				socket.close();

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				view.setOnline(false);
				ligaAServ();
			}

		}

		private void receiveMessage(Mensagem msg) {
			if (msg instanceof MensagemDeGrupo) {
				Contacto sender = user.getContacto(msg.getUser().getNome());
				Grupo grupo = user.getGrupo(((MensagemDeGrupo) msg).getGrupo().getNome());
				grupo.setImg(((MensagemDeGrupo) msg).getGrupo().getImg());
				sender.setImg(msg.getUser().getPic());
				sender.saveImage(user.getNome(), sender.getImg());
				grupo.saveImage(user.getNome(), grupo.getImg());
				
				

				if (msg.isHaspic()) {
					grupo.getConversa().getConversa().receiveImg(msg.getPic(), sender);
				} else {
					grupo.getConversa().getConversa().receiveMessage(msg.getConteudo(), sender);
				}

				((MensagemDeGrupo) msg).setSender(sender);
				grupo.getConversa().novaMensagem(msg);
			} else {
				Contacto c = user.getContacto(msg.getUser().getNome());
				((MensagemIndividual) msg).setContacto(c);
				c.setImg(msg.getUser().getPic());
				c.saveImage(user.getNome(), c.getImg());
				c.getConversa().novaMensagem(msg);
				if (msg.isHaspic()) {
					c.getConversa().getConversa().receiveImg(msg.getPic(), c);
				} else {
					c.getConversa().getConversa().receiveMessage(msg.getConteudo(), c);
				}
			}
			addToClient(msg);
			msg.setSent(false);
			msg.setUser(user);
			if(msg.isHaspic()){
				msg.storePic(msg.getPic(), msg.getId());
			}
			showNewNotification(msg);
		}

		private void sentReceipt(Mensagem msg) {
			if (msg instanceof MensagemDeGrupo) {
				Grupo grupo = user.getGrupo(((MensagemDeGrupo) msg).getGrupo().getNome());
				grupo.getConversa().setSent(msg);
			} else {
				Contacto c = user.getContacto(((MensagemIndividual) msg).getSender().getNome());
				c.getConversa().setSent(msg);
			}

		}

		private void showNewNotification(Mensagem msg) {
			if (msg instanceof MensagemDeGrupo) {
				Grupo grupo = user.getGrupo(((MensagemDeGrupo) msg).getGrupo().getNome());
				grupo.setNotifications(grupo.getNotifications() + 1);
				Contacto selected = view.getList().getSelectedValue();
				view.getModel().removeElement(grupo);
				view.getModel().insertElementAt(grupo, 0);
				view.getList().setSelectedValue(selected, true);
				
			} else if (msg instanceof MensagemIndividual) {
				Contacto c = user.getContacto(((MensagemIndividual) msg).getSender().getNome());
				c.setNotifications(c.getNotifications() + 1);
				Contacto selected = view.getList().getSelectedValue();
				view.getModel().removeElement(c);
				view.getModel().insertElementAt(c, 0);
				view.getList().setSelectedValue(selected, true);
			}
		}

		public void closeStream() {
			close = true;
		}
	}

	public void addToClient(Mensagem msg) {
		if (msg instanceof MensagemDeGrupo) {
			Contacto sender = user.getContacto(msg.getUser().getNome());

			Grupo grupo = user.getGrupo(((MensagemDeGrupo) msg).getGrupo().getNome());
			if (!view.getModel().contains(grupo)) {
				view.getModel().addElement(grupo);
				for (Contacto c : ((MensagemDeGrupo) msg).getContactosAEnviar()) {
					if (!c.getNome().equals(user.getNome())) {
						Contacto cont = user.getContacto(c.getNome());
						grupo.addContacto(cont);
					}
				}
				grupo.addContacto(sender);
				user.novoGrupo(grupo);
			}
			System.out.println(grupo.getContactos());
		} else {
			Contacto c = user.getContacto(((MensagemIndividual) msg).getSender().getNome());

			if (!view.getModel().contains(c)) {
				view.getModel().addElement(c);
				user.novoContacto(c);
			}
		}
	}

	public void closeSocket() throws IOException {
		Info info = new Info(user.getNome(), ClientState.OFFLINE);
		out.writeObject(info);
	}

	public void setView(ViewGUI view) {
		this.view = view;
	}

}
