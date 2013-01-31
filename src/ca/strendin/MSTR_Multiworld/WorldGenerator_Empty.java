package ca.strendin.MSTR_MultiWorld;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class WorldGenerator_Empty extends ChunkGenerator {
	
	public byte[][] generateBlockSections(World world, Random random, int chunk_x, int chunk_z, BiomeGrid biomes) {
		byte[][]result = new byte[world.getMaxHeight() / 16][];
		
		if (chunk_x == 0) {
			if (chunk_z == 0) {
				for (int x = 0; x < 4; x++) {
					for (int z = 0; z < 4; z++) {
						setBlock(result, x, 128, z, (byte)Material.GLASS.getId());
					}					
				}				
			}
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
