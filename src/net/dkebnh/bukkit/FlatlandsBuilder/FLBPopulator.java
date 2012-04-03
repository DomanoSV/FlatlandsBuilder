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
	private byte BlockADV;
	private byte BlockBDV;
	
    protected FLBPopulator(int height, Material BlockA, Material BlockB, byte BlockADV, byte BlockBDV, String genMode){
    	this.genMode = genMode;
        this.height = height;
        this.BlockA = BlockA;
        this.BlockB = BlockB;
        this.BlockADV = BlockADV;
        this.BlockBDV = BlockBDV;
    }
    
	public void populate(World world, Random random, Chunk chunk) {
		int blockx, blockz, blockx2, blockz2;
		Block block;
       
        for (blockx2 = 0; blockx2 < 16; ++blockx2){
        	for (blockz2 = 0; blockz2 < 16; ++blockz2){
        		block = chunk.getBlock(blockx2, height, blockz2);	
        		block.setType(BlockA);
        		block.setData(BlockADV);
			}
        }
        
        if (genMode == "grid"){
            
        	for (blockx = 1; blockx < 15; ++blockx){
        		for (blockz = 1; blockz < 15; ++blockz){
        			block = chunk.getBlock(blockx, height, blockz);
						
        			block.setType(BlockB);
        			block.setData(BlockBDV);
				}
        	}
        }
    }
}
