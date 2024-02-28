package com.lvum.automaton.algorithms.utility;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Algorithm;

/**
 * <h1>Checks if an Automata is a DFA</h1>
 * <p><b>Result:</b> <b>Boolean</b> that indicates if the automaton is a DFA.</p>
 */
public class IsDFA implements Algorithm<Boolean> {
    @Override
    public Boolean run(Automata automata) {
        return !automata.getAlphabet().contains(Automata.EPSILON)
                && automata.getTransitions().stream().noneMatch(transition -> transition.entry().equals(Automata.EPSILON))
                && automata.getTransitions().stream()
                .mapToLong(transition -> automata.getTransitions().stream()
                        .filter(t -> t.from().equals(transition.from()))
                        .filter(t -> t.entry().equals(transition.entry()))
                        .count())
                .max().orElse(0) <= 1;
    }
}
