package gol;

public class StepDelayer {
	private long computationTimeStart = System.currentTimeMillis();
	private int stepDelay;

	public StepDelayer(Arguments arguments) {
		stepDelay = arguments.getStepDelay();
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
