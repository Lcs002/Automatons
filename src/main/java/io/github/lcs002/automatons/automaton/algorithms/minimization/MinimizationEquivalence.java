package io.github.lcs002.automatons.automaton.algorithms.minimization;

import com.sun.source.tree.BinaryTree;
import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.Algorithm;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Minimization of a DFA using the <a href="https://www.geeksforgeeks.org/minimization-of-dfa/">Equivalence Method.</a>
 */
// TODO: Implement the Minimization of a DFA using the Equivalence Method
public final class MinimizationEquivalence extends Algorithm<Automaton> {

    public MinimizationEquivalence(Automaton automaton) {
        super(automaton);
    }

    @Override
    public Automaton call() {
        // The automaton must be a DFA
        if (Boolean.FALSE.equals(automaton.isDfa())) return null;
        // The automaton must be complete
        if (Boolean.FALSE.equals(automaton.isComplete())) return null;
        // TODO Add another condition called has unreachable states

        Map<String, Boolean> partitionsAndFinalized = new HashMap<>();
        Map<String, String> statesAndPartition = new HashMap<>();

        // Register the final states partition
        partitionsAndFinalized.put("C1", false);
        // Register the non-final states partition
        partitionsAndFinalized.put("C2", false);

        // Register the partition of each state
        for (String state : automaton.getStates()) {
            if (automaton.isFinal(state)) statesAndPartition.put(state, "C1");
            else statesAndPartition.put(state, "C2");
        }

        boolean hasNewPartition = true;
        while (hasNewPartition) {
            hasNewPartition = false;

            for (Map.Entry<String, Boolean> entry : partitionsAndFinalized.entrySet()) {
                // If the partition is finalized, skip
                if (Boolean.TRUE.equals(entry.getValue())) continue;

                String partition = entry.getKey();
                Set<String> partitionStates = statesAndPartition.entrySet().stream()
                        .filter(e -> e.getValue().equals(partition))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toSet());

                Map<String, Set<String>> newPartitionsAndState = new HashMap<>();
                for (String state : partitionStates) {
                    Set<String> nextPartitions = new HashSet<>();
                    for (char symbol : automaton.getAlphabet()) {
                        String nextState = automaton.getTransitions().stream()
                                .filter(transition -> transition.from().equals(state))
                                .filter(transition -> transition.entry().equals(symbol))
                                .map(Automaton.Transition::to)
                                .findFirst().orElse(null);
                        String nextPartition = statesAndPartition.get(nextState);
                        nextPartitions.add(nextPartition);
                    }
                    if (!newPartitionsAndState.containsKey(nextPartitions)) {
                        newPartitionsAndState.put(String.join("", nextPartitions), new HashSet<>());
                    } else {
                        newPartitionsAndState.get(String.join("", nextPartitions)).add(state);
                    }
                }

                if (newPartitionsAndState.size() > 1) {
                    hasNewPartition = true;
                    partitionsAndFinalized.remove(partition);
                    int i = 1;
                    for (Map.Entry<Set<String>, Set<String>> entry1 : newPartitionsAndState.entrySet()) {
                        String newPartition = partition + i++;
                        partitionsAndFinalized.put(newPartition, false);
                        for (String state : entry1.getValue()) {
                            statesAndPartition.put(state, newPartition);
                        }
                    }
                }
            }
        }

        return null;
    }
}
