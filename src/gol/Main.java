package gol;

public class Main {
	public static void main(final String[] args) {
		final ArgumentParser parser = new ArgumentParser(args);
		try {
			final Arguments arguments = parser.parse();
			final GameOfLifeScope gameOfLifeScope = new GameOfLifeScope(arguments);
			final GameOfLife game = gameOfLifeScope.gameOfLife();
			game.runSimulation();
		} catch(final Exception e) {
			parser.usage(e.getMessage());
		}
	}
}
