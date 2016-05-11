package gol;

import common.argument_parser.ArgumentParser;
import common.dependency_injection.ScopeEntrance;
import gol.game.GameOfLife;

public class GameOfLifeApplication {

	private final ScopeEntrance<GameOfLife, GameOfLifeScope> gameOfLifeScopeEntrance;
	private final Arguments arguments;
	private final ArgumentParser parser;
	private final String[] args;

	public GameOfLifeApplication(
			final ScopeEntrance<GameOfLife, GameOfLifeScope> gameOfLifeScopeEntrance,
			final Arguments arguments,
			final ArgumentParser parser,
			final String[] args) {
		this.gameOfLifeScopeEntrance = gameOfLifeScopeEntrance;
		this.arguments = arguments;
		this.parser = parser;
		this.args = args;
	}

	public void run() {
		try {
			parser.parse(args);
			final GameOfLifeScope scope = new GameOfLifeScope(arguments);
			final GameOfLife game = gameOfLifeScopeEntrance.enter(scope);
			game.runSimulation();
		} catch(final Exception e) {
			parser.usage(e.getMessage());
		}
	}
}
