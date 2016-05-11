package gol;

import java.util.function.Supplier;

import common.dependency_injection.Cache;
import common.history.History;
import common.history.LoopDetector;
import gol.game.StepCounter;
import gol.game.world.World;

public class GameOfLifeScope {
	private final Arguments arguments;

	private final Cache<History<World>> historyCache = new Cache<>();
	private final Cache<LoopDetector<World>> loopDetectorCache = new Cache<>();
	private final Cache<StepCounter> stepCounterCache = new Cache<>();

	public GameOfLifeScope(final Arguments arguments) {
		this.arguments = arguments;
	}

	public Arguments getArguments() {
		return arguments;
	}

	public History<World> getHistory(final Supplier<History<World>> supplier) {
		return historyCache.get(supplier);
	}

	public LoopDetector<World> getLoopDetector(
			final Supplier<LoopDetector<World>> supplier) {
		return loopDetectorCache.get(supplier);
	}

	public StepCounter getStepCounter(final Supplier<StepCounter> supplier) {
		return stepCounterCache.get(supplier);
	}
}