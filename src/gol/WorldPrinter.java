package gol;

public class WorldPrinter {
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
			for (int x = 0; x < viewPortWidth; ++x) {
				final boolean alive = world.isAlive(x, y);
				worldString.append(getSign(alive));
			}
			worldString.append("\n");
		}
		System.out.print(worldString.toString());
	}

	private String getSign(final boolean alive) {
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
