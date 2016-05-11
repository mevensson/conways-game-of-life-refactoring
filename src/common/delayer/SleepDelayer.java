package common.delayer;

public final class SleepDelayer implements Delayer {
	private final int delay;

	private long computationTimeStart = System.currentTimeMillis();

	public SleepDelayer(final int delay) {
		this.delay = delay;
	}

	@Override
	public void delay() {
		final long computationTime =
				System.currentTimeMillis() - computationTimeStart;
		if (delay - computationTime >= 0) {
			try {
				Thread.sleep(delay - computationTime);
			} catch (final InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
		computationTimeStart = System.currentTimeMillis();
	}
}
