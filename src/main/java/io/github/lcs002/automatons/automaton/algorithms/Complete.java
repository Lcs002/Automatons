package io.github.lcs002.automatons.automaton.algorithms;

import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.utility.IsDFA;

/**
 * <h2>Completes a DFA</h2>
 * <p><b>Result:</b> <b>DFA</b> that recognizes the same language as the original automaton, but is complete.</p>
 * <b>Note:</b> A complete DFA is a DFA in which every state has a transition for every symbol in the alphabet.
 * <p><b>Requisites:</b></p>
 * <ol>
 *  <li>The automaton <b>{@link IsDFA is a DFA}</b>.</li>
 * </ol>
 */
public class Complete extends Algorithm<Automaton> {

    public Complete(Automaton automaton) {
        super(automaton);
    }

    @Override
    public Automaton call() {
        // The automaton must be a DFA
        if (Boolean.FALSE.equals(automaton.run(new IsDFA()))) return null;

        // The result of the complete DFA is a new automaton
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
        // Add the transitions for the missing symbols
        automaton.getStates()
                .forEach(state -> automaton.getAlphabet()
                        .forEach(symbol -> {
                            if (automaton.getTransitions().stream()
                                    .noneMatch(transition ->
                                            transition.from().equals(state) && transition.entry().equals(symbol)
                                    )
                            )
                            {
                                result.addTransition(state, Automaton.EMPTY_STATE, symbol);
                            }
                        }
                )
        );
        // Add the transitions to the empty state
        automaton.getAlphabet()
                .forEach(symbol -> result.addTransition(Automaton.EMPTY_STATE, Automaton.EMPTY_STATE, symbol));
        // Add the initial state of the original automaton to the initial state of the result automaton
        result.setInitialState(automaton.getInitialState());
        // Add the final states of the original automaton to the final states of the result automaton
        automaton.getFinalStates()
                .forEach(result::addFinalState);
        // Return the complete DFA
        return result.build();
    }
}
