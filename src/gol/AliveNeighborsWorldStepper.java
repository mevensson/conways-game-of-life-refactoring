package gol;

import java.util.function.Supplier;

public class AliveNeighborsWorldStepper implements WorldStepper {

	private final Supplier<World> emptyWorldSupplier;
	private final AliveNeighborCounter aliveNeighborCounter;

	public AliveNeighborsWorldStepper(final Supplier<World> emptyWorldSupplier,
			final AliveNeighborCounter aliveNeighborCounter) {
		this.emptyWorldSupplier = emptyWorldSupplier;
		this.aliveNeighborCounter = aliveNeighborCounter;
	}

	@Override
	public World step(final World oldWorld) {
		final World newWorld = emptyWorldSupplier.get();
		aliveNeighborCounter.count(oldWorld)
			.filter((a) -> shouldBeAlive(a.point, a.count, oldWorld))
			.forEach((a) -> newWorld.setAlive(a.point.x, a.point.y));
		return newWorld;
	}

	private boolean shouldBeAlive(final Point point, final int aliveCount,
			final World oldWorld) {
		return aliveCount == 3 ||
				(aliveCount == 2 && oldWorld.isAlive(point.x, point.y));
	}
}