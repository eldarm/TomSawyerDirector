package com.eldar.TomSawyerDirector;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.eldar.TomSawyerDirector.Corp;

public class Drone {
	private static int glevel = 0;
	protected static Random rand = new Random();
	
	public int level;
	public Drone parent;
	public Drone left;
	public Drone right;
	public double angle = Double.NaN;
	public boolean starter = false; 
	
	public Drone(Drone parent, int depth) {
		if (depth > 0) {
			final int ld = depth - 1;
			left = new Drone(this, ld);
			right = new Drone(this, ld);
		}
		this.parent = parent;
	}
	
	public void Paint(double angle) {
		if (Double.isNaN(this.angle)) {
			level = ++glevel;
			this.angle = angle;
			if (parent != null) parent.Paint(mutate(angle));
			if (left != null)   left.Paint(mutate(angle));
			if (right != null)  right.Paint(mutate(angle));			
		}
	}
		
	private double mutate(double angle) {
		final double full = Math.PI * 2;
		double delta = (double)(full * Corp.noise / 100.0 + rand.nextGaussian() * Corp.noise);
		if (rand.nextDouble() > 0.5) delta = -delta;
		delta += angle;
		return delta < 0 ? delta + full : delta >= full ? delta - full : delta;
	}
	
	public void Draw(Graphics g, int width, int y, int x, int step, int size) {
	  int newY = y + step;
	  int leftX = x - width / 2;
	  int rightX = x + width / 2;
	  g.setColor(Color.BLACK);
	  if (left != null) {
		  g.drawLine(x, y, leftX, newY);
	  }
	  if (right != null) {
		  g.drawLine(x, y, rightX, newY);
	  }
      if (starter) {
    	  g.fillOval(x - size / 2 - 4, y - size / 2 - 4, size + 8, size + 8);
      }
	  if (left != null) {
		  left.Draw(g, width / 2, newY, leftX, step, size);
	  }
	  if (right != null) {
		  right.Draw(g, width / 2, newY, rightX, step, size);
	  }
	  g.setColor(Color.WHITE);
	  g.fillOval(x - size / 2, y - size / 2, size, size);
	  g.setColor(Color.BLACK);
	  g.fillArc(x - size / 2, y - size / 2, size, size, (int)Math.toDegrees(angle) + 90 - 30, 60);
	  g.drawOval(x - size / 2, y - size / 2, size, size);
	}

}
