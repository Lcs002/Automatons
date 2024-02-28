package com.lvum.automaton.algorithms.minimization;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Algorithm;
import org.javatuples.Pair;
import java.util.*;


/**
 * Minimization of a DFA using the <a href="https://www.geeksforgeeks.org/minimization-of-dfa/">Myhill-Nerode Theorem.</a>
 */
// TODO: Implement the Minimization of a DFA using the Myhill-Nerode Theorem
public class MinimizationTable implements Algorithm<Automata> {

    @Override
    public Automata run(Automata automata) {
        if (automata.getStates().size() < 2) return automata;

        Automata result = new Automata(automata.getAlphabet());

        // List of Set of States we have already checked
        Map<Pair<String, String>, Boolean> table = createTable(automata);

        // Mark all the pairs (Qa,Qb) such a that Qa is Final state and Qb is Non-Final State.
        for (Pair<String, String> pair : table.keySet()) {
            if (automata.isFinal(pair.getValue0()) ^ automata.isFinal(pair.getValue1())) {
                table.put(pair, true);
            }
        }

        for (Map.Entry<Pair<String, String>, Boolean> entry : table.entrySet()) {
            // If there is any unmarked pair (Qa,Qb)
            if (Boolean.FALSE.equals(entry.getValue())) {
                // Such a that δ(Qa,x) and δ(Qb,x) is marked
                for (Character symbol : automata.getAlphabet()) {
                    Pair<String, String> pair = new Pair<>(
                            // δ(Qa,x)
                            automata.getTransitions().stream()
                                    .filter(transition -> transition.from().equals(entry.getKey().getValue0()))
                                    .filter(transition -> transition.entry().equals(symbol))
                                    .map(Automata.Transition::to)
                                    .findFirst().orElse(null),
                            // δ(Qb,x)
                            automata.getTransitions().stream()
                                    .filter(transition -> transition.from().equals(entry.getKey().getValue1()))
                                    .filter(transition -> transition.entry().equals(symbol))
                                    .map(Automata.Transition::to)
                                    .findFirst().orElse(null)
                    );
                    if (Boolean.TRUE.equals(table.get(pair))) {
                        // Then mark (Qa,Qb)
                        entry.setValue(true);
                        break; // Go to the next pair since this one is already marked.
                    }
                }
                // Combine all the unmarked pairs and make them as a single state in the minimized DFA.
                if (Boolean.FALSE.equals(entry.getValue())) {
                    result.addState(entry.getKey().getValue0() + Automata.SEPARATOR + entry.getKey().getValue1());
                }
            }
        }

        result.addState(automata.getInitialState());
        result.setInitialState(automata.getInitialState());

        for (Pair<String, String> pair : table.keySet()) {
            if (Boolean.FALSE.equals(table.get(pair))) {
                result.addTransition(
                        pair.getValue0() + Automata.SEPARATOR + pair.getValue1(),
                        pair.getValue0() + Automata.SEPARATOR + pair.getValue1(),
                        automata.getAlphabet().iterator().next()
                );
            }
        }

        return null;
    }


    private Map<Pair<String, String>, Boolean> createTable(Automata automata) {
        Map<Pair<String, String>, Boolean> table = new HashMap<>();
        List<String> states = automata.getStates().stream().toList();
        for (int i = 0; i < states.size() - 1; i++) {
            for (int j = 1; j < states.size(); j++) {
                table.put(new Pair<>(states.get(i), states.get(j)), false);
            }
        }
        return table;
    }
}
