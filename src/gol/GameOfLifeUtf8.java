package gol;

public class GameOfLifeUtf8 extends GameOfLife {

	boolean isBlocks;

	@Override
	void printWorldLine(String line) {
		if (isBlocks)
			super.printWorldLine(line.replace('#', '█').replace('-', '░'));
		else
			super.printWorldLine(line.replace("#", "● ").replace("-", "◌ "));
	}
}