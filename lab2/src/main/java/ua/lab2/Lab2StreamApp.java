package ua.lab2;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Головний клас лабораторної роботи №2 (Stream API + Gatherer).
 * Варіант C4 = 3: хтонічні істоти.
 */
public class Lab2StreamApp {

    private static final int TARGET_SIZE = 500;

    private static final int N_SKIP = 50;
    private static final String SPECIES_TO_SKIP = "Вампір";

    private static final long MIN_YEARS = 50;
    private static final long MAX_YEARS = 800;

    public static void main(String[] args) {
        ChthonicCreatureGenerator generator = new ChthonicCreatureGenerator();

        List<ChthonicCreature> generated = generator.stream()
                .gather(CreatureGatherers.skipFirstNBySpecies(SPECIES_TO_SKIP, N_SKIP))
                .limit(TARGET_SIZE)
                .collect(Collectors.toList());

        System.out.println("Згенеровано істот після gather+limit: " + generated.size());

        LocalDate now = LocalDate.now();

        Map<String, List<ChthonicCreature>> groupedBySpecies = generated.stream()
                .filter(c -> {
                    long years = ChronoUnit.YEARS.between(c.getFirstMention(), now);
                    return years >= MIN_YEARS && years <= MAX_YEARS;
                })
                .collect(Collectors.groupingBy(ChthonicCreature::getSpecies));

        System.out.println("\nГрупування за видами після фільтрації за роками [" +
                MIN_YEARS + "; " + MAX_YEARS + "]:");
        groupedBySpecies.forEach((species, list) ->
                System.out.printf("  %s: %d істот%n", species, list.size())
        );

        List<ChthonicCreature> filteredList = groupedBySpecies.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        System.out.println("\nКількість істот після фільтрації за роками: " + filteredList.size());

        AttackStats stats = filteredList.stream()
                .collect(AttackStats.collector());

        System.out.println("\nСтатистика по силі атаки (Поле Г):");
        System.out.printf("  Кількість: %d%n", stats.getCount());
        System.out.printf("  Мінімум:   %.2f%n", stats.getMin());
        System.out.printf("  Максимум:  %.2f%n", stats.getMax());
        System.out.printf("  Середнє:   %.2f%n", stats.getMean());
        System.out.printf("  Сигма:     %.2f%n", stats.getStdDev());

        Map<String, Long> outlierSummary = OutlierAnalysis.analyzeAttackPower(filteredList);

        System.out.println("\nРозподіл значень сили атаки за викидами:");
        System.out.println("  " + outlierSummary);
    }
}
