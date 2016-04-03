package gol;

public interface World extends Iterable<Point> {

	boolean isAlive(int x, int y);
	void setAlive(int x, int y);
}
