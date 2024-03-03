package com.lvum.automaton.algorithms.conversion;

import com.lvum.automaton.Automaton;
import com.lvum.automaton.algorithms.Algorithm;
import com.lvum.automaton.algorithms.utility.GetEpsilonClosure;

import java.util.*;

/**
 * <h1>Converts ε-NFA to DFA</h1>
 * <p>Converts an NFA to its equivalent DFA, taking into account Epsilon transitions.</p>
 * <p><b>Result:</b> <b>{@link Automaton}</b> that is the equivalent DFA of the original NFA.</p>
 */
public class NFAToDFAEpsilon implements Algorithm<Automaton> {

    @Override
    public Automaton run(Automaton automaton) {
        // As a DFA, the result automaton won't have Epsilon as a symbol of the alphabet
        Set<Character> alphabet = new HashSet<>(automaton.getAlphabet());
        alphabet.remove(Automaton.EPSILON);
        // The automaton resultant of the conversion
        Automaton.Builder result = new Automaton.Builder().setAlphabet(alphabet);
        // List of Set of States we have already checked
        List<Set<String>> marked = new ArrayList<>();
        // Queue of Set of States
        Queue<Set<String>> queue = new LinkedList<>();
        // Initial State of the automaton
        String initialState = automaton.getInitialState();
        // Get the Epsilon Closure of the initial State
        Set<String> initialStates = automaton.run(new GetEpsilonClosure(initialState));
        initialStates.add(initialState);
        // Add and Mark the first State
        queue.add(initialStates);
        // Mark the initial State
        marked.add(new HashSet<>(Collections.singletonList(initialState)));
        // Set the initial State of the result automaton
        result.setInitialState(String.join(Automaton.SEPARATOR, initialStates));
        // Check if the initial State is a final State
        if (initialStates.stream().anyMatch(automaton::isFinal))
            result.addFinalState(String.join(Automaton.SEPARATOR, initialStates));
        // While there is still some Set of States to check...
        while (!queue.isEmpty()) {
            Set<String> superstate = queue.poll();
            // Get all Transitions of the Superstate for each entry
            for (Character entry : automaton.getAlphabet()) {
                // Epsilon is not an entry, so we ignore it
                if (entry.equals(Automaton.EPSILON)) continue;
                Set<String> nextSuperstate = new HashSet<>();
                // Fill the next Superstate with the union of sets of next states of each current state in superstate
                for (String state : superstate) {
                    // Get next states of the current state with a certain entry
                    automaton.getTransitions().stream()
                            .filter(transition -> transition.from().equals(state))
                            .filter(transition -> transition.entry().equals(entry))
                            .map(Automaton.Transition::to)
                            .forEach(nextState -> {
                                // Add it to the next Superstate
                                nextSuperstate.add(nextState);
                                // Get the Epsilon Closure of the next states
                                // Add them to the next Superstate
                                Set<String> epsilonClosure = automaton.run(new GetEpsilonClosure(nextState));
                                nextSuperstate.addAll(epsilonClosure);
                            });
                }
                // If its empty it means there is no transition with this entry on the current superstate
                if (!nextSuperstate.isEmpty()) {
                    // Check if we have already evaluated the next Superstate
                    if (!marked.contains(nextSuperstate)) {
                        queue.add(nextSuperstate);
                        marked.add(nextSuperstate);
                        if (nextSuperstate.stream().anyMatch(automaton::isFinal))
                            result.addFinalState(String.join(Automaton.SEPARATOR, nextSuperstate));
                    }
                    // Add the transition connecting current Superstate and next Superstate with certain entry
                    result.addTransition(
                            String.join(Automaton.SEPARATOR, superstate),
                            String.join(Automaton.SEPARATOR, nextSuperstate),
                            entry
                    );
                }
            }
        }
        return result.build();
    }
}
