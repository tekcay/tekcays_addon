package tekcays_addon.gtapi.module;

import gregtech.api.modules.IGregTechModule;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;
import tekcays_addon.gtapi.worldgen.FluidDepositSaveData;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

public class ModuleManager implements IGregTechModule {
    @Nonnull
    @Override
    public Set<ResourceLocation> getDependencyUids() {
        return IGregTechModule.super.getDependencyUids();
    }

    @Nonnull
    @Override
    public Set<String> getModDependencyIDs() {
        return IGregTechModule.super.getModDependencyIDs();
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        IGregTechModule.super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        IGregTechModule.super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        IGregTechModule.super.postInit(event);
    }

    @Override
    public void loadComplete(FMLLoadCompleteEvent event) {
        IGregTechModule.super.loadComplete(event);
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event) {
        IGregTechModule.super.serverStarting(event);
    }

    @Override
    public void serverStarted(FMLServerStartedEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
            if (!world.isRemote) {
                FluidDepositSaveData saveData = (FluidDepositSaveData) world.loadData(FluidDepositSaveData.class, FluidDepositSaveData.dataName);
                if (saveData == null) {
                    saveData = new FluidDepositSaveData(FluidDepositSaveData.dataName);
                    world.setData(FluidDepositSaveData.dataName, saveData);
                }
                FluidDepositSaveData.setInstance(saveData);
            }
        }
    }

    @Override
    public void serverStopped(FMLServerStoppedEvent event) {
        IGregTechModule.super.serverStopped(event);
    }

    @Override
    public void registerPackets() {
        IGregTechModule.super.registerPackets();
    }

    @Nonnull
    @Override
    public List<Class<?>> getEventBusSubscribers() {
        return IGregTechModule.super.getEventBusSubscribers();
    }

    @Override
    public boolean processIMC(FMLInterModComms.IMCMessage message) {
        return IGregTechModule.super.processIMC(message);
    }

    @Nonnull
    @Override
    public Logger getLogger() {
        return null;
    }
}
