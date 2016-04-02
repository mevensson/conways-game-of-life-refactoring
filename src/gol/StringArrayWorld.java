package gol;

import java.util.ArrayList;
import java.util.List;

public class StringArrayWorld implements World {
	private List<String> world;
	private int heightOffset;
	private int widthOffset;

	public StringArrayWorld(List<String> world, int heightOffset, int widthOffset) {
		this.world = world;
		this.heightOffset = heightOffset;
		this.widthOffset = widthOffset;
	}

	public StringArrayWorld() {
		world = new ArrayList<String>();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StringArrayWorld other = (StringArrayWorld) obj;
		if (heightOffset != other.heightOffset)
			return false;
		if (widthOffset != other.widthOffset)
			return false;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		return true;
	}

	@Override
	public int heightOffset() {
		return heightOffset;
	}

	@Override
	public int widthOffset() {
		return widthOffset;
	}

	@Override
	public int height() {
		return world.size();
	}

	@Override
	public int width() {
		return world.isEmpty() ? 0 : world.get(0).length();
	}

	@Override
	public boolean isAlive(int x, int y) {
		if (y < heightOffset || y >= heightOffset + height()) {
			return false;
		}

		if (x < widthOffset || x >= widthOffset + width()) {
			return false;
		}

		return isAliveLocal(x - widthOffset, y - heightOffset);
	}

	private boolean isAliveLocal(int x, int y) {
		char c = world.get(y).charAt(x);
		return c == '#';
	}

	@Override
	public void setAlive(int x, int y) {
		while (y < heightOffset) {
			addTopEmptyLine();
		}

		while (y >= heightOffset + height()) {
			addBottomEmptyLine();
		}

		while (x < widthOffset) {
			addLeftEmptyColumn();
		}

		while (x >= widthOffset + width()) {
			addRightEmptyColumn();
		}

		setAliveLocal(x - widthOffset, y - heightOffset);
	}

	private void setAliveLocal(int x, int y) {
		String line = world.get(y);
		String newLine = "";
		if (x > 0) {
			newLine += line.substring(0, x);
		}
		newLine += '#';
		if (x + 1 < width()) {
			newLine += line.substring(x + 1, width());
		}
		world.set(y, newLine);
	}

	private void addLeftEmptyColumn() {
		for (int y = 0; y < height(); y++) {
			String line = world.get(y);
			world.set(y, '-' + line);
		}
		widthOffset--;
	}

	private void addRightEmptyColumn() {
		for (int i = 0; i < height(); i++) {
			String line = world.get(i);
			world.set(i, line + '-');
		}
	}

	private void addTopEmptyLine() {
		world.add(0, emptyLine());
		heightOffset--;
	}

	private void addBottomEmptyLine() {
		world.add(emptyLine());
	}

	private String emptyLine() {
		if (world.isEmpty())
			return "";
		String result = "";
		while (result.length() < world.get(0).length())
			result += '-';
		return result;
	}
}
