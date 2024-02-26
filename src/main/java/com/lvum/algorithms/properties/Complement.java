package com.lvum.algorithms.properties;

import com.lvum.Automaton;
import com.lvum.algorithms.Algorithm;
import com.lvum.algorithms.utility.IsComplete;
import com.lvum.algorithms.utility.IsDFA;

/**
 * <h1>Algorithm for Automaton Complement</h1>
 * <p> FORMULA </p>
 * <p><b>Input:</b> One <b>{@link Automaton}</b>.</p>
 * <p><b>Result:</b> <b>NFA</b> that accepts the contrary language of the original automaton.</p>
 * <p><b>Requisites:</b></p>
 * <ol>
 *  <li> The automaton <b>{@link IsDFA is a DFA}</b>.</li>
 *  <li> The automaton <b>{@link IsComplete is complete}</b>.</li>
 * </ol>
 */
public class Complement implements Algorithm<Automaton> {
    @Override
    public Automaton run(Automaton automaton) {
        // The automaton must be a DFA
        if (Boolean.FALSE.equals(automaton.run(new IsDFA()))) return null;
        // The automaton must be complete
        if (Boolean.FALSE.equals(automaton.run(new IsComplete()))) return null;
        // The result of the complement of an automaton is a new automaton
        Automaton result = new Automaton(automaton.getAlphabet());
        // Add the transitions of the original automaton
        automaton.getTransitions()
                .forEach(transition ->
                        result.addTransition(
                                transition.from(),
                                transition.to(),
                                transition.entry()
                        )
                );
        // Set the initial state of the result automaton
        result.setInitialState(automaton.getInitialState());
        // Interchange the final states with the non-final states
        automaton.getStates().stream()
                .filter(state -> !automaton.getFinalStates().contains(state))
                .forEach(result::addFinalState);
        return result;
    }
}
