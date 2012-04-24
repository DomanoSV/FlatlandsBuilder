package net.dkebnh.bukkit.FlatlandsBuilder;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class FLBPopulator extends BlockPopulator {
	
	private String genMode;
	private int height;
	private Material BlockFLB[] = new Material[3];
	private byte BlockFLBDV[] = new byte[3];

	
    protected FLBPopulator(int height, Material[] BlockFLB, byte[] BlockFLBDV, String genMode){
    	this.genMode = genMode;
        this.height = height;
        this.BlockFLB = BlockFLB;
        this.BlockFLBDV = BlockFLBDV;
    }
    
	public void populate(World world, Random random, Chunk chunk) {
		int blockx, blockz, blockx2, blockz2;
		Block block;
       
        if(genMode == "normal" || genMode == null){
        	for(blockx2 = 0; blockx2 < 16; ++blockx2){
        		for(blockz2 = 0; blockz2 < 16; ++blockz2){
        			block = chunk.getBlock(blockx2, height, blockz2);	
        			block.setType(BlockFLB[0]);
        			block.setData(BlockFLBDV[0]);
				}
        	}
        }else if(genMode == "grid"){
        	for(blockx2 = 0; blockx2 < 16; ++blockx2){
        		for(blockz2 = 0; blockz2 < 16; ++blockz2){
        			block = chunk.getBlock(blockx2, height, blockz2);	
        			block.setType(BlockFLB[1]);
        			block.setData(BlockFLBDV[1]);
				}
        	}
        	for (blockx = 1; blockx < 15; ++blockx){
        		for (blockz = 1; blockz < 15; ++blockz){
        			block = chunk.getBlock(blockx, height, blockz);
        			block.setType(BlockFLB[0]);
        			block.setData(BlockFLBDV[0]);
				}
        	}
        }else if(genMode == "grid2"){
			int chunkx = chunk.getX();
			int chunkz = chunk.getZ();
		
			if((chunkx + chunkz) % 2 == 0) {
		        for(blockx2 = 0; blockx2 < 16; ++blockx2){
		        	for(blockz2 = 0; blockz2 < 16; ++blockz2){
		        		block = chunk.getBlock(blockx2, height, blockz2);	
	        			block.setType(BlockFLB[1]);
	        			block.setData(BlockFLBDV[1]);
					}
		        }
        	}else{
		        for(blockx2 = 0; blockx2 < 16; ++blockx2){
		        	for(blockz2 = 0; blockz2 < 16; ++blockz2){
		        		block = chunk.getBlock(blockx2, height, blockz2);	
	        			block.setType(BlockFLB[2]);
	        			block.setData(BlockFLBDV[2]);
					}
		        }
        	} 
			
			for(blockx = 1; blockx < 15; ++blockx){
        		for(blockz = 1; blockz < 15; ++blockz){
        			block = chunk.getBlock(blockx, height, blockz);
        			block.setType(BlockFLB[0]);
        			block.setData(BlockFLBDV[0]);
				}
        	}
		}
    }
}
