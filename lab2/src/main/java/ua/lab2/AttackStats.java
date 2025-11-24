// file: src/main/java/lab2/AttackStats.java
package ua.lab2;

import java.util.Objects;
import java.util.stream.Collector;

/**
 * Клас для статистики по силі атаки (Поле Г).
 * Підтримує обчислення:
 *  - мінімум
 *  - максимум
 *  - середнє значення
 *  - стандартне відхилення
 * Має статичний метод collector() для використання як Collector у Stream API.
 */
public class AttackStats {

    private long count;
    private double sum;
    private double sumSq;
    private double min = Double.POSITIVE_INFINITY;
    private double max = Double.NEGATIVE_INFINITY;

    public AttackStats() {
    }

    /**
     * Додає істоту до статистики.
     */
    public void accept(ChthonicCreature creature) {
        Objects.requireNonNull(creature);
        add(creature.getAttackPower());
    }

    private void add(double value) {
        count++;
        sum += value;
        sumSq += value * value;

        if (value < min) {
            min = value;
        }
        if (value > max) {
            max = value;
        }
    }

    /**
     * Об'єднує дві статистики (для паралельних стрімів).
     */
    public AttackStats combine(AttackStats other) {
        this.count += other.count;
        this.sum += other.sum;
        this.sumSq += other.sumSq;

        if (other.min < this.min) {
            this.min = other.min;
        }
        if (other.max > this.max) {
            this.max = other.max;
        }
        return this;
    }

    public long getCount() {
        return count;
    }

    public double getMin() {
        return count == 0 ? Double.NaN : min;
    }

    public double getMax() {
        return count == 0 ? Double.NaN : max;
    }

    public double getMean() {
        if (count == 0) return Double.NaN;
        return sum / count;
    }

    public double getStdDev() {
        if (count == 0) return Double.NaN;

        double mean = getMean();
        double variance = sumSq / count - mean * mean;
        if (variance < 0) {
            variance = 0;
        }
        return Math.sqrt(variance);
    }

    @Override
    public String toString() {
        return "AttackStats{" +
                "count=" + count +
                ", min=" + getMin() +
                ", max=" + getMax() +
                ", mean=" + getMean() +
                ", stdDev=" + getStdDev() +
                '}';
    }

    /**
     * Створює Collector, що збирає ChthonicCreature в один AttackStats.
     */
    public static Collector<ChthonicCreature, AttackStats, AttackStats> collector() {
        return Collector.of(
                AttackStats::new,
                AttackStats::accept,
                (left, right) -> {
                    left.combine(right);
                    return left;
                }
        );
    }
}
