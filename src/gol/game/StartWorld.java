package gol.game;

import gol.game.world.World;

public class StartWorld {

	private final World world;
	private final int width;
	private final int height;

	public StartWorld(final World world, final int width, final int height) {
		this.world = world;
		this.width = width;
		this.height = height;
	}

	public World getWorld() {
		return world;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
