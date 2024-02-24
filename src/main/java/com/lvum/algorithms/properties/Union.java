package com.lvum.algorithms.properties;

import com.lvum.Automaton;
import com.lvum.algorithms.Algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 *  Algorithm to compute the union of two Automatons.
 *  <p>
 *  The union of two automata is the automaton that recognizes the union of the languages recognized by the two automata.
 *  <p>
 *  Both automata must have the same alphabet.
 *  @see <a href="https://www.geeksforgeeks.org/union-process-in-dfa/">Union Algorithm</a>
 */
public class Union implements Algorithm<Automaton> {
    private final Automaton other;


    public Union(Automaton other) {
        this.other = other;
    }


    /**
     * The union of two automata is the automaton that recognizes the union of the languages recognized by the two
     * @param automaton The first automaton
     * @return The union of the two automata
     */
    @Override
    public Automaton run(Automaton automaton) {
        // The alphabet must be the same for both automata
        if (!automaton.getAlphabet().equals(other.getAlphabet())) return null;
        Set<Character> alphabet = new HashSet<>(automaton.getAlphabet());
        // Add the epsilon symbol to the alphabet if it is not present
        if (!alphabet.contains(Automaton.EPSILON)) alphabet.add(Automaton.EPSILON);
        // The result of the union of two automata is a new automaton
        Automaton result = new Automaton(alphabet);
        // The initial state of the result automaton is the concatenation of the initial states of the two automata
        String initialState = automaton.getInitialState() + "¹" + Automaton.SEPARATOR + other.getInitialState() + "²";
        // Add the initial state to the result automaton
        result.setInitialState(initialState);
        // Add the transitions of the initial state to the initial states of the two automata
        result.addTransition(initialState, automaton.getInitialState() + "¹", Automaton.EPSILON);
        result.addTransition(initialState, other.getInitialState() + "²", Automaton.EPSILON);
        // Add the transitions of the first automaton
        automaton.getTransitions().forEach(
                transition -> result.addTransition(
                        transition.from() + "¹",
                        transition.to() + "¹",
                        transition.entry()
                )
        );
        // Add the transitions of the second automaton
        other.getTransitions().forEach(
                transition -> result.addTransition(
                        transition.from() + "²",
                        transition.to() + "²",
                        transition.entry()
                )
        );
        // Add the final states of the first automaton
        automaton.getFinalStates().forEach(
                state -> result.addFinalState(state + "¹")
        );
        // Add the final states of the second automaton
        other.getFinalStates().forEach(
                state -> result.addFinalState(state + "²")
        );
        // Return the union of the two automata
        return result;
    }
}