package gol;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Arguments {
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

	public void setFilename(String filename) {
		this.filename = Optional.of(filename);
	}

	public int getHeight() {
		return height.orElse(15);
	}

	public int getHeightOrElse(Supplier<? extends Integer> other) {
		return height.orElseGet(other);
	}

	public void setHeight(int height) {
		this.height = Optional.of(height);
	}

	public int getWidth() {
		return width.orElse(20);
	}

	public int getWidthOrElse(Supplier<? extends Integer> other) {
		return width.orElseGet(other);
	}

	public void setWidth(int width) {
		this.width = Optional.of(width);
	}

	public int getSteps() {
		return steps.orElse(100);
	}

	public void setSteps(int steps) {
		this.steps = Optional.of(steps);
	}

	public int getStepDelay() {
		return stepDelay.orElse(-1);
	}

	public void setStepDelay(int stepDelay) {
		this.stepDelay = Optional.of(stepDelay);
	}

	public int getHistoryLength() {
		return historyLength.orElse(0);
	}

	public void setHistoryLength(int historyLength) {
		this.historyLength = Optional.of(historyLength);
	}

	public OutputFormat getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(OutputFormat outputFormat) {
		this.outputFormat = outputFormat;
	}

	public boolean isQuietMode() {
		return quietMode;
	}

	public void setQuietMode(boolean quietMode) {
		this.quietMode = quietMode;
	}
}
