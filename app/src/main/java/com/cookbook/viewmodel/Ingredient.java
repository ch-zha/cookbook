package com.cookbook.viewmodel;

public class Ingredient {

    //TODO take this out and use the version in model
    public enum Measurement {
        Whole,
        Gram,
        TBSP
    }

    private String name = null;
    private Measurement measurement = Measurement.Whole;

    public Ingredient(String name) {
        this(name, Measurement.Whole);
    }

    public Ingredient(String name, Measurement measurement) {
        String cleanName = name.toLowerCase().replaceAll("[^A-Za-z0-9 ]","");
        this.name = cleanName.toLowerCase();
        this.measurement = measurement;
    }

    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        String[] nameParts = this.name.split(" ");
        String displayName = "";
        for (String name : nameParts) {
            displayName += name.substring(0, 1).toUpperCase() + name.substring(1) + " ";
        }
        return displayName;
    }

}
