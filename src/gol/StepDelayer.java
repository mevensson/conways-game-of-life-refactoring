package gol;

public class StepDelayer {
	private final int stepDelay;

	private long computationTimeStart = System.currentTimeMillis();

	public StepDelayer(int stepDelay) {
		this.stepDelay = stepDelay;
	}

	public void delay() {
		long computationTime = System.currentTimeMillis()
				- computationTimeStart;
		if (stepDelay - computationTime >= 0) {
			try {
				Thread.sleep(stepDelay - computationTime);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
		computationTimeStart = System.currentTimeMillis();
	}
}
