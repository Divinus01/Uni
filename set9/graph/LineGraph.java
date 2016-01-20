package programming.set9.graph;

import java.awt.Color;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import programming.set8.statistics.StatisticsCollector;

public class LineGraph extends GraphicsProgram {

	public void run() {

//		setSize(600, 300);
//		validate();
		double[] values = new double[] { 1, 4, 8, 7, 5, 7, 2, 9 };
		double[] values2 = new double[] { 10, 3, 1, 4, 8, 7, 5, 7, 2, 9 };
		Color color = new Color(255, 0, 0);
		Color color2 = new Color(0, 255, 0);
		plotGraph("TEST", color, values);
		plotGraph("WEITERE WERTE", color2, values2);
	}

	/**
	 * Plots the given set of data points. For each data point, a small circle
	 * is added to the drawing area, and each neighbouring set of circles is
	 * connected by a line. The data set's name should be added in the form of a
	 * label near the leftmost circle. Everything is to be drawn in the given
	 * color.
	 * 
	 * In the x direction, the circles should be evenly distributed over the
	 * whole width of the dawing area. In the y direction, the top border of the
	 * drawing area should represent the given maximum y value, and the bottom
	 * border of the drawing area should represent the given minimum y value.
	 * Leave a small border around the drawing area to keep circles from being
	 * cut off.
	 * 
	 * @param name
	 *            the data set's name.
	 * @param color
	 *            the color to use for displaying the data set.
	 * @param data
	 *            the data set to be plotted. You can assume that this will
	 *            contain at least one item.
	 * @param minY
	 *            minimum Y value that can properly be displayed in the window.
	 * @param maxY
	 *            maximum Y value that can properly be displayed in the window.
	 *            You can assume that maxY is greater than minY.
	 */
	public void plotGraph(String name, Color color, double[] data, double minY, double maxY) {

		int usableHeight = getHeight() - 20;
		double usableWidth = getWidth() - 20;

		double xDiff = usableWidth / (data.length - 1);
		double dMinYMaxY = maxY - minY;

		// Alle Punkte berechnen
		GPoint[] points = new GPoint[data.length];
		for (int i = 0; i < data.length; i++) {
			double xCoord = i * xDiff + 10;

			double shareFromUpperBorder = 1 - ((data[i] - minY) / dMinYMaxY);
			double yCoord = shareFromUpperBorder * usableHeight + 10;

			points[i] = new GPoint(xCoord, yCoord);
		}

		// Das Label
		GLabel label = new GLabel(name, points[0].getX() + 8, points[0].getY() + 3);
		label.setColor(color);
		add(label);

		// Punkt und nächste Linie zeichnen
		for (int i = 0; i < data.length - 1; i++) {
			drawCircle(points[i], color);
			drawLine(points[i], points[i + 1], color);
		}
		// übriger Punkt
		drawCircle(points[data.length - 1], color);
	}

	/**
	 * Zeichnen eine Linie von {@code gPoint} nach {@code gPoint2} mit der Farbe
	 * {@code color}
	 * 
	 * @param gPoint
	 *            Anfangspunkt der Linie
	 * @param gPoint2
	 *            Endpunkt der Linie
	 * @param color
	 *            Farbe der Linie
	 */
	private void drawLine(GPoint gPoint, GPoint gPoint2, Color color) {

		GLine line = new GLine(gPoint.getX(), gPoint.getY(), gPoint2.getX(), gPoint2.getY());
		line.setColor(color);
		add(line);
	}

	/**
	 * Zeichnet einen gefüllten, eingefärbten Kreis der Farbe {@code color} mit
	 * dem Radius 3 an einen {@code gPoint}
	 * 
	 * @param gPoint
	 *            Punkt, an dem der Kreis gezeichnet werden soll
	 * @param color
	 *            Farbe des Punktes
	 */
	private void drawCircle(GPoint gPoint, Color color) {

		GOval circle = new GOval(gPoint.getX() - 3, gPoint.getY() - 3, 6, 6);
		circle.setFilled(true);
		circle.setColor(color);
		circle.setFillColor(color);
		add(circle);
	}

	/**
	 * Same as {@link #plotGraph(String, Color, double[], double, double)}, but
	 * calculates the minimum and maximum Y values necessary to completely fit
	 * the data set inside the drawing area. If the minimum and maximum Y values
	 * are equal (let's call that value {@code yVal}, call
	 * {@link #plotGraph(String, Color, double[], double, double)} with the
	 * minimum and maximum Y values chosen such that {@code yVal} is in the
	 * middle. That is, if all values of the data array are equal, the line
	 * graph should be a horizontal line through the window's center.
	 * 
	 * @param name
	 *            the data set's name.
	 * @param color
	 *            the color to use for displaying the data set.
	 * @param data
	 *            the data set itself.
	 */
	public void plotGraph(String name, Color color, double[] data) {
		StatisticsCollector stats = new StatisticsCollector();
		for (double num : data) {
			stats.addItem(num);
		}
		double max = stats.getMaximum();
		double min = stats.getMinimum();

		if (max == min) {
			plotGraph(name, color, data, min - 20, max + 20);
		}
		else {
			plotGraph(name, color, data, min, max);
		}
	}
}
