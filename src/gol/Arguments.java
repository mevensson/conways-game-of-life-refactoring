package gol;

import java.util.Optional;

public class Arguments {
	private static final int DEFAULT_HEIGHT = 15;
	private static final int DEFAULT_WIDTH = 20;
	private static final int DEFAULT_STEPS = 100;
	private static final int DEFAULT_STEP_DELAY = -1;
	private static final int DEFAULT_HISTORY_LENGTH = 0;

	private Optional<String> filename = Optional.empty();
	private Optional<Integer> height = Optional.empty();
	private Optional<Integer> width = Optional.empty();
	private Optional<Integer> steps = Optional.empty();
	private Optional<Integer> stepDelay = Optional.empty();
	private Optional<Integer> historyLength = Optional.empty();
	private OutputFormat outputFormat = OutputFormat.DEFAULT;
	private boolean quietMode = false;

	public Optional<String> getFilename() {
		return filename;
	}

	public void setFilename(final String filename) {
		this.filename = Optional.of(filename);
	}

	public int getHeight() {
		return height.orElse(DEFAULT_HEIGHT);
	}

	public int getHeightOrElse(final int other) {
		return height.orElse(other);
	}

	public void setHeight(final int height) {
		this.height = Optional.of(height);
	}

	public int getWidth() {
		return width.orElse(DEFAULT_WIDTH);
	}

	public int getWidthOrElse(final int other) {
		return width.orElse(other);
	}

	public void setWidth(final int width) {
		this.width = Optional.of(width);
	}

	public int getSteps() {
		return steps.orElse(DEFAULT_STEPS);
	}

	public void setSteps(final int steps) {
		this.steps = Optional.of(steps);
	}

	public int getStepDelay() {
		return stepDelay.orElse(DEFAULT_STEP_DELAY);
	}

	public void setStepDelay(final int stepDelay) {
		this.stepDelay = Optional.of(stepDelay);
	}

	public int getHistoryLength() {
		return historyLength.orElse(DEFAULT_HISTORY_LENGTH);
	}

	public void setHistoryLength(final int historyLength) {
		this.historyLength = Optional.of(historyLength);
	}

	public OutputFormat getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(final OutputFormat outputFormat) {
		this.outputFormat = outputFormat;
	}

	public boolean isQuietMode() {
		return quietMode;
	}

	public void setQuietMode(final boolean quietMode) {
		this.quietMode = quietMode;
	}
}
