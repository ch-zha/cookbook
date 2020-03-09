package com.cookbook.data.api;

import android.util.Log;

import com.cookbook.data.entity.MeasurementUnit;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiRecipeGsonAdapter extends TypeAdapter<ApiRecipe> {

    // Note: no aliases for "Whole" since it is considered default
    // First element should always be String version of enum
    private static String[] GRAM_ALIASES = new String[] {
            MeasurementUnit.getMeasurementUnitString(MeasurementUnit.Gram),
            "g", "gram"};
    private static String[] TBSP_ALIASES = new String[] {
            MeasurementUnit.getMeasurementUnitString(MeasurementUnit.TBSP),
            "tbsp", "tablespoon", "tbls"};
    private static String[] TSP_ALIASES = new String[] {
            MeasurementUnit.getMeasurementUnitString(MeasurementUnit.TSP),
            "tsp", "teaspoon"};
    private static String[] CUP_ALIASES = new String[] {
            MeasurementUnit.getMeasurementUnitString(MeasurementUnit.Cup),
            "cup"};
    private static String[] LB_ALIASES = new String[] {
            MeasurementUnit.getMeasurementUnitString(MeasurementUnit.lb),
            "lb", "pound"};
    private static String[] mL_ALIASES = new String[] {
            MeasurementUnit.getMeasurementUnitString(MeasurementUnit.mL),
            "ml", "milliliter"};
    private static String[] PINCH_ALIASES = new String[] {
            MeasurementUnit.getMeasurementUnitString(MeasurementUnit.Pinch),
            "pinch", "dash", "to taste", "dusting"};

    private static String[] aliases;

    public ApiRecipeGsonAdapter() {
        super();
        if (aliases == null) {
            aliases = buildAliases();
        }
    }

    @Override
    public void write(JsonWriter out, ApiRecipe value) throws IOException {

        // Empty since we don't need to serialize

    }

    @Override
    public ApiRecipe read(JsonReader in) throws IOException {

        // Enter result body
        in.beginObject();
        // "meals" array name
        in.nextName();
        // Enter meals array
        in.beginArray();
        // Enter first result object
        in.beginObject();

        ApiRecipe recipe = new ApiRecipe();

        String fieldname = null;

        while (in.hasNext()) {

            JsonToken token = in.peek();

            if (token.equals(JsonToken.NAME)) {
                //get the current token
                fieldname = in.nextName();
            }

            if ("strMeal".equals(fieldname)) {
                recipe.setMealName(in.nextString());
            }

            else if ("strInstructions".equals(fieldname)) {
                String rawInstructions = in.nextString();
                String[] processedInstructions = rawInstructions.split("\r\n");
                recipe.addSteps(Arrays.asList(processedInstructions));
            }

            else if ("strMealThumb".equals(fieldname)) {
                recipe.addImageUrl(in.nextString());
            }

            else if (fieldname.matches("strIngredient\\d+")) {
                String ingredient = in.nextString();
                if (!ingredient.isEmpty())
                    recipe.addIngredient(ingredient);
            }

            else if (fieldname.matches("strMeasure\\d+")) {
                String rawMeasure = in.nextString();
                if (!rawMeasure.isEmpty()) {

                    //Parse quantity
                    double quantity = -1.;
                    List<Double> quantities = parseNumber(rawMeasure);
                    if (quantities.size() > 0) {
                        quantity = quantities.get(0);
                    }
                    if (quantities.size() != 1) {
                        recipe.setUnreadableFields(true);
                    }
                    recipe.addQuantity(quantity);

                    //Parse unit
                    MeasurementUnit unit = parseUnit(rawMeasure);
                    if (unit.equals(MeasurementUnit.Whole))
                        recipe.setUnreadableFields(true);
                    recipe.addUnit(unit);

                }
            }

            //TODO add original recipe source as link and display it somewhere

            else {
                in.skipValue();
            }

        }

        in.endObject();
        in.endArray();
        in.endObject();

        return recipe;

    }

    List<Double> parseNumber(String string) {

        List<Double> quantities = new ArrayList<>();

        // Turns fraction characters (e.g. ½) into normal numbers
        string = Normalizer.normalize(string, Normalizer.Form.NFKD);
        //TODO this will make proper fractions involving vulgar fraction characters (eg 6½) act weird. Fix later
        Pattern pattern = Pattern.compile("[\\d.⁄/]*");
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            try {
                double quantity;
                if (matcher.group(0).contains("⁄") || matcher.group(0).contains("/")) {
                    String[] parts = matcher.group(0).split("[/⁄]");
                    quantity = Double.valueOf(parts[0])/Double.valueOf(parts[1]);
                } else {
                    quantity = Double.valueOf(matcher.group(0));
                }
                quantities.add(quantity);
            } catch (NumberFormatException e) {
                Log.e("Parse Json Quantity", "Unreadable number string: " + matcher.group(0));
            }
        }

        return quantities;

    }

    MeasurementUnit parseUnit(String string) {

        MeasurementUnit unit = MeasurementUnit.Whole;

        boolean found = false;
        for (String alias : ApiRecipeGsonAdapter.aliases) {
            Pattern pattern = Pattern.compile(alias, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(string);
            if (matcher.find()) {
                String result = alias;
                // Uses first part of regex to get unit so it is not vulnerable to
                // changes in ordering. But seems inefficient. Fix later?
                unit = MeasurementUnit.getMeasurementUnit(alias.split("[|]")[0]);
            }
        }

        return unit;

    }

    private static String[] buildAliases() {

        // I hate this. Remove string concat & make more concise later

        String gram = "";
        for (String alias : GRAM_ALIASES) {
            gram += alias + "|";
        }
        gram = gram.substring(0, gram.length()-1);

        String tbsp = "";
        for (String alias : TBSP_ALIASES) {
            tbsp += alias + "|";
        }
        tbsp = tbsp.substring(0, tbsp.length()-1);

        String tsp = "";
        for (String alias : TSP_ALIASES) {
            tsp += alias + "|";
        }
        tsp = tsp.substring(0, tsp.length()-1);

        String cup = "";
        for (String alias : CUP_ALIASES) {
            cup += alias + "|";
        }
        cup = cup.substring(0, cup.length()-1);

        String lb = "";
        for (String alias : LB_ALIASES) {
            lb += alias + "|";
        }
        lb = lb.substring(0, lb.length()-1);

        String ml = "";
        for (String alias : mL_ALIASES) {
            ml += alias + "|";
        }
        ml = ml.substring(0, ml.length()-1);

        String pinch = "";
        for (String alias : PINCH_ALIASES) {
            pinch += alias + "|";
        }
        pinch = pinch.substring(0, pinch.length()-1);

        return new String[] { gram, tbsp, tsp, cup, lb, ml, pinch };
    }


}
