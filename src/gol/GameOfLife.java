package gol;

import gol.delayer.Delayer;
import gol.history.History;
import gol.history.LoopDetector;

public class GameOfLife {
	private final History<World> history;
	private final Delayer stepDelayer;
	private final LoopDetector<World> loopDetector;
	private final WorldPrinter worldPrinter;
	private final int maxSteps;
	private final boolean quietMode;

	private World world;
	private int stepCount = 0;

	public GameOfLife(final World world, final History<World> history,
			final Delayer stepDelayer, final LoopDetector<World> loopDetector,
			final WorldPrinter worldPrinter, final int maxSteps,
			final boolean quietMode) {
		this.world = world;
		this.history = history;
		this.stepDelayer = stepDelayer;
		this.loopDetector = loopDetector;
		this.worldPrinter = worldPrinter;
		this.maxSteps = maxSteps;
		this.quietMode = quietMode;
	}

	public void runSimulation() {
		while (stepCount <= maxSteps) {
			if (stepCount != 0) {
				stepWorld();
			}

			if (!quietMode || simulationDone()) {
				worldPrinter.printWorld(world);
				printStepCount();
				line("");
			}

			stepCount++;
			stepDelayer.delay();

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
		return stepCount >= maxSteps || loopDetector.hasLoop(world);
	}

	private void printStepCount() {
		if (stepCount == 0) {
			line("start");
		} else {
			line("step " + stepCount + loopDetector.getLoopString(world));
		}
	}

	private void line(final String s) {
		System.out.println(s);
	}
}
