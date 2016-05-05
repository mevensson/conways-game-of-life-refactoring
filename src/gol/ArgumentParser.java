package gol;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ArgumentParser {

	private List<String> argList;

	public Arguments parse(final String[] args) throws Exception {
		argList = new LinkedList<String>(Arrays.asList(args));
		final Arguments arguments = new Arguments();
		while (argList.size() > 0) {
			parseArgument(arguments);
		}
		return arguments;
	}

	private void parseArgument(final Arguments arguments) throws Exception {
		final String arg = getArg();
		switch (arg) {
		case "-f":
			arguments.setFilename(getArg());
			break;
		case "-h":
			arguments.setHeight(getIntArg());
			break;
		case "-w":
			arguments.setWidth(getIntArg());
			break;
		case "-s":
			arguments.setSteps(getIntArg());
			break;
		case "-t":
			arguments.setStepDelay(getIntArg());
			break;
		case "-l":
			arguments.setHistoryLength(getIntArg());
			break;
		case "-@":
			arguments.setOutputFormat(OutputFormat.AT_SIGNS);
			break;
		case "-O":
			arguments.setOutputFormat(OutputFormat.O_SIGNS);
			break;
		case "-q":
			arguments.setQuietMode(true);
			break;
		case "-?":
			throw new Exception("Help requested");
		default:
			throw new Exception("Unknown argument " + arg);
		}
	}
	private String getArg() {
		final String arg = argList.get(0);
		argList.remove(0);
		return arg;
	}

	private int getIntArg() {
		final String arg = getArg();
		final int n = Integer.parseInt(arg);
		if (n < 0)
			throw new RuntimeException("Invalid argument value " + n);
		return n;
	}

	public void usage(final String message) {
		line(message);
		line("");
		line("Usage");
		line(" java gol.Main [ARGUMENTS...]");
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

	void line(final String s) {
		System.out.println(s);
	}
}
