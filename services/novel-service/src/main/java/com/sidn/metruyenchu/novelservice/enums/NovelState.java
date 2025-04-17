package com.sidn.metruyenchu.novelservice.enums;

public enum NovelState {
    CREATED, PENDING, SUSPENDED, REVIEWING, PUBLISHED, REJECTED, DELETED;

    public static NovelState fromString(String state) {
        for (NovelState novelState : NovelState.values()) {
            if (novelState.name().equalsIgnoreCase(state)) {
                return novelState;
            }
        }
        throw new IllegalArgumentException("No enum constant " + NovelState.class.getCanonicalName() + "." + state);
    }


}
