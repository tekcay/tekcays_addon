# TODO


## Casting and alloying overhaul

### Removals
* PBF
* EBF
* Alloy Smelter
* Extractor (or at least the related recipes)
* Fluid Solidifier
* **[GTCYM](https://github.com/GregTechCEu/gregicality-multiblocks)**' Alloy Blast Smelter 

### Melter
* add a melter multiblock made of clay/brick derivative for stone age

Logic:
* `ore:ore` must be crushed to be melted: in stone age, it would require a `toolHammer`, in bronze age it would require a steam forge hammer or a steam macerator
* melting `ore:crushed` would give `unpureMaterial`. To reach bronze age, it would need `unpureTin` and `unpureCopper` to make `unpureBronze`, which suffices to build steam machines
* melting would consume fuel based on the `duration` of the recipe and the value of the used fuel (this logic should be found in **[GTCEu LargeBoiler class](https://github.com/GregTechCEu/GregTech/blob/master/src/main/java/gregtech/api/capability/impl/BoilerRecipeLogic.java)**
* fuel can be an `ItemStack` such as coal, charcoal, wood *etc.* or a `LiquidStack` such as `liquid:natural_gas`, `liquid:creosote` *etc.*  

### Casting
The molten material/alloy is poured in a **casting block**, probably a MTE.
* this **casting block** contains 1 input and 1 fluid input, one for the mold, one for the molten material/alloy.
* additionnal molds made of different material must be made, namely ceramic for the early game, then probably something inspired from **[GT6](https://github.com/GregTech6/gregtech6)**, including tungsten and carbon higher melting point materials/alloys.
* adding all the molds in different `oreRegistry` might be more convenient for `recipes`.

`RecipeMap`:
```
.notConsummable(ore:moldShapeMaterial)
.fluidInputs(material/alloy.getFluid)
.outputs(component)
```
* if `material.fluidTemperature` of the mold < `material/alloy.fluidTemperature`, the mold and fluidInputs are consumed, giving scrap.
* for better fluid handling, introduce ULV fluid regulator to control the exact the fluid amount in the **casting block**

### Alloying 

## Electrolysis overhaul

### Removals

* electrolyzer
* `ELECTROLYZER_RECIPES`

### Electrodes

* ~~Addition of a new `OrePrefix` (See the  **[OrePrefix class](https://github.com/GregTechCEu/GregTech/blob/master/src/main/java/gregtech/api/unification/ore/OrePrefix.java)**)~~ (Might not be comptabible with the desired behavior)
* Textures based on the `longStick`
* They will be based on various `Materials` such as carbon, platinum *etc.*
* Such as rotorTurbines, electrodes would take damage when used in a recipe (See **[RotorTurbineBehavior](https://github.com/GregTechCEu/GregTech/blob/master/src/main/java/gregtech/common/items/behaviors/TurbineRotorBehavior.java)**)

### The new machine

* `RecipeBuilder` would like this:
```
.notConsummable(dust, Material)
.inputs(electrode, MaterialAnode, electrode, MaterialCathode)
.fluidInputs(Water.getFluid())
.duration()
.EUt()
.outputs(dust, MaterialWaste)
.fluidOutputs(Hydrogen.getFluid(), Oxygen.getFluid())
```
* if it is viable performance wise, the `fluidOutputs` would occur `perTick`
* Textures would be the same as the GTCEu ones

## Foil making overhaul

### Addition

* The Cluster Mill machine, present in both **[GT6](https://github.com/GregTech6/gregtech6)** and **[GTCYL](https://github.com/GregTechCEu/gregicality-legacy)**.
* `RecipeBuilder` would like this:
```
.inputs(plate, Material)
.duration()
.EUt()
.outputs(foil, Material)
```
* Textures would be the same as the Wiremill

### Modification

* `processFoil` would use `CLUSTER_MILL_RECIPES` instead of `BLENDER_RECIPES` (See the **[PartsRecipeHandler class](https://github.com/GregTechCEu/GregTech/blob/37864035c16e838ea7d38b40ee7a487a01f52d97/src/main/java/gregtech/loaders/recipe/handlers/PartsRecipeHandler.java#L106-L137)**)

## Materials processing chains

### Inorganic chemistry

* Al (based on either **[GT6](https://github.com/GregTech6/gregtech6)** or **[GTCYL](https://github.com/GregTechCEu/gregicality-legacy)**)
* Au (based on **[GTCYL](https://github.com/GregTechCEu/gregicality-legacy)**)
* Cr (based on **[GT6u](https://github.com/GregTech6-Unofficial/GregTech6-Unofficial)**)

### Organic Chemistry

#### Natural compounds

* Quinine
* Quinidine
* Cinchonine
* Cinchonidine
* Cinnamic acid

#### Advanced compounds

#### Chiral scaffolds

* DACH
* BINOL

#### Chiral ligands

#### Chiral catalysts


## Parts processing overhaul

Each processing machine such as the Blender, the Cluster Mill *etc.* would require a special **part** in their `inputs`. This **part**, as `electrodes`, would take damage for each recipe, but would still be much more resistant than `tools` that are used in hand-crafting recipes. This module would thus requires:
* addition of a specific **part** for each machine
* textures based on **[GT6](https://github.com/GregTech6/gregtech6)**'s
* ~~addition of a new `OrePrefix` (See the  **[OrePrefix class](https://github.com/GregTechCEu/GregTech/blob/master/src/main/java/gregtech/api/unification/ore/OrePrefix.java)**)~~ (Might not be comptabible with the desired behavior)
* They will be based on various `Materials` such as bronze, steel *etc.*
* Such as rotorTurbines, electrodes would take damage when used in a recipe (See **[RotorTurbineBehavior](https://github.com/GregTechCEu/GregTech/blob/master/src/main/java/gregtech/common/items/behaviors/TurbineRotorBehavior.java)**)
