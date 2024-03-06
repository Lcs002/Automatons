package io.github.lcs002.automatons.automaton.algorithms.properties;

import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.Algorithm;
import io.github.lcs002.automatons.automaton.algorithms.AutomatonAlgorithms;
import io.github.lcs002.automatons.automaton.algorithms.utility.IsComplete;
import io.github.lcs002.automatons.automaton.algorithms.utility.IsDFA;

/**
 * <h2>Algorithm for Automaton Complement</h2>
 * <p> FORMULA </p>
 * <p><b>Input:</b> One <b>{@link Automaton}</b>.</p>
 * <p><b>Result:</b> <b>NFA</b> that accepts the contrary language of the original automaton.</p>
 * <p><b>Requisites:</b></p>
 * <ol>
 *  <li> The automaton <b>{@link IsDFA is a DFA}</b>.</li>
 *  <li> The automaton <b>{@link IsComplete is complete}</b>.</li>
 * </ol>
 */
public class Complement extends Algorithm<Automaton> {


    public Complement(Automaton automaton) {
        super(automaton);
    }

    @Override
    public Automaton call() {
        // The automaton must be a DFA
        if (Boolean.FALSE.equals(AutomatonAlgorithms.isDFA(automaton))) return null;
        // The automaton must be complete
        if (Boolean.FALSE.equals(AutomatonAlgorithms.isComplete(automaton))) return null;

        // The result of the complement of an automaton is a new automaton
        Automaton.Builder result = new Automaton.Builder().setAlphabet(automaton.getAlphabet());
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
        // Return the result automaton
        return result.build();
    }
}
