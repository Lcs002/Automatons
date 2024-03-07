package io.github.lcs002.automatons.automaton.algorithms.properties;

import io.github.lcs002.automatons.automaton.algorithms.utility.IsDFA;
import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.Algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * <h2>Algorithm for Automaton Concatenation</h2>
 * <p>Concatenation of two DFA automatons: L1*L2</p>
 * <p><b>Input:</b> Two <b>{@link Automaton automatons}</b>.</p>
 * <p><b>Result:</b> <b>NFA</b> that accepts words 'w' such that 'w' = 'xy' where x belongs to L1 and y to L2.</p>
 * <p><b>Requisites:</b></p>
 * <ol>
 *  <li> The automatons <b>{@link IsDFA are both DFA}</b>.</li>
 *  <li> The automatons have the same <b>alphabet</b>.</li>
 * </ol>
 * <p><b><i>References:</i></b></p>
 * <ol>
 *     <li> <a href="resources/documents/Automaton_Properties.pdf">Automaton_Properties.pdf</a>, page 15.</li>
 * </ol>
 */
public final class Concatenation extends Algorithm<Automaton> {
    private final Automaton automaton2;


    public Concatenation(Automaton automaton1, Automaton automaton2) {
        super(automaton1);
        this.automaton2 = automaton2;
    }


    @Override
    public Automaton call() {
        // If any of the automaton is not a DFA, return null
        if (Boolean.FALSE.equals(automaton.isDfa())
                || Boolean.FALSE.equals(automaton.isDfa())) return null;
        // The alphabet must be the same for both automaton
        if (!automaton.getAlphabet().equals(automaton2.getAlphabet())) return null;

        Set<Character> alphabet = new HashSet<>(automaton.getAlphabet());
        // Add the epsilon symbol to the alphabet if it is not present
        if (!alphabet.contains(Automaton.EPSILON)) alphabet.add(Automaton.EPSILON);
        // The result of the union of two automaton is a new automaton
        Automaton.Builder result = new Automaton.Builder().setAlphabet(alphabet);
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
        automaton2.getTransitions().forEach(
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
                        automaton2.getInitialState() + '²',
                        Automaton.EPSILON
                )
        );
        // Add the final states of the second automaton to the final states of the result automaton
        automaton2.getFinalStates().forEach(
                state -> result.addFinalState(state + '²')
        );
        // Return the result automaton
        return result.build();
    }
}
