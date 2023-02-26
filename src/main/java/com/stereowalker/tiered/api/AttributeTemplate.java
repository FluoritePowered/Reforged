package com.stereowalker.tiered.api;

import java.util.UUID;

import com.google.common.collect.Multimap;
import com.google.gson.annotations.SerializedName;
import com.stereowalker.tiered.Tiered;
import com.stereowalker.unionlib.world.entity.AccessorySlot;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

/**
 * Stores information on an AttributeModifier template applied to an ItemStack.
 *
 * The ID of the AttributeTemplate is the logical ID used to determine what "type" of attribute of is.
 * An AttributeModifier has:
 *   - a UUID, which is a unique identifier to separate different attributes of the same type
 *   - a name, which is used for generating a non-specified UUID and displaying in tooltips in some context
 *   - an amount, which is used in combination with the operation to modify the final relevant value
 *   - a modifier, which can be something such as addition or subtraction
 *
 * The EquipmentSlot is used to only apply this template to certain items.
 */
public class AttributeTemplate {

    @SerializedName("type")
    private final String attributeTypeID;

    @SerializedName("modifier")
    private final AttributeModifier attributeModifier;

    @SerializedName("required_equipment_slots")
    private final EquipmentSlot[] requiredEquipmentSlotTypes;

    @SerializedName("optional_equipment_slots")
    private final EquipmentSlot[] optionalEquipmentSlotTypes;

    @SerializedName("required_accessory_slots")
    private final AccessorySlot[] requiredAccessorySlotTypes;

    @SerializedName("optional_accessory_slots")
    private final AccessorySlot[] optionalAccessorySlotTypes;

    @SerializedName("required_accessory_groups")
    private final AccessorySlot.Group[] requiredAccessoryGroupTypes;

    @SerializedName("optional_accessory_groups")
    private final AccessorySlot.Group[] optionalAccessoryGroupTypes;

    @SerializedName("required_curio_slots")
    private final String[] requiredCurioSlotTypes;

    @SerializedName("optional_curio_slots")
    private final String[] optionalCurioSlotTypes;

    public AttributeTemplate(String attributeTypeID, AttributeModifier AttributeModifier, 
    		EquipmentSlot[]  requiredEquipmentSlotTypes, EquipmentSlot[]  optionalEquipmentSlotTypes, 
    		AccessorySlot[] requiredAccessorySlotTypes, AccessorySlot[] optionalAccessorySlotTypes, 
    		AccessorySlot.Group[] requiredAccessoryGroupTypes, AccessorySlot.Group[] optionalAccessoryGroupTypes, 
    		String[] requiredCurioSlotTypes, String[] optionalCurioSlotTypes) {
        this.attributeTypeID = attributeTypeID;
        this.attributeModifier = AttributeModifier;
        this.requiredEquipmentSlotTypes = requiredEquipmentSlotTypes;
        this.optionalEquipmentSlotTypes = optionalEquipmentSlotTypes;
        this.requiredAccessorySlotTypes = requiredAccessorySlotTypes;
        this.optionalAccessorySlotTypes = optionalAccessorySlotTypes;
        this.requiredAccessoryGroupTypes = requiredAccessoryGroupTypes;
        this.optionalAccessoryGroupTypes = optionalAccessoryGroupTypes;
        this.requiredCurioSlotTypes = requiredCurioSlotTypes;
        this.optionalCurioSlotTypes = optionalCurioSlotTypes;
    }

    public EquipmentSlot[]  getRequiredEquipmentSlot() {
        return requiredEquipmentSlotTypes;
    }

    public EquipmentSlot[]  getOptionalEquipmentSlot() {
        return optionalEquipmentSlotTypes;
    }

    public AccessorySlot[] getRequiredAccessorySlot() {
		return requiredAccessorySlotTypes;
	}

	public AccessorySlot[] getOptionalAccessorySlot() {
		return optionalAccessorySlotTypes;
	}

	public AccessorySlot.Group[] getRequiredAccessoryGroup() {
		return requiredAccessoryGroupTypes;
	}

	public AccessorySlot.Group[] getOptionalAccessoryGroup() {
		return optionalAccessoryGroupTypes;
	}

	public String[]  getRequiredCurioSlot() {
        return requiredCurioSlotTypes;
    }

    public String[]  getOptionalCurioSlot() {
        return optionalCurioSlotTypes;
    }

    /**
     * Uses this {@link AttributeTemplate} to create an {@link AttributeModifier}, which is placed into the given {@link Multimap}.
     * <p>Note that this method assumes the given {@link Multimap} is mutable.
     *
     * @param multimap  map to add {@link AttributeTemplate}
     * @param slot
     */
    public void realize(Multimap<Attribute, AttributeModifier> multimap, EquipmentSlot slot) {
        realize(multimap, Tiered.MODIFIERS[slot.getFilterFlag()], slot.getName());
    }

    /**
     * Uses this {@link AttributeTemplate} to create an {@link AttributeModifier}, which is placed into the given {@link Multimap}.
     * <p>Note that this method assumes the given {@link Multimap} is mutable.
     *
     * @param multimap  map to add {@link AttributeTemplate}
     * @param slot
     */
    public void realize(Multimap<Attribute, AttributeModifier> multimap, AccessorySlot slot) {
        realize(multimap, Tiered.MODIFIERS[slot.getIndex()+6], slot.getName());
    }

    /**
     * Uses this {@link AttributeTemplate} to create an {@link AttributeModifier}, which is placed into the given {@link Multimap}.
     * <p>Note that this method assumes the given {@link Multimap} is mutable.
     *
     * @param multimap  map to add {@link AttributeTemplate}
     * @param slot
     */
    public void realize(Multimap<Attribute, AttributeModifier> multimap, AccessorySlot.Group slot) {
        realize(multimap, Tiered.MODIFIERS[slot.ordinal()+15], slot.getName());
    }

    /**
     * Uses this {@link AttributeTemplate} to create an {@link AttributeModifier}, which is placed into the given {@link Multimap}.
     * <p>Note that this method assumes the given {@link Multimap} is mutable.
     *
     * @param multimap  map to add {@link AttributeTemplate}
     * @param slot
     */
    public void realize(Multimap<Attribute, AttributeModifier> multimap, String slot) {
        realize(multimap, Tiered.CURIO_MODIFIERS.getOrDefault(slot, UUID.fromString("fee48d8c-1b51-4c46-9f4b-c58162623a7c")), slot);
    }

    /**
     * Uses this {@link AttributeTemplate} to create an {@link AttributeModifier}, which is placed into the given {@link Multimap}.
     * <p>Note that this method assumes the given {@link Multimap} is mutable.
     *
     * @param multimap  map to add {@link AttributeTemplate}
     * @param slot
     */
    private void realize(Multimap<Attribute, AttributeModifier> multimap, UUID id, String name) {
        AttributeModifier cloneModifier = new AttributeModifier(
                id,
                attributeModifier.getName() + "_" + name,
                attributeModifier.getAmount(),
                attributeModifier.getOperation()
        );

        Attribute key = BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation(attributeTypeID));
        if(key == null) {
            Tiered.LOGGER.warn(String.format("%s was referenced as an attribute type, but it does not exist! A data file in /tiered/item_attributes/ has an invalid type property.", attributeTypeID));
        } else {
            multimap.put(key, cloneModifier);
        }
    }
}
