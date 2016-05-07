package gol;

import gol.history.History;

public class LoopDetector {
	private final History<World> history;

	public LoopDetector(final History<World> history) {
		this.history = history;
	}

	public boolean hasLoop(final World world) {
		return history.indexOf(world) != -1;
	}

	public String getLoopString(final World world) {
		String loopDetection = "";
		final int index = history.indexOf(world);
		if (index != -1) {
			loopDetection = " - loop of length " + (index + 1) + " detected";
		}
		return loopDetection;
	}
}
