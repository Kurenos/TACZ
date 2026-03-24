package com.tacz.guns.api.item.attachment;

import java.util.*;
import java.util.stream.Collectors;

public enum UniversalAttachmentType {
    SCOPE(AttachmentType.SCOPE, -1),
    ATTACHMENT_1(AttachmentType.MUZZLE, 0),
    ATTACHMENT_2(AttachmentType.STOCK, 3),
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

    public static List<UniversalAttachmentType> getUnlockedTypes(int weaponLevel) {
        List<UniversalAttachmentType> unlocked = new ArrayList<>();
        for (UniversalAttachmentType slot : UniversalAttachmentType.values()) {
            if (slot.getRequiredLevel() >= 0 && slot.isUnlocked(weaponLevel)) {
                unlocked.add(slot);
            }
        }
        return unlocked;
    }

    public static List<UniversalAttachmentType> getVisible() {
        return LOOKUP.values().stream()
                .filter(slot -> slot.getRequiredLevel() >= 0)
                .sorted(Comparator.comparingInt(UniversalAttachmentType::getRequiredLevel))
                .collect(Collectors.toList());
    }

    public boolean isUnlocked(int weaponLevel) {
        return weaponLevel >= getRequiredLevel();
    }
}
