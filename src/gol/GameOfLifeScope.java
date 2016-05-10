package gol;

import java.io.FileNotFoundException;
import java.util.function.Supplier;

import gol.delayer.Delayer;
import gol.delayer.NoDelayDelayer;
import gol.delayer.SleepDelayer;
import gol.game.AliveNeighborCounter;
import gol.game.AliveNeighborsWorldStepper;
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
import gol.history.History;
import gol.history.LoopDetector;

public class GameOfLifeScope {
	private final Arguments arguments;
	private History<World> history;
	private Delayer delayer;
	private LoopDetector<World> loopDetector;
	private WorldStepper worldStepper;
	private StepCounter stepCounter;

	public GameOfLifeScope(final Arguments arguments) {
		this.arguments = arguments;
	}

	public GameOfLife gameOfLife() throws FileNotFoundException {
		return new GameOfLife(
				startWorldGenerator(), startWorldCreatedScopeEntrance(),
				history(), delayer(), loopDetector(), worldStepper(),
				stepCounter(), arguments.getSteps());
	}

	private Supplier<StartWorld> startWorldGenerator() {
		return new StartWorldFactory(
				fileWorldReader(),
				randomWorldGenerator(),
				arguments.getWidth(),
				arguments.getHeight(),
				arguments.getFilename());
	}

	private FileWorldReader fileWorldReader() {
		return new FileWorldReader(SetWorld::new);
	}

	private RandomWorldGenerator randomWorldGenerator() {
		return new RandomWorldGenerator(SetWorld::new);
	}

	private ScopeEntrance<GamePrinter, StartWorldCreatedScope> startWorldCreatedScopeEntrance() {
		return (scope) -> injectGamePrinter(scope);
	}

	private GamePrinter injectGamePrinter(final StartWorldCreatedScope scope) {
		return new GamePrinter(loopDetector(), worldPrinter(scope),
				stepCounter(), arguments.getSteps(), arguments.isQuietMode());
	}

	private WorldPrinter worldPrinter(final StartWorldCreatedScope scope) {
		return new WorldPrinter(arguments.getOutputFormat(),
				scope.getWidth(), scope.getHeight());
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

	private WorldStepper worldStepper() {
		if (worldStepper == null) {
			worldStepper = new AliveNeighborsWorldStepper(
					SetWorld::new, new AliveNeighborCounter());
		}
		return worldStepper;
	}

	private StepCounter stepCounter() {
		if (stepCounter == null) {
			stepCounter = new StepCounter();
		}
		return stepCounter;
	}
}