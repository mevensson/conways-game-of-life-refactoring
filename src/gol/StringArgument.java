package gol;

import java.util.Optional;
import java.util.Queue;

public class StringArgument extends Argument {

	private Optional<String> argument = Optional.empty();

	public StringArgument(final String flag, final String help) {
		super(flag, help);
	}

	public Optional<String> getArgument() {
		return argument;
	}

	@Override
	public void parse(final Queue<String> argList) {
		argument = Optional.of(argList.remove());
	}

}
