package com.lvum.automaton.algorithms.properties;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Algorithm;
import com.lvum.automaton.algorithms.utility.IsDFA;

import java.util.HashSet;
import java.util.Set;

// Explanation in Class Notes, #5 Operations With Languages
// Explanation in document Automaton_Properties.pdf, page 12
/**
 * <h1>Algorithm for Automata Union</h1>
 * <p>Union of two Automatons: L1+L2 or L1UL2</p>
 * <p><b>Input:</b> Two <b>{@link Automata Automatons}</b>.</p>
 * <p><b>Result:</b> <b>NFA</b> that recognizes the union of the languages recognized by the two.</p>
 * <p><b>Requisites:</b></p>
 * <ol>
 *  <li> The automatons <b>{@link IsDFA are both DFA}</b>.</li>
 *  <li> The automatons have the same <b>alphabet</b>.</li>
 * </ol>
 *  @see <a href="https://www.geeksforgeeks.org/union-process-in-dfa/">Union Algorithm</a>
 */
public class Union implements Algorithm<Automata> {
    private final Automata other;


    public Union(Automata other) {
        this.other = other;
    }


    @Override
    public Automata run(Automata automata) {
        // If any of the automata is not a DFA, return null
        if (Boolean.FALSE.equals(automata.run(new IsDFA())) || Boolean.FALSE.equals(other.run(new IsDFA()))) return null;
        // The alphabet must be the same for both automata
        if (!automata.getAlphabet().equals(other.getAlphabet())) return null;

        Set<Character> alphabet = new HashSet<>(automata.getAlphabet());
        // Add the epsilon symbol to the alphabet if it is not present
        if (!alphabet.contains(Automata.EPSILON)) alphabet.add(Automata.EPSILON);
        // The result of the union of two automata is a new automata
        Automata result = new Automata(alphabet);
        // The initial state of the result automata is the concatenation of the initial states of the two automata
        String initialState = automata.getInitialState() + "¹" + Automata.SEPARATOR + other.getInitialState() + "²";
        // Add the initial state to the result automata
        result.setInitialState(initialState);
        // Add the transitions of the initial state to the initial states of the two automata
        result.addTransition(initialState, automata.getInitialState() + "¹", Automata.EPSILON);
        result.addTransition(initialState, other.getInitialState() + "²", Automata.EPSILON);
        // Add the transitions of the first automata
        automata.getTransitions().forEach(
                transition -> result.addTransition(
                        transition.from() + "¹",
                        transition.to() + "¹",
                        transition.entry()
                )
        );
        // Add the transitions of the second automata
        other.getTransitions().forEach(
                transition -> result.addTransition(
                        transition.from() + "²",
                        transition.to() + "²",
                        transition.entry()
                )
        );
        // Add the final states of the first automata
        automata.getFinalStates().forEach(
                state -> result.addFinalState(state + "¹")
        );
        // Add the final states of the second automata
        other.getFinalStates().forEach(
                state -> result.addFinalState(state + "²")
        );
        // Return the union of the two automata
        return result;
    }
}
