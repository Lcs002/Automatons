package com.lvum.automaton.algorithms.properties;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Algorithm;
import com.lvum.automaton.algorithms.utility.IsComplete;
import com.lvum.automaton.algorithms.utility.IsDFA;

/**
 * <h1>Algorithm for Automata Complement</h1>
 * <p> FORMULA </p>
 * <p><b>Input:</b> One <b>{@link Automata}</b>.</p>
 * <p><b>Result:</b> <b>NFA</b> that accepts the contrary language of the original automaton.</p>
 * <p><b>Requisites:</b></p>
 * <ol>
 *  <li> The automaton <b>{@link IsDFA is a DFA}</b>.</li>
 *  <li> The automaton <b>{@link IsComplete is complete}</b>.</li>
 * </ol>
 */
public class Complement implements Algorithm<Automata> {
    @Override
    public Automata run(Automata automata) {
        // The automata must be a DFA
        if (Boolean.FALSE.equals(automata.run(new IsDFA()))) return null;
        // The automata must be complete
        if (Boolean.FALSE.equals(automata.run(new IsComplete()))) return null;

        // The result of the complement of an automata is a new automata
        Automata result = new Automata(automata.getAlphabet());
        // Add the transitions of the original automata
        automata.getTransitions()
                .forEach(transition ->
                        result.addTransition(
                                transition.from(),
                                transition.to(),
                                transition.entry()
                        )
                );
        // Set the initial state of the result automata
        result.setInitialState(automata.getInitialState());
        // Interchange the final states with the non-final states
        automata.getStates().stream()
                .filter(state -> !automata.getFinalStates().contains(state))
                .forEach(result::addFinalState);
        return result;
    }
}
