package io.github.lcs002.automatons.automaton.algorithms.properties;

import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.Algorithm;
import io.github.lcs002.automatons.automaton.algorithms.utility.IsDFA;

import java.util.HashSet;
import java.util.Set;

// Explanation in Class Notes, #5 Operations With Languages
// Explanation in document Automaton_Properties.pdf, page 12
/**
 * <h2>Algorithm for Automaton Union</h2>
 * <p>Union of two automatons: L1+L2 or L1UL2</p>
 * <p><b>Input:</b> Two <b>{@link Automaton automatons}</b>.</p>
 * <p><b>Result:</b> <b>NFA</b> that recognizes the union of the languages recognized by the two.</p>
 * <p><b>Requisites:</b></p>
 * <ol>
 *  <li> The automatons <b>{@link IsDFA are both DFA}</b>.</li>
 *  <li> The automatons have the same <b>alphabet</b>.</li>
 * </ol>
 *  @see <a href="https://www.geeksforgeeks.org/union-process-in-dfa/">Union Algorithm</a>
 */
public final class Union extends Algorithm<Automaton> {
    private final Automaton automaton2;


    public Union(Automaton automaton1, Automaton automaton2) {
        super(automaton1);
        this.automaton2 = automaton2;
    }


    @Override
    public Automaton call() {
        // The automaton must be a DFA
        if (Boolean.FALSE.equals(automaton.isDfa())) return null;
        // The alphabet must be the same for both automaton
        if (!automaton.getAlphabet().equals(automaton2.getAlphabet())) return null;

        Set<Character> alphabet = new HashSet<>(automaton.getAlphabet());
        // Add the epsilon symbol to the alphabet if it is not present
        if (!alphabet.contains(Automaton.EPSILON)) alphabet.add(Automaton.EPSILON);
        // The result of the union of two automaton is a new automaton
        Automaton.Builder result = new Automaton.Builder().setAlphabet(alphabet);
        // The initial state of the result automaton is the concatenation of the initial states of the two automaton
        String initialState = automaton.getInitialState() + "¹" + Automaton.SEPARATOR + automaton2.getInitialState() + "²";
        // Add the initial state to the result automaton
        result.setInitialState(initialState);
        // Add the transitions of the initial state to the initial states of the two automaton
        result.addTransition(initialState, automaton.getInitialState() + "¹", Automaton.EPSILON);
        result.addTransition(initialState, automaton2.getInitialState() + "²", Automaton.EPSILON);
        // Add the transitions of the first automaton
        automaton.getTransitions().forEach(
                transition -> result.addTransition(
                        transition.from() + "¹",
                        transition.to() + "¹",
                        transition.entry()
                )
        );
        // Add the transitions of the second automaton
        automaton2.getTransitions().forEach(
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
        automaton2.getFinalStates().forEach(
                state -> result.addFinalState(state + "²")
        );
        // Return the union of the two automaton
        return result.build();
    }
}
