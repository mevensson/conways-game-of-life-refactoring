package gol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class WorldStepper {

	public World step(World oldWorld) {
		World newWorld = new SetWorld(oldWorld.numAlive());
		Map<Point, Integer> aliveNeighborsMap = countAliveNeighbors(oldWorld);
		for (Entry<Point, Integer> entry : aliveNeighborsMap.entrySet()) {
			int n = entry.getValue();
			Point p = entry.getKey();
			if (n == 3) {
				newWorld.setAlive(p.getX(), p.getY());
			} else if (n == 2 && oldWorld.isAlive(p.getX(), p.getY())) {
				newWorld.setAlive(p.getX(), p.getY());
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