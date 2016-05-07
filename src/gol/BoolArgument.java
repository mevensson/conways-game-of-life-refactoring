package gol;

import java.util.Optional;
import java.util.Queue;

public class BoolArgument extends Argument {

	private boolean set;

	public BoolArgument(final String flag, final String help) {
		super(flag, Optional.empty(), help);
	}

	public boolean isSet() {
		return set;
	}

	@Override
	public void parse(final Queue<String> argList) {
		set = true;
	}

}