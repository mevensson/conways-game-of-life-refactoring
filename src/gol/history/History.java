package gol.history;

import java.util.LinkedList;
import java.util.List;

public final class History<Item> {
	private final List<Item> history = new LinkedList<Item>();
	private final int maxHistoryLength;

	public History(final int maxHistoryLength) {
		this.maxHistoryLength = maxHistoryLength;
	}

	public int indexOf(final Item item) {
		return history.indexOf(item);
	}

	public void add(final Item item) {
		history.add(0, item);
		if (history.size() == maxHistoryLength + 1)
			history.remove(maxHistoryLength);
	}
}
