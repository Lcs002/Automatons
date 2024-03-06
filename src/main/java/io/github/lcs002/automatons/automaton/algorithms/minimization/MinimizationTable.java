package io.github.lcs002.automatons.automaton.algorithms.minimization;

import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.Algorithm;
import org.javatuples.Pair;
import java.util.*;


/**
 * Minimization of a DFA using the <a href="https://www.geeksforgeeks.org/minimization-of-dfa/">Myhill-Nerode Theorem.</a>
 */
// TODO: Implement the Minimization of a DFA using the Myhill-Nerode Theorem
public class MinimizationTable extends Algorithm<Automaton> {

    public MinimizationTable(Automaton automaton) {
        super(automaton);
    }

    @Override
    public Automaton call() {
        /*
        if (automaton.getStates().size() < 2) return automaton;

        Automaton.Builder result = new Automaton.Builder().setAlphabet(automaton.getAlphabet());

        // List of Set of States we have already checked
        Map<Pair<String, String>, Boolean> table = createTable(automaton);

        // Mark all the pairs (Qa,Qb) such a that Qa is Final state and Qb is Non-Final State.
        for (Pair<String, String> pair : table.keySet()) {
            if (automaton.isFinal(pair.getValue0()) ^ automaton.isFinal(pair.getValue1())) {
                table.put(pair, true);
            }
        }

        for (Map.Entry<Pair<String, String>, Boolean> entry : table.entrySet()) {
            // If there is any unmarked pair (Qa,Qb)
            if (Boolean.FALSE.equals(entry.getValue())) {
                // Such a that δ(Qa,x) and δ(Qb,x) is marked
                for (Character symbol : automaton.getAlphabet()) {
                    Pair<String, String> pair = new Pair<>(
                            // δ(Qa,x)
                            automaton.getTransitions().stream()
                                    .filter(transition -> transition.from().equals(entry.getKey().getValue0()))
                                    .filter(transition -> transition.entry().equals(symbol))
                                    .map(Automaton.Transition::to)
                                    .findFirst().orElse(null),
                            // δ(Qb,x)
                            automaton.getTransitions().stream()
                                    .filter(transition -> transition.from().equals(entry.getKey().getValue1()))
                                    .filter(transition -> transition.entry().equals(symbol))
                                    .map(Automaton.Transition::to)
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
                    result.addState(entry.getKey().getValue0() + Automaton.SEPARATOR + entry.getKey().getValue1());
                }
            }
        }

        result.addState(automaton.getInitialState());
        result.setInitialState(automaton.getInitialState());

        for (Pair<String, String> pair : table.keySet()) {
            if (Boolean.FALSE.equals(table.get(pair))) {
                result.addTransition(
                        pair.getValue0() + Automaton.SEPARATOR + pair.getValue1(),
                        pair.getValue0() + Automaton.SEPARATOR + pair.getValue1(),
                        automaton.getAlphabet().iterator().next()
                );
            }
        }
        */
        return null;
    }


    private Map<Pair<String, String>, Boolean> createTable(Automaton automaton) {
        Map<Pair<String, String>, Boolean> table = new HashMap<>();
        List<String> states = automaton.getStates().stream().toList();
        for (int i = 0; i < states.size() - 1; i++) {
            for (int j = 1; j < states.size(); j++) {
                table.put(new Pair<>(states.get(i), states.get(j)), false);
            }
        }
        return table;
    }
}
