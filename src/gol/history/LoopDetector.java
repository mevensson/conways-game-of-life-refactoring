package gol.history;

public final class LoopDetector<Item> {
	private final History<Item> history;

	public LoopDetector(final History<Item> history) {
		this.history = history;
	}

	public boolean hasLoop(final Item item) {
		return history.indexOf(item) != -1;
	}

	public String getLoopString(final Item item) {
		String loopDetection = "";
		final int index = history.indexOf(item);
		if (index != -1) {
			loopDetection = " - loop of length " + (index + 1) + " detected";
		}
		return loopDetection;
	}
}
