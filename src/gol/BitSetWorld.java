package gol;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class BitSetWorld implements World {
	public static class Row {
		private BitSet alive = new BitSet();
		private int minX = Integer.MAX_VALUE;

		public boolean isAlive(int x) {
			if (x < minX) {
				return false;
			}
			return alive.get(x - minX);
		}

		public void setALive(int x) {
			if (alive.isEmpty()) {
				minX = Math.floorDiv(x, 64) * 64;
			}
			if (x < minX) {
				long[] oldArray = alive.toLongArray();
				int newMinX = Math.floorDiv(x, 64) * 64;
				int numLongsToAdd = minX - newMinX;
				long[] newArray = new long[oldArray.length + numLongsToAdd];
				System.arraycopy(oldArray, 0, newArray, numLongsToAdd,
						oldArray.length);
				alive = BitSet.valueOf(newArray);
			}
			alive.set(x - minX);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Row other = (Row) obj;
			if (alive == null) {
				if (other.alive != null)
					return false;
			} else if (!alive.equals(other.alive))
				return false;
			if (minX != other.minX)
				return false;
			return true;
		}
	}
	private final Map<Integer, Row> world = new HashMap<>();

	private int minX = Integer.MAX_VALUE;
	private int maxX = Integer.MIN_VALUE;
	private int minY = Integer.MAX_VALUE;
	private int maxY = Integer.MIN_VALUE;

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
		Row row = world.get(y);
		if (row == null) {
			return false;
		}
		return row.isAlive(x);
	}

	@Override
	public void setAlive(int x, int y) {
		minX = Math.min(minX, x);
		maxX = Math.max(maxX, x);
		minY = Math.min(minY, y);
		maxY = Math.max(maxY, y);

		Row row = world.get(y);
		if (row == null) {
			row = new Row();
			world.put(y, row);
		}
		row.setALive(x);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BitSetWorld other = (BitSetWorld) obj;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		return true;
	}
}
