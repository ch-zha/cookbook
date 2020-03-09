package com.cookbook.data.entity;

import androidx.room.TypeConverter;

public enum MeasurementUnit {
    Whole,
    Gram,
    TBSP,
    TSP,
    Cup,
    lb,
    mL,
    Pinch;

    private static String[] ALL_UNITS_AS_STRING;

    public static String[] getAllUnitsAsString() {
        if (ALL_UNITS_AS_STRING == null) {
            ALL_UNITS_AS_STRING = new String[] {
                    getMeasurementUnitString(MeasurementUnit.Whole),
                    getMeasurementUnitString(MeasurementUnit.Gram),
                    getMeasurementUnitString(MeasurementUnit.TBSP),
                    getMeasurementUnitString(MeasurementUnit.TSP),
                    getMeasurementUnitString(MeasurementUnit.Cup),
                    getMeasurementUnitString(MeasurementUnit.lb),
                    getMeasurementUnitString(MeasurementUnit.mL),
                    getMeasurementUnitString(MeasurementUnit.Pinch)
            };
        }
        return ALL_UNITS_AS_STRING;
    }

    @TypeConverter
    public static MeasurementUnit getMeasurementUnit(String unit) {
        switch (unit) {
            case "Whole":
                return Whole;
            case "Gram":
                return Gram;
            case "TBSP":
                return TBSP;
            case "TSP":
                return TSP;
            case "Cup":
                return Cup;
            case "lb":
                return lb;
            case "mL":
                return mL;
            case "Pinch":
                return Pinch;
            default:
                return null;
        }
    }

    @TypeConverter
    public static String getMeasurementUnitString (MeasurementUnit unit) {

        switch (unit) {
            case Whole:
                return "Whole";
            case Gram:
                return "Gram";
            case TBSP:
                return "TBSP";
            case TSP:
                return "TSP";
            case Cup:
                return "Cup";
            case lb:
                return "lb";
            case mL:
                return "mL";
            case Pinch:
                return "Pinch";
            default:
                return null;
        }

    }
}
