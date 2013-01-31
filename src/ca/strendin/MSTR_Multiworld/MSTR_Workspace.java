package ca.strendin.MSTR_MultiWorld;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;

public class MSTR_Workspace {	
	private WorldCreator worldCreator;
	private boolean hasSet_Environment = false;
	private boolean hasSet_WorldType = false;
	private boolean hasSet_Generator = false;	
	private boolean hasSet_Seed = false;
	private long generatedSeed; 
	private Difficulty difficulty = Difficulty.NORMAL;
	private boolean enableAnimals = true;
	private boolean enableMobs = true;
	private boolean enablePVP = true;
	
	
	public WorldCreator getWorldCreator() {
		return worldCreator;
	}
		
	/* Constructor */
	public MSTR_Workspace(String name) {
		worldCreator = new WorldCreator(name);
		generatedSeed = worldCreator.seed();
	}
	
	public void setEnvironment(Environment env) {
		worldCreator.environment(env);
		hasSet_Environment = true;
	}
	
	public Environment getEnvironment() {		
		return worldCreator.environment();
		
	}
	
	public boolean hasSetEnvironment() {
		return hasSet_Environment;
	}
	
	public boolean hasSetWorldType() {
		return hasSet_WorldType;
	}
	
	public boolean hasSetGenerator() {
		return hasSet_Generator;
	}
	
	public boolean hasSetSeed() {
		return hasSet_Seed;
	}
	
	public String getName() {
		return worldCreator.name();
	}
	
	public ChunkGenerator getGenerator() {
		return worldCreator.generator();
	}
	
	public WorldType getWorldType() {
		return worldCreator.type();
	}
	
	public long getSeed() {
		return worldCreator.seed();
	}
	
	public void setGenerator(ChunkGenerator thisGen) {
		hasSet_Generator = true;
		
		worldCreator.generator(thisGen);
	
		if (thisGen == null) {			
			hasSet_WorldType = false;
		} else {
			/* Only the default generator will need the world type, so far, so don't bother asking the player for one */
			hasSet_WorldType = true;
		}		
	}
	
	public void setWorldType(WorldType thisType) {
		hasSet_WorldType = true;
		worldCreator.type(thisType);
	}
	
	public void setSeed(long newSeed) {
		hasSet_Seed = true;
		if (newSeed == 0) {
			worldCreator.seed(generatedSeed);
		} else {
			worldCreator.seed(newSeed);
		}
		
	}
	
	public void setDifficulty(Difficulty newDiff) {
		difficulty = newDiff;
	}
	
	public void setSpawnAnimals(boolean spawnAnimals) {
		enableAnimals = spawnAnimals;
	}
	
	public void setSpawnMobs(boolean spawnMobs) {
		enableMobs = spawnMobs;
	}
	
	public void setPVP(boolean doPVP) {
		enablePVP = doPVP;
	}
	
	public World createWorld() {
		World newWorld = worldCreator.createWorld();
		newWorld.setDifficulty(difficulty);
		newWorld.setPVP(enablePVP);
		
		if (!enableAnimals) {
			newWorld.setAnimalSpawnLimit(0);
			newWorld.setAmbientSpawnLimit(0);
		}
		
		if (!enableMobs) {
			newWorld.setMonsterSpawnLimit(0);			
		}
				
		// Add the other attributes here in the future
		
		return newWorld;
		
	}
	

}
