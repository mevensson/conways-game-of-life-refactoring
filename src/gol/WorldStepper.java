package gol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

	private List<Point> neighbors(Point point) {
		List<Point> neighbors = new ArrayList<>();
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