package gol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorldStepper {

	public World step(World oldWorld) {
		World newWorld = new SetWorld(oldWorld.numAlive());
		Map<Point, Integer> aliveNeighbors = countAliveNeighbors(oldWorld);
		for (int y = oldWorld.heightOffset() - 1;
				y < oldWorld.heightOffset() + oldWorld.height() + 1;
				++y) {
			for (int x = oldWorld.widthOffset() - 1;
					x < oldWorld.widthOffset() + oldWorld.width() + 1;
					++x) {
				int n = aliveNeighbors.getOrDefault(new Point(x, y), 0);
				if (oldWorld.isAlive(x, y)) {
					if (n == 2 || n == 3)
						newWorld.setAlive(x, y);
				} else {
					if (n == 3)
						newWorld.setAlive(x, y);
				}
			}
		}
		return newWorld;
	}

	private Map<Point, Integer> countAliveNeighbors(World world) {
		Map<Point, Integer> aliveNeighbors = new HashMap<>(world.numAlive());
		for (Point point : world) {
			for (Point neighbor : neighbors(point)) {
				int alive = aliveNeighbors.getOrDefault(neighbor, 0);
				aliveNeighbors.put(neighbor, alive + 1);
			}
		}
		return aliveNeighbors;
	}

	private Set<Point> neighbors(Point point) {
		Set<Point> neighbors = new HashSet<>();
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