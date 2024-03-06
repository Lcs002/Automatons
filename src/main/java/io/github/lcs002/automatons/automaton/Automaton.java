package io.github.lcs002.automatons.automaton;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.lcs002.automatons.automaton.algorithms.Algorithm;
import io.github.lcs002.automatons.automaton.algorithms.conversion.NFAToDFA;
import io.github.lcs002.automatons.automaton.algorithms.conversion.NFAToDFAEpsilon;
import io.github.lcs002.automatons.automaton.algorithms.minimization.MinimizationEquivalence;
import io.github.lcs002.automatons.automaton.algorithms.minimization.MinimizationTable;
import io.github.lcs002.automatons.automaton.algorithms.properties.Complement;
import io.github.lcs002.automatons.automaton.algorithms.properties.Concatenation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <h2>Automaton</h2>
 * <p>Representation of a finite automaton.</p>
 */
@JsonDeserialize(builder = Automaton.Builder.class)
public class Automaton {
    /**
     * Character that represents the Epsilon symbol.
     * <p>
     * Used for Epsilon transitions.
     */
    @JsonIgnore
    public static final Character EPSILON = 'ε';
    /**
     * Character that represents the empty state.
     * <p>
     * Used for empty transitions.
     */
    @JsonIgnore
    public static final String EMPTY_STATE = "∅";
    /**
     * Separator between states.
     * <p>
     * Example with '-': S1-S2
     */
    @JsonIgnore
    public static final String SEPARATOR = "-";
    private final Set<Character> alphabet;
    private final Set<String> states;
    private final Set<Transition> transitions;
    private final Set<String> finalStates;
    private final String initialState;


    private Automaton(Set<Character> alphabet, Set<Transition> transitions,
                      Set<String> finalStates, String initialState)
    {
        checkIfSetAlphabet(alphabet);
        checkIfSetTransition(transitions);
        checkIfSetInitialState(initialState);
        checkIfSetFinalState(finalStates);

        this.alphabet = alphabet;
        this.transitions = transitions;
        this.finalStates = finalStates;
        this.initialState = initialState;
        this.states = new HashSet<>();

        transitions.forEach(transition -> {
                    if (!alphabet.contains(transition.entry)) throw new IllegalArgumentException("Entry not in alphabet");
                    states.add(transition.from);
                    states.add(transition.to);
                }
        );
    }


    public boolean isFinal(String state) {
        return finalStates.contains(state);
    }

    public Set<Character> getAlphabet() {
        return alphabet;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public String getInitialState() {
        return initialState;
    }

    public Set<String> getStates() {
        return states;
    }

    public Set<Transition> getTransitions() {
        return transitions;
    }


    private void checkIfSetAlphabet(Set<Character> alphabet) {
        // Check if the alphabet is not null
        if (alphabet == null) throw new IllegalArgumentException("Alphabet cannot be null");
        // Check if the alphabet is not empty
        if (alphabet.isEmpty()) throw new IllegalArgumentException("Alphabet cannot be empty");
    }

    private void checkIfSetTransition(Set<Transition> transitions) {
        // Check if the transitions are not null
        if (transitions == null) throw new IllegalArgumentException("Transitions cannot be null");
        // Check if the transitions are not empty
        if (transitions.isEmpty()) throw new IllegalArgumentException("Transitions cannot be empty");
    }

    private void checkIfSetInitialState(String initialState) {
        // Check if the initial state is not null
        if (initialState == null) throw new IllegalArgumentException("Initial state cannot be null");
        // Check if the initial state is not empty
        if (initialState.isEmpty()) throw new IllegalArgumentException("Initial state cannot be empty");
    }

    private void checkIfSetFinalState(Set<String> finalStates) {
        // Check if the final states are not null
        if (finalStates == null) throw new IllegalArgumentException("Final states cannot be null");
        // Check if the final states are not empty
        if (finalStates.isEmpty()) throw new IllegalArgumentException("Final states cannot be empty");
    }


    @Override
    public String toString() {
        // Get the biggest state relation
        long biggestRelation = states.stream()
                .mapToLong(state -> alphabet.stream()
                        .mapToLong(symbol -> transitions.stream()
                                .filter(transition -> transition.from.equals(state))
                                .filter(transition -> transition.entry.equals(symbol))
                                .count()
                        ).max().orElse(0)
                ).max().orElse(0);

        // Get the longest state name
        long longestState = states.stream()
                .mapToLong(String::length)
                .max().orElse(0) + 2;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%-"+longestState+"s |", " "));
        for (Character entry : alphabet) {
            stringBuilder.append(String.format(" %-"+((longestState+1)*biggestRelation)+"s |", entry));
        }
        stringBuilder.append('\n');
        for (String state : states) {
            int l = 0;
            if (getInitialState().equals(state)) {
                stringBuilder.append("->");
                l+=2;
            }
            if (getFinalStates().contains(state)) {
                stringBuilder.append("*");
                l+=1;
            }
            stringBuilder.append(String.format("%-"+(longestState-l) +"s |", state));
            Set<Transition> transitionsFrom = getTransitions().stream()
                    .filter(transition -> transition.from.equals(state))
                    .collect(Collectors.toSet());
            for (Character entry : alphabet) {
                String to = " ";
                boolean first = true;
                for (Transition transition : transitionsFrom) {
                    if (!transition.entry.equals(entry)) continue;
                    if (!first) to += ", ";
                    to += transition.to;
                    first = false;
                }
                stringBuilder.append(String.format(" %-"+((longestState+1)*biggestRelation)+"s ", to)
                        .replace('\u0000', ' '));
                stringBuilder.append("|");
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    /**
     * Representation of a transition between two states given an entry.
     * @param from Origin state.
     * @param to Destination state.
     * @param entry Symbol that triggers the transition.
     */
    public record Transition(String from, String to, Character entry) {}

    @JsonPOJOBuilder
    public static class Builder {
        Set<Character> alphabet;
        Set<Transition> transitions;
        Set<String> finalStates;
        String initialState;


        public Builder() {
            this.alphabet = new HashSet<>();
            this.transitions = new HashSet<>();
            this.finalStates = new HashSet<>();
        }


        public Builder setAlphabet(Set<Character> alphabet) {
            this.alphabet = alphabet;
            return this;
        }

        public Builder setTransitions(Set<Transition> transitions) {
            this.transitions = transitions;
            return this;
        }

        public Builder setFinalStates(Set<String> finalStates) {
            this.finalStates = finalStates;
            return this;
        }

        public Builder setInitialStates(String initialState) {
            this.initialState = initialState;
            return this;
        }

        public Builder addTransition(String from, String to, Character entry) {
            Transition transition = new Transition(from, to, entry);
            this.transitions.add(transition);
            return this;
        }

        public Builder addFinalState(String state) {
            this.finalStates.add(state);
            return this;
        }

        public Builder setInitialState(String state) {
            this.initialState = state;
            return this;
        }

        public Automaton build() {
            return new Automaton(alphabet, transitions, finalStates, initialState);
        }
    }
}
