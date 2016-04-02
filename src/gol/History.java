package gol;

import java.util.LinkedList;
import java.util.List;

public class History {
	private List<World> history = new LinkedList<World>();
	private int maxHistoryLength;

	public History(Arguments arguments) {
		maxHistoryLength = arguments.getHistoryLength();
	}

	int indexOf(World world) {
		return history.indexOf(world);
	}

	void add(World world) {
		history.add(0, world);
		if (history.size() == maxHistoryLength + 1)
			history.remove(maxHistoryLength);
	}
}
