package Rede;

public class ServerHandler extends Thread {

	ServerResource sv;

	public ServerHandler(ServerResource sv) {
		this.sv = sv;
	}

	//Distribui as mensagens que estão na queue geral para a messagebox dos clientes
	
	@Override
	public void run() {
		try {
			while (!isInterrupted()) {
				sv.distribute();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
