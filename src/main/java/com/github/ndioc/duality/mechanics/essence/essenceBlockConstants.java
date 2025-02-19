package com.github.ndioc.duality.mechanics.essence;
public abstract class essenceBlockConstants {
    public enum essenceContainers {

        TESTORB(64000, 320, 3, new int[]{-1, 1, 10}),
        CREATIVEORB(256000, 1280, 5, new int[]{-1,1,10});

        private final int volumePerContainer; // Highest Quantity allowed per 'essence' object
        private final int maxTransferPerSecond; // Quantity of essence allowed to be transferred per second
        private final int NumberOfContainers; // Num of essence objects allowed to exist in block
        private final int[] AllowedEssenceTypes; // array of allowed types on essence objects (essenceTYPE.NumericalID)

        essenceContainers(

            final int volumePerContainer,
            final int maxTransferPerSecond,
            final int NumberOfContainers,
            final int[] AllowedEssenceTypes

        ) {

            this.volumePerContainer = volumePerContainer;
            this.maxTransferPerSecond = maxTransferPerSecond;
            this.NumberOfContainers = NumberOfContainers;
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

        public int[] getAllowedEssenceTypes() {
            return this.AllowedEssenceTypes;
        }

    }

    public enum essenceTransporters {

        TESTRELAY(60, 5, 1, 1),
        LONGRANGETESTRELAY(400,40, 1, 2),
        YEETERTEST(3200,300, 2, 5);

        private final int amountPerTransfer; //essence quantity moved per transfer operation
        private final int ticksBetweenTransfers; //delay between transfer operations in ticks

        private final int travelSpeedMult; // multiplier of travelSpeed from essenceType
        private final int distanceBeforeLossMult; //same as above but for distanceBeforeLoss

        essenceTransporters(
            final int amountPerTransfer,
            final int ticksBetweenTransfers,

            final int travelSpeedMult,
            final int distanceBeforeLossMult
        ) {

            this.amountPerTransfer = amountPerTransfer;
            this.ticksBetweenTransfers = ticksBetweenTransfers;

            this.travelSpeedMult = travelSpeedMult;
            this.distanceBeforeLossMult = distanceBeforeLossMult;
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
    }

    public static int[][] fetchConstants(String blockName) {
        int[] array1 = new int[4];
        int[] array2 = new int[1];
        switch(blockName) {
            case "block.duality.test_orb":
                array1[0] = essenceContainers.TESTORB.volumePerContainer;
                array1[1] = essenceContainers.TESTORB.maxTransferPerSecond;
                array1[2] = essenceContainers.TESTORB.NumberOfContainers;
                array2 = essenceContainers.TESTORB.AllowedEssenceTypes;
                break;
            case "block.duality.test_relay":
                array1[0] = essenceTransporters.TESTRELAY.amountPerTransfer;
                array1[1] = essenceTransporters.TESTRELAY.ticksBetweenTransfers;
                array1[2] = essenceTransporters.TESTRELAY.travelSpeedMult;
                array1[3] = essenceTransporters.TESTRELAY.distanceBeforeLossMult;
                break;
            case "block.duality.creative_orb":
                array1[0] = essenceContainers.CREATIVEORB.volumePerContainer;
                array1[1] = essenceContainers.CREATIVEORB.maxTransferPerSecond;
                array1[2] = essenceContainers.CREATIVEORB.NumberOfContainers;
                array2 = essenceContainers.CREATIVEORB.AllowedEssenceTypes;
                break;
            default:
                return null;
        }
        return new int[][]{array1,array2};
    }

}