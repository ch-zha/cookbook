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
