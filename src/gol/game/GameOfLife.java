package gol.game;

import java.util.function.Supplier;

import gol.ScopeEntrance;
import gol.delayer.Delayer;
import gol.game.output.GamePrinter;
import gol.game.world.World;
import gol.history.History;
import gol.history.LoopDetector;

public class GameOfLife {
	private final Supplier<StartWorld> startWorldSupplier;
	private final ScopeEntrance<GamePrinter, StartWorldCreatedScope> startWorldCreatedScopeEntrance;
	private final History<World> history;
	private final Delayer stepDelayer;
	private final LoopDetector<World> loopDetector;
	private final WorldStepper worldStepper;
	private final StepCounter stepCounter;
	private final int maxSteps;

	public GameOfLife(
			final Supplier<StartWorld> startWorldSupplier,
			final ScopeEntrance<GamePrinter, StartWorldCreatedScope> startWorldCreatedScopeEntrance,
			final History<World> history,
			final Delayer stepDelayer,
			final LoopDetector<World> loopDetector,
			final WorldStepper worldStepper,
			final StepCounter stepCounter,
			final int maxSteps) {
		this.startWorldSupplier = startWorldSupplier;
		this.startWorldCreatedScopeEntrance = startWorldCreatedScopeEntrance;
		this.history = history;
		this.stepDelayer = stepDelayer;
		this.loopDetector = loopDetector;
		this.worldStepper = worldStepper;
		this.stepCounter = stepCounter;
		this.maxSteps = maxSteps;
	}

	public void runSimulation() {
		final StartWorld startWorld = startWorldSupplier.get();
		final StartWorldCreatedScope scope = new StartWorldCreatedScope(
				startWorld.getWidth(), startWorld.getHeight());
		final GamePrinter gamePrinter = startWorldCreatedScopeEntrance.enter(scope);

		World world = startWorld.getWorld();
		gamePrinter.printStartWorld(world);

		while (!isDone(world)) {
			stepCounter.increaseCount();
			history.add(world);
			world = worldStepper.step(world);
			gamePrinter.printWorld(world);
			stepDelayer.delay();
		}
	}

	private boolean isDone(final World world) {
		return stepCounter.getCount() >= maxSteps || loopDetector.hasLoop(world);
	}
}
