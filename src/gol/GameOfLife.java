package gol;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class GameOfLife {

	public static void main(String[] args) {
		GameOfLife game = new GameOfLife();

		ArgumentParser parser = new ArgumentParser(args);

		if (parser.parse(game)) {
			game.init();

			game.runSimulation();
		}
	}

	static void line(String s) {
		System.out.println(s);
	}

	private long computationTimeStart = System.currentTimeMillis();
	private int height = -1;
	private int width = -1;
	private int steps = 100;
	private int stepDelay = -1;
	private boolean quietMode = false;
	private boolean isAtSigns = false;
	private boolean isOSigns = false;
	private int historyLength;

	private World world = null;
	private List<World> history = new LinkedList<World>();
	private int stepCount = 0;

	public void init() {
		if (world == null) {
			height = height == -1 ? 15 : height;
			width = width == -1 ? 20 : width;

			world = new World(width, height);
		}
	}

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
			stepWorld();

			String loopDetection = "";
			int index = history.indexOf(world);
			if (index != -1) {
				loopDetection = " - loop of length " + (index + 1)
						+ " detected";
			}

			if (!quietMode || stepCount == steps || !loopDetection.isEmpty()) {
				printWorld();

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

	private void stepWorld() {
		if (stepCount != 0) {
			world.addMargins();

			World newWorld = world.step();

			world.stripMargins();

			history.add(0, world);
			if (history.size() == historyLength + 1)
				history.remove(historyLength);

			world = newWorld;

			world.stripMargins();
		}
	}

	private void printWorld() {
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
