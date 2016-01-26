package gol;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class GameOfLife {

	static void line(String s) {
		System.out.println(s);
	}

	private World world;
	private int height;
	private int width;
	private int steps;
	private int stepDelay;
	private int historyLength;
	private OutputFormat outputFormat;
	private boolean quietMode;

	private long computationTimeStart = System.currentTimeMillis();
	private List<World> history = new LinkedList<World>();
	private int stepCount = 0;

	public GameOfLife(Arguments arguments) throws FileNotFoundException {
		if (arguments.getFilename().isPresent()) {
			world = new World(arguments.getFilename().get());
			height = arguments.getHeightOrElse(() -> world.height());
			width = arguments.getWidthOrElse(() -> world.width());
		} else {
			height = arguments.getHeight();
			width = arguments.getWidth();
			world = new World(width, height);
		}
		steps = arguments.getSteps();
		stepDelay = arguments.getStepDelay();
		historyLength = arguments.getHistoryLength();
		outputFormat = arguments.getOutputFormat();
		quietMode = arguments.isQuietMode();
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
		if (outputFormat == OutputFormat.AT_SIGNS)
			System.out.println(line.replace("#", "@ ").replace("-", ". "));
		else if (outputFormat == OutputFormat.O_SIGNS)
			System.out.println(line.replace("#", "O"));
		else
			System.out.println(line);
	}
}
