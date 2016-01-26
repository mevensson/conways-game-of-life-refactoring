package gol;

public class Main {
	public static void main(String[] args) {
		ArgumentParser parser = new ArgumentParser(args);
		try {
			Arguments arguments = parser.parse();
			GameOfLife game = new GameOfLife(arguments);
			game.runSimulation();
		} catch(Exception e) {
			parser.usage(e.getMessage());
		}
	}
}
