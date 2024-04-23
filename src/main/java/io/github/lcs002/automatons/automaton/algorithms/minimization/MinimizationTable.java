package io.github.lcs002.automatons.automaton.algorithms.minimization;

import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.Algorithm;
import org.javatuples.Pair;
import java.util.*;


/**
 * Minimization of a DFA using the <a href="https://www.geeksforgeeks.org/minimization-of-dfa/">Myhill-Nerode Theorem.</a>
 */
// TODO: Implement the Minimization of a DFA using the Myhill-Nerode Theorem
public final class MinimizationTable extends Algorithm<Automaton> {

    public MinimizationTable(Automaton automaton) {
        super(automaton);
    }

    @Override
    public Automaton call() {
        Automaton.Builder result = new Automaton.Builder()
                .setAlphabet(automaton.getAlphabet());

        // List of Set of States we have already checked
        Map<Pair<String, String>, Boolean> table = createTable(automaton);

        // Mark all the pairs (Qa,Qb) such a that Qa is Final state and Qb is Non-Final State.
        table.keySet().stream()
                .filter(pair -> automaton.isFinal(pair.getValue0()) ^ automaton.isFinal(pair.getValue1()))
                .forEach(pair -> table.put(pair, true));

        boolean hasMarked = true;
        while (hasMarked) {
            hasMarked = false;
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
                            hasMarked = true;
                            break; // Go to the next pair since this one is already marked.
                        }
                    }
                }
            }
        }

        // Group the non-marked pairs if they share one or more states
        Map<String, Set<String>> partitions = new HashMap<>();
for (Map.Entry<Pair<String, String>, Boolean> entry : table.entrySet()) {
            if (Boolean.FALSE.equals(entry.getValue())) {
                String state0 = entry.getKey().getValue0();
                String state1 = entry.getKey().getValue1();
                String partition = partitions.keySet().stream()
                        .filter(key -> partitions.get(key).contains(state0) || partitions.get(key).contains(state1))
                        .findFirst()
                        .orElse(null);
                if (partition == null) {
                    partition = state0 + state1;
                    partitions.put(partition, new HashSet<>());
                }
                partitions.get(partition).add(state0);
                partitions.get(partition).add(state1);
            }
        }


        return result.build();
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
