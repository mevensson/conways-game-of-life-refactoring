package gol.game;

import gol.delayer.Delayer;
import gol.game.output.WorldPrinter;
import gol.game.world.World;
import gol.history.History;
import gol.history.LoopDetector;

public class GameOfLife {
	private final History<World> history;
	private final Delayer stepDelayer;
	private final LoopDetector<World> loopDetector;
	private final WorldPrinter worldPrinter;
	private final WorldStepper worldStepper;
	private final StepCounter stepCounter;
	private final int maxSteps;
	private final boolean quietMode;

	private World world;

	public GameOfLife(final World world, final History<World> history,
			final Delayer stepDelayer, final LoopDetector<World> loopDetector,
			final WorldPrinter worldPrinter, final WorldStepper worldStepper,
			final StepCounter stepCounter, final int maxSteps,
			final boolean quietMode) {
		this.world = world;
		this.history = history;
		this.stepDelayer = stepDelayer;
		this.loopDetector = loopDetector;
		this.worldPrinter = worldPrinter;
		this.worldStepper = worldStepper;
		this.stepCounter = stepCounter;
		this.maxSteps = maxSteps;
		this.quietMode = quietMode;
	}

	public void runSimulation() {
		if (shouldPrintStartWorld()) {
			printStartWorld();
		}

		while (!isDone()) {
			stepWorld();
			if (shouldPrintWorld()) {
				printWorld();
			}
			stepDelayer.delay();
		}
	}

	private boolean shouldPrintStartWorld() {
		return !quietMode || maxSteps == 0;
	}

	private void printStartWorld() {
		worldPrinter.printWorld(world);
		line(stepCounter.toString());
		line("");
	}

	private boolean isDone() {
		return stepCounter.getCount() >= maxSteps || loopDetector.hasLoop(world);
	}

	private void stepWorld() {
		history.add(world);
		world = worldStepper.step(world);
		stepCounter.increaseCount();
	}
	private boolean shouldPrintWorld() {
		return !quietMode || simulationDone();
	}

	private void printWorld() {
		worldPrinter.printWorld(world);
		line(stepCounter.toString() + loopDetector.getLoopString(world));
		line("");
	}

	private boolean simulationDone() {
		return stepCounter.getCount() >= maxSteps || loopDetector.hasLoop(world);
	}

	private void line(final String s) {
		System.out.println(s);
	}
}
