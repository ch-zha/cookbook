package com.cookbook.data.api;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cookbook.data.entity.MeasurementUnit;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class GsonAdapterTest {

    private ApiRecipeGsonAdapter setup() {
        return new ApiRecipeGsonAdapter();
    }

    @Test
    public void NumberParseShouldReturnCorrectValues() {

        ApiRecipeGsonAdapter adapter = setup();
        List<Double> list = adapter.parseNumber("1/2 .35 1025 3/8");
        double[] answers = new double[] {.5, .35, 1025., .375};
        for (int i = 0; i < list.size(); i++) {
            assertEquals(answers[i], list.get(i), 0.01);
        }

    }

    @Test
    public void NumberParseShouldUnderstandVulgarFractions() {

        ApiRecipeGsonAdapter adapter = setup();
        List<Double> list = adapter.parseNumber("½ ¼");
        double[] answers = new double[] {.5, .25};
        for (int i = 0; i < list.size(); i++) {
            assertEquals(answers[i], list.get(i), 0.01);
        }

    }

    @Test
    public void UnitParseShouldReturnCorrectValues() {

        ApiRecipeGsonAdapter adapter = setup();
        assertEquals(MeasurementUnit.Whole, adapter.parseUnit(" 50 cans"));
        assertEquals(MeasurementUnit.TBSP, adapter.parseUnit("1 tablespoon"));
        assertEquals(MeasurementUnit.Gram, adapter.parseUnit("700g"));
        assertEquals(MeasurementUnit.Cup, adapter.parseUnit("3 cups of water"));
    }

}
