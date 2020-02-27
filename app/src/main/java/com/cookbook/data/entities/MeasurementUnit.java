package com.cookbook.data.entities;

import androidx.room.TypeConverter;

public enum MeasurementUnit {
    Whole,
    Gram,
    TBSP;

    @TypeConverter
    public static MeasurementUnit getMeasurementUnit(String unit) {
        switch (unit) {
            case "Whole":
                return Whole;
            case "Gram":
                return Gram;
            case "TBSP":
                return TBSP;
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
            default:
                return null;
        }
    }
}
