package oldSource;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class FLBRoadsPopulator extends BlockPopulator {
	private String genMode;
	private int height;
	private Material BlockFLB[] = new Material[3];
	private byte BlockFLBDV[] = new byte[3];
	private Material pathMaterial = Material.WOOL;
	private byte pathID = (byte) 0x0;
	private int plotSize;
	private boolean plotEnabled;
	
    public FLBRoadsPopulator(int height, Material[] BlockFLB, byte[] BlockFLBDV, String genMode, boolean plotEnabled, int plotSize){
    	this.genMode = genMode;
        this.height = height;
        this.BlockFLB = BlockFLB;
        this.BlockFLBDV = BlockFLBDV;
        this.plotSize = plotSize;
        this.plotEnabled = plotEnabled;
    }
    
public boolean isPathBlock(int x, int z){
		
		for (int i = 0; i <= 8; i++){
			if (((x + i) % this.plotSize == 0) || ((z + i) % this.plotSize == 0)){
				if (i == 8){
					pathMaterial = Material.WOOL;
					pathID = (byte) 0x3;
					return true;
				}else{
					pathMaterial = Material.WOOL;
					pathID = (byte) 0x0;
					return true;
				}

			}else if (((x - (i-1)) % this.plotSize == 0) || ((z - (i-1)) % this.plotSize == 0)){
				if (i == 8){
					pathMaterial = Material.WOOL;
					pathID = (byte) 0x3;
					return true;
				}else{
					pathMaterial = Material.WOOL;
					pathID = (byte) 0x0;
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
		
        if(genMode == "normal" || genMode == null){
        	for(blockx = 0; blockx < 16; ++blockx){
        		for(blockz = 0; blockz < 16; ++blockz){	
        				if (this.plotEnabled && this.isPathBlock((chunkx * 16) + blockx, (chunkz * 16) + blockz)){
        					block = chunk.getBlock(blockx, height, blockz);
        					block.setType(pathMaterial);
        					block.setData(pathID);
        				}else{
        					block = chunk.getBlock(blockx, height, blockz);	
        					block.setType(BlockFLB[0]);
        					block.setData(BlockFLBDV[0]);
        				}
				}
        	}
        }else if(genMode == "grid"){
        	for(blockx = 0; blockx < 16; ++blockx){
        		for (blockz = 0; blockz < 16; ++blockz){
    				if (this.plotEnabled && this.isPathBlock((chunkx * 16) + blockx, (chunkz * 16) + blockz)){
    					block = chunk.getBlock(blockx, height, blockz);
    					block.setType(pathMaterial);
    					block.setData(pathID);
    				}else{
    					if (blockx == 7 || blockx == 8 || blockz == 7 || blockz == 8){
    						block = chunk.getBlock(blockx, height, blockz);
    						block.setType(BlockFLB[1]);
        			   		block.setData(BlockFLBDV[1]);
    					}else{
        					block = chunk.getBlock(blockx, height, blockz);
        					block.setType(BlockFLB[0]);
        					block.setData(BlockFLBDV[0]);
    					}
    				}
        		}		
        	}
        }else if(genMode == "grid2"){
        	for(blockx = 0; blockx < 16; ++blockx){
        		for (blockz = 0; blockz < 16; ++blockz){
    				if (this.plotEnabled && this.isPathBlock((chunkx * 16) + blockx, (chunkz * 16) + blockz)){
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
		}else if(genMode == "grid3"){
	    	for(blockx = 0; blockx < 16; ++blockx){
	    		for (blockz = 0; blockz < 16; ++blockz){
    				if (this.plotEnabled && this.isPathBlock((chunkx * 16) + blockx, (chunkz * 16) + blockz)){
    					block = chunk.getBlock(blockx, height, blockz);
    					block.setType(pathMaterial);
    					block.setData(pathID);
    				}else{
	    				block = chunk.getBlock(blockx, height, blockz);
						if (blockx > 7 && blockz > 7 || blockx < 8 && blockz < 8){
							if((chunkx + chunkz) % 2 == 0) {
								block.setType(BlockFLB[0]);
    							block.setData(BlockFLBDV[0]);
							}else{
								block.setType(BlockFLB[1]);
    							block.setData(BlockFLBDV[1]);
							}
						}else{
							if((chunkx + chunkz) % 2 == 0) {
								block.setType(BlockFLB[1]);
    							block.setData(BlockFLBDV[1]);
							}else{
								block.setType(BlockFLB[0]);
    							block.setData(BlockFLBDV[0]);
							}
						}
    				}
	    		}		
	    	}
		}else if(genMode == "grid4"){
			for(blockx = 0; blockx < 16; ++blockx){
				for(blockz = 0; blockz < 16; ++blockz){
					if (this.plotEnabled && this.isPathBlock((chunkx * 16) + blockx, (chunkz * 16) + blockz)){
						block = chunk.getBlock(blockx, height, blockz);
    					block.setType(pathMaterial);
    					block.setData(pathID);
					}else{
						if (blockx > 7 && blockz > 7 || blockx < 8 && blockz < 8){
						if((chunkx + chunkz) % 2 == 0) {
							block = chunk.getBlock(blockx, height, blockz);	
		        			block.setType(BlockFLB[1]);
		        			block.setData(BlockFLBDV[1]);
						}else{
							if (blockx == 7 || blockx == 8 || blockz == 7 || blockz == 8){
								block = chunk.getBlock(blockx, height, blockz);
								block.setType(BlockFLB[2]);
								block.setData(BlockFLBDV[2]);
							}else{
								block = chunk.getBlock(blockx, height, blockz);
								block.setType(BlockFLB[0]);
								block.setData(BlockFLBDV[0]);
							}
						}	
						}else{
							if((chunkx + chunkz) % 2 == 0) {
								if (blockx == 7 || blockx == 8 || blockz == 7 || blockz == 8){
									block = chunk.getBlock(blockx, height, blockz);
									block.setType(BlockFLB[2]);
									block.setData(BlockFLBDV[2]);
								}else{
									block = chunk.getBlock(blockx, height, blockz);
									block.setType(BlockFLB[0]);
									block.setData(BlockFLBDV[0]);
								}
							}else{
								block = chunk.getBlock(blockx, height, blockz);	
			        			block.setType(BlockFLB[1]);
			        			block.setData(BlockFLBDV[1]);
							}	
						}
					}
				}
			}
		}else if(genMode == "grid5"){
        	for(blockx = 0; blockx < 16; ++blockx){
        		for (blockz = 0; blockz < 16; ++blockz){
    				if (this.plotEnabled && this.isPathBlock((chunkx * 16) + blockx, (chunkz * 16) + blockz)){
    					block = chunk.getBlock(blockx, height, blockz);
    					block.setType(pathMaterial);
    					block.setData(pathID);
    				}else{
    					if (blockx == 7 || blockx == 8 || blockz == 7 || blockz == 8){
    						block = chunk.getBlock(blockx, height, blockz);
    			   			block.setType(BlockFLB[1]);
    			   			block.setData(BlockFLBDV[1]);
    					}else{
    						block = chunk.getBlock(blockx, height, blockz);
    							if (blockx > 8 && blockz > 8 || blockx < 7 && blockz < 7){
    								if((chunkx + chunkz) % 2 == 0) {
	    								block.setType(BlockFLB[0]);
	        							block.setData(BlockFLBDV[0]);
    								}else{
    									block.setType(BlockFLB[2]);
            							block.setData(BlockFLBDV[2]);
    								}
    							}else{
    								if((chunkx + chunkz) % 2 == 0) {
	    								block.setType(BlockFLB[2]);
	        							block.setData(BlockFLBDV[2]);
    								}else{
    									block.setType(BlockFLB[0]);
            							block.setData(BlockFLBDV[0]);
    								}
    							}
    						
    					}
    				}
        		}		
        	}
		}
    }
}

