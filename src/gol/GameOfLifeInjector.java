package gol;

import java.util.function.Supplier;

import common.delayer.Delayer;
import common.delayer.NoDelayDelayer;
import common.delayer.SleepDelayer;
import common.history.History;
import common.history.LoopDetector;
import gol.game.AliveNeighborCounter;
import gol.game.AliveNeighborsWorldStepper;
import gol.game.DelayChooser;
import gol.game.GameOfLife;
import gol.game.StartWorld;
import gol.game.StartWorldCreatedScope;
import gol.game.StartWorldFactory;
import gol.game.StepCounter;
import gol.game.WorldStepper;
import gol.game.input.FileWorldReader;
import gol.game.input.RandomWorldGenerator;
import gol.game.output.GamePrinter;
import gol.game.output.WorldPrinter;
import gol.game.world.SetWorld;
import gol.game.world.World;

public class GameOfLifeInjector {

	public static GameOfLife injectGameOfLife(final GameOfLifeScope golScope) {
		return new GameOfLife(
				injectStartWorldGenerator(golScope),
				injectStartWorldCreatedScopeEntrance(golScope),
				injectHistory(golScope),
				injectDelayer(golScope),
				injectLoopDetector(golScope),
				injectWorldStepper(),
				injectStepCounter(golScope),
				golScope.getArguments().getSteps());
	}

	private static Supplier<StartWorld> injectStartWorldGenerator(
			final GameOfLifeScope golScope) {
		return new StartWorldFactory(
				injectFileWorldReader(),
				injectRandomWorldGenerator(),
				golScope.getArguments().getWidth(),
				golScope.getArguments().getHeight(),
				golScope.getArguments().getFilename());
	}

	private static FileWorldReader injectFileWorldReader() {
		return new FileWorldReader(injectWorldSupplier());
	}

	private static RandomWorldGenerator injectRandomWorldGenerator() {
		return new RandomWorldGenerator(SetWorld::new);
	}

	private static Supplier<World> injectWorldSupplier() {
		return SetWorld::new;
	}

	private static ScopeEntrance<GamePrinter, StartWorldCreatedScope>
			injectStartWorldCreatedScopeEntrance(
					final GameOfLifeScope golScope) {
		return (swcScope) -> injectGamePrinter(golScope, swcScope);
	}

	private static GamePrinter injectGamePrinter(
			final GameOfLifeScope golScope,
			final StartWorldCreatedScope swcScope) {
		return new GamePrinter(
				injectLoopDetector(golScope),
				injectWorldPrinter(golScope, swcScope),
				injectStepCounter(golScope),
				golScope.getArguments().getSteps(),
				golScope.getArguments().isQuietMode());
	}

	private static LoopDetector<World> injectLoopDetector(
			final GameOfLifeScope golScope) {
		return golScope.getLoopDetector(() -> new LoopDetector<>(
				injectHistory(golScope)));
	}

	private static History<World> injectHistory(
			final GameOfLifeScope golScope) {
		return golScope.getHistory(() -> new History<>(
				golScope.getArguments().getHistoryLength()));
	}

	private static WorldPrinter injectWorldPrinter(
			final GameOfLifeScope golScope,
			final StartWorldCreatedScope swcScope) {
		return new WorldPrinter(
				golScope.getArguments().getOutputFormat(),
				swcScope.getWidth(),
				swcScope.getHeight());
	}

	private static StepCounter injectStepCounter(
			final GameOfLifeScope golScope) {
		return golScope.getStepCounter(StepCounter::new);
	}

	private static Delayer injectDelayer(final GameOfLifeScope golScope) {
		final DelayChooser delayChooser = new DelayChooser(
				NoDelayDelayer::new,
				() -> injectSleepDelayer(golScope),
				golScope.getArguments().isQuietMode());
		return delayChooser.get();
	}

	private static Delayer injectSleepDelayer(final GameOfLifeScope golScope) {
		return new SleepDelayer(golScope.getArguments().getStepDelay());
	}

	private static WorldStepper injectWorldStepper() {
		return new AliveNeighborsWorldStepper(
				injectWorldSupplier(),
				injectAliveNeighbourCounter());
	}

	private static AliveNeighborCounter injectAliveNeighbourCounter() {
		return new AliveNeighborCounter();
	}
}
