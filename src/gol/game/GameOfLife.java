package gol.game;

import gol.delayer.Delayer;
import gol.game.output.GamePrinter;
import gol.game.world.World;
import gol.history.History;
import gol.history.LoopDetector;

public class GameOfLife {
	private final History<World> history;
	private final Delayer stepDelayer;
	private final LoopDetector<World> loopDetector;
	private final GamePrinter gamePrinter;
	private final WorldStepper worldStepper;
	private final StepCounter stepCounter;
	private final int maxSteps;

	private World world;

	public GameOfLife(final World world, final History<World> history,
			final Delayer stepDelayer, final LoopDetector<World> loopDetector,
			final GamePrinter gamePrinter, final WorldStepper worldStepper,
			final StepCounter stepCounter, final int maxSteps) {
		this.world = world;
		this.history = history;
		this.stepDelayer = stepDelayer;
		this.loopDetector = loopDetector;
		this.gamePrinter = gamePrinter;
		this.worldStepper = worldStepper;
		this.stepCounter = stepCounter;
		this.maxSteps = maxSteps;
	}

	public void runSimulation() {
		gamePrinter.printStartWorld(world);

		while (!isDone()) {
			stepCounter.increaseCount();
			history.add(world);
			world = worldStepper.step(world);
			gamePrinter.printWorld(world);
			stepDelayer.delay();
		}
	}

	private boolean isDone() {
		return stepCounter.getCount() >= maxSteps || loopDetector.hasLoop(world);
	}
}
