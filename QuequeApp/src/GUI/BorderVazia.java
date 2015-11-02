package GUI;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.border.EmptyBorder;

public class BorderVazia extends EmptyBorder {

	public BorderVazia(int top, int left, int bottom, int right) {
		super(top, left, bottom, right);
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y,
			int width, int height) {
		g.setColor(c.getParent().getBackground());
		g.fillRect(x, y, left, height);
		g.fillRect(x + width - right, y, right, height);
	}
}
