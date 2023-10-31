package tekcays_addon.gtapi.unification.material.materials;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Zincite;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static gregtech.api.util.GTUtility.gregtechId;
import static tekcays_addon.gtapi.unification.TKCYAMaterials.*;

public class TKCYARoastMaterials {

    public static void init() {
        RoastedTetrahedrite = new Material.Builder(24801, gregtechId("roasted_tetrahedrite"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Materials.CupricOxide, 6, Materials.AntimonyTrioxide, 1, BandedIron, 1)
                .color((CupricOxide.getMaterialRGB() * 6 + AntimonyTrioxide.getMaterialRGB() + BandedIron.getMaterialRGB()) / 8)
                .build();

        RoastedCobaltite = new Material.Builder(24802, gregtechId("roasted_cobaltite"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(CobaltOxide, 2, ArsenicTrioxide, 1)
                .color((CobaltOxide.getMaterialRGB() + ArsenicTrioxide.getMaterialRGB()) / 2)
                .build();

        SilverOxide = new Material.Builder(24803, gregtechId("silver_oxide"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Silver, 1, Oxygen, 1)
                .color((Silver.getMaterialRGB() + Oxygen.getMaterialRGB()) / 2)
                .build();

        RoastedGalena = new Material.Builder(24804, gregtechId("roasted_galena"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Massicot, 9, SilverOxide, 6)
                .color((Massicot.getMaterialRGB() * 9 + SilverOxide.getMaterialRGB() * 6) / 15)
                .build();

        RoastedChalcopyrite = new Material.Builder(24805, gregtechId("roasted_chalcopyrite"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(CupricOxide, 1, Ferrosilite, 1)
                .color((CupricOxide.getMaterialRGB() + Ferrosilite.getMaterialRGB()) / 2)
                .build();

        RoastedKesterite = new Material.Builder(24806, gregtechId("roasted_kesterite"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(CupricOxide, 4, Zincite, 1, Cassiterite, 1)
                .color((CupricOxide.getMaterialRGB() + Ferrosilite.getMaterialRGB()) / 2)
                .build();

        RoastedStannite = new Material.Builder(24807, gregtechId("roasted_stannite"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(CupricOxide, 4, BandedIron, 1, Cassiterite, 2)
                .color((CupricOxide.getMaterialRGB() * 4 + BandedIron.getMaterialRGB() + Cassiterite.getMaterialRGB() * 2) / 7)
                .build();

        RoastedArsenopyrite = new Material.Builder(24808, gregtechId("roasted_arsenopyrite"))
                .dust().flags(DISABLE_DECOMPOSITION)
                .components(BandedIron, 1, ArsenicTrioxide, 1)
                .color((BandedIron.getMaterialRGB() + ArsenicTrioxide.getMaterialRGB() ) / 2)
                .build();

        RoastedBornite = new Material.Builder(24809, gregtechId("roasted_bornite"))
                .dust().flags(DISABLE_DECOMPOSITION)
                .components(CupricOxide, 10, BandedIron, 1)
                .color((CupricOxide.getMaterialRGB() * 10 + BandedIron.getMaterialRGB()) / 11)
                .build();
    }
}
