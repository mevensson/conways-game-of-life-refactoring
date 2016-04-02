package gol;

public interface World {

	int heightOffset();
	int widthOffset();
	int height();
	int width();

	boolean isAlive(int x, int y);
	void setAlive(int x, int y);
}
