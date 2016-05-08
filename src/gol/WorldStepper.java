package gol;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

public class WorldStepper {

	private final Supplier<World> emptyWorldSupplier;
	private final AliveNeighborCounter aliveNeighborCounter;

	public WorldStepper(final Supplier<World> emptyWorldSupplier,
			final AliveNeighborCounter aliveNeighbors) {
		this.emptyWorldSupplier = emptyWorldSupplier;
		aliveNeighborCounter = aliveNeighbors;
	}

	public World step(final World oldWorld) {
		final World newWorld = emptyWorldSupplier.get();
		final Map<Point, Integer> aliveNeighborMap = aliveNeighborCounter.count(oldWorld);
		for (final Entry<Point, Integer> entry : aliveNeighborMap.entrySet()) {
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
}