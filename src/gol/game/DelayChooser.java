package gol.game;

import java.util.function.Supplier;

import gol.delayer.Delayer;

public class DelayChooser implements Supplier<Delayer> {

	private final Supplier<Delayer> quietDelayer;
	private final Supplier<Delayer> nonQuietDelayer;
	private final boolean quietMode;

	public DelayChooser(
			final Supplier<Delayer> quietDelayer,
			final Supplier<Delayer> nonQuietDelayer,
			final boolean quietMode) {
		this.quietDelayer = quietDelayer;
		this.nonQuietDelayer = nonQuietDelayer;
		this.quietMode = quietMode;
	}

	@Override
	public Delayer get() {
		if (quietMode) {
			return quietDelayer.get();
		}
		return nonQuietDelayer.get();
	}
}
