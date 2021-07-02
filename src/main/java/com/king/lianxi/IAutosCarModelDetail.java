package com.king.lianxi;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 一车网车型详细信息
 */
@NoArgsConstructor
@Data
public class IAutosCarModelDetail {
    /**
     * basic : {"country":"德国","emission":"","modelID":"62939"}
     * body : {"doors":"3","frontTrack":"1389","fuelTank":"0","length":"3820","maximumTrunk":"0","minimumGroundClearance":"124.0000","rearTrack":"1359","seating":"4","trunk":"345","weight":"800","wheelBase":"2400","width":"1610"}
     * chassis : {"driveHubDiameter":"13","driveTireHeightAspectRatio":"0","driveTireWidth":"145","drivingMethod":"前驱","fWDMethod":"","frontBrake":"实心盘","frontSuspension":"","frontTireSize":"155 R13","powerSteering":"","rearBrake":"毂式","rearSuspension":"","rearTireSize":"145 R13","spareTire":"","transmission":"4档 手动"}
     * engine : {"airIntake":"自然进气","compression":"0.0000","cylinderBlock":"","cylinderCover":"","cylinderDiameter":"0.0000","cylinderNum":"4","engineModel":"","engineStroke":"0.0000","engineType":"直列","exhaust":"1457","fuelSupplyMode":"化油器","fuelType":"无铅汽油93#","maximumPower":"51(70)/5600","peakTorque":"110/2500","valveNum":"4"}
     */

    private BasicBean basic;
    private BodyBean body;
    private ChassisBean chassis;
    private EngineBean engine;

    @NoArgsConstructor
    @Data
    public static class BasicBean {
        /**
         * country : 德国
         * emission :
         * modelID : 62939
         */

        private String country;
        private String emission;
        private String modelID;
    }

    @NoArgsConstructor
    @Data
    public static class BodyBean {
        /**
         * doors : 3
         * frontTrack : 1389
         * fuelTank : 0
         * length : 3820
         * maximumTrunk : 0
         * minimumGroundClearance : 124.0000
         * rearTrack : 1359
         * seating : 4
         * trunk : 345
         * weight : 800
         * wheelBase : 2400
         * width : 1610
         */

        private String doors;
        private String frontTrack;
        private String fuelTank;
        private String length;
        private String maximumTrunk;
        private String minimumGroundClearance;
        private String rearTrack;
        private String seating;
        private String trunk;
        private String weight;
        private String wheelBase;
        private String width;
    }

    @NoArgsConstructor
    @Data
    public static class ChassisBean {
        /**
         * driveHubDiameter : 13
         * driveTireHeightAspectRatio : 0
         * driveTireWidth : 145
         * drivingMethod : 前驱
         * fWDMethod :
         * frontBrake : 实心盘
         * frontSuspension :
         * frontTireSize : 155 R13
         * powerSteering :
         * rearBrake : 毂式
         * rearSuspension :
         * rearTireSize : 145 R13
         * spareTire :
         * transmission : 4档 手动
         */

        private String driveHubDiameter;
        private String driveTireHeightAspectRatio;
        private String driveTireWidth;
        private String drivingMethod;
        private String fWDMethod;
        private String frontBrake;
        private String frontSuspension;
        private String frontTireSize;
        private String powerSteering;
        private String rearBrake;
        private String rearSuspension;
        private String rearTireSize;
        private String spareTire;
        private String transmission;
    }

    @NoArgsConstructor
    @Data
    public static class EngineBean {
        /**
         * airIntake : 自然进气
         * compression : 0.0000
         * cylinderBlock :
         * cylinderCover :
         * cylinderDiameter : 0.0000
         * cylinderNum : 4
         * engineModel :
         * engineStroke : 0.0000
         * engineType : 直列
         * exhaust : 1457
         * fuelSupplyMode : 化油器
         * fuelType : 无铅汽油93#
         * maximumPower : 51(70)/5600
         * peakTorque : 110/2500
         * valveNum : 4
         */

        private String airIntake;
        private String compression;
        private String cylinderBlock;
        private String cylinderCover;
        private String cylinderDiameter;
        private String cylinderNum;
        private String engineModel;
        private String engineStroke;
        private String engineType;
        private String exhaust;
        private String fuelSupplyMode;
        private String fuelType;
        private String maximumPower;
        private String peakTorque;
        private String valveNum;
    }
    /*
    *//**
     * airintake : 测试内容e11y
     * carModelId :
     * compression :
     * country :
     * cylinderBlock :
     * cylinderCover :
     * cylinderDiameter :
     * cylinderNum :
     * doors :
     * driveHubDiameter :
     * driveTireHeightAspectRatio :
     * driveTireWidth :
     * drivingMethod :
     * emission :
     * engineModel :
     * engineStroke :
     * engineType :
     * exhaust :
     * frontBrake :
     * frontSuspension :
     * frontTireSize :
     * frontTrack :
     * fuelSupplyMode :
     * fuelTank :
     * fuelType :
     * fwdMethod :
     * length :
     * maximumPower :
     * maximumTrunk :
     * minimumGroundclearance :
     * peakTorque :
     * powerSteering :
     * rearBrake :
     * rearSuspension :
     * rearTireSize :
     * rearTrack :
     * seating :
     * spareTire :
     * transmission :
     * trunk :
     * valveNum :
     * weight :
     * wheelBase :
     * width :
     *//*

    private String airintake;
    private String carModelId;
    private String compression;
    private String country;
    private String cylinderBlock;
    private String cylinderCover;
    private String cylinderDiameter;
    private String cylinderNum;
    private String doors;
    private String driveHubDiameter;
    private String driveTireHeightAspectRatio;
    private String driveTireWidth;
    private String drivingMethod;
    private String emission;
    private String engineModel;
    private String engineStroke;
    private String engineType;
    private String exhaust;
    private String frontBrake;
    private String frontSuspension;
    private String frontTireSize;
    private String frontTrack;
    private String fuelSupplyMode;
    private String fuelTank;
    private String fuelType;
    private String fwdMethod;
    private String length;
    private String maximumPower;
    private String maximumTrunk;
    private String minimumGroundclearance;
    private String peakTorque;
    private String powerSteering;
    private String rearBrake;
    private String rearSuspension;
    private String rearTireSize;
    private String rearTrack;
    private String seating;
    private String spareTire;
    private String transmission;
    private String trunk;
    private String valveNum;
    private String weight;
    private String wheelBase;
    private String width;*/

}
