package gol.game.world;

import gol.game.Point;

public interface World extends Iterable<Point> {

	boolean isAlive(int x, int y);
	void setAlive(int x, int y);
}
