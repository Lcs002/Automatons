package io.github.lcs002.automatons.automaton.algorithms.utility;

import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.Algorithm;

/**
 * <h2>Checks if an Automaton is a DFA</h2>
 * <p><b>Result:</b> <b>Boolean</b> that indicates if the automaton is a DFA.</p>
 */
public final class IsDFA extends Algorithm<Boolean> {
    public IsDFA(Automaton automaton) {
        super(automaton);
    }

    @Override
    public Boolean call() {
        return !automaton.getAlphabet().contains(Automaton.EPSILON)
                && automaton.getTransitions().stream().noneMatch(transition -> transition.entry().equals(Automaton.EPSILON))
                && automaton.getTransitions().stream()
                .mapToLong(transition -> automaton.getTransitions().stream()
                        .filter(t -> t.from().equals(transition.from()))
                        .filter(t -> t.entry().equals(transition.entry()))
                        .count())
                .max().orElse(0) <= 1;
    }
}
