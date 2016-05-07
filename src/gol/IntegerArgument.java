package gol;

import java.util.OptionalInt;
import java.util.Queue;

public class IntegerArgument extends Argument {

	private OptionalInt argument = OptionalInt.empty();
	private final int defaultValue;

	public IntegerArgument(final String flag, final String help, final int defaultValue) {
		super(flag, help);
		this.defaultValue = defaultValue;
	}

	public OptionalInt getArgumentWithoutDefault() {
		return argument;
	}

	public int getArgumentWithDefault() {
		return argument.orElse(defaultValue);
	}

	@Override
	public void parse(final Queue<String> argList) {
		argument = OptionalInt.of(getIntArg(argList));
	}

	private int getIntArg(final Queue<String> argList) {
		final String arg = argList.remove();
		final int n = Integer.parseInt(arg);
		if (n < 0)
			throw new RuntimeException("Invalid argument value " + n);
		return n;
	}
}
