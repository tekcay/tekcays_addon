package tekcays_addon.api.covers.molds;

import com.google.common.base.CaseFormat;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import lombok.Data;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tekcays_addon.gtapi.render.TKCYATextures.*;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.Ceramic;
import static tekcays_addon.gtapi.unification.material.ore.TKCYAOrePrefix.*;

@Data
public class CoverMoldWrapper {

    private final SimpleOverlayRenderer textures;
    private final OrePrefix moldPrefix;
    private final OrePrefix outputPrefix;
    private final Material material;

    public String getPathln() {
        return String.format("%s_%s",
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.outputPrefix.name()),
                this.material.getUnlocalizedName().replaceAll("material.", ""));
    }

    public static final CoverMoldWrapper COVER_MOLD_INGOT_CERAMIC_WRAPPER = new CoverMoldWrapper(COVER_MOLD_INGOT_CERAMIC_TEXTURE, moldIngot, ingot, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_PLATE_CERAMIC_WRAPPER = new CoverMoldWrapper(COVER_MOLD_PLATE_CERAMIC_TEXTURE, moldPlate, plate, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_STICK_CERAMIC_WRAPPER = new CoverMoldWrapper(COVER_MOLD_STICK_CERAMIC_TEXTURE, moldStick, stick, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_STICK_LONG_CERAMIC_WRAPPER = new CoverMoldWrapper(COVER_MOLD_STICK_LONG_CERAMIC_TEXTURE, moldStickLong, stickLong, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_CERAMIC_WRAPPER = new CoverMoldWrapper(COVER_MOLD_GEAR_CERAMIC_TEXTURE, moldGear, gear, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_SMALL_CERAMIC_WRAPPER = new CoverMoldWrapper(COVER_MOLD_GEAR_SMALL_CERAMIC_TEXTURE, moldGearSmall, gearSmall, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_BOLT_CERAMIC_WRAPPER = new CoverMoldWrapper(COVER_MOLD_BOLT_CERAMIC_TEXTURE, moldBolt, bolt, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_RING_CERAMIC_WRAPPER = new CoverMoldWrapper(COVER_MOLD_RING_CERAMIC_TEXTURE, moldRing, ring, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_BLOCK_CERAMIC_WRAPPER = new CoverMoldWrapper(COVER_MOLD_BLOCK_CERAMIC_TEXTURE, moldBlock, block, Ceramic);
    public static final CoverMoldWrapper COVER_MOLD_INGOT_STEEL_WRAPPER = new CoverMoldWrapper(COVER_MOLD_INGOT_STEEL_TEXTURE, moldIngot, ingot, Steel);
    public static final CoverMoldWrapper COVER_MOLD_PLATE_STEEL_WRAPPER = new CoverMoldWrapper(COVER_MOLD_PLATE_STEEL_TEXTURE, moldPlate, plate, Steel);
    public static final CoverMoldWrapper COVER_MOLD_STICK_STEEL_WRAPPER = new CoverMoldWrapper(COVER_MOLD_STICK_STEEL_TEXTURE, moldStick, stick, Steel);
    public static final CoverMoldWrapper COVER_MOLD_STICK_LONG_STEEL_WRAPPER = new CoverMoldWrapper(COVER_MOLD_STICK_LONG_STEEL_TEXTURE, moldStickLong, stickLong, Steel);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_STEEL_WRAPPER = new CoverMoldWrapper(COVER_MOLD_GEAR_STEEL_TEXTURE, moldGear, gear, Steel);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_SMALL_STEEL_WRAPPER = new CoverMoldWrapper(COVER_MOLD_GEAR_SMALL_STEEL_TEXTURE, moldGearSmall, gearSmall, Steel);
    public static final CoverMoldWrapper COVER_MOLD_BOLT_STEEL_WRAPPER = new CoverMoldWrapper(COVER_MOLD_BOLT_STEEL_TEXTURE, moldBolt, bolt, Steel);
    public static final CoverMoldWrapper COVER_MOLD_RING_STEEL_WRAPPER = new CoverMoldWrapper(COVER_MOLD_RING_STEEL_TEXTURE, moldRing, ring, Steel);
    public static final CoverMoldWrapper COVER_MOLD_BLOCK_STEEL_WRAPPER = new CoverMoldWrapper(COVER_MOLD_BLOCK_STEEL_TEXTURE, moldBlock, block, Steel);
    public static final CoverMoldWrapper COVER_MOLD_INGOT_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(COVER_MOLD_INGOT_TUNGSTEN_CARBIDE_TEXTURE, moldIngot, ingot, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_PLATE_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(COVER_MOLD_PLATE_TUNGSTEN_CARBIDE_TEXTURE, moldPlate, plate, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_STICK_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(COVER_MOLD_STICK_TUNGSTEN_CARBIDE_TEXTURE, moldStick, stick, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_STICK_LONG_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(COVER_MOLD_STICK_LONG_TUNGSTEN_CARBIDE_TEXTURE, moldStickLong, stickLong, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(COVER_MOLD_GEAR_TUNGSTEN_CARBIDE_TEXTURE, moldGear, gear, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(COVER_MOLD_GEAR_SMALL_TUNGSTEN_CARBIDE_TEXTURE, moldGearSmall, gearSmall, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_BOLT_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(COVER_MOLD_BOLT_TUNGSTEN_CARBIDE_TEXTURE, moldBolt, bolt, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_RING_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(COVER_MOLD_RING_TUNGSTEN_CARBIDE_TEXTURE, moldRing, ring, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE_WRAPPER = new CoverMoldWrapper(COVER_MOLD_BLOCK_TUNGSTEN_CARBIDE_TEXTURE, moldBlock, block, TungstenCarbide);
    public static final CoverMoldWrapper COVER_MOLD_INGOT_CARBON_WRAPPER = new CoverMoldWrapper(COVER_MOLD_INGOT_CARBON_TEXTURE, moldIngot, ingot, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_PLATE_CARBON_WRAPPER = new CoverMoldWrapper(COVER_MOLD_PLATE_CARBON_TEXTURE, moldPlate, plate, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_STICK_CARBON_WRAPPER = new CoverMoldWrapper(COVER_MOLD_STICK_CARBON_TEXTURE, moldStick, stick, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_STICK_LONG_CARBON_WRAPPER = new CoverMoldWrapper(COVER_MOLD_STICK_LONG_CARBON_TEXTURE, moldStickLong, stickLong, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_CARBON_WRAPPER = new CoverMoldWrapper(COVER_MOLD_GEAR_CARBON_TEXTURE, moldGear, gear, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_GEAR_SMALL_CARBON_WRAPPER = new CoverMoldWrapper(COVER_MOLD_GEAR_SMALL_CARBON_TEXTURE, moldGearSmall, gearSmall, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_BOLT_CARBON_WRAPPER = new CoverMoldWrapper(COVER_MOLD_BOLT_CARBON_TEXTURE, moldBolt, bolt, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_RING_CARBON_WRAPPER = new CoverMoldWrapper(COVER_MOLD_RING_CARBON_TEXTURE, moldRing, ring, Carbon);
    public static final CoverMoldWrapper COVER_MOLD_BLOCK_CARBON_WRAPPER = new CoverMoldWrapper(COVER_MOLD_BLOCK_CARBON_TEXTURE, moldBlock, block, Carbon);






}
