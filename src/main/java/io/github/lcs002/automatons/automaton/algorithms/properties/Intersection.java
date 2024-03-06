package io.github.lcs002.automatons.automaton.algorithms.properties;

import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.Algorithm;
import io.github.lcs002.automatons.automaton.algorithms.utility.IsComplete;
import io.github.lcs002.automatons.automaton.algorithms.utility.IsDFA;
import org.javatuples.Pair;

import java.util.*;

/**
 * <h2>Algorithm for Automaton Intersection</h2>
 *
 * <p><b>Result:</b> <b>NFA</b> that accepts the intersection of the language between both automata.</p>
 * <p><b>Requisites:</b></p>
 * <ol>
 *  <li> The automaton <b>{@link IsDFA is a DFA}</b>.</li>
 *  <li> The automaton <b>{@link IsComplete is complete}</b>.</li>
 *  <li> The automatons have the same <b>alphabet</b>.</li>
 * </ol>
 */
public class Intersection extends Algorithm<Automaton> {
    private final Automaton automaton2;


    public Intersection(Automaton automaton1, Automaton automaton2) {
        super(automaton1);
        this.automaton2 = automaton2;
    }


    @Override
    public Automaton call() {
        // The automaton must be a dfa
        if (Boolean.FALSE.equals(automaton.run(new IsDFA()))) return null;
        // The automaton must be complete
        if (Boolean.FALSE.equals(automaton.run(new IsComplete()))) return null;
        // The automaton must have the same alphabet
        if (!automaton.getAlphabet().equals(automaton2.getAlphabet())) return null;

        Queue<Pair<String, String>> queue = new LinkedList<>();
        Set<Pair<String, String>> checked = new HashSet<>();
        // The result Automaton
        Automaton.Builder result = new Automaton.Builder().setAlphabet(automaton.getAlphabet());
        // The initial state of the result automaton is the intersection of the initial states of the two automaton
        Pair<String, String> initialStates = new Pair<>(automaton.getInitialState(), automaton2.getInitialState());
        result.setInitialState(initialStates.getValue0() + Automaton.SEPARATOR + initialStates.getValue1());
        queue.add(initialStates);
        checked.add(initialStates);
        // While there are states to check
        while (!queue.isEmpty()) {
            Pair<String, String> current = queue.poll();
            // For each symbol in the alphabet
            for (char symbol : automaton.getAlphabet()) {
                // Get the next original state given the current state and the symbol
                String nextOriginal = automaton.getTransitions().stream()
                        .filter(transition -> transition.from().equals(current.getValue0()))
                        .filter(transition -> transition.entry().equals(symbol))
                        .map(Automaton.Transition::to)
                        .findFirst()
                        .orElse(null);
                // Get the next other state given the current state and the symbol
                String nextOther = automaton2.getTransitions().stream()
                        .filter(transition -> transition.from().equals(current.getValue1()))
                        .filter(transition -> transition.entry().equals(symbol))
                        .map(Automaton.Transition::to)
                        .findFirst()
                        .orElse(null);
                Pair<String, String> nextPair = new Pair<>(nextOriginal, nextOther);
                // If the next state has not been checked yet
                if (!checked.contains(nextPair)) {
                    // Add it to the queue
                    queue.add(nextPair);
                    // And mark the current as checked
                    checked.add(current);
                    // If both states are final, add the new state as final
                    if (automaton.isFinal(nextOriginal) && automaton2.isFinal(nextOther)) {
                        result.addFinalState(nextPair.getValue0() + Automaton.SEPARATOR + nextPair.getValue1());
                    }
                }
                // Add the transition to the result automaton
                result.addTransition(
                        current.getValue0() + Automaton.SEPARATOR + current.getValue1(),
                        nextPair.getValue0() + Automaton.SEPARATOR + nextPair.getValue1(),
                        symbol
                );
            }
        }
        // Return the result automaton
        return result.build();
    }
}
