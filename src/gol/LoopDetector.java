package gol;

public class LoopDetector {
	private History history;

	public LoopDetector(History history) {
		this.history = history;
	}
	
	public boolean hasLoop(StringArrayWorld world) {
		return history.indexOf(world) != -1;
	}
	
	public String getLoopString(StringArrayWorld world) {
		String loopDetection = "";
		int index = history.indexOf(world);
		if (index != -1) {
			loopDetection = " - loop of length " + (index + 1) + " detected";
		}
		return loopDetection;
	}
}
