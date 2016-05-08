package gol;

import gol.argument_parser.ArgumentParser;
import gol.game.GameOfLife;

public class Main {
	public static void main(final String[] args) {
		final ArgumentParser parser = new ArgumentParser();
		final Arguments arguments = new Arguments(parser);
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
