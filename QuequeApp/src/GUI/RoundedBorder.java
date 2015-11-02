package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;

public class RoundedBorder extends AbstractBorder {
	
	

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {

		Graphics2D graph = (Graphics2D) g.create();
		RoundRectangle2D round = new RoundRectangle2D.Float(x, y, width - 1,
				height - 1, 30, 30);
		Container parent = c.getParent();

		if (parent != null) {
			graph.setColor(parent.getBackground());
			Area canto = new Area(new Rectangle2D.Float(x,y,width,height));
			canto.subtract(new Area(round));
			graph.fill(canto);
		}

		
		graph.draw(round);
		graph.dispose();

	}
	
	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(5, 10, 5, 10);
	}
	
	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.left = 10;
		insets.right = insets.left;
		insets.top = 5;
		insets.bottom = insets.top;
		return insets;
	}

}
