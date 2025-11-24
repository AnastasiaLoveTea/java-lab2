// file: src/main/java/lab2/ChthonicCreature.java
package ua.lab2;

import java.time.LocalDate;

/**
 * Модель хтонічної істоти (нечисті).
 * Поля:
 *  - name: ім'я істоти
 *  - species: вид істоти (Поле A і Поле В)
 *  - firstMention: дата першої згадки в літературі
 *  - attackPower: сила атаки (Поле Г)
 */
public class ChthonicCreature {

    private final String name;
    private final String species;
    private final LocalDate firstMention;
    private final double attackPower;

    public ChthonicCreature(String name, String species, LocalDate firstMention, double attackPower) {
        this.name = name;
        this.species = species;
        this.firstMention = firstMention;
        this.attackPower = attackPower;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public LocalDate getFirstMention() {
        return firstMention;
    }

    public double getAttackPower() {
        return attackPower;
    }

    @Override
    public String toString() {
        return "ChthonicCreature{" +
                "name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", firstMention=" + firstMention +
                ", attackPower=" + attackPower +
                '}';
    }
}
