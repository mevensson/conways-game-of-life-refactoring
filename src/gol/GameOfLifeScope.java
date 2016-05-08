package gol;

import java.io.FileNotFoundException;
import java.util.Optional;

import gol.delayer.Delayer;
import gol.delayer.NoDelayDelayer;
import gol.delayer.SleepDelayer;
import gol.game.AliveNeighborCounter;
import gol.game.AliveNeighborsWorldStepper;
import gol.game.GameOfLife;
import gol.game.WorldStepper;
import gol.history.History;
import gol.history.LoopDetector;
import gol.stdout_output.WorldPrinter;

public class GameOfLifeScope {
	private final Arguments arguments;
	private History<World> history;
	private Delayer delayer;
	private LoopDetector<World> loopDetector;
	private WorldPrinter worldPrinter;
	private WorldStepper worldStepper;
	private World world;
	private Optional<Integer> viewPortHeight = Optional.empty();
	private Optional<Integer> viewPortWidth = Optional.empty();

	public GameOfLifeScope(final Arguments arguments) {
		this.arguments = arguments;
	}

	public GameOfLife gameOfLife() throws FileNotFoundException {
		return new GameOfLife(world(), history(), delayer(), loopDetector(),
				worldPrinter(), worldStepper(), arguments.getSteps(),
				arguments.isQuietMode());
	}

	private World world() throws FileNotFoundException {
		if (world == null) {
			createWorld();
		}
		return world;
	}

	private void createWorld() throws FileNotFoundException {
		if (arguments.getFilename().isPresent()) {
			createFileWorld();
		} else {
			createRandomWorld();
		}
	}

	private void createFileWorld() throws FileNotFoundException {
		final FileWorldReader fileWorldReader = new FileWorldReader();
		world = fileWorldReader.read(arguments.getFilename().get());
		viewPortHeight = Optional.of(
				arguments.getHeightOrElse(fileWorldReader.getHeight()));
		viewPortWidth = Optional.of(
				arguments.getWidthOrElse(fileWorldReader.getWidth()));
	}

	private void createRandomWorld() {
		viewPortHeight = Optional.of(arguments.getHeight());
		viewPortWidth = Optional.of(arguments.getWidth());
		world = new RandomWorldGenerator().generate(
				viewPortWidth.get(), viewPortHeight.get());
	}

	private History<World> history() {
		if (history == null) {
			history = new History<World>(arguments.getHistoryLength());
		}
		return history;
	}

	private Delayer delayer() {
		if (delayer == null) {
			if (arguments.isQuietMode()) {
				delayer = noDelayDelayer();
			} else {
				delayer = sleepDelayer();
			}
		}
		return delayer;
	}

	private Delayer noDelayDelayer() {
		return new NoDelayDelayer();
	}

	private Delayer sleepDelayer() {
		return new SleepDelayer(arguments.getStepDelay());
	}

	private LoopDetector<World> loopDetector() {
		if (loopDetector == null) {
			loopDetector = new LoopDetector<World>(history());
		}
		return loopDetector;
	}

	private WorldPrinter worldPrinter() throws FileNotFoundException {
		if (worldPrinter == null) {
			worldPrinter = new WorldPrinter(arguments.getOutputFormat(),
					viewPortWidth(), viewPortHeight());
		}
		return worldPrinter;
	}

	private WorldStepper worldStepper() {
		if (worldStepper == null) {
			worldStepper = new AliveNeighborsWorldStepper(
					BitSetWorld::new, new AliveNeighborCounter());
		}
		return worldStepper;
	}

	private int viewPortWidth() throws FileNotFoundException {
		if (!viewPortWidth.isPresent()) {
			createWorld();
		}
		return viewPortWidth.get();
	}

	private int viewPortHeight() throws FileNotFoundException {
		if (!viewPortHeight.isPresent()) {
			createWorld();
		}
		return viewPortHeight.get();
	}
}