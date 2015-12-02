package gol;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameOfLife {

	private static long computationTimeStart;

	public static void main(String[] args) {
		GameOfLife game = new GameOfLife();

		computationTimeStart = System.currentTimeMillis();

		ArgumentParser parser = new ArgumentParser(args);

		if (parser.parse(game)) {
			if (game.world.world == null) {
				game.world.world = new ArrayList<String>();

				game.height = game.height == -1 ? 15 : game.height;
				game.width = game.width == -1 ? 20 : game.width;

				Random rand = new Random();
				for (int h = 0; h < game.height; h++) {
					String line = "";
					for (int w = 0; w < game.width; w++) {

						line += rand.nextBoolean() ? '#' : '-';
					}
					game.world.world.add(line);
				}
			}

			if (game.steps == -1)
				game.steps = 100;

			game.runSimulation();
		}
	}

	static void line(String s) {
		System.out.println(s);
	}

	static class History {
		List<String> world;
		int heightOffset;
		int widthOffset;

		History(List<String> world, int heightOffset, int widthOffset) {
			this.world = world;
			this.heightOffset = heightOffset;
			this.widthOffset = widthOffset;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			History other = (History) obj;
			if (heightOffset != other.heightOffset)
				return false;
			if (widthOffset != other.widthOffset)
				return false;
			if (world == null) {
				if (other.world != null)
					return false;
			} else if (!world.equals(other.world))
				return false;
			return true;
		}
	}

	private int height = -1;
	private int width = -1;
	private int steps = -1;
	private int stepDelay = -1;
	private boolean quietMode = false;
	private boolean isAtSigns = false;
	private boolean isOSigns = false;
	private int historyLength;

	private World world = new World(null, 0, 0);
	private List<History> history = new LinkedList<History>();
	private int stepCount = 0;

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public void setStepDelay(int stepDelay) {
		this.stepDelay = stepDelay;
	}

	public void setHistoryLength(int historyLength) {
		this.historyLength = historyLength;
	}

	public void setQuietMode(boolean quietMode) {
		this.quietMode = quietMode;
	}

	public void setAtSigns(boolean isAtSigns) {
		this.isAtSigns = isAtSigns;
	}

	public void setOSigns(boolean isOSigns) {
		this.isOSigns = isOSigns;
	}

	public void parseFile(String filePath) throws FileNotFoundException {
		world = new World(filePath);
		if (height == -1)
			height = world.height();
		if (width == -1)
			width = world.width();
	}

	public void runSimulation() {

		while (stepCount <= steps) {

			if (stepCount != 0) {

				world.addMargins();

				List<String> newWorld = new ArrayList<>();
				int newHeightOffset = world.heightOffset;
				int newWidthtOffset = world.widthOffset;

				for (int h = 0; h < world.height(); h++) {
					String line = "";
					for (int w = 0; w < world.world.get(0).length(); w++) {
						int n = world.countAliveNeighbors(h, w);

						char cell = '-';

						if (world.isAlive(w, h)) {
							if (n == 2 || n == 3)
								cell = '#';
						} else {
							if (n == 3)
								cell = '#';
						}

						line += cell;
					}
					newWorld.add(line);
				}

				world.stripMargins();

				history.add(0, new History(world.world, world.heightOffset, world.widthOffset));
				if (history.size() == historyLength + 1)
					history.remove(historyLength);

				world.world = newWorld;
				world.heightOffset = newHeightOffset;
				world.widthOffset = newWidthtOffset;

				world.stripMargins();
			}
			
			String loopDetection = "";

			int index = history.indexOf(new History(world.world, world.heightOffset,
					world.widthOffset));
			if (index != -1) {
				loopDetection = " - loop of length " + (index + 1)
						+ " detected";
			}

			String linePrefix = "";

			for (int i = 0; i < world.widthOffset; i++) {
				linePrefix += '-';
			}

			String lineSuffix = "";

			int worldWidth = world.width();
			for (int i = 0; i < width - worldWidth - world.widthOffset; i++) {
				lineSuffix += '-';
			}

			int printHeight = 0;

			if (!quietMode || stepCount == steps || !loopDetection.isEmpty()) {
				for (int i = 0; i < Math.min(world.heightOffset, height); i++) {
					String line = "";
					while (line.length() < width) {
						line += '-';
					}
					printWorldLine(line);
					printHeight++;
				}

				for (int i = Math.max(0, -world.heightOffset); i < world.height(); i++) {

					if (printHeight == height)
						break;
					String line = world.world.get(i);

					line = linePrefix + line + lineSuffix;

					if (world.widthOffset < 0)
						line = line.substring(-world.widthOffset);

					if (line.length() > width)
						line = line.substring(0, width);
					printWorldLine(line);
					printHeight++;
				}

				for (; printHeight < height; printHeight++) {
					String line = "";
					while (line.length() < width) {
						line += '-';
					}
					printWorldLine(line);
				}

			

				if (stepCount == 0) {
					System.out.println("start");
				} else
					line("step " + stepCount + loopDetection);
				System.out.println();
			}

			stepCount++;

			long computationTime = System.currentTimeMillis()
					- computationTimeStart;

			if (!quietMode && stepDelay - computationTime >= 0) {
				try {

					Thread.sleep(stepDelay - computationTime);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}

			computationTimeStart = System.currentTimeMillis();

			if (!loopDetection.isEmpty()) {
				break;
			}
		}
	}

	private void printWorldLine(String line) {
		if (isAtSigns)
			System.out.println(line.replace("#", "@ ").replace("-", ". "));
		else if (isOSigns)
			System.out.println(line.replace("#", "O"));
		else
			System.out.println(line);
	}
}
