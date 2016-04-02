package gol;

import java.io.FileNotFoundException;
import java.util.Optional;

public class GameOfLifeScope {
	private final Arguments arguments;
	private History history;
	private StepDelayer stepDelayer;
	private LoopDetector loopDetector;
	private WorldPrinter worldPrinter;
	private World world;
	private Optional<Integer> viewPortHeight = Optional.empty();
	private Optional<Integer> viewPortWidth = Optional.empty();

	public GameOfLifeScope(Arguments arguments) {
		this.arguments = arguments;
	}

	public GameOfLife gameOfLife() throws FileNotFoundException {
		return new GameOfLife(world(), history(), stepDelayer(), loopDetector(),
				worldPrinter(), arguments.getSteps(), arguments.isQuietMode());
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
		FileWorldReader fileWorldReader = new FileWorldReader();
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

	private History history() {
		if (history == null) {
			history = new History(arguments.getHistoryLength());
		}
		return history;
	}

	private StepDelayer stepDelayer() {
		if (stepDelayer == null) {
			stepDelayer = new StepDelayer(arguments.getStepDelay());
		}
		return stepDelayer;
	}

	private LoopDetector loopDetector() {
		if (loopDetector == null) {
			loopDetector = new LoopDetector(history());
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