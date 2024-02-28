package com.lvum.automaton.algorithms.utility;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Algorithm;

/**
 * <h1>Checks Completeness of an Automata</h1>
 * <p><b>Result:</b> <b>Boolean</b> that indicates if the automaton is complete.</p>
 */
public class IsComplete implements Algorithm<Boolean> {
    @Override
    public Boolean run(Automata automata) {
        // Return true if the automata is complete
        // An automata is complete if every state has a transition for every symbol in the alphabet
        return automata.getStates().stream()
                .allMatch(state -> automata.getAlphabet().stream()
                        .allMatch(symbol -> automata.getTransitions().stream()
                                .anyMatch(transition ->
                                        transition.from().equals(state) && transition.entry().equals(symbol)
                                )
                        )
                );
    }
}
