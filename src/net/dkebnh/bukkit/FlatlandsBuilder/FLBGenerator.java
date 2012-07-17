package net.dkebnh.bukkit.FlatlandsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import net.dkebnh.bukkit.FlatlandsBuilder.populators.FLBPopulatorGridFive;
import net.dkebnh.bukkit.FlatlandsBuilder.populators.FLBPopulatorGridFour;
import net.dkebnh.bukkit.FlatlandsBuilder.populators.FLBPopulatorGridOne;
import net.dkebnh.bukkit.FlatlandsBuilder.populators.FLBPopulatorGridThree;
import net.dkebnh.bukkit.FlatlandsBuilder.populators.FLBPopulatorGridTwo;
import net.dkebnh.bukkit.FlatlandsBuilder.populators.FLBPopulatorNormal;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World; 
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class FLBGenerator extends ChunkGenerator{
	
	private Logger log = Logger.getLogger("Minecraft");
	private int height = 0;
	private String genMode;
	private Material[] BlockFLB = new Material[3];
	private byte[] BlockFLBDV = new byte[3];
	private Material[] PathFLB = new Material[2];
	private byte[] PathFLBDV = new byte[2];
	public boolean plotsEnabled = true;
	private int plotSize = 128 + 16;
	
	private List<String> genModechk = Arrays.asList("normal","grid","grid2","grid3","grid4","grid5");
	private List<Integer> plotSizechk = Arrays.asList(64,128,256,512,1024);
	
	private FlatlandsBuilder plugin;
	
	public Material getBlockMaterial(String BlockID){	        
	    Material mat = Material.matchMaterial(BlockID);
	        
	    if (mat == null){
	    	try{
	    		mat = Material.getMaterial(Integer.parseInt(BlockID));
	        }catch (Exception e){
	        
	        }
	    	
	    	if (mat == null){
	        	plugin.log.warningMSG("[FlatlandsBuilder] Invalid Block ID '" + BlockID + "'. Defaulting to WHITE_WOOL.");
	        	mat = Material.WOOL;
	        }
	    }
	        
	    if (!mat.isBlock()){
	    	plugin.log.warningMSG("[FlatlandsBuilder] Error, Block'" + BlockID + "' is not a block. Defaulting to WHITE_WOOL.");
	    	mat = Material.WOOL;
	    }

	    return mat;
	}
	
	public byte getBlockDataValue(String BlockDataValue){	        
		byte dataValue;
		
    	try{
    		dataValue = Byte.parseByte(BlockDataValue);
        }catch (Exception e){
        	plugin.log.warningMSG("[FlatlandsBuilder] Invalid Block Data Value '" + BlockDataValue + "'. Defaulting to 0.");
            dataValue = (byte) 0x0;
        }
    	
    	return dataValue;
	}
	
	public int getPlotSize(){
		return this.plotSize - 16;
	}
	
	public FLBGenerator(FlatlandsBuilder plugin, String worldName){
		this.plugin = plugin;
		
		String block1, block2, block3, pathblock, wallblock, mode;
		int plotsize, height;
		boolean plots;
			
			try{	// Trys to get the height value for the generator.
				if(plugin.conf.contains("worlds." + worldName + ".height")){
					height = plugin.conf.getInt("worlds." + worldName + ".height");
				}else if(plugin.conf.contains("global.defaults.height")){
					height = plugin.conf.getInt("global.defaults.height");
				}else{
					height = 64;
				}
				
				if (height <= 0 || height >= 128){		// May change max height later on, making it generate any higher seems impractical at this stage.
					log.warning("[FlatlandsBuilder] Invalid height '" +  "'. Using 64 instead.");
					this.height = 64;
				}else{
					this.height = height;
				}
			}catch (Exception e){
				e.printStackTrace();
				this.height = 64;	
			}
			
			try{	// Trys to get the generation mode value for the generator.
				if(plugin.conf.contains("worlds." + worldName + ".mode")){
					mode = plugin.conf.getString("worlds." + worldName + ".mode");
				}else if(plugin.conf.contains("global.defaults.mode")){
					mode = plugin.conf.getString("global.defaults.mode");
				}else{
					mode = "normal";
				}
				
        		if (genModechk.contains(mode)){
        			this.genMode = mode;
        		}else{
        			this.genMode = "normal";
        		}
			}catch (Exception e){
				e.printStackTrace();
				this.genMode = "normal";
			}
			
			try{	// Trys to get all grid block values for the generator.
				if(plugin.conf.contains("worlds." + worldName + ".block1")){
					block1 = plugin.conf.getString("worlds." + worldName + ".block1");
				}else if(plugin.conf.contains("global.defaults.block1")){
					block1 = plugin.conf.getString("global.defaults.block1");
				}else{
					block1 = "wool:15";
				}
				
				String block1Tokens[] = block1.split("[:]", 2);
				
				BlockFLB[0] = this.getBlockMaterial(block1Tokens[0]);
				if (block1Tokens.length == 2) BlockFLBDV[0] = this.getBlockDataValue(block1Tokens[1]);
			
				if(genMode != "normal"){
					if(plugin.conf.contains("worlds." + worldName + ".block2")){
						block2 = plugin.conf.getString("worlds." + worldName + ".block2");
					}else if(plugin.conf.contains("global.defaults.block2")){
						block2 = plugin.conf.getString("global.defaults.block2");
					}else{
						block2 = "wool:7";
					}
					
					String block2Tokens[] = block2.split("[:]", 2);
				
					BlockFLB[1] = this.getBlockMaterial(block2Tokens[0]);
					if (block2Tokens.length == 2) BlockFLBDV[1] = this.getBlockDataValue(block2Tokens[1]);
					
				
					if(genMode != "grid" || genMode != "grid3"){
						if(plugin.conf.contains("worlds." + worldName + ".block3")){
							block3 = plugin.conf.getString("worlds." + worldName + ".block3");
						}else if(plugin.conf.contains("global.defaults.block3")){
							block3 = plugin.conf.getString("global.defaults.block3");
						}else{
							block3 = "wool:8";
						}
						
						String block3Tokens[] = block3.split("[:]", 2);
					
						BlockFLB[2] = this.getBlockMaterial(block3Tokens[0]);
						if (block3Tokens.length == 2) BlockFLBDV[2] = this.getBlockDataValue(block3Tokens[1]);
					}
				}
			}catch (Exception e){
				e.printStackTrace();
				BlockFLB[0] = Material.WOOL;
				BlockFLB[1] = Material.WOOL;
		    	BlockFLB[2] = Material.WOOL;
		    	BlockFLBDV[0] = (byte) 0xf;
		    	BlockFLBDV[1] = (byte) 0x7;
		    	BlockFLBDV[2] = (byte) 0x8;
			}
			
			try{	// Trys to get the plotsEnabled value for the generator.
				if(plugin.conf.contains("worlds." + worldName + ".plots")){
					plots = plugin.conf.getBoolean("worlds." + worldName + ".plots");
				}else if(plugin.conf.contains("global.defaults.plots")){
					plots = plugin.conf.getBoolean("global.defaults.plots");
				}else{
					plots = false;
				}
				
				this.plotsEnabled = plots;
				
			}catch (Exception e){
				e.printStackTrace();
				this.plotsEnabled = false;
			}
			
			if(plotsEnabled == true){
				try{	// Trys to get the plotsize value for the generator.
					if(plugin.conf.contains("worlds." + worldName + ".plotsize")){
						plotsize = plugin.conf.getInt("worlds." + worldName + ".plotsize");
					}else if(plugin.conf.contains("global.defaults.plotsize")){
						plotsize = plugin.conf.getInt("global.defaults.plotsize");
					}else{
						plotsize = 128;
					}
					
	        		if (plotSizechk.contains(plotsize)){
	        			this.plotSize = plotsize + 16;
	        		}else{
	        			this.plotSize = 128 + 16;
	        		}
				}catch (Exception e){
					e.printStackTrace();
					this.plotSize = 128 + 16;
				}
				
				try{	// Trys to get the path block values for the generator.
					if(plugin.conf.contains("worlds." + worldName + ".pathblock")){
						pathblock = plugin.conf.getString("worlds." + worldName + ".pathblock");
					}else if(plugin.conf.contains("global.defaults.pathblock")){
						pathblock = plugin.conf.getString("global.defaults.pathblock");
					}else{
						pathblock = "wool";
					}
			
					if(plugin.conf.contains("worlds." + worldName + ".wallblock")){
						wallblock = plugin.conf.getString("worlds." + worldName + ".wallblock");
					}else if(plugin.conf.contains("global.defaults.wallblock")){
						wallblock = plugin.conf.getString("global.defaults.wallblock");
					}else{
						wallblock = "wool:3";
					}
				
					String pathblockTokens[] = pathblock.split("[:]", 2),	wallblockTokens[] = wallblock.split("[:]", 2);
				
					PathFLB[0] = this.getBlockMaterial(pathblockTokens[0]);
					if (pathblockTokens.length == 2) PathFLBDV[0] = this.getBlockDataValue(pathblockTokens[1]);;
				
					PathFLB[1] = this.getBlockMaterial(wallblockTokens[0]);
					if (wallblockTokens.length == 2) PathFLBDV[1] = this.getBlockDataValue(wallblockTokens[1]);
				}catch (Exception e){
					e.printStackTrace();
					PathFLB[0] = Material.WOOL;
					PathFLB[1] = Material.WOOL;
					PathFLBDV[0] = (byte) 0x0;
					PathFLBDV[1] = (byte) 0x3;
			}
		}
	}
	
	public List<BlockPopulator> getDefaultPopulators(World world){
		ArrayList<BlockPopulator> populators = new ArrayList<BlockPopulator>();
		
		 if(genMode.equalsIgnoreCase("normal") || genMode == null){
			 populators.add(new FLBPopulatorNormal(height, BlockFLB, BlockFLBDV, PathFLB, PathFLBDV, plotsEnabled, plotSize));
	     }else if(genMode.equalsIgnoreCase("grid")){
	    	 populators.add(new FLBPopulatorGridOne(height, BlockFLB, BlockFLBDV, PathFLB, PathFLBDV, plotsEnabled, plotSize));
	     }else if(genMode.equalsIgnoreCase("grid2")){
	    	 populators.add(new FLBPopulatorGridTwo(height, BlockFLB, BlockFLBDV, PathFLB, PathFLBDV, plotsEnabled, plotSize));
	     }else if(genMode.equalsIgnoreCase("grid3")){
	    	 populators.add(new FLBPopulatorGridThree(height, BlockFLB, BlockFLBDV, PathFLB, PathFLBDV, plotsEnabled, plotSize));
		 }else if(genMode.equalsIgnoreCase("grid4")){
			 populators.add(new FLBPopulatorGridFour(height, BlockFLB, BlockFLBDV, PathFLB, PathFLBDV, plotsEnabled, plotSize));
		 }else if(genMode.equalsIgnoreCase("grid5")){
			 populators.add(new FLBPopulatorGridFive(height, BlockFLB, BlockFLBDV, PathFLB, PathFLBDV, plotsEnabled, plotSize));
		 }
		
		return populators;
	}
	
	public Location getFixedSpawnLocation(World world, Random random){
		return new Location(world, 0, height + 1, 0);
	}
	
	private int coordsToInt(int x, int y, int z){
			return (x * 16 + z) * 256 + y;
	}
	
	public byte[] generate(World world, Random rand, int chunkx, int chunkz){
		byte[] blocks = new byte[65536];
		int x, y, z;
		
		for (x = 0; x < 16; ++x){
			for (z = 0; z < 16; ++z){
				blocks[this.coordsToInt(x, 0, z)] = (byte) Material.BEDROCK.getId();
				for (y = 1; y < height; ++y){
					blocks[this.coordsToInt(x, y, z)] = (byte) Material.STONE.getId();
				}
				blocks[this.coordsToInt(x, height, z)] = (byte) Material.WOOL.getId();
			}
		}
		return blocks;
	}
}
