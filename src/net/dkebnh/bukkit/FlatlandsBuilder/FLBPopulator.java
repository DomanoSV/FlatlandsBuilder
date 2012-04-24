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
	private Material BlockA;
	private Material BlockB;
	private Material BlockC;
	private byte BlockADV;
	private byte BlockBDV;
	private byte BlockCDV;

	
    protected FLBPopulator(int height, Material BlockA, Material BlockB, Material BlockC, byte BlockADV, byte BlockBDV, byte BlockCDV, String genMode){
    	this.genMode = genMode;
        this.height = height;
        this.BlockA = BlockA;
        this.BlockB = BlockB;
        this.BlockC = BlockC;
        this.BlockADV = BlockADV;
        this.BlockBDV = BlockBDV;
        this.BlockCDV = BlockCDV;

    }
    
	public void populate(World world, Random random, Chunk chunk) {
		int blockx, blockz, blockx2, blockz2;
		Block block;
       
        if(genMode == "normal" || genMode == null){
        	for(blockx2 = 0; blockx2 < 16; ++blockx2){
        		for(blockz2 = 0; blockz2 < 16; ++blockz2){
        			block = chunk.getBlock(blockx2, height, blockz2);	
        			block.setType(BlockA);
        			block.setData(BlockADV);
				}
        	}
        }else if(genMode == "grid"){
        	for(blockx2 = 0; blockx2 < 16; ++blockx2){
        		for(blockz2 = 0; blockz2 < 16; ++blockz2){
        			block = chunk.getBlock(blockx2, height, blockz2);	
        			block.setType(BlockA);
        			block.setData(BlockADV);
				}
        	}
        	for (blockx = 1; blockx < 15; ++blockx){
        		for (blockz = 1; blockz < 15; ++blockz){
        			block = chunk.getBlock(blockx, height, blockz);
						
        			block.setType(BlockB);
        			block.setData(BlockBDV);
				}
        	}
        }else if(genMode == "grid2"){
			int chunkx = chunk.getX();
			int chunkz = chunk.getZ();
		
			if((chunkx + chunkz) % 2 == 0) {
		        for(blockx2 = 0; blockx2 < 16; ++blockx2){
		        	for(blockz2 = 0; blockz2 < 16; ++blockz2){
		        		block = chunk.getBlock(blockx2, height, blockz2);	
		        		block.setType(BlockA);
		        		block.setData(BlockADV);
					}
		        }
        	}else{
		        for(blockx2 = 0; blockx2 < 16; ++blockx2){
		        	for(blockz2 = 0; blockz2 < 16; ++blockz2){
		        		block = chunk.getBlock(blockx2, height, blockz2);	
		        		block.setType(BlockC);
		        		block.setData(BlockCDV);
					}
		        }
        	} 
			
			for(blockx = 1; blockx < 15; ++blockx){
        		for(blockz = 1; blockz < 15; ++blockz){
        			block = chunk.getBlock(blockx, height, blockz);
        			block.setType(BlockB);
        			block.setData(BlockBDV);
				}
        	}
		}
    }
}
