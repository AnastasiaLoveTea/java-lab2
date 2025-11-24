// file: src/main/java/lab2/ChthonicCreatureGenerator.java
package ua.lab2;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Генератор випадкових хтонічних істот.
 * Дає нескінченний Stream з параметрами.
 */
public class ChthonicCreatureGenerator {

    private static final List<String> NAMES = List.of(
            "Морана", "Чугайстер", "Лихо", "Вурдалак", "Мара", "Русалка",
            "Домовик", "Відьма", "Навка", "Кощій", "Блуд", "Вампір", "Сірко"
    );

    private static final List<String> SPECIES = List.of(
            "Вампір",
            "Відьма",
            "Демон",
            "Дух",
            "Русалка",
            "Лісовик",
            "Болотник",
            "Пітьмовик"
    );

    private static final int MIN_YEAR = 1000;
    private static final int MAX_YEAR = 2020;

    private final Random random = new Random();

    /**
     * Створює одну випадкову істоту.
     */
    public ChthonicCreature next() {
        String name = NAMES.get(random.nextInt(NAMES.size()));
        String species = SPECIES.get(random.nextInt(SPECIES.size()));

        int year = MIN_YEAR + random.nextInt(MAX_YEAR - MIN_YEAR + 1);
        int month = 1 + random.nextInt(12);
        int day = 1 + random.nextInt(28);
        LocalDate firstMention = LocalDate.of(year, month, day);

        double attackPower = 10 + random.nextDouble() * 490;

        return new ChthonicCreature(name, species, firstMention, attackPower);
    }

    /**
     * Повертає нескінченний стрім істот.
     */
    public Stream<ChthonicCreature> stream() {
        return Stream.generate(this::next);
    }
}
