package gol.game.world;

import java.util.stream.Stream;

import gol.game.Point;

public interface World {

	boolean isAlive(int x, int y);
	void setAlive(int x, int y);

	Stream<Point> stream();
}
