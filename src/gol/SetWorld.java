package gol;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SetWorld implements World {
	private final Set<Point> world;

	private int minX = Integer.MAX_VALUE;
	private int maxX = Integer.MIN_VALUE;
	private int minY = Integer.MAX_VALUE;
	private int maxY = Integer.MIN_VALUE;

	public SetWorld() {
		world = new HashSet<>();
	}

	public SetWorld(int sizeEstimate) {
		world = new HashSet<>(sizeEstimate);
	}

	@Override
	public int heightOffset() {
		if (world.isEmpty()) {
			return 0;
		}

		return minY;
	}

	@Override
	public int widthOffset() {
		if (world.isEmpty()) {
			return 0;
		}

		return minX;
	}

	@Override
	public int height() {
		if (world.isEmpty()) {
			return 0;
		}

		return maxY - minY + 1;
	}

	@Override
	public int width() {
		if (world.isEmpty()) {
			return 0;
		}

		return maxX - minX + 1;
	}

	@Override
	public boolean isAlive(int x, int y) {
		return world.contains(new Point(x, y));
	}

	@Override
	public void setAlive(int x, int y) {
		minX = Math.min(minX, x);
		maxX = Math.max(maxX, x);
		minY = Math.min(minY, y);
		maxY = Math.max(maxY, y);

		world.add(new Point(x, y));
	}

	@Override
	public boolean equals(Object obj) {
		SetWorld other = (SetWorld) obj;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		return true;
	}

	@Override
	public Iterator<Point> iterator() {
		return world.iterator();
	}

	@Override
	public int numAlive() {
		return world.size();
	}
}