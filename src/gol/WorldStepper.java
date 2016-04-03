package gol;

public class WorldStepper {

	public World step(World oldWorld) {
		World newWorld = new BitSetWorld();

		for (int y = oldWorld.heightOffset() - 1;
				y < oldWorld.heightOffset() + oldWorld.height() + 1;
				++y) {
			for (int x = oldWorld.widthOffset() - 1;
					x < oldWorld.widthOffset() + oldWorld.width() + 1;
					++x) {
				int n = countAliveNeighbors(oldWorld, x, y);
				if (oldWorld.isAlive(x, y)) {
					if (n == 2 || n == 3)
						newWorld.setAlive(x, y);;
				} else {
					if (n == 3)
						newWorld.setAlive(x, y);;
				}
			}
		}
		return newWorld;
	}

	private int countAliveNeighbors(World world, int x, int y) {
		int n = 0;

		if (world.isAlive(x - 1, y - 1))
			n++;
		if (world.isAlive(x, y - 1))
			n++;
		if (world.isAlive(x + 1, y - 1))
			n++;
		if (world.isAlive(x - 1, y))
			n++;
		if (world.isAlive(x + 1, y))
			n++;
		if (world.isAlive(x - 1, y + 1))
			n++;
		if (world.isAlive(x, y + 1))
			n++;
		if (world.isAlive(x + 1, y + 1))
			n++;
		return n;
	}
}