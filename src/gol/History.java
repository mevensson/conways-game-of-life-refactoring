package gol;

import java.util.LinkedList;
import java.util.List;

public class History {
	private List<StringArrayWorld> history = new LinkedList<StringArrayWorld>();
	private int historyLength;

	public History(Arguments arguments) {
		historyLength = arguments.getHistoryLength();
	}
	
	int indexOf(StringArrayWorld world) {
		return history.indexOf(world);
	}
	
	void add(StringArrayWorld world) {
		history.add(0, world);
		if (history.size() == historyLength + 1)
			history.remove(historyLength);
	}
}
