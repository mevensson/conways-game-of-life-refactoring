package gol;

public class GameOfLifeSpecialOutputFormat extends GameOfLife {

	boolean isAtSigns;

	@Override
	void printWorldLine(String line) {
		if (isAtSigns)
			super.printWorldLine(line.replace("#", "@ ").replace("-", ". "));
		else
			super.printWorldLine(line.replace("#", "O"));
	}
}