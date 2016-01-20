package programming.set9.distances;

import java.awt.geom.Rectangle2D;

public class RectangleDistanceCalculator {

	public static void main(String[] args) {

		RectangleDistanceCalculator instance = new RectangleDistanceCalculator();

		Rectangle2D.Double[] rects = new Rectangle2D.Double[4];

		rects = generateRectangles(rects);

		// System.out.println(rects[0].outcode(rects[1].getX(),
		// rects[1].getY()));

		double mininmalDistance = instance.minDistance(rects);

		System.out.println("Kleinste Entfernung zweier Rechtecke: " + mininmalDistance + "LE");
	}

	/**
	 * Füllt das Array {@code rects} mit Rectangles
	 * 
	 * @param array
	 *            Array, welches gefüllt werden soll
	 */
	private static Rectangle2D.Double[] generateRectangles(Rectangle2D.Double[] array) {

		array[0] = new Rectangle2D.Double(0, 0, 10, 10);
		array[2] = new Rectangle2D.Double(30, 30, 20, 20);
		array[1] = new Rectangle2D.Double(100, 100, 10, 10);
		array[3] = new Rectangle2D.Double(100, 100, 20, 20);

		return array;
	}

	/**
	 * Berechnet die Differenz zwischen zwei Punkten
	 * 
	 * @param x1
	 *            x-Koordinate des ersten Punktes
	 * @param y1
	 *            y-Koordinate des ersten Punktes
	 * @param x2
	 *            x-Koordinate des zweiten Punktes
	 * @param y2
	 *            y-Koordinate des zweiten Punktes
	 * 
	 * @return Differenz der Punkte
	 */
	private static double pointDiff(Tupel point1, Tupel point2) {

		double xSquaredDiff = Math.pow(point1.x - point2.x, 2);
		double ySquaredDiff = Math.pow(point1.y - point2.y, 2);

		return Math.sqrt(xSquaredDiff + ySquaredDiff);
	}

	/**
	 * Calculates the minimum distance between any two rectangles in the given
	 * array. The distance between two rectangles is defined as the length of
	 * the shortest line that can be drawn between them. If two rectangles
	 * overlap, their distance is defined to be 0. A distance can of course
	 * never be negative.
	 * 
	 * @param rectangles
	 *            array of rectangles.
	 * @return minimum distance between any pair of rectangles, or -1 if the
	 *         array contains fewer than 2 rectangles.
	 */
	public double minDistance(Rectangle2D.Double[] rectangles) {

		double minDist = -1;
		Tupel[][] rectsCorners = rectangleCorners(rectangles);
		// Vergleicht jedes Rechteck mit allen anderen
		for (int i = 0; i < rectangles.length - 1; i++) {
			for (int j = i + 1; j < rectangles.length; j++) {
				double minDistPair = pairDist(rectsCorners[i], rectsCorners[j], rectangles[i], rectangles[j]);
				// Erst einen Wert für minDist setzen, ansonsten vergleichen, ob
				// der neue kleiner ist, als der alte
				if (minDist == -1) {
					minDist = minDistPair;
				}
				else if (minDistPair < minDist) {
					minDist = minDistPair;
				}
			}
		}
		return minDist;
	}

	/**
	 * Gibt ein Array zurück, in dem sich an der i-ten Stelle ein Array mit den
	 * vier Eckpunkten des i-ten Rechteck befindet
	 * 
	 * @param rectangles
	 *            Alle Rechtecke, deren Ecken berechnet werden sollen
	 * @return ein Array, in dem sich Arrays mit den Eckpunkten des i-ten
	 *         Rechtecks an der i-ten Stelle befinden
	 */
	private Tupel[][] rectangleCorners(Rectangle2D.Double[] rectangles) {

		Tupel[][] points = new Tupel[rectangles.length][4];
		for (int i = 0; i < rectangles.length; i++) {
			points[i] = getCorners(rectangles[i]);
		}
		return points;
	}

	/**
	 * Berechnet die kürzeste Distanz zwischen zwei Rechtecken
	 * 
	 * @param pointsRect1
	 *            Ecken des {@code rect1}
	 * @param pointsRect2
	 *            Ecken des {@code rect2}
	 * @param rect1
	 *            Das erste Rechteck
	 * @param rect2
	 *            Das zweite Rechteck
	 * @return minimale Differenz zwischen den Rechtecken
	 */
	private double pairDist(Tupel[] pointsRect1, Tupel[] pointsRect2, Rectangle2D.Double rect1,
			Rectangle2D.Double rect2) {

		// Mittelpunkt des ersten Rechtecks
		Tupel middle = new Tupel(rect1.getCenterX(), rect1.getCenterY());
		// Array mit Tupeln (Abstand der Punkte|Ecken Nr.)
		Tupel[] differences = new Tupel[4];
		for (int i = 0; i < 4; i++) {
			differences[i] = new Tupel(pointDiff(middle, pointsRect2[i]), i);
		}
		// Sortieren nach den Abständen
		for (int i = 4; i > 1; i--) {
			for (int j = 0; j < i - 1; j++) {
				if (differences[j].x > differences[j + 1].x) {
					Tupel h = differences[j + 1];
					differences[j + 1] = differences[j];
					differences[j] = h;
				}
			}
		}
		System.out.println("HIER:" + pointsRect2[(int) differences[0].y].x + "|" + pointsRect2[(int) differences[0].y].y
				+ " , " + pointsRect2[(int) differences[1].y].x + "|" + pointsRect2[(int) differences[1].y].y);

		// Die Punkte nach dem Index=(nähe zu rect1) lesbar darstellen
		Tupel close1 = new Tupel(pointsRect2[(int) differences[0].y].x, pointsRect2[(int) differences[0].y].y);
		Tupel close2 = new Tupel(pointsRect2[(int) differences[1].y].x, pointsRect2[(int) differences[1].y].y);
		Tupel close3 = new Tupel(pointsRect2[(int) differences[2].y].x, pointsRect2[(int) differences[2].y].y);
		Tupel close4 = new Tupel(pointsRect2[(int) differences[3].y].x, pointsRect2[(int) differences[3].y].y);

		// Alle möglichen Seiten der Punkte von rect2 zu rect1 in int stecken
		int out1 = rect1.outcode(close1.x, close1.y);
		int out2 = rect1.outcode(close2.x, close2.y);
		int out3 = rect1.outcode(close3.x, close3.y);
		int out4 = rect1.outcode(close4.x, close4.y);

		int pos = out1 | out2 | out3 | out4;

		pos = pos | pos << 8 | pos << 16;

		// Prüfen, ob Punkte hinter gegenüberliegenden Ecken/Seiten liegen; Wenn
		// ja, liegt rect1 in rect2
		for (int i = 0; i < 8; i++) {
			if ((pos & (64 << i)) == 1 && (pos & (64 << i + 3)) == 1) {
				System.out.println("rect1 liegt (teilweise) in rect2. Punkte alle außerhalb");
				return 0;
			}
		}

		// Prüfen, ob der nahegelegenste Punkt neben einer Seite, oder gar in
		// rect1 liegt
		double diff = pointSideDiff(close1, rect1);
		System.out.println("LEL: " + diff);
		if (diff >= 0) {
			System.out.println("der nahegelegenste Punkt liegt neben einer Seite/im ersten Rechteck");
			return diff;
		}

		// Prüfen, ob das zweite Rechteck komplett diagonal zu dem ersten liegt
		if (out1 == out2 && out2 == out3 && out3 == out4) {
			switch (out1) {
			case 3:
				System.out.println("Das rechteck2 liegt links über dem rechteck1");
				return pointDiff(close1, new Tupel(rect1.getMinX(), rect1.getMinY()));
			case 6:
				System.out.println("Das rechteck2 liegt rechts über dem rechteck1");
				return pointDiff(close1, new Tupel(rect1.getMaxX(), rect1.getMinY()));
			case 12:
				System.out.println("Das rechteck2 liegt rechts unter dem rechteck1");
				return pointDiff(close1, new Tupel(rect1.getMaxX(), rect1.getMaxY()));
			case 9:
				System.out.println("Das rechteck2 liegt links unter dem rechteck1");
				return pointDiff(close1, new Tupel(rect1.getMinX(), rect1.getMaxY()));
			default:
				break;
			}
		}

		// Das zweite Rechteck ist mit der naheliegenden Seite länger, als die
		// parallel verlaufende Seite des ersten Rechtecks. Beim Aufruf dieser
		// Funktion liegt somit ein Punkt des ersten Rechtecks neben einer Seite
		// des zweiten Rechtecks und liefert einen Wert zurück
		double switched = pairDist(pointsRect2, pointsRect1, rect2, rect1);
		return switched;

	}

	/**
	 * Checkt, ob ein Punkt neben einer Seite eines Rechtecks liegt
	 * 
	 * @param point
	 *            Punkt, welcher zu checken ist
	 * @param rect
	 *            Rechteck, welches zu checken ist
	 * @return -1, wenn der Punkt nicht neben einer der Seiten von {@code rect}
	 *         liegt; 0, wenn der {@code point} in {@code rect} liegt; Ansonsten
	 *         die Distanz zwischen {@code point} und {@code rect}
	 */
	private static double pointSideDiff(Tupel point, Rectangle2D.Double rect) {
		int pos = rect.outcode(point.x, point.y);
		System.out.println("POS: " + pos);
		switch (pos) {
		case 0:
			return 0;
		case 1:
			return Math.abs(point.x - rect.getMinX());
		case 2:
			return Math.abs(point.y - rect.getMinY());
		case 4:
			return Math.abs(point.x - rect.getMaxX());
		case 8:
			return Math.abs(point.y - rect.getMaxY());
		default:
			return -1;
		}
	}

	/**
	 * Berechnet die Ecken eines Rechtecks und gibt es in einem Array zurück
	 * 
	 * @param rectangle
	 *            Rechteck, von dem die Ecken berechnet werden sollen
	 * @return Array mit den Ecken des Rechtecks
	 */
	private Tupel[] getCorners(Rectangle2D.Double rectangle) {

		Tupel[] corners = new Tupel[4];

		corners[0] = new Tupel(rectangle.getX(), rectangle.getY());
		corners[1] = new Tupel(rectangle.getX() + rectangle.getWidth(), rectangle.getY());
		corners[2] = new Tupel(rectangle.getX(), rectangle.getY() + rectangle.getHeight());
		corners[3] = new Tupel(rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight());

		return corners;
	}

	/**
	 * Ein Tupel mit zwei Werten (({@code x}|{@code y})
	 * 
	 * @author flo
	 *
	 */
	private class Tupel {

		public double x;
		public double y;

		/**
		 * Erstellt einen neues {@code Tupel} mit den Werten ({@code x}|
		 * {@code y})
		 * 
		 * @param x
		 *            x-Koordinate des Punktes
		 * @param y
		 *            y-Koordinate des Punktes
		 */
		public Tupel(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

}
