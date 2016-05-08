package gol;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import gol.game.Point;

public class BitSetWorld implements World {
	public static class Row {
		private BitSet alive = new BitSet();
		private int minX = Integer.MAX_VALUE;

		public boolean isAlive(final int x) {
			if (x < minX) {
				return false;
			}
			return alive.get(x - minX);
		}

		public void setAlive(final int x) {
			if (alive.isEmpty()) {
				minX = Math.floorDiv(x, 64) * 64;
			} else if (x < minX) {
				final long[] oldArray = alive.toLongArray();
				final int newMinX = Math.floorDiv(x, 64) * 64;
				final int numLongsToAdd = (minX - newMinX) / 64;
				final long[] newArray = new long[oldArray.length + numLongsToAdd];
				System.arraycopy(oldArray, 0, newArray, numLongsToAdd,
						oldArray.length);
				alive = BitSet.valueOf(newArray);
				minX = newMinX;
			}
			alive.set(x - minX);
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Row other = (Row) obj;
			if (alive == null) {
				if (other.alive != null)
					return false;
			} else if (!alive.equals(other.alive))
				return false;
			if (minX != other.minX)
				return false;
			return true;
		}

		public Stream<Point> stream(final int y) {
			return alive.stream().mapToObj(x -> new Point(x + minX, y));
		}
	}

	private final Map<Integer, Row> world = new HashMap<>();

	@Override
	public boolean isAlive(final int x, final int y) {
		final Row row = world.get(y);
		if (row == null) {
			return false;
		}
		return row.isAlive(x);
	}

	@Override
	public void setAlive(final int x, final int y) {
		Row row = world.get(y);
		if (row == null) {
			row = new Row();
			world.put(y, row);
		}
		row.setAlive(x);
	}

	@Override
	public Iterator<Point> iterator() {
		Stream<Point> stream = Stream.empty();
		for (final Entry<Integer, Row> entry : world.entrySet()) {
			final Stream<Point> rowStream = entry.getValue().stream(entry.getKey());
			stream = Stream.concat(stream, rowStream);
		}
		return stream.iterator();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BitSetWorld other = (BitSetWorld) obj;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		return true;
	}
}
