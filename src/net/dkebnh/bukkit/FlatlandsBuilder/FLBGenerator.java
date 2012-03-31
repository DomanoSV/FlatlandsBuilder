package net.dkebnh.bukkit.FlatlandsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World; 
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class FLBGenerator extends ChunkGenerator{
	
	private BuilderFL plugin;
	
	public FLBGenerator(BuilderFL instance){
		this.plugin = instance;
	}
	
	public List<BlockPopulator> getDefaultPopulators(World world){
		ArrayList<BlockPopulator> populators = new ArrayList<BlockPopulator>();
		
		populators.add(new FLBPopulator());
		
		return populators;
	}
	
	public Location getFixedSpawnLocation(World world, Random random){
		return new Location(world, 0, 17, 0);
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
				
				for (y = 1; y < 15; ++y){
					blocks[this.coordsToInt(x, y, z)] = (byte) Material.STONE.getId();
				}

				blocks[this.coordsToInt(x, 15, z)] = (byte) Material.WOOL.getId();
			}
		}
		
	return blocks;
	}
}
