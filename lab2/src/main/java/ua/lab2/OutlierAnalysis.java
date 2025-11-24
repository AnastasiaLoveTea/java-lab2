// file: src/main/java/lab2/OutlierAnalysis.java
package ua.lab2;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Клас для аналізу викидів у значеннях сили атаки (Поле Г).
 * Рахує:
 *  - Q1, Q3, IQR
 *  - межі для викидів
 *  - повертає {"data": ..., "outliers": ...}
 */
public class OutlierAnalysis {

    /**
     * Аналізує силу атаки у списку істот та повертає кількість
     * нормальних значень ("data") та викидів ("outliers").
     */
    public static Map<String, Long> analyzeAttackPower(List<ChthonicCreature> creatures) {
        if (creatures.isEmpty()) {
            return Map.of(
                    "data", 0L,
                    "outliers", 0L
            );
        }

        List<Double> sortedValues = creatures.stream()
                .map(ChthonicCreature::getAttackPower)
                .sorted()
                .collect(Collectors.toList());

        double q1 = quantile(sortedValues, 0.25);
        double q3 = quantile(sortedValues, 0.75);
        double iqr = q3 - q1;

        double lower = q1 - 1.5 * iqr;
        double upper = q3 + 1.5 * iqr;

        Map<String, Long> raw = creatures.stream()
                .collect(Collectors.groupingBy(
                        c -> {
                            double v = c.getAttackPower();
                            return (v < lower || v > upper) ? "outliers" : "data";
                        },
                        Collectors.counting()
                ));

        long dataCount = raw.getOrDefault("data", 0L);
        long outlierCount = raw.getOrDefault("outliers", 0L);

        return Map.of(
                "data", dataCount,
                "outliers", outlierCount
        );
    }

    /**
     * Обчислення квантиля q (0..1) для відсортованого списку значень.
     * Використовується лінійна інтерполяція.
     */
    private static double quantile(List<Double> sortedValues, double q) {
        if (sortedValues.isEmpty()) return Double.NaN;
        if (q <= 0) return sortedValues.get(0);
        if (q >= 1) return sortedValues.get(sortedValues.size() - 1);

        double pos = q * (sortedValues.size() - 1);
        int lowerIndex = (int) Math.floor(pos);
        int upperIndex = (int) Math.ceil(pos);

        if (lowerIndex == upperIndex) {
            return sortedValues.get(lowerIndex);
        }

        double lower = sortedValues.get(lowerIndex);
        double upper = sortedValues.get(upperIndex);
        double weight = pos - lowerIndex;

        return lower + (upper - lower) * weight;
    }
}
