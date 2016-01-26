package gol;

public class Main {
	public static void main(String[] args) {
		GameOfLife game = new GameOfLife();

		ArgumentParser parser = new ArgumentParser(args);

		if (parser.parse(game)) {
			game.init();

			game.runSimulation();
		}
	}
}
