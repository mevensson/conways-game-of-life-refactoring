package gol;

import java.io.FileNotFoundException;

public class GameOfLife {
	private StringArrayWorld world;
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
			world = new StringArrayWorld(arguments.getFilename().get());
			viewPortHeight = arguments.getHeightOrElse(() -> world.height());
			viewPortWidth = arguments.getWidthOrElse(() -> world.width());
		} else {
			viewPortHeight = arguments.getHeight();
			viewPortWidth = arguments.getWidth();
			world = (StringArrayWorld) new RandomWorldGenerator().generate(viewPortWidth, viewPortHeight);
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
			stepWorld();

			if (!quietMode || stepCount == steps || loopDetector.hasLoop(world)) {
				worldPrinter.printWorld(world);

				if (stepCount == 0) {
					System.out.println("start");
				} else
					line("step " + stepCount + loopDetector.getLoopString(world));
				System.out.println();
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
		if (stepCount != 0) {
			world.addMargins();

			StringArrayWorld newWorld = world.step();

			world.stripMargins();

			history.add(world);

			world = newWorld;

			world.stripMargins();
		}
	}

	private void line(String s) {
		System.out.println(s);
	}
}
