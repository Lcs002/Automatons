package com.lvum.algorithms;

import com.lvum.Automaton;
import org.javatuples.Pair;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Equivalency implements Algorithm<Boolean> {
    private Automaton other;


    public  Equivalency(Automaton other) {
        this.other = other;
    }


    @Override
    public Boolean run(Automaton automaton) {
        /*
        Two Automaton are equivalent if they satisfy the following conditions :
            1. The initial and final states of both the automatons must be same.
            2. Every pair of states chosen is from a different automaton only.
            3. While combining the states with the input alphabets, the pair results must be either both final states
             or intermediate states.(i.e. both should lie either in the final state or in the non-final state).
            4. If the resultant pair has different types of states, then it will be non-equivalent. (i.e. One lies in
             the final state and the other lies in the intermediate state ).
         */

        // If the languages are different, the automata are not equivalent
        if (!automaton.getLanguage().equals(other.getLanguage())) return false;

        // Set of pairs of states that have already been checked
        Set<Pair<String, String>> checked = new HashSet<>();
        // Queue of pairs of states to be checked
        Queue<Pair<String, String>> queue = new LinkedList<>();

        // Add the initial states to the queue and the set of checked pairs
        checked.add(new Pair<>(automaton.getInitialState(), other.getInitialState()));
        queue.add(new Pair<>(automaton.getInitialState(), other.getInitialState()));

        // While there are still pairs of states to check...
        while (!queue.isEmpty()) {
            // Get the current pair of states
            Pair<String, String> superstate = queue.poll();
            // For each symbol in the language
            for (Character symbol : automaton.getLanguage()) {
                // Get the next state of the current original automaton state given a certain symbol
                String nextOriginal = automaton.getTransitions().stream()
                        .filter(transition -> transition.from().equals(superstate.getValue0()))
                        .filter(transition -> transition.entry().equals(symbol))
                        .map(Automaton.Transition::to)
                        .findFirst()
                        .orElse(null);
                // Get the next state of the current other automaton state given a certain symbol
                String nextOther = other.getTransitions().stream()
                        .filter(transition -> transition.from().equals(superstate.getValue1()))
                        .filter(transition -> transition.entry().equals(symbol))
                        .map(Automaton.Transition::to)
                        .findFirst()
                        .orElse(null);
                // If the next state of either automaton is null,
                // there is no transition with this entry on the current superstate
                if (nextOriginal == null || nextOther == null) return false;
                // If the pair of next states has not been checked yet
                if (!checked.contains(new Pair<>(nextOriginal, nextOther))) {
                    // If both states are final or both are non-final
                    if (automaton.getFinalStates().contains(nextOriginal)
                            && other.getFinalStates().contains(nextOther)
                            || (!automaton.getFinalStates().contains(nextOriginal)
                            && !other.getFinalStates().contains(nextOther)))
                    {
                        // Add the pair to the queue and the set of checked pairs
                        queue.add(new Pair<>(nextOriginal, nextOther));
                        checked.add(new Pair<>(superstate.getValue0(), superstate.getValue1()));
                    }
                    // If the pair of next states is not both final or non-final
                    else
                    {
                        // The automata are not equivalent
                        return false;
                    }
                }
            }
        }
        // The automata are equivalent
        return true;
    }
}
