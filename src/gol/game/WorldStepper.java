package gol.game;

import gol.game.world.World;

public interface WorldStepper {

	World step(World oldWorld);
}