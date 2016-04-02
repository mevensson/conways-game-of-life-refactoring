package gol;

public class WorldPrinter {
	private final OutputFormat outputFormat;
	private final int viewPortWidth;
	private final int viewPortHeight;

	public WorldPrinter(OutputFormat outputFormat, int width, int height) {
		this.outputFormat = outputFormat;
		this.viewPortWidth = width;
		this.viewPortHeight = height;
	}

	public void printWorld(World world) {
		for (int y = 0; y < viewPortHeight; ++y) {
			String line = "";
			for (int x = 0; x < viewPortWidth; ++x) {
				boolean alive = world.isAlive(x, y);
				line += getSign(alive);
			}
			System.out.println(line);
		}
	}

	private String getSign(boolean alive) {
		switch (outputFormat) {
		case AT_SIGNS:
			return alive ? "@ " : ". ";

		case O_SIGNS:
			return alive ? "O" : "-";

		default:
			return alive ? "#" : "-";
		}
	}
}
