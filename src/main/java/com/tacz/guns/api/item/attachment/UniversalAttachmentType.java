package com.tacz.guns.api.item.attachment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum UniversalAttachmentType {
    SCOPE(AttachmentType.SCOPE, -1),
    ATTACHMENT_1(AttachmentType.MUZZLE, 2),
    ATTACHMENT_2(AttachmentType.STOCK, 4),
    ATTACHMENT_3(AttachmentType.GRIP, 6),
    ATTACHMENT_4(AttachmentType.LASER, 8),
    ATTACHMENT_5(AttachmentType.EXTENDED_MAG, 10);

    private final AttachmentType mappedType;
    private final int requiredLevel;

    private static final Map<AttachmentType, UniversalAttachmentType> LOOKUP = new HashMap<>();

    static {
        for (UniversalAttachmentType type : values()) {
            LOOKUP.put(type.mappedType, type);
        }
    }

    UniversalAttachmentType(AttachmentType mappedType, int requiredLevel) {
        this.mappedType = mappedType;
        this.requiredLevel = requiredLevel;
    }

    public AttachmentType getMappedType() {
        return mappedType;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public static UniversalAttachmentType fromAttachment(AttachmentType attachmentType) {
        return LOOKUP.get(attachmentType);
    }

    public static AttachmentType toAttachment(UniversalAttachmentType universalType) {
        return universalType.getMappedType();
    }

    public static List<AttachmentType> getUnlockedTypes(int weaponLevel) {
        List<AttachmentType> unlocked = new ArrayList<>();
        for (UniversalAttachmentType slot : UniversalAttachmentType.values()) {
            if (slot.getRequiredLevel() >= 0 && slot.isUnlocked(weaponLevel)) {
                unlocked.add(slot.getMappedType());
            }
        }
        return unlocked;
    }

    public boolean isUnlocked(int weaponLevel) {
        return weaponLevel >= getRequiredLevel();
    }
}
