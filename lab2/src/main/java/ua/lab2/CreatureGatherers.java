// file: src/main/java/lab2/CreatureGatherers.java
package ua.lab2;

import java.util.Objects;
import java.util.stream.Gatherer;

/**
 * Набір Gatherer'ів для роботи з ChthonicCreature.
 */
public final class CreatureGatherers {

    private CreatureGatherers() {
    }

    /**
     * Створює послідовний Gatherer, який:
     *  - пропускає перші n істот із заданим видом (speciesToSkip)
     *  - усі інші елементи пропускає далі вниз по стріму без змін.
     * Поле A / В -  вид істоти (species).
     */
    public static Gatherer<ChthonicCreature, int[], ChthonicCreature> skipFirstNBySpecies(
            String speciesToSkip,
            int n
    ) {
        Objects.requireNonNull(speciesToSkip, "speciesToSkip must not be null");
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }

        return Gatherer.ofSequential(
                () -> new int[]{0},
                (state, creature, downstream) -> {
                    int skippedSoFar = state[0];

                    if (creature.getSpecies().equals(speciesToSkip) && skippedSoFar < n) {
                        state[0] = skippedSoFar + 1;
                        return true;
                    } else {
                        return downstream.push(creature);
                    }
                }
        );
    }
}
