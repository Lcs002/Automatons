package com.github.Lcs002.Automatons.automaton.algorithms.utility;

import com.github.Lcs002.Automatons.automaton.Automaton;
import com.github.Lcs002.Automatons.automaton.algorithms.Algorithm;

/**
 * <h1>Checks Completeness of an Automaton</h1>
 * <p><b>Result:</b> <b>Boolean</b> that indicates if the automaton is complete.</p>
 */
public class IsComplete implements Algorithm<Boolean> {
    @Override
    public Boolean run(Automaton automaton) {
        // Return true if the automaton is complete
        // An automaton is complete if every state has a transition for every symbol in the alphabet
        return automaton.getStates().stream()
                .allMatch(state -> automaton.getAlphabet().stream()
                        .allMatch(symbol -> automaton.getTransitions().stream()
                                .anyMatch(transition ->
                                        transition.from().equals(state) && transition.entry().equals(symbol)
                                )
                        )
                );
    }
}
