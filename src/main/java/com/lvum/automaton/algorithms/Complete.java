package com.lvum.automaton.algorithms;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.utility.IsDFA;

/**
 * <h1>Completes a DFA</h1>
 * <p><b>Result:</b> <b>DFA</b> that recognizes the same language as the original automaton, but is complete.</p>
 * <b>Note:</b> A complete DFA is a DFA in which every state has a transition for every symbol in the alphabet.
 * <p><b>Requisites:</b></p>
 * <ol>
 *  <li>The automaton <b>{@link IsDFA is a DFA}</b>.</li>
 * </ol>
 */
public class Complete implements Algorithm<Automata> {

    @Override
    public Automata run(Automata automata) {
        // The automata must be a DFA
        if (Boolean.FALSE.equals(automata.run(new IsDFA()))) return null;

        // The result of the complete DFA is a new automata
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
        // Add the transitions for the missing symbols
        automata.getStates()
                .forEach(state -> automata.getAlphabet()
                        .forEach(symbol -> {
                            if (automata.getTransitions().stream()
                                    .noneMatch(transition ->
                                            transition.from().equals(state) && transition.entry().equals(symbol)
                                    )
                            )
                            {
                                result.addTransition(state, Automata.EMPTY_STATE, symbol);
                            }
                        }
                )
        );
        // Add the transitions to the empty state
        automata.getAlphabet()
                .forEach(symbol -> result.addTransition(Automata.EMPTY_STATE, Automata.EMPTY_STATE, symbol));
        // Add the initial state of the original automata to the initial state of the result automata
        result.setInitialState(automata.getInitialState());
        // Add the final states of the original automata to the final states of the result automata
        automata.getFinalStates()
                .forEach(result::addFinalState);
        // Return the complete DFA
        return result;
    }
}
