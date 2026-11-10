package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;

public class PriceSortingValidationTest {

    public boolean isPriceSortedLowToHigh(List<Double> prices) {
        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) > prices.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPriceSortedHighToLow(List<Double> prices) {
        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) < prices.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testLowToHighSortingValid() {
        List<Double> sortedPrices = Arrays.asList(7.99, 9.99, 15.99, 29.99, 49.99);
        Assert.assertTrue(isPriceSortedLowToHigh(sortedPrices), 
                "Test failed: List should be sorted in ascending order.");
    }

    @Test
    public void testLowToHighSortingInvalid() {
        List<Double> unsortedPrices = Arrays.asList(7.99, 15.99, 9.99, 29.99);
        Assert.assertFalse(isPriceSortedLowToHigh(unsortedPrices), 
                "Test failed: List is not properly sorted in ascending order.");
    }

    @Test
    public void testHighToLowSortingValid() {
        List<Double> sortedPrices = Arrays.asList(49.99, 29.99, 15.99, 9.99, 7.99);
        Assert.assertTrue(isPriceSortedHighToLow(sortedPrices), 
                "Test failed: List should be sorted in descending order.");
    }

    @Test
    public void testHighToLowSortingInvalid() {
        List<Double> unsortedPrices = Arrays.asList(49.99, 15.99, 29.99, 7.99);
        Assert.assertFalse(isPriceSortedHighToLow(unsortedPrices), 
                "Test failed: List is not properly sorted in descending order.");
    }
}