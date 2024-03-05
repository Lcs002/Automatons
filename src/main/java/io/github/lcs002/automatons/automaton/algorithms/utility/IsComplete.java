package io.github.lcs002.automatons.automaton.algorithms.utility;

import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.Algorithm;

/**
 * <h2>Checks Completeness of an Automaton</h2>
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
