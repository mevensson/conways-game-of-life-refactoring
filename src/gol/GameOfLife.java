package gol;

import java.io.FileNotFoundException;

public class GameOfLife {
	private World world;
	private int steps;
	private boolean quietMode;
	private History history;
	private StepDelayer stepDelayer;
	private LoopDetector loopDetector;
	private WorldPrinter worldPrinter;

	private int stepCount = 0;

	public GameOfLife(Arguments arguments) throws FileNotFoundException {
		final int viewPortHeight;
		final int viewPortWidth;
		if (arguments.getFilename().isPresent()) {
			FileWorldReader fileWorldReader = new FileWorldReader();
			world = fileWorldReader.read(arguments.getFilename().get());
			viewPortHeight = arguments.getHeightOrElse(() -> fileWorldReader.getHeight());
			viewPortWidth = arguments.getWidthOrElse(() -> fileWorldReader.getWidth());
		} else {
			viewPortHeight = arguments.getHeight();
			viewPortWidth = arguments.getWidth();
			world = new RandomWorldGenerator().generate(viewPortWidth, viewPortHeight);
		}
		steps = arguments.getSteps();
		quietMode = arguments.isQuietMode();
		history = new History(arguments);
		stepDelayer = new StepDelayer(arguments);
		loopDetector = new LoopDetector(history);
		worldPrinter = new WorldPrinter(arguments.getOutputFormat(), viewPortWidth, viewPortHeight);
	}

	public void runSimulation() {
		while (stepCount <= steps) {
			if (stepCount != 0) {
				stepWorld();
			}

			if (!quietMode || simulationDone()) {
				worldPrinter.printWorld(world);
				printStepCount();
				line("");
			}

			stepCount++;

			if (!quietMode) {
				stepDelayer.delay();
			}

			if (loopDetector.hasLoop(world)) {
				break;
			}
		}
	}

	private void stepWorld() {
		history.add(world);
		world = new WorldStepper().step(world);
	}

	private boolean simulationDone() {
		return stepCount >= steps || loopDetector.hasLoop(world);
	}

	private void printStepCount() {
		if (stepCount == 0) {
			line("start");
		} else {
			line("step " + stepCount + loopDetector.getLoopString(world));
		}
	}

	private void line(String s) {
		System.out.println(s);
	}
}
