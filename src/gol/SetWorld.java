package gol;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class SetWorld implements World {
	private final Set<Point> world;

	public SetWorld() {
		world = new TreeSet<>();
	}

	@Override
	public boolean isAlive(int x, int y) {
		return world.contains(new Point(x, y));
	}

	@Override
	public void setAlive(int x, int y) {
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