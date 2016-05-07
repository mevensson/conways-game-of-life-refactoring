package gol;

import java.util.Optional;

import gol.argument_parser.ArgumentParser;
import gol.argument_parser.BoolArgument;
import gol.argument_parser.IntegerArgument;
import gol.argument_parser.StringArgument;

public class Arguments {
	private static final int DEFAULT_HEIGHT = 15;
	private static final int DEFAULT_WIDTH = 20;
	private static final int DEFAULT_STEPS = 100;
	private static final int DEFAULT_HISTORY_LENGTH = 0;
	private static final int DEFAULT_STEP_DELAY = -1;

	private final IntegerArgument width =
			new IntegerArgument(
					"-w",
					"WIDTH",
					"Width of simulation view port. Default is 20.",
					DEFAULT_WIDTH);

	private final IntegerArgument height =
			new IntegerArgument(
					"-h",
					"HEIGHT",
					"Height of simulation view port. Default is 15.",
					DEFAULT_HEIGHT);

	private final StringArgument filename =
			new StringArgument(
					"-f",
					"FILE_PATH",
					"File with start state. Default is a random start state.");

	private final BoolArgument atFormat =
			new BoolArgument(
					"-@",
					"Use spaced '@' and '.' instead of default '#' and '-'.");

	private final BoolArgument oFormat =
			new BoolArgument(
					"-O",
					"Use 'O' instead of default '#'.");

	private final IntegerArgument steps =
			new IntegerArgument(
					"-s",
					"STEPS",
					"Number of maximum generation steps. Default is 100.",
					DEFAULT_STEPS);

	private final IntegerArgument historyLength =
			new IntegerArgument(
					"-l",
					"X",
					"Detect loops of maximum length x. Default is 0 - no loop detection.",
					DEFAULT_HISTORY_LENGTH);

	private final IntegerArgument stepDelay =
			new IntegerArgument(
					"-t",
					"MS",
					"Time delay (ms) to wait between each step. Default is 0 ms.",
					DEFAULT_STEP_DELAY);

	private final BoolArgument quietMode =
			new BoolArgument(
					"-q",
					"Quiet mode. Only outputs the last step in a simulation. Ignores time delay.");

	public Optional<String> getFilename() {
		return filename.getArgument();
	}

	public int getHeight() {
		return height.getArgumentWithDefault();
	}

	public int getHeightOrElse(final int other) {
		return height.getArgumentWithoutDefault().orElse(other);
	}

	public int getWidth() {
		return width.getArgumentWithDefault();
	}

	public int getWidthOrElse(final int other) {
		return width.getArgumentWithoutDefault().orElse(other);
	}

	public int getSteps() {
		return steps.getArgumentWithDefault();
	}

	public int getStepDelay() {
		return stepDelay.getArgumentWithDefault();
	}

	public int getHistoryLength() {
		return historyLength.getArgumentWithDefault();
	}

	public OutputFormat getOutputFormat() {
		if (atFormat.isSet()) {
			return OutputFormat.AT_SIGNS;
		}
		if (oFormat.isSet()) {
			return OutputFormat.O_SIGNS;
		}
		return OutputFormat.DEFAULT;
	}

	public boolean isQuietMode() {
		return quietMode.isSet();
	}

	public void registerArguments(final ArgumentParser parser) {
		parser.registerArgument(width);
		parser.registerArgument(height);
		parser.registerArgument(filename);
		parser.registerArgument(atFormat);
		parser.registerArgument(oFormat);
		parser.registerArgument(steps);
		parser.registerArgument(historyLength);
		parser.registerArgument(stepDelay);
		parser.registerArgument(quietMode);
	}
}
