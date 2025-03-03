package com.github.ndioc.duality.mechanics.essence;
public abstract class essenceBlockConstants {
    public enum essenceContainers {

        TESTORB(64000, 320, 3, 0, new int[]{-1, 0, 1}),
        CREATIVEORB(256000, 1280, 5, 1, new int[]{-1,0,1});

        private final int volumePerContainer; // Highest Quantity allowed per 'essence' object
        private final int maxTransferPerSecond; // Quantity of essence allowed to be transferred per second
        private final int NumberOfContainers; // Num of essence objects allowed to exist in block
        private final int Unlimited; // should the container have unlimited essence? 1 = true: 0 = false;
        private final int[] AllowedEssenceTypes; // array of allowed types on essence objects (essenceTYPE.NumericalID)

        essenceContainers(

            final int volumePerContainer,
            final int maxTransferPerSecond,
            final int NumberOfContainers,
            final int Unlimited,
            final int[] AllowedEssenceTypes

        ) {

            this.volumePerContainer = volumePerContainer;
            this.maxTransferPerSecond = maxTransferPerSecond;
            this.NumberOfContainers = NumberOfContainers;
            this.Unlimited = Unlimited;
            this.AllowedEssenceTypes = AllowedEssenceTypes;
        }

        public int getVolumePerContainer() {
            return this.volumePerContainer;
        }

        public int getMaxTransferPerSecond() {
            return this.maxTransferPerSecond;
        }

        public int getNumberOfContainers() {
            return this.NumberOfContainers;
        }

        public int getUnlimited() {
            return this.Unlimited;
        }

        public int[] getAllowedEssenceTypes() {
            return this.AllowedEssenceTypes;
        }

        public int getHighestContainerCount() {
            return essenceType.values().length;
        }

    }

    public enum essenceTransporters {

        TESTRELAY(60, 5, 1, 1, 2, 3),
        LONGRANGETESTRELAY(400,40, 1, 2, 3, 5),
        YEETERTEST(3200,300, 2, 5, 1, 1);

        private final int amountPerTransfer; //essence quantity moved per transfer operation
        private final int ticksBetweenTransfers;//delay between transfer operations in ticks

        private final int travelSpeedMult; // multiplier of travelSpeed from essenceType
        private final int distanceBeforeLossMult; //same as above but for distanceBeforeLoss

        private final int maxSources;
        private final int maxDestinations;

        essenceTransporters(
            final int amountPerTransfer,
            final int ticksBetweenTransfers,

            final int travelSpeedMult,
            final int distanceBeforeLossMult,

            final int maxSources,
            final int maxDestinations
        ) {

            this.amountPerTransfer = amountPerTransfer;
            this.ticksBetweenTransfers = ticksBetweenTransfers;

            this.travelSpeedMult = travelSpeedMult;
            this.distanceBeforeLossMult = distanceBeforeLossMult;

            this.maxSources = maxSources;
            this.maxDestinations = maxDestinations;
        }

        public int getAmountPerTransfer() {
            return this.amountPerTransfer;
        }

        public int getTicksBetweenTransfers() {
            return this.ticksBetweenTransfers;
        }

        public int getTravelSpeedMult() {
            return this.travelSpeedMult;
        }

        public int getDistanceBeforeLossMult() {
            return this.distanceBeforeLossMult;
        }

        public int getMaxSources() {
            return this.maxSources;
        }

        public int getMaxDestinations() {
            return this.maxDestinations;
        }
    }

    public static int[][] fetchConstants(String translationKey) {
        int[] array1 = new int[6];
        int[] array2 = null;
        switch(translationKey) {
            case "block.duality.test_orb":
                array1[0] = essenceContainers.TESTORB.volumePerContainer;
                array1[1] = essenceContainers.TESTORB.maxTransferPerSecond;
                array1[2] = essenceContainers.TESTORB.NumberOfContainers;
                array1[3] = essenceContainers.TESTORB.Unlimited;
                array2 = essenceContainers.TESTORB.AllowedEssenceTypes;
                break;
            case "block.duality.test_relay":
                array1[0] = essenceTransporters.TESTRELAY.amountPerTransfer;
                array1[1] = essenceTransporters.TESTRELAY.ticksBetweenTransfers;
                array1[2] = essenceTransporters.TESTRELAY.travelSpeedMult;
                array1[3] = essenceTransporters.TESTRELAY.distanceBeforeLossMult;
                array1[4] = essenceTransporters.TESTRELAY.maxSources;
                array1[5] = essenceTransporters.TESTRELAY.maxDestinations;
                break;
            case "block.duality.creative_orb":
                array1[0] = essenceContainers.CREATIVEORB.volumePerContainer;
                array1[1] = essenceContainers.CREATIVEORB.maxTransferPerSecond;
                array1[2] = essenceContainers.CREATIVEORB.NumberOfContainers;
                array1[3] = essenceContainers.CREATIVEORB.Unlimited;
                array2 = essenceContainers.CREATIVEORB.AllowedEssenceTypes;
                break;
            default:
                return null;
        }
        return new int[][]{array1,array2};
    }

}