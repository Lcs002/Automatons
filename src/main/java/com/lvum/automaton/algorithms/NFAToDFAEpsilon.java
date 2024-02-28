package com.lvum.automaton.algorithms;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.utility.GetEpsilonClosure;

import java.util.*;

/**
 * <h1>Converts ε-NFA to DFA</h1>
 * <p>Converts an NFA to its equivalent DFA, taking into account Epsilon transitions.</p>
 * <p><b>Result:</b> <b>{@link Automata}</b> that is the equivalent DFA of the original NFA.</p>
 */
public class NFAToDFAEpsilon implements Algorithm<Automata> {

    @Override
    public Automata run(Automata automata) {
        // As a DFA, the result automata won't have Epsilon as a symbol of the alphabet
        Set<Character> alphabet = new HashSet<>(automata.getAlphabet());
        alphabet.remove(Automata.EPSILON);
        // The automata resultant of the conversion
        Automata result = new Automata(alphabet);
        // List of Set of States we have already checked
        List<Set<String>> marked = new ArrayList<>();
        // Queue of Set of States
        Queue<Set<String>> queue = new LinkedList<>();
        // Initial State of the automata
        String initialState = automata.getInitialState();
        // Get the Epsilon Closure of the initial State
        Set<String> initialStates = automata.run(new GetEpsilonClosure(initialState));
        initialStates.add(initialState);
        // Add and Mark the first State
        queue.add(initialStates);
        // Mark the initial State
        marked.add(new HashSet<>(Collections.singletonList(initialState)));
        // Set the initial State of the result automata
        result.setInitialState(String.join(Automata.SEPARATOR, initialStates));
        // Check if the initial State is a final State
        if (initialStates.stream().anyMatch(automata::isFinal))
            result.addFinalState(String.join(Automata.SEPARATOR, initialStates));
        // While there is still some Set of States to check...
        while (!queue.isEmpty()) {
            Set<String> superstate = queue.poll();
            // Get all Transitions of the Superstate for each entry
            for (Character entry : automata.getAlphabet()) {
                // Epsilon is not an entry, so we ignore it
                if (entry.equals(Automata.EPSILON)) continue;
                Set<String> nextSuperstate = new HashSet<>();
                // Fill the next Superstate with the union of sets of next states of each current state in superstate
                for (String state : superstate) {
                    // Get next states of the current state with a certain entry
                    automata.getTransitions().stream()
                            .filter(transition -> transition.from().equals(state))
                            .filter(transition -> transition.entry().equals(entry))
                            .map(Automata.Transition::to)
                            .forEach(nextState -> {
                                // Add it to the next Superstate
                                nextSuperstate.add(nextState);
                                // Get the Epsilon Closure of the next states
                                // Add them to the next Superstate
                                Set<String> epsilonClosure = automata.run(new GetEpsilonClosure(nextState));
                                nextSuperstate.addAll(epsilonClosure);
                            });
                }
                // If its empty it means there is no transition with this entry on the current superstate
                if (!nextSuperstate.isEmpty()) {
                    // Check if we have already evaluated the next Superstate
                    if (!marked.contains(nextSuperstate)) {
                        queue.add(nextSuperstate);
                        marked.add(nextSuperstate);
                        if (nextSuperstate.stream().anyMatch(automata::isFinal))
                            result.addFinalState(String.join(Automata.SEPARATOR, nextSuperstate));
                    }
                    // Add the transition connecting current Superstate and next Superstate with certain entry
                    result.addTransition(
                            String.join(Automata.SEPARATOR, superstate),
                            String.join(Automata.SEPARATOR, nextSuperstate),
                            entry
                    );
                }
            }
        }
        return result;
    }
}
