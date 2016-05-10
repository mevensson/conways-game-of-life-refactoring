package gol;

import gol.argument_parser.ArgumentParser;
import gol.game.GameOfLife;

public class GameOfLifeApplication {

	private final Arguments arguments;
	private final ArgumentParser parser;
	private final String[] args;

	public GameOfLifeApplication(final Arguments arguments,
			final ArgumentParser parser, final String[] args) {
		this.arguments = arguments;
		this.parser = parser;
		this.args = args;
	}

	public void run() {
		try {
			parser.parse(args);
			final GameOfLifeScope gameOfLifeScope = new GameOfLifeScope(arguments);
			final GameOfLife game = gameOfLifeScope.gameOfLife();
			game.runSimulation();
		} catch(final Exception e) {
			parser.usage(e.getMessage());
		}
	}
}
