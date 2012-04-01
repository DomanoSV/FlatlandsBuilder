package net.dkebnh.bukkit.FlatlandsBuilder;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class FLBPopulator extends BlockPopulator {

	public void populate(World world, Random random, Chunk chunk) {
		int blockx, blockz, blockx2, blockz2;
		Block block;
		
		for (blockx2 = 0; blockx2 < 16; ++blockx2){
			for (blockz2 = 0; blockz2 < 16; ++blockz2){
				block = chunk.getBlock(blockx2, 15, blockz2);
						
				block.setType(Material.WOOL);
				block.setData((byte) 0x7);
				}
		}
		
		for (blockx = 1; blockx < 15; ++blockx){
			for (blockz = 1; blockz < 15; ++blockz){
				block = chunk.getBlock(blockx, 15, blockz);
						
				block.setType(Material.WOOL);
				block.setData((byte) 0xf);
				}
		}
	}

}
