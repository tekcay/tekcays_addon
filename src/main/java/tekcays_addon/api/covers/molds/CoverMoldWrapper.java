package tekcays_addon.api.covers.molds;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import lombok.Data;
import tekcays_addon.gtapi.render.TKCYATextures;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.Ceramic;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.*;

@Data
public class CoverMoldWrapper {

    private final SimpleOverlayRenderer textures;
    private final OrePrefix moldPrefix;
    private final OrePrefix outputPrefix;
    private final Material material;

    public static final CoverMoldWrapper COVER_MOLD_INGOT_CERAMIC_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_INGOT_CERAMIC, moldIngot, ingot, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_PLATE_CERAMIC_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_PLATE_CERAMIC, moldPlate, plate, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_STICK_CERAMIC_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_STICK_CERAMIC, moldStick, stick, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_STICK_LONG_CERAMIC_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_STICK_LONG_CERAMIC, moldStickLong, stickLong, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_CERAMIC_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_GEAR_CERAMIC, moldGear, gear, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_SMALL_CERAMIC_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_GEAR_SMALL_CERAMIC, moldGearSmall, gearSmall, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_BOLT_CERAMIC_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_BOLT_CERAMIC, moldBolt, bolt, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_RING_CERAMIC_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_RING_CERAMIC, moldRing, ring, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_BLOCK_CERAMIC_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_BLOCK_CERAMIC, moldBlock, block, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_INGOT_STEEL_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_INGOT_STEEL, moldIngot, ingot, Steel);
    public static final CoverMoldWrapper COVER_MOLD_PLATE_STEEL_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_PLATE_STEEL, moldPlate, plate, Steel);
    public static final CoverMoldWrapper COVER_MOLD_STICK_STEEL_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_STICK_STEEL, moldStick, stick, Steel);
    public static final CoverMoldWrapper COVER_MOLD_STICK_LONG_STEEL_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_STICK_LONG_STEEL, moldStickLong, stickLong, Steel);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_STEEL_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_GEAR_STEEL, moldGear, gear, Steel);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_SMALL_STEEL_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_GEAR_SMALL_STEEL, moldGearSmall, gearSmall, Steel);
    public static final CoverMoldWrapper COVER_MOLD_BOLT_STEEL_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_BOLT_STEEL, moldBolt, bolt, Steel);
    public static final CoverMoldWrapper COVER_MOLD_RING_STEEL_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_RING_STEEL, moldRing, ring, Steel);
    public static final CoverMoldWrapper COVER_MOLD_BLOCK_STEEL_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_BLOCK_STEEL, moldBlock, block, Steel);
    public static final CoverMoldWrapper COVER_MOLD_INGOT_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_INGOT_TUNGSTEN_CARBIDE, moldIngot, ingot, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_PLATE_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_PLATE_TUNGSTEN_CARBIDE, moldPlate, plate, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_STICK_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_STICK_TUNGSTEN_CARBIDE, moldStick, stick, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_STICK_LONG_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_STICK_LONG_TUNGSTEN_CARBIDE, moldStickLong, stickLong, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_GEAR_TUNGSTEN_CARBIDE, moldGear, gear, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE, moldGearSmall, gearSmall, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_BOLT_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_BOLT_TUNGSTEN_CARBIDE, moldBolt, bolt, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_RING_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_RING_TUNGSTEN_CARBIDE, moldRing, ring, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE, moldBlock, block, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_INGOT_CARBON_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_INGOT_CARBON, moldIngot, ingot, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_PLATE_CARBON_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_PLATE_CARBON, moldPlate, plate, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_STICK_CARBON_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_STICK_CARBON, moldStick, stick, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_STICK_LONG_CARBON_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_STICK_LONG_CARBON, moldStickLong, stickLong, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_CARBON_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_GEAR_CARBON, moldGear, gear, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_SMALL_CARBON_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_GEAR_SMALL_CARBON, moldGearSmall, gearSmall, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_BOLT_CARBON_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_BOLT_CARBON, moldBolt, bolt, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_RING_CARBON_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_RING_CARBON, moldRing, ring, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_BLOCK_CARBON_WRAPPER = new CoverMoldWrapper(TKCYATextures.COVER_MOLD_BLOCK_CARBON, moldBlock, block, Carbon);



}
