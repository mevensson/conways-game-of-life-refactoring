package gol;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ArgumentParser {

	static void line(String s) {
		System.out.println(s);
	}

	List<String> argList;

	public ArgumentParser(String[] args) {
		argList = new LinkedList<String>(Arrays.asList(args));
	}

	public boolean parse(GameOfLife game) {
		try {
			while (argList.size() > 0) {
				parseArgument(game, getArg());
			}
		} catch (Exception e) {
			usage(e.getMessage());
			return false;
		}
		return true;
	}

	private void parseArgument(GameOfLife game, String arg) throws Exception {
		switch (arg) {
		case "-s":
			game.steps = getIntArg();
			break;
		case "-f":
			game.parseFile(getArg());
			break;
		case "-?":
			throw new Exception("Help requested");
		case "-@":
			game.isAtSigns = true;
			break;
		case "-O":
			game.isOSigns = true;
			break;
		case "-w":
			game.width = getIntArg();
			break;
		case "-h":
			game.height = getIntArg();
			break;
		case "-l":
			game.historyLength = getIntArg();
			break;
		case "-t":
			game.stepDelay = getIntArg();
			break;
		case "-q":
			game.quietMode = true;
			break;
		default:
			throw new Exception("Unknown argument " + arg);
		}
	}
	private String getArg() {
		String arg = argList.get(0);
		argList.remove(0);
		return arg;
	}

	private int getIntArg() {
		String arg = getArg();
		int n = Integer.parseInt(arg);
		if (n < 0)
			throw new RuntimeException("Invalid argument value " + n);
		return n;
	}

	private void usage(String message) {
		line(message);
		line("");
		line("Usage");
		line(" java gol.GameOfLife [ARGUMENTS...]");
		line("");
		line(" arguments:");
		line("   -?              Prints this usage help.");
		line("   -w <WIDTH>      Width of simulation view port. Default is 20.");
		line("   -h <HEIGHT>     Height of simulation view port. Default is 15.");
		line("   -f <FILE_PATH>  File with start state. Default is a random start state.");
		line("   -@              Use spaced '@' and '.' instead of default '#' and '-'.");
		line("   -O              Use 'O' instead of default '#'.");
		line("   -s <STEPS>      Number of maximum generation steps. Default is 100.");
		line("   -l <X>          Detect loops of maximum length x. Default is 0 - no loop detection.");
		line("   -t <MS>         Time delay (ms) to wait between each step. Default is 0 ms.");
		line("   -q              Quiet mode. Only outputs the last step in a simulation. Ignores time delay.");
	}
}
