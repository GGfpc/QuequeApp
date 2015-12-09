package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;

import Projecto.Contacto;
import Projecto.Mensagem;

public class JanelaDeConversa extends JPanel {

	private String nome;
	private static final int PREF_W = 400;
	private static final int PREF_H = 500;
	private JPanel msgHoldingPanel = new JPanel();
	//Guarda a JTextArea de cada mensagem associada ao id da mensagem
	private Map<String, JTextArea> msgs;

	public JanelaDeConversa(String nome) {
		msgs = new HashMap<String, JTextArea>();
		msgHoldingPanel.setLayout(new BoxLayout(msgHoldingPanel,
				BoxLayout.PAGE_AXIS));
		setLayout(new BorderLayout());
		add(msgHoldingPanel, BorderLayout.PAGE_START);
		this.nome = nome;

	}

	public void receiveMessage(String s, Contacto c) {

		JPanel painel = new JPanel();
		painel.setLayout(new BoxLayout(painel, BoxLayout.LINE_AXIS));

		JTextArea msg = new JTextArea();

		msg.setText(s);
		Color bg = Color.decode("#AFEEEE");
		msg.setBackground(bg);
		msg.setEditable(false);

		if (msg.getPreferredSize().width >= (PREF_W)) {

			msg.setBorder(new RoundedBorder());
			msg.setLineWrap(true);
			msg.setWrapStyleWord(true);

		} else {
			int left = PREF_W - msg.getPreferredSize().width;
			msg.setBorder(new CompoundBorder(new BorderVazia(0, 0, 0, left),
					new RoundedBorder()));
			msg.setMaximumSize(msg.getPreferredSize());
		}
		JLabel fotoDeContacto = new JLabel();
		fotoDeContacto.setIcon(c.getImg());
		painel.add(fotoDeContacto);

		painel.add(msg);
		msgHoldingPanel.add(painel);
		msgHoldingPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		this.revalidate();
	}
	//Faz bue cenas e envia a mensagem para o ecrã
	public void sendMessage(Mensagem m, boolean loading) {
		msgHoldingPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		JPanel painel = new JPanel();
		painel.setLayout(new BoxLayout(painel, BoxLayout.LINE_AXIS));
		JTextArea msg2 = new JTextArea();
		msg2.setEditable(false);
		msg2.setText(m.getConteudo());
		msg2.setBorder(new RoundedBorder());

		Color bg = Color.decode("#CCF1DA");
		Color disabledColor = Color.decode("#646464");
		boolean enabled = false;
		if (loading && m.isReceived()) {
			enabled = true;
			bg = Color.decode("#99E3B6");
		}

		msg2.setBackground(bg);

		if (msg2.getPreferredSize().width >= PREF_W) {
			System.out.println("yes");
			msg2.setLineWrap(true);
			msg2.setWrapStyleWord(true);

		} else {
			int right = PREF_W - msg2.getPreferredSize().width + 60;
			msg2.setBorder(new CompoundBorder(new BorderVazia(0, right, 0, 0),
					new RoundedBorder()));
			msg2.setMaximumSize(msg2.getPreferredSize());
		}
		msgs.put(m.getId(), msg2);
		msg2.setEnabled(enabled);
		msg2.setDisabledTextColor(disabledColor);
		painel.add(msg2);
		msgHoldingPanel.add(painel);
		// this.invalidate();
		// this.validate();
		this.revalidate();
		// this.repaint();

	}
	
	//Muda a cor da mensagem quando é notificado
	public void setSent(Mensagem m) {

		System.out.println("notificado");
		Color bg3 = Color.decode("#99E3B6");

		msgs.get(m.getId()).setBackground(bg3);

		msgs.get(m.getId()).setEnabled(true);
		this.repaint();
		this.revalidate();

	}

	@Override
	public String toString() {
		return nome;
	}

}