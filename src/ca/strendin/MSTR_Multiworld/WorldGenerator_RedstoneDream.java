package ca.strendin.MSTR_MultiWorld;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class WorldGenerator_RedstoneDream extends ChunkGenerator {
	private boolean hasSetSpawn = false;
	
	public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
		byte[][]result = new byte[world.getMaxHeight() / 16][];
		
		for (x =0; x < 16; x++) {
			for (z = 0; z < 16; z++) {
				// Bedrock at y=0
				setBlock(result, x, 0, z, (byte)Material.BEDROCK.getId());
				
				// Stone for 3 levels
				setBlock(result, x, 1, z, (byte)Material.STONE.getId());
				setBlock(result, x, 2, z, (byte)Material.STONE.getId());
				setBlock(result, x, 3, z, (byte)Material.STONE.getId());
				
				// Sandstone for the rest
				for (int y = 4; y < 52; y++ ) {
					setBlock(result, x, y, z, (byte)Material.SANDSTONE.getId());					
				}
				
			}
		}
		
		if (hasSetSpawn == false) {
			world.setSpawnLocation(0, 0, 0);			
		}
		
		return result;		
	}
	
	void setBlock(byte[][] result, int x, int y, int z, byte blkid) {
		if (result[y >> 4] == null) //is this chunkpart already initialised?
		{
			result [y >> 4] = new byte[4096]; // Initialise the chunk part		
		}
		
		result[y >> 4][((y & 0xf) << 8) | (z << 4) | x] = blkid; // set the block
	}

}
