package gol.stdout_output;

import gol.game.world.World;

public final class WorldPrinter {
	private final OutputFormat outputFormat;
	private final int viewPortWidth;
	private final int viewPortHeight;

	public WorldPrinter(final OutputFormat outputFormat, final int width,
			final int height) {
		this.outputFormat = outputFormat;
		viewPortWidth = width;
		viewPortHeight = height;
	}

	public void printWorld(final World world) {
		final StringBuilder worldString = new StringBuilder();
		for (int y = 0; y < viewPortHeight; ++y) {
			addLine(worldString, world, y);
		}
		System.out.print(worldString.toString());
	}

	private void addLine(final StringBuilder worldString, final World world,
			final int y) {
		for (int x = 0; x < viewPortWidth; ++x) {
			final boolean alive = world.isAlive(x, y);
			worldString.append(outputFormat.getSign(alive));
		}
		worldString.append("\n");
	}
}
