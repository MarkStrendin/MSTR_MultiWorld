package ca.strendin.MSTR_MultiWorld;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class WorldGenerator_WaterWorld extends ChunkGenerator {
	
	public byte[][] generateBlockSections(World world, Random random, int chunk_x, int chunk_z, BiomeGrid biomes) {
		byte[][]result = new byte[world.getMaxHeight() / 16][];
		
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				setBlock(result, x, 0, z, (byte)Material.BEDROCK.getId());
				setBlock(result, x, 1, z, (byte)Material.SAND.getId());
				setBlock(result, x, 2, z, (byte)Material.SAND.getId());
				setBlock(result, x, 3, z, (byte)Material.SAND.getId());
				
				for (int y = 4; y < 128; y++ ) {
					setBlock(result, x, y, z, (byte)Material.WATER.getId());					
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
