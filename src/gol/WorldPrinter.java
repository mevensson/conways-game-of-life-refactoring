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
		StringBuilder worldString = new StringBuilder();
		for (int y = 0; y < viewPortHeight; ++y) {
			for (int x = 0; x < viewPortWidth; ++x) {
				boolean alive = world.isAlive(x, y);
				worldString.append(getSign(alive));
			}
			worldString.append("\n");
		}
		System.out.print(worldString.toString());
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
