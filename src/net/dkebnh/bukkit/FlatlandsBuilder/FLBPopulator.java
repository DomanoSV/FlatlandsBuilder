package net.dkebnh.bukkit.FlatlandsBuilder;

import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class FLBPopulator extends BlockPopulator {
	
	private Logger log = Logger.getLogger("Minecraft");
	
	private int height;
	private String fillblock;
	private String borderAblock;
	private String borderBblock;
	
    protected FLBPopulator(int height, String fillblock, String borderAblock, String borderBblock)
    {
        this.height = height;
        this.fillblock = fillblock;
        this.borderAblock = borderAblock;
        this.borderBblock = borderBblock;
    }
    
	public void populate(World world, Random random, Chunk chunk) {
		int blockx, blockz, blockx2, blockz2;
		Block block;
		
		String fillmaterialTokens[] = fillblock.split("[:]", 2);
		String bordermaterialTokens[] = borderAblock.split("[:]", 2);
        byte filldatavalue = 0;
        byte borderdatavalue = 0;
        
        if (fillmaterialTokens.length == 2)
        {
            try
            {
                filldatavalue = Byte.parseByte(fillmaterialTokens[1]);
            } catch (Exception e)
            {
                log.warning("[FlatlandsBuilder] Invalid Data Value '" + fillmaterialTokens[1] + "'. Defaulting to 0.");
                filldatavalue = 0;
            }
        }
        
        if (bordermaterialTokens.length == 2)
        {
            try
            {
                borderdatavalue = Byte.parseByte(bordermaterialTokens[1]);
            } catch (Exception e)
            {
                log.warning("[FlatlandsBuilder] Invalid Data Value '" + bordermaterialTokens[1] + "'. Defaulting to 0.");
                borderdatavalue = 0;
            }
        }
        
        Material fillmat = Material.matchMaterial(fillmaterialTokens[0]);
        if (fillmat == null)
        {
            try
            {
            	fillmat = Material.getMaterial(Integer.parseInt(fillmaterialTokens[0]));
            } catch (Exception e){

            }

            if (fillmat == null)
            {
                log.warning("[FlatlandsBuilder] Invalid Block ID '" + fillmaterialTokens[0] + "'. Defaulting fill to WHITE_WOOL.");
                fillmat = Material.WOOL;
            }
        }
        
        if (!fillmat.isBlock())
        {
            log.warning("[FlatlandsBuilder] Error, '" + fillmaterialTokens[0] + "' is not a block. Defaulting fill to WHITE_WOOL.");
            fillmat = Material.WOOL;
        }
        
        Material bordermat = Material.matchMaterial(bordermaterialTokens[0]);
        if (bordermat == null)
        {
            try
            {
            	bordermat = Material.getMaterial(Integer.parseInt(bordermaterialTokens[0]));
            } catch (Exception e){

            }

            if (bordermat == null)
            {
                log.warning("[FlatlandsBuilder] Invalid Block ID '" + bordermaterialTokens[0] + "'. Defaulting fill to WHITE_WOOL.");
                bordermat = Material.WOOL;
            }
        }
        
        if (!bordermat.isBlock())
        {
            log.warning("[FlatlandsBuilder] Error, '" + bordermaterialTokens[0] + "' is not a block. Defaulting fill to WHITE_WOOL.");
            bordermat = Material.WOOL;
        }
        
		for (blockx2 = 0; blockx2 < 16; ++blockx2){
			for (blockz2 = 0; blockz2 < 16; ++blockz2){
				block = chunk.getBlock(blockx2, height, blockz2);
						
				block.setType(bordermat);
				block.setData(borderdatavalue);
				}
		}
		
		for (blockx = 1; blockx < 15; ++blockx){
			for (blockz = 1; blockz < 15; ++blockz){
				block = chunk.getBlock(blockx, height, blockz);
						
				block.setType(fillmat);
				block.setData(filldatavalue);
				}
		}
	}

}
