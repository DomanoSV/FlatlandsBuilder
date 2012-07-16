package net.dkebnh.bukkit.FlatlandsBuilder.populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class FLBPopulatorGridTwo extends BlockPopulator {
	private int height;
	private Material BlockFLB[] = new Material[3];
	private byte BlockFLBDV[] = new byte[3];
	private Material PathFLB[] = new Material[2];
	private byte PathFLBDV[] = new byte[2];
	private Material pathMaterial = Material.WOOL;
	private byte pathID = (byte) 0x0;
	private int plotSize;
	private boolean plotsEnabled;
	
    public FLBPopulatorGridTwo(int height, Material[] BlockFLB, byte[] BlockFLBDV, Material[] PathFLB, byte[] PathFLBDV, boolean plotsEnabled, int plotSize){
        this.height = height;
        this.BlockFLB = BlockFLB;
        this.BlockFLBDV = BlockFLBDV;
        this.PathFLB = PathFLB;
        this.PathFLBDV = PathFLBDV;
        this.plotSize = plotSize;
        this.plotsEnabled = plotsEnabled;
    }
    
	public boolean isPathBlock(int x, int z){
		
		for (int i = 0; i <= 8; i++){
			if (((x + i) % this.plotSize == 0) || ((z + i) % this.plotSize == 0)){
				if (i == 8){
					pathMaterial = PathFLB[1];
					pathID = PathFLBDV[1];
					return true;
				}else{
					pathMaterial = PathFLB[0];
					pathID = PathFLBDV[0];
					return true;
				}

			}else if (((x - (i-1)) % this.plotSize == 0) || ((z - (i-1)) % this.plotSize == 0)){
				if (i == 8){
					pathMaterial = PathFLB[1];
					pathID = PathFLBDV[1];
					return true;
				}else{
					pathMaterial = PathFLB[0];
					pathID = PathFLBDV[0];
					return true;
				}
			}else{
			
			}
			
		}
		return false;
	}
	
	public void populate(World world, Random random, Chunk chunk) {
		int blockx, blockz;
    	int chunkx = chunk.getX();
    	int chunkz = chunk.getZ();
		Block block;
		
		if(plotsEnabled == true){
			for(blockx = 0; blockx < 16; ++blockx){
        		for (blockz = 0; blockz < 16; ++blockz){
    				if (this.isPathBlock((chunkx * 16) + blockx, (chunkz * 16) + blockz)){
    					block = chunk.getBlock(blockx, height, blockz);
    					block.setType(pathMaterial);
    					block.setData(pathID);
    				}else{
    					if (blockx == 7 || blockx == 8 || blockz == 7 || blockz == 8){
    						block = chunk.getBlock(blockx, height, blockz);
							if (blockx > 7 && blockz > 7 || blockx < 8 && blockz < 8){
								if((chunkx + chunkz) % 2 == 0) {
    								block.setType(BlockFLB[1]);
        							block.setData(BlockFLBDV[1]);
								}else{
									block.setType(BlockFLB[2]);
        							block.setData(BlockFLBDV[2]);
								}
							}else{
								if((chunkx + chunkz) % 2 == 0) {
    								block.setType(BlockFLB[2]);
        							block.setData(BlockFLBDV[2]);
								}else{
									block.setType(BlockFLB[1]);
        							block.setData(BlockFLBDV[1]);
								}
							}
    					}else{
    						block = chunk.getBlock(blockx, height, blockz);
    						block.setType(BlockFLB[0]);
    						block.setData(BlockFLBDV[0]);
        			    }	
    				}
        		}		
        	}
		}else{
        	for(blockx = 0; blockx < 16; ++blockx){
        		for (blockz = 0; blockz < 16; ++blockz){
        			if (blockx == 0 || blockx == 15 || blockz == 0 || blockz == 15){
        				block = chunk.getBlock(blockx, height, blockz);
        				if((chunkx + chunkz) % 2 == 0) {
        			   		block.setType(BlockFLB[1]);
        		    		block.setData(BlockFLBDV[1]);
        		    	}else{
        		    		block.setType(BlockFLB[2]);
        		    		block.setData(BlockFLBDV[2]);
        		    	}
        			}else{
        				block = chunk.getBlock(blockx, height, blockz);
    					block.setType(BlockFLB[0]);
    					block.setData(BlockFLBDV[0]);
        			}
        		}		
        	}
		}
		
	}
}
