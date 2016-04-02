package gol;

public interface World extends Iterable<Point> {

	int heightOffset();
	int widthOffset();
	int height();
	int width();

	boolean isAlive(int x, int y);
	void setAlive(int x, int y);
	int numAlive();
}
