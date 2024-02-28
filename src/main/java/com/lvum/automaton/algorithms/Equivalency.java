package com.lvum.automaton.algorithms;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.utility.IsDFA;
import org.javatuples.Pair;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

//

/**
 * <h1>Checks Equivalency between two Automatons</h1>
 * <p><b>Result:</b> <b>Boolean</b> that indicates if the automaton is equivalent to another automaton.</p>
 * <p><b>Requisites:</b></p>
 * <ol>
 *     <li>Both automatons <b>{@link IsDFA must be a DFA} </b>.</li>
 *     <li>Both automatons have the same <b>alphabet</b>.</li>
 * </ol>
 * <p><b><i>References:</i></b></p>
 * <ol>
 *     <li> <a href="resources/university/Leccion 4.pdf">Leccion 4.pdf</a>, page 53.</li>
 * </ol>
 */
public class Equivalency implements Algorithm<Boolean> {
    private Automata other;


    public  Equivalency(Automata other) {
        this.other = other;
    }


    @Override
    public Boolean run(Automata automata) {

        /*
        Two Automata are equivalent if they satisfy the following conditions :
            1. The initial and final states of both the automatons must be same.
            2. Every pair of states chosen is from a different automata only.
            3. While combining the states with the input alphabets, the pair results must be either both final states
             or intermediate states.(i.e. both should lie either in the final state or in the non-final state).
            4. If the resultant pair has different types of states, then it will be non-equivalent. (i.e. One lies in
             the final state and the other lies in the intermediate state ).
         */

        // If the alphabet are different, the automata are not equivalent
        if (!automata.getAlphabet().equals(other.getAlphabet())) return false;

        // Set of pairs of states that have already been checked
        Set<Pair<String, String>> checked = new HashSet<>();
        // Queue of pairs of states to be checked
        Queue<Pair<String, String>> queue = new LinkedList<>();
        // The automata are equivalent until proven otherwise
        boolean equivalent = true;

        // Add the initial states to the queue and the set of checked pairs
        checked.add(new Pair<>(automata.getInitialState(), other.getInitialState()));
        queue.add(new Pair<>(automata.getInitialState(), other.getInitialState()));

        // While there are still pairs of states to check...
        while (!queue.isEmpty() && equivalent) {
            // Get the current pair of states
            Pair<String, String> superstate = queue.poll();
            // For each symbol in the language
            for (Character symbol : automata.getAlphabet()) {
                // Get the next state of the current original automata state given a certain symbol
                String nextOriginal = automata.getTransitions().stream()
                        .filter(transition -> transition.from().equals(superstate.getValue0()))
                        .filter(transition -> transition.entry().equals(symbol))
                        .map(Automata.Transition::to)
                        .findFirst()
                        .orElse(null);
                // Get the next state of the current other automata state given a certain symbol
                String nextOther = other.getTransitions().stream()
                        .filter(transition -> transition.from().equals(superstate.getValue1()))
                        .filter(transition -> transition.entry().equals(symbol))
                        .map(Automata.Transition::to)
                        .findFirst()
                        .orElse(null);
                // If one of the next states is null and the other is not
                // (i.e. one of the next states does not exist and the other does)
                // The automata are not equivalent
                if (nextOriginal == null ^ nextOther == null) equivalent = false;
                // If the pair of next states has not been checked yet
                else if (!checked.contains(new Pair<>(nextOriginal, nextOther))) {
                    // If both states are final or both are non-final
                    if (automata.isFinal(nextOriginal) && other.isFinal(nextOther)
                            || (!automata.isFinal(nextOriginal) && !other.isFinal(nextOther)))
                    {
                        // Add the pair to the queue and the set of checked pairs
                        queue.add(new Pair<>(nextOriginal, nextOther));
                        checked.add(superstate);
                    }
                    // If the pair of next states is not both final or non-final
                    // The automata are not equivalent
                    else equivalent = false;
                }
            }
        }
        // Return whether the automata are equivalent
        return equivalent;
    }
}
