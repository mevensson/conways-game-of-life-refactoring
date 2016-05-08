package gol;

import java.util.Random;

import gol.game.world.BitSetWorld;
import gol.game.world.World;

public class RandomWorldGenerator {

	public World generate(final int width, final int height) {
		final World world = new BitSetWorld();

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