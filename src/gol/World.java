package gol;

import java.util.List;

public class World {
	public List<String> world;
	public int heightOffset;
	public int widthOffset;

	public World(List<String> world, int heightOffset, int widthOffset) {
		this.world = world;
		this.heightOffset = heightOffset;
		this.widthOffset = widthOffset;
	}
}