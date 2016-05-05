package gol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WorldStepper {

	public World step(final World oldWorld) {
		final World newWorld = new BitSetWorld();
		final Map<Point, Integer> aliveNeighborsMap = countAliveNeighbors(oldWorld);
		for (final Entry<Point, Integer> entry : aliveNeighborsMap.entrySet()) {
			final int n = entry.getValue();
			final Point p = entry.getKey();
			if (n == 3) {
				newWorld.setAlive(p.getX(), p.getY());
			} else if (n == 2 && oldWorld.isAlive(p.getX(), p.getY())) {
				newWorld.setAlive(p.getX(), p.getY());
			}
		}
		return newWorld;
	}

	private Map<Point, Integer> countAliveNeighbors(final World world) {
		final Map<Point, Integer> aliveNeighbors = new HashMap<>();
		for (final Point point : world) {
			for (final Point neighbor : neighbors(point)) {
				final int alive = aliveNeighbors.getOrDefault(neighbor, 0);
				aliveNeighbors.put(neighbor, alive + 1);
			}
		}
		return aliveNeighbors;
	}

	private List<Point> neighbors(final Point point) {
		final List<Point> neighbors = new ArrayList<>();
		neighbors.add(new Point(point.getX() - 1, point.getY() - 1));
		neighbors.add(new Point(point.getX()    , point.getY() - 1));
		neighbors.add(new Point(point.getX() + 1, point.getY() - 1));
		neighbors.add(new Point(point.getX() - 1, point.getY()));
		neighbors.add(new Point(point.getX() + 1, point.getY()));
		neighbors.add(new Point(point.getX() - 1, point.getY() + 1));
		neighbors.add(new Point(point.getX()    , point.getY() + 1));
		neighbors.add(new Point(point.getX() + 1, point.getY() + 1));
		return neighbors;
	}
}