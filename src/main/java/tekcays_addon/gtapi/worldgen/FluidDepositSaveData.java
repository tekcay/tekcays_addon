package tekcays_addon.gtapi.worldgen;

import gregtech.api.worldgen.bedrockFluids.ChunkPosDimension;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import tekcays_addon.TekCaysAddon;

import javax.annotation.Nonnull;
import java.util.Map;

public class FluidDepositSaveData extends WorldSavedData {

    private static FluidDepositSaveData INSTANCE;
    public static final String dataName = TekCaysAddon.MODID + ".fluidDepositData";

    public FluidDepositSaveData(String s) {
        super(s);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList veinList = nbt.getTagList("veinInfo", 10);
        FluidDepositHandler.veinCache.clear();
        for (int i = 0; i < veinList.tagCount(); i++) {
            NBTTagCompound tag = veinList.getCompoundTagAt(i);
            ChunkPosDimension coords = ChunkPosDimension.readFromNBT(tag);
            if (coords != null) {
                FluidDepositWorldEntry info = FluidDepositWorldEntry.readFromNBT(tag.getCompoundTag("info"));
                FluidDepositHandler.veinCache.put(coords, info);
            }
        }
    }

    @Override
    public @Nonnull
    NBTTagCompound writeToNBT(@Nonnull NBTTagCompound nbt) {
        NBTTagList oilList = new NBTTagList();
        for (Map.Entry<ChunkPosDimension, FluidDepositWorldEntry > e : FluidDepositHandler.veinCache.entrySet()) {
            if (e.getKey() != null && e.getValue() != null) {
                NBTTagCompound tag = e.getKey().writeToNBT();
                tag.setTag("info", e.getValue().writeToNBT());
                oilList.appendTag(tag);
            }
        }
        nbt.setTag("veinInfo", oilList);

        return nbt;
    }

    public static void setDirty() {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && INSTANCE != null)
            INSTANCE.markDirty();
    }

    public static void setInstance(FluidDepositSaveData in) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
            INSTANCE = in;
    }
}
