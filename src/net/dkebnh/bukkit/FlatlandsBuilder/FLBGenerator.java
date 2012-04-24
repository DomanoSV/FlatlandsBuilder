package net.dkebnh.bukkit.FlatlandsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World; 
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class FLBGenerator extends ChunkGenerator{
	
	private Logger log = Logger.getLogger("Minecraft");
	private String genMode;
	private int height;
	private Material BlockA;
	private Material BlockB;
	private Material BlockC;
	private byte BlockADV;
	private byte BlockBDV;
	private byte BlockCDV;
	
	public void setDefaults(String msg){
        height = 64;
        genMode = "grid2";
        BlockA = Material.WOOL;
		BlockB = Material.WOOL;
		BlockC = Material.WOOL;
		BlockADV = (byte) 0x7;
		BlockBDV = (byte) 0xf;
		BlockCDV = (byte) 0x8;
		this.log.warning(msg);
	}
	
	public FLBGenerator(){
		this("64,wool:15,wool:7,wool:8");
	}
	
	public FLBGenerator(String id){
		if (id != null){
			try{
				if (id.length() > 0){
					String tokens[] = id.split("[,]");
					
					if (tokens.length == 4){
						height = Integer.parseInt(tokens[0]);
							if (height <= 0 || height >= 255){ // Will change max height later on, once sure 256 is the maximum value and not lower.
								log.warning("[FlatlandsBuilder] Invalid height '" + tokens[0] + "'. Using 64 instead.");
								height = 64;
							} 
						genMode = "grid2";
						
				        String blockAmaterialTokens[] = tokens[2].split("[:]", 2);
				        String blockBmaterialTokens[] = tokens[1].split("[:]", 2);
				        String blockCmaterialTokens[] = tokens[3].split("[:]", 2);
				        
				        if (blockAmaterialTokens.length == 2){
				            try{
				            	BlockADV = Byte.parseByte(blockAmaterialTokens[1]);
				            }catch (Exception e){
				            			log.warning("[FlatlandsBuilder] Invalid Border Block Data Value '" + blockAmaterialTokens[1] + "'. Defaulting to 0.");
				            			BlockADV = (byte) 0x0;
				            }
				        }
				        
				        Material blockAmat = Material.matchMaterial(blockAmaterialTokens[0]);
				        
				        if (blockAmat == null){
				            try{
				            	blockAmat = Material.getMaterial(Integer.parseInt(blockAmaterialTokens[0]));
				            }catch (Exception e){

				            }
				            
				            if (blockAmat == null){
				            	log.warning("[FlatlandsBuilder] Invalid Border Block ID '" + blockAmaterialTokens[0] + "'. Defaulting to WHITE_WOOL.");
				            	blockAmat = Material.WOOL;
				            }
				        }
				        
				        if (!blockAmat.isBlock()){
				            log.warning("[FlatlandsBuilder] Error, Border Block'" + blockAmaterialTokens[0] + "' is not a block. Defaulting to WHITE_WOOL.");
				            blockAmat = Material.WOOL;
				        }
				        
				        if (blockBmaterialTokens.length == 2){
				            try{
				            	BlockBDV = Byte.parseByte(blockBmaterialTokens[1]);
				            }catch (Exception e){
				                log.warning("[FlatlandsBuilder] Invalid Fill Block Data Value '" + blockBmaterialTokens[1] + "'. Defaulting to 0.");
				                BlockBDV = (byte) 0x0;
				            }
				        }
				    	
				        Material blockBmat = Material.matchMaterial(blockBmaterialTokens[0]);
				        
				        if (blockBmat == null)
				        {
				            try
				            {
				            	blockBmat = Material.getMaterial(Integer.parseInt(blockBmaterialTokens[0]));
				            } catch (Exception e){

				            }

				            if (blockBmat == null)
				            {
				                log.warning("[FlatlandsBuilder] Invalid Fill Block ID '" + blockBmaterialTokens[0] + "'. Defaulting fill to WHITE_WOOL.");
				                blockBmat = Material.WOOL;
				            }
				        }
				        
				        if (!blockBmat.isBlock())
				        {
				            log.warning("[FlatlandsBuilder] Error, Fill Block '" + blockBmaterialTokens[0] + "' is not a block. Defaulting fill to WHITE_WOOL.");
				            blockBmat = Material.WOOL;
				        }
				        
				        if (blockCmaterialTokens.length == 2){
				            try{
				            	BlockCDV = Byte.parseByte(blockCmaterialTokens[1]);
				            }catch (Exception e){
				                log.warning("[FlatlandsBuilder] Invalid Fill Block Data Value '" + blockCmaterialTokens[1] + "'. Defaulting to 0.");
				                BlockCDV = (byte) 0x0;
				            }
				        }
				    	
				        Material blockCmat = Material.matchMaterial(blockCmaterialTokens[0]);
				        
				        if (blockCmat == null)
				        {
				            try
				            {
				            	blockCmat = Material.getMaterial(Integer.parseInt(blockCmaterialTokens[0]));
				            } catch (Exception e){

				            }

				            if (blockCmat == null)
				            {
				                log.warning("[FlatlandsBuilder] Invalid Fill Block ID '" + blockCmaterialTokens[0] + "'. Defaulting fill to WHITE_WOOL.");
				                blockCmat = Material.WOOL;
				            }
				        }
				        
				        if (!blockCmat.isBlock())
				        {
				            log.warning("[FlatlandsBuilder] Error, Fill Block '" + blockCmaterialTokens[0] + "' is not a block. Defaulting fill to WHITE_WOOL.");
				            blockCmat = Material.WOOL;
				        }

				        BlockA = blockAmat;
				        BlockB = blockBmat;
				        BlockC = blockCmat;
				        
					}else if (tokens.length == 3){
						height = Integer.parseInt(tokens[0]);
							if (height <= 0 || height >= 128){ // Will change max height later on, once sure 256 is the maximum value and not lower.
								log.warning("[FlatlandsBuilder] Invalid height '" + tokens[0] + "'. Using 64 instead.");
								height = 64;
							} 
						genMode = "grid";
						
				        String blockAmaterialTokens[] = tokens[2].split("[:]", 2);
				        String blockBmaterialTokens[] = tokens[1].split("[:]", 2);
				        
				        if (blockAmaterialTokens.length == 2){
				            try{
				            	BlockADV = Byte.parseByte(blockAmaterialTokens[1]);
				            }catch (Exception e){
				            			log.warning("[FlatlandsBuilder] Invalid Border Block Data Value '" + blockAmaterialTokens[1] + "'. Defaulting to 0.");
				            			BlockADV = (byte) 0x0;
				            }
				        }
				        
				        Material blockAmat = Material.matchMaterial(blockAmaterialTokens[0]);
				        
				        if (blockAmat == null){
				            try{
				            	blockAmat = Material.getMaterial(Integer.parseInt(blockAmaterialTokens[0]));
				            }catch (Exception e){

				            }
				            
				            if (blockAmat == null){
				            	log.warning("[FlatlandsBuilder] Invalid Border Block ID '" + blockAmaterialTokens[0] + "'. Defaulting to WHITE_WOOL.");
				            	blockAmat = Material.WOOL;
				            }
				        }
				        
				        if (!blockAmat.isBlock()){
				            log.warning("[FlatlandsBuilder] Error, Border Block'" + blockAmaterialTokens[0] + "' is not a block. Defaulting to WHITE_WOOL.");
				            blockAmat = Material.WOOL;
				        }
				        
				        if (blockBmaterialTokens.length == 2){
				            try{
				            	BlockBDV = Byte.parseByte(blockBmaterialTokens[1]);
				            }catch (Exception e){
				                log.warning("[FlatlandsBuilder] Invalid Fill Block Data Value '" + blockBmaterialTokens[1] + "'. Defaulting to 0.");
				                BlockBDV = (byte) 0x0;
				            }
				        }
				    	
				        Material blockBmat = Material.matchMaterial(blockBmaterialTokens[0]);
				        
				        if (blockBmat == null)
				        {
				            try
				            {
				            	blockBmat = Material.getMaterial(Integer.parseInt(blockBmaterialTokens[0]));
				            } catch (Exception e){

				            }

				            if (blockBmat == null)
				            {
				                log.warning("[FlatlandsBuilder] Invalid Fill Block ID '" + blockBmaterialTokens[0] + "'. Defaulting fill to WHITE_WOOL.");
				                blockBmat = Material.WOOL;
				            }
				        }
				        
				        if (!blockBmat.isBlock())
				        {
				            log.warning("[FlatlandsBuilder] Error, Fill Block '" + blockBmaterialTokens[0] + "' is not a block. Defaulting fill to WHITE_WOOL.");
				            blockBmat = Material.WOOL;
				        }

				        BlockA = blockAmat;
				        BlockB = blockBmat;
				        
					}else{
						if (tokens.length == 2){
							height = Integer.parseInt(tokens[0]);
								if (height <= 0 || height >= 128){ // Will change max height later on, once sure 256 is the maximum value and not lower.
									log.warning("[FlatlandsBuilder] Invalid height '" + tokens[0] + "'. Using 64 instead.");
									height = 64;
							} 
							genMode = "normal";
							
							String blockAmaterialTokens[] = tokens[1].split("[:]", 2);
					        
					        if (blockAmaterialTokens.length == 2){
					            try{
					            	BlockADV = Byte.parseByte(blockAmaterialTokens[1]);
					            }catch (Exception e){
					            	log.warning("[FlatlandsBuilder] Invalid Data Value '" + blockAmaterialTokens[1] + "'. Defaulting to 0.");
					            	BlockADV = (byte) 0x0;
					            }
					        }
					        
					        Material blockAmat = Material.matchMaterial(blockAmaterialTokens[0]);
					        
					        if (blockAmat == null){
					            try{
					            	blockAmat = Material.getMaterial(Integer.parseInt(blockAmaterialTokens[0]));
					            }catch (Exception e){

					            }

					            if (blockAmat == null){
					            	log.warning("[FlatlandsBuilder] Invalid Block ID '" + blockAmaterialTokens[0] + "'. Defaulting to WHITE_WOOL.");
					            	blockAmat = Material.WOOL;
					            }
					        }
					        
					        if (!blockAmat.isBlock()){
					            log.warning("[FlatlandsBuilder] Error, '" + blockAmaterialTokens[0] + "' is not a block. Defaulting to WHITE_WOOL.");
					            blockAmat = Material.WOOL;
					        }
					        
					        BlockA = blockAmat;
					        
						}else{
							this.setDefaults("[FlatlandsBuilder] Invalid Settings provided, using defaults '64,wool:15,wool:7,wool:8'");
						}
					} 
				} 
			}catch (Exception e){
			log.severe("[FlatlandsBuilder] Error parsing FlatlandsBuilder Settings '" + id + "'. using defaults '64,wool:15,wool:7,wool:8': " + e.toString());
            e.printStackTrace();
            height = 64;
            genMode = "grid2";
            BlockA = Material.WOOL;
			BlockB = Material.WOOL;
			BlockC = Material.WOOL;
			BlockADV = (byte) 0x7;
			BlockBDV = (byte) 0xf;
			BlockCDV = (byte) 0x8;
			}
			
		}else{
			this.setDefaults("[FlatlandsBuilder] No Settings provided, using defaults '64,wool:15,wool:7,wool:8'");
		}
	}
	
	public List<BlockPopulator> getDefaultPopulators(World world){
		ArrayList<BlockPopulator> populators = new ArrayList<BlockPopulator>();

		populators.add(new FLBPopulator(height, BlockA, BlockB, BlockC, BlockADV, BlockBDV, BlockCDV, genMode));
		
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
