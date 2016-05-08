package gol.game;

import gol.World;

public interface WorldStepper {

	World step(World oldWorld);
}