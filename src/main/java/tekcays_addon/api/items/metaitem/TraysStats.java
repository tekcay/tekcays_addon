package tekcays_addon.api.items.metaitem;

import gregtech.api.items.metaitem.stats.IItemComponent;
import gregtech.api.unification.material.Material;

public class TraysStats implements IItemComponent {

    private final Material material;
    private final int thickness;
    private final int bubbleCapNumber;

    public TraysStats(Material material, int thickness, int bubbleCapNumber) {
        this.material = material;
        this.thickness = thickness;
        this.bubbleCapNumber = bubbleCapNumber;
    }

    public Material getMaterial() {
        return material;
    }

    public int getThickness() {
        return thickness;
    }

    public int getBubbleCapNumber() {
        return bubbleCapNumber;
    }

}
