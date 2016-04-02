package gol;

import java.util.Random;

public class RandomWorldGenerator {

	public World generate(int width, int height) {
		World world = new StringArrayWorld();

		Random rand = new Random();
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