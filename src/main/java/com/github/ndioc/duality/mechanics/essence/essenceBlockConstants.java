package com.github.ndioc.duality.mechanics.essence;

public enum essenceBlockConstants {

    TESTORB(64000, 3, new int[] {-1, 0, 1} );

    private final int AmountPerContainer;
    private final int NumberOfContainers;
    private final int[] AllowedEssenceTypes;

    essenceBlockConstants(

        final int AmountPerContainer,
        final int NumberOfContainers,
        final int[] AllowedEssenceTypes

    ) {

        this.AmountPerContainer = AmountPerContainer;
        this.NumberOfContainers = NumberOfContainers;
        this.AllowedEssenceTypes = AllowedEssenceTypes;
    }

    public int getAmountPerContainer() {
        return this.AmountPerContainer;
    }

    public int getNumberOfContainers() {
        return this.NumberOfContainers;
    }

    public int[] getAllowedEssenceTypes() {
        return this.AllowedEssenceTypes;
    }

}
