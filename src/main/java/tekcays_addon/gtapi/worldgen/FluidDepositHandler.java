package tekcays_addon.gtapi.worldgen;

import gregtech.api.GregTechAPI;
import gregtech.api.util.XSTR;
import gregtech.api.worldgen.bedrockFluids.ChunkPosDimension;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import tekcays_addon.gtapi.network.PacketFluidDepositList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class FluidDepositHandler {

    public final static LinkedHashMap<FluidDepositDefinition, Integer> veinList2 = new LinkedHashMap<>();
    private final static Map<Integer, HashMap<Integer, Integer>> totalWeightMap = new HashMap<>();
    public final static List<FluidDepositDefinition> veinList = new ArrayList<>();
    public static HashMap<ChunkPosDimension, FluidDepositWorldEntry> veinCache = new HashMap<>();

    public static final int VEIN_CHUNK_SIZE = 8; // veins are 8x8 chunk squares

    /**
     * Gets the FluidVeinWorldInfo object associated with the given chunk
     * @param world  The world to retrieve
     * @param chunkX X coordinate of desired chunk
     * @param chunkZ Z coordinate of desired chunk
     * @return The FluidVeinWorldInfo corresponding with the given chunk
     */
    @Nullable
    public static FluidDepositWorldEntry getFluidVeinWorldEntry(@Nonnull World world, int chunkX, int chunkZ) {
        if (world.isRemote)
            return null;

        ChunkPosDimension coords = new ChunkPosDimension(world.provider.getDimension(), chunkX / VEIN_CHUNK_SIZE, chunkZ / VEIN_CHUNK_SIZE);

        FluidDepositWorldEntry worldEntry = veinCache.get(coords);
        if (worldEntry == null) {
            FluidDepositDefinition definition = null;

            int query = world.getChunk(chunkX / VEIN_CHUNK_SIZE, chunkZ / VEIN_CHUNK_SIZE).getRandomWithSeed(90210).nextInt();

            int totalWeight = getTotalWeight(world.provider, biome);
            if (totalWeight > 0) {
                int weight = Math.abs(query % totalWeight);


                veinList.forEach(fluidDepositDefinition -> {


                });

                for (Map.Entry<FluidDepositDefinition, Integer> entry : veinList2.entrySet()) {
                    int veinWeight = entry.getValue() + entry.getKey().getBiomeWeightModifier().apply(biome);
                    if (veinWeight > 0 && entry.getKey().getDimensionFilter().test(world.provider)) {
                        weight -= veinWeight;
                        if (weight < 0) {
                            definition = entry.getKey();
                            break;
                        }
                    }
                }
            }

            Random random = new XSTR(31L * 31 * chunkX + chunkZ * 31L + Long.hashCode(world.getSeed()));

            int currentFluidAmount = 0;
            int depth = 0;

            if (definition != null) {

                int maxFluidAmomunt = definition.getMaxFluidAmount();
                int minFluidAmount = definition.getMinFluidAmount();
                if (maxFluidAmomunt - minFluidAmount <= 0) {
                   currentFluidAmount = minFluidAmount;
                } else {
                    currentFluidAmount = random.nextInt(maxFluidAmomunt - minFluidAmount) + minFluidAmount;
                }

                int maxDepth = definition.getMaxDepth();
                int minDepth = definition.getMinDepth();
                if (maxDepth - minDepth <= 0) {
                    depth = minDepth;
                } else {
                    depth = random.nextInt(maxDepth - minDepth) + minDepth;
                }
            }

            worldEntry = new FluidDepositWorldEntry(definition, currentFluidAmount, depth);
            veinCache.put(coords, worldEntry);
        }
        return worldEntry;
    }

    /**
     * Gets the total weight of all veins for the given dimension ID and biome type
     * @param provider The WorldProvider whose dimension to check
     * @return The total weight associated with the dimension/biome pair
     */
    public static int getTotalWeight(@Nonnull WorldProvider provider) {
        int dim = provider.getDimension();
        if (!totalWeightMap.containsKey(dim)) {
            totalWeightMap.put(dim, new HashMap<>());
        }

        Map<Integer, Integer> dimMap = totalWeightMap.get(dim);

        if (dimMap.containsKey(biomeID)) {
            return dimMap.get(biomeID);
        }

        int totalWeight = 0;
        for (Map.Entry<FluidDepositDefinition, Integer> entry : veinList2.entrySet()) {
            if (entry.getKey().getDimensionFilter().test(provider)) {
                totalWeight += entry.getKey().getWeight();
            }
        }

        dimMap.put(biomeID, totalWeight);
        return totalWeight;
    }

    /**
     * Adds a vein to the pool of veins
     * @param definition the vein to add
     */
    public static void addFluidDeposit(FluidDepositDefinition definition) {
        veinList2.put(definition, definition.getWeight());
    }

    public static void recalculateChances(boolean mutePackets) {
         totalWeightMap.clear();
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && !mutePackets) {
            HashMap<FluidDepositWorldEntry, Integer> packetMap = new HashMap<>();
            for (Map.Entry<ChunkPosDimension, FluidDepositWorldEntry> entry : FluidDepositHandler.veinCache.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null)
                    packetMap.put(entry.getValue(), entry.getValue().getFluidDepositDefinition().getWeight());
            }
            GregTechAPI.networkHandler.sendToAll(new PacketFluidDepositList(packetMap));
        }
    }

    /**
     * Gets the current fluid remaining amount in a specific chunk's vein
     * @param world  The world to test
     * @param chunkX X coordinate of desired chunk
     * @param chunkZ Z coordinate of desired chunk
     * @return amount of operations in the given chunk
     */
    public static int getFluidAmount(World world, int chunkX, int chunkZ) {
        FluidDepositWorldEntry info = getFluidVeinWorldEntry(world, chunkX, chunkZ);
        if (info == null) return 0;
        return info.getFluidAmount();
    }

    /**
     * Gets the depth in a specific chunk's vein
     * @param world  The world to test
     * @param chunkX X coordinate of desired chunk
     * @param chunkZ Z coordinate of desired chunk
     * @return amount of operations in the given chunk
     */
    public static int getDepth(World world, int chunkX, int chunkZ) {
        FluidDepositWorldEntry info = getFluidVeinWorldEntry(world, chunkX, chunkZ);
        if (info == null) return 0;
        return info.getDepth();
    }

    /**
     * Gets the Fluid in a specific chunk's vein
     * @param world  The world to test
     * @param chunkX X coordinate of desired chunk
     * @param chunkZ Z coordinate of desired chunk
     * @return Fluid in given chunk
     */
    @Nullable
    public static Fluid getFluidInChunk(World world, int chunkX, int chunkZ) {
        FluidDepositWorldEntry info = getFluidVeinWorldEntry(world, chunkX, chunkZ);
        if (info == null || info.getFluidDepositDefinition() == null) return null;
        return info.getFluidDepositDefinition().getFluid();
    }

    /**
     * Depletes fluid from a given chunk
     * @param world           World whose chunk to drain
     * @param chunkX          Chunk x
     * @param chunkZ          Chunk z
     * @param amount          the amount of fluid to deplete the vein by
     */
    public static void depleteVein(World world, int chunkX, int chunkZ, int amount) {
        FluidDepositWorldEntry info = getFluidVeinWorldEntry(world, chunkX, chunkZ);
        if (info == null) return;

        info.decreaseFluidAmount(amount);
        FluidDepositSaveData.setDirty();
    }


}
