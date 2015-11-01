package gol.util;

import java.util.function.Consumer;

public class Text {
	public String text = null;

	public Text ln(String s) {
		text = text == null ? "" : text;
		text += s + System.lineSeparator();
		return this;
	}

	public Text ln() {
		return ln("");
	}

	public void add(Consumer<Text> c) {
		c.accept(this);
	}
}