package gol.game;

public class StepCounter {
	private int stepCount = 0;

	public int getCount() {
		return stepCount;
	}

	public void increaseCount() {
		stepCount++;
	}

	@Override
	public String toString() {
		if (stepCount == 0) {
			return "start";
		}
		return "step " + stepCount;
	}
}