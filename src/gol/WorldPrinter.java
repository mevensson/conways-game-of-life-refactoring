package gol;

public class WorldPrinter {
	private OutputFormat outputFormat;
	private int viewPortWidth;
	private int viewPortHeight;

	public WorldPrinter(OutputFormat outputFormat, int width, int height) {
		this.outputFormat = outputFormat;
		this.viewPortWidth = width;
		this.viewPortHeight = height;
	}

	public void printWorld(World world) {
		String linePrefix = "";
		for (int i = 0; i < world.widthOffset; i++) {
			linePrefix += '-';
		}

		String lineSuffix = "";
		int worldWidth = world.width();
		for (int i = 0; i < viewPortWidth - worldWidth - world.widthOffset; i++) {
			lineSuffix += '-';
		}

		int printHeight = 0;
		for (int i = 0; i < Math.min(world.heightOffset, viewPortHeight); i++) {
			String line = "";
			while (line.length() < viewPortWidth) {
				line += '-';
			}
			printWorldLine(line);
			printHeight++;
		}

		for (int i = Math.max(0, -world.heightOffset); i < world.height(); i++) {
			if (printHeight == viewPortHeight)
				break;
			String line = world.world.get(i);

			line = linePrefix + line + lineSuffix;

			if (world.widthOffset < 0)
				line = line.substring(-world.widthOffset);

			if (line.length() > viewPortWidth)
				line = line.substring(0, viewPortWidth);
			printWorldLine(line);
			printHeight++;
		}

		for (; printHeight < viewPortHeight; printHeight++) {
			String line = "";
			while (line.length() < viewPortWidth) {
				line += '-';
			}
			printWorldLine(line);
		}
	}

	private void printWorldLine(String line) {
		if (outputFormat == OutputFormat.AT_SIGNS)
			System.out.println(line.replace("#", "@ ").replace("-", ". "));
		else if (outputFormat == OutputFormat.O_SIGNS)
			System.out.println(line.replace("#", "O"));
		else
			System.out.println(line);
	}
}
