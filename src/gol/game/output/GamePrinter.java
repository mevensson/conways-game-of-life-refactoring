package gol.game.output;

import gol.game.StepCounter;
import gol.game.world.World;
import gol.history.LoopDetector;

public class GamePrinter {

	private final LoopDetector<World> loopDetector;
	private final WorldPrinter worldPrinter;
	private final StepCounter stepCounter;
	private final int maxSteps;
	private final boolean quietMode;

	public GamePrinter(final LoopDetector<World> loopDetector,
			final WorldPrinter worldPrinter, final StepCounter stepCounter,
			final int maxSteps, final boolean quietMode) {
		this.loopDetector = loopDetector;
		this.worldPrinter = worldPrinter;
		this.stepCounter = stepCounter;
		this.maxSteps = maxSteps;
		this.quietMode = quietMode;
	}

	public void printStartWorld(final World world) {
		if (shouldPrintStartWorld()) {
			doPrintWorld(world);
		}
	}

	private boolean shouldPrintStartWorld() {
		return !quietMode || maxSteps == 0;
	}

	public void printWorld(final World world) {
		if (shouldPrintWorld(world)) {
			doPrintWorld(world);
		}
	}

	private boolean shouldPrintWorld(final World world) {
		return !quietMode || simulationDone(world);
	}

	private boolean simulationDone(final World world) {
		return stepCounter.getCount() >= maxSteps || loopDetector.hasLoop(world);
	}

	private void doPrintWorld(final World world) {
		worldPrinter.printWorld(world);
		line(stepCounter.toString() + loopDetector.getLoopString(world));
		line("");
	}

	private void line(final String s) {
		System.out.println(s);
	}
}
