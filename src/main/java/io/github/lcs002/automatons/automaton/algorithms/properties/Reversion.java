package io.github.lcs002.automatons.automaton.algorithms.properties;

import io.github.lcs002.automatons.automaton.algorithms.utility.IsDFA;
import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.Algorithm;

/**
 * <h2>Algorithm for Automaton Reversion</h2>
 * <p>Reverts the automata.</p>
 * <p><b>Input:</b> An <b>{@link Automaton Automaton}</b>.</p>
 * <p><b>Result:</b> <b>ε-NFA</b> that recognizes the reverse language of the original automaton.</p>
 * <p><b>Requisites:</b></p>
 * <ol>
 *     <li> The automaton <b>{@link IsDFA is a DFA}</b>.</li>
 * </ol>
 */
public final class Reversion extends Algorithm<Automaton> {

    public Reversion(Automaton automaton) {
        super(automaton);
    }

    @Override
    public Automaton call() {
        // The automaton must be a DFA
        if (Boolean.FALSE.equals(automaton.isDfa())) return null;

        // The result of the reversion of an automaton is a new automaton
        Automaton.Builder result = new Automaton.Builder().setAlphabet(automaton.getAlphabet());
        // Reverse all transitions: from -> to becomes to -> from
        automaton.getTransitions().forEach(
                transition -> result.addTransition(
                        transition.to(),
                        transition.from(),
                        transition.entry()
                )
        );
        // The final state of the result automaton is the initial state of the original automaton
        result.addFinalState(automaton.getInitialState());
        // The initial state of the result automaton is the final states of the original automaton
        // If there is more than one final state, as there can't be more than one initial state, we create a new state
        // and add an epsilon transition to the original final state.
        // Graphical Example:
        //      -a-> *S1             <-a- S1 <-e-
        // ->S0 -b-> *S2    TO   *S0 <-b- S2 <-e- Se<-
        //      -c-> *S3             <-c- S3 <-e-
        if (automaton.getFinalStates().size() > 1) {
            String newInitialState = String.join(Automaton.SEPARATOR, automaton.getFinalStates());
            result.setInitialState(newInitialState);
            automaton.getFinalStates().forEach(
                    finalState -> result.addTransition(newInitialState, finalState, Automaton.EPSILON)
            );
        } else {
            result.setInitialState(automaton.getFinalStates().stream().findFirst().orElse(null));
        }

        // Return the result
        return result.build();
    }
}
