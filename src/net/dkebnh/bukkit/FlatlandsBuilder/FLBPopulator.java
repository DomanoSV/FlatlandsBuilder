package net.dkebnh.bukkit.FlatlandsBuilder;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class FLBPopulator extends BlockPopulator {

	public void populate(World world, Random random, Chunk chunk) {
		int blockx, blockz;
		
		for (blockx = 1; blockx < 15; ++blockx){
			for (blockz = 1; blockz < 15; ++blockz){
				chunk.getBlock(blockx, 15, blockz).setType(Material.COBBLESTONE);
				}
		}
	}

}
