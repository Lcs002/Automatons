package com.lvum.automaton.algorithms.properties;

import com.lvum.automaton.Automaton;
import com.lvum.automaton.algorithms.Algorithm;
import com.lvum.automaton.algorithms.utility.IsDFA;

import java.util.HashSet;
import java.util.Set;

/**
 * <h1>Algorithm for Automaton Concatenation</h1>
 * <p>Concatenation of two DFA Automatons: L1*L2</p>
 * <p><b>Input:</b> Two <b>{@link Automaton Automatons}</b>.</p>
 * <p><b>Result:</b> <b>NFA</b> that accepts words 'w' such that 'w' = 'xy' where x belongs to L1 and y to L2.</p>
 * <p><b>Requisites:</b></p>
 * <ol>
 *  <li> The automatons <b>{@link IsDFA are both DFA}</b>.</li>
 *  <li> The automatons have the same <b>alphabet</b>.</li>
 * </ol>
 * <p><b><i>References:</i></b></p>
 * <ol>
 *     <li> <a href="resources/university/class-notes/Class Notes.md">Class Notes.md</a>, #5 Operations With Languages.</li>
 *     <li> <a href="resources/documents/Automaton_Properties.pdf">Automaton_Properties.pdf</a>, page 15.</li>
 * </ol>
 */
public class Concatenation implements Algorithm<Automaton> {
    private final Automaton other;


    public Concatenation(Automaton other) {
        this.other = other;
    }


    @Override
    public Automaton run(Automaton automaton) {
        // If any of the automaton is not a DFA, return null
        if (Boolean.FALSE.equals(automaton.run(new IsDFA())) || Boolean.FALSE.equals(other.run(new IsDFA()))) return null;
        // The alphabet must be the same for both automaton
        if (!automaton.getAlphabet().equals(other.getAlphabet())) return null;

        Set<Character> alphabet = new HashSet<>(automaton.getAlphabet());
        // Add the epsilon symbol to the alphabet if it is not present
        if (!alphabet.contains(Automaton.EPSILON)) alphabet.add(Automaton.EPSILON);
        // The result of the union of two automaton is a new automaton
        Automaton result = new Automaton(alphabet);
        result.setInitialState(automaton.getInitialState() + '¹');
        // Add the transitions of the first automaton
        automaton.getTransitions().forEach(
                transition -> result.addTransition(
                        transition.from() + '¹',
                        transition.to() + '¹',
                        transition.entry()
                )
        );
        // Add the transitions of the second automaton
        other.getTransitions().forEach(
                transition -> result.addTransition(
                        transition.from() + '²',
                        transition.to() + '²',
                        transition.entry()
                )
        );
        // Add the transitions from the final states of the first automaton to the initial states of the second automaton
        automaton.getFinalStates().forEach(
                state -> result.addTransition(
                        state + '¹',
                        other.getInitialState() + '²',
                        Automaton.EPSILON
                )
        );
        // Add the final states of the second automaton to the final states of the result automaton
        other.getFinalStates().forEach(
                state -> result.addFinalState(state + '²')
        );
        // Return the result automaton
        return result;
    }
}
