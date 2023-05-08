package tekcays_addon.gtapi.capability.containers;

import gregtech.api.unification.ore.OrePrefix;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import tekcays_addon.api.consts.CapabilityId;

import javax.annotation.Nonnull;

public interface IMoldCoverable<T, U extends T> {

    OrePrefix getOrePrefixOutput();
}
