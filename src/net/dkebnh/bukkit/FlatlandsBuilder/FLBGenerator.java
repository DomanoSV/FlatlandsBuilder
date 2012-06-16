package net.dkebnh.bukkit.FlatlandsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World; 
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class FLBGenerator extends ChunkGenerator{
	
	private Logger log = Logger.getLogger("Minecraft");
	private String genMode;
	private String genModeParse;
	private int height = 0;
	private Material[] BlockFLB = new Material[3];
	private byte[] BlockFLBDV = new byte[3];
	private Biome BiomeFLB = Biome.PLAINS;	
	
	public void setDefaults(String msg){        
		genMode = "grid2";      
		BlockFLB[0] = Material.WOOL;		
		BlockFLB[1] = Material.WOOL;		
		BlockFLB[2] = Material.WOOL;		
		BlockFLBDV[0] = (byte) 0xf;		
		BlockFLBDV[1] = (byte) 0x7;		
		BlockFLBDV[2] = (byte) 0x8;
		
		if (height != 0){		// Checks to see if a height variable is accepted by FlatlandsBuiler.
			msg = "[FlatlandsBuilder] No Block Settings provided, using default blocks 'wool:15,wool:7,wool:8'";
		}else{
			height = 64;
		} 
			this.log.warning(msg);	
	}
	
	public FLBGenerator(){
		this("64,wool:15,wool:7,wool:8");
	}
	
	public FLBGenerator(String id){
		if (id != null){
			try{
            	String[] parts = id.split("[;]");
            	id = parts.length == 0 ? "" : parts[0];
            	if(parts.length >= 2) {
            		this.genModeParse = parts[1];
            	}
            	
            	
				
        		List<String> genModechk = Arrays.asList("normal","grid","grid2","grid3","grid4","grid5");
        		
        		if (genModechk.contains(genModeParse)){
        			log.info("[FlatlandsBuilder] Generation Mode " + genModeParse);
        		}else{
        			genModeParse = null;
        		}
            	
                if (parts[0].length() > 0){
                    String tokens[] = parts[0].split("[,]");
                    
                    if (tokens.length > 4){			// Checks to see if a larger string has been provided, and adjusts accordingly.
						String tokenStore[] = new String[4];
					
						log.warning("[FlatlandsBuilder] The number of variables entered [" + tokens.length + "], is too many. Using first four.");

							for (int tokenNumb = 0; tokenNumb < 4; tokenNumb ++){
								tokenStore[tokenNumb] = tokens[tokenNumb];
								}

						tokens = tokenStore;
						
					}
 
					height = Integer.parseInt(tokens[0]);		// Sets height variable.
					
					if (height <= 0 || height >= 128){		// May change max height later on, making it generate any higher seems impractical at this stage.
						log.warning("[FlatlandsBuilder] Invalid height '" + tokens[0] + "'. Using 64 instead.");
						height = 64;
					} 

                    for (int i = 1; i < tokens.length; i ++){		// Sets blocks array in sequential order.    
                    	int t = i - 1;
                    	
                        String materialTokens[] = tokens[i].split("[:]", 2);		// Splits Block Type and Type ID into 2 so it can be parsed by the FlatlandsBuilder.
    				        
                        if (materialTokens.length == 2){
                        	try{
                        		BlockFLBDV[t] = Byte.parseByte(materialTokens[1]);
    				        }catch (Exception e){
    				            log.warning("[FlatlandsBuilder] Invalid Block Data Value '" + materialTokens[1] + "'. Defaulting to 0.");
    				            BlockFLBDV[t] = (byte) 0x0;
    				        }
    				    }
    				        
    				    Material mat = Material.matchMaterial(materialTokens[0]);
    				        
    				    if (mat == null){
    				    	try{
    				    		mat = Material.getMaterial(Integer.parseInt(materialTokens[0]));
    				        }catch (Exception e){
    				        
    				        }
    				    	
    				    	if (mat == null){
    				        	log.warning("[FlatlandsBuilder] Invalid Block ID '" + materialTokens[0] + "'. Defaulting to WHITE_WOOL.");
    				        	mat = Material.WOOL;
    				        }
    				    }
    				        
    				    if (!mat.isBlock()){
    				    	log.warning("[FlatlandsBuilder] Error, Block'" + materialTokens[0] + "' is not a block. Defaulting to WHITE_WOOL.");
    				    	mat = Material.WOOL;
    				    }
       
                    BlockFLB[t] = mat;    
                    }
					
                	if (tokens.length == 4){		// Sets generation format based on number of variables entered. 
                		genMode = "grid2";
                	}else if (tokens.length == 3){
                		genMode = "grid";
                	}else if (tokens.length == 2){
                		genMode = "normal";
                	}else{
                		this.setDefaults("[FlatlandsBuilder] Invalid Settings provided, using defaults '64,wool:15,wool:7,wool:8'");
                	}
                    
                    if (genModeParse != null){
                    	if (tokens.length == 4){		// Sets generation format based on number of variables entered.
                    		if(genModeParse.equalsIgnoreCase("grid5")){
                    			genMode = "grid5";
                    		}else if(genModeParse.equalsIgnoreCase("grid4")){
                    			genMode = "grid4";
                    		}else{
                    			genMode = "grid2";
                    		}
                    	}else if (tokens.length == 3){
                    		if(genModeParse.equalsIgnoreCase("grid3")){
                    			genMode = "grid3";
                    		}else{
                    			genMode = "grid";
                    		}
                    	}else if (tokens.length == 2){
        						genMode = "normal";
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
            BlockFLB[0] = Material.WOOL;
    		BlockFLB[1] = Material.WOOL;
    		BlockFLB[2] = Material.WOOL;
    		BlockFLBDV[0] = (byte) 0xf;
    		BlockFLBDV[1] = (byte) 0x7;
    		BlockFLBDV[2] = (byte) 0x8;
			}
			
		}else{
			this.setDefaults("[FlatlandsBuilder] No Settings provided, using defaults '64,wool:15,wool:7,wool:8'");
		}
	}
	
	public List<BlockPopulator> getDefaultPopulators(World world){
		ArrayList<BlockPopulator> populators = new ArrayList<BlockPopulator>();
		
		populators.add(new FLBPopulator(height, BlockFLB, BlockFLBDV, genMode));
		
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
				world.setBiome(x, z, BiomeFLB);
			}
		}
		return blocks;
	}
}
