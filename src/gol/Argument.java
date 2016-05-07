package gol;

import java.util.Queue;

public abstract class Argument {

	private final String flag;
	private final String help;

	public Argument(final String flag, final String help) {
		this.flag = flag;
		this.help = help;
	}

	public final String getFlag() {
		return flag;
	}

	public final String getHelp() {
		return help;
	}

	public abstract void parse(Queue<String> argList);
}