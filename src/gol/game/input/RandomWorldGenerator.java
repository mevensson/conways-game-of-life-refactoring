package gol.game.input;

import java.util.Random;
import java.util.function.Supplier;

import gol.game.world.World;

public class RandomWorldGenerator {

	private final Supplier<World> emptyWorldSupplier;

	public RandomWorldGenerator(final Supplier<World> emptyWorldSupplier) {
		this.emptyWorldSupplier = emptyWorldSupplier;
	}

	public World generate(final int width, final int height) {
		final World world = emptyWorldSupplier.get();

		final Random rand = new Random();
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				if (rand.nextBoolean()) {
					world.setAlive(x, y);
				}
			}
		}

		return world;
	}
}