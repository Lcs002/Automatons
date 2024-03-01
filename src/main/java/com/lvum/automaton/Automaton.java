package com.lvum.automaton;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.lvum.automaton.algorithms.Algorithm;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>Automaton</h1>
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
    private String initialState;


    private Automaton(Set<Character> alphabet, Set<Transition> transitions,
                      Set<String> finalStates, String initialState)
    {
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.finalStates = finalStates;
        this.initialState = initialState;
        this.states = new HashSet<>();

        transitions.forEach(transition -> {
                    if (!alphabet.contains(transition.entry)) throw new IllegalArgumentException("Entry not in alphabet");
                    if (!states.contains(transition.from)) states.add(transition.from);
                    if (!states.contains(transition.to)) states.add(transition.to);
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

    /**
     * Runs an algorithm on the automaton.
     * @param algorithm Algorithm to run.
     * @return Result of the algorithm.
     * @param <T> Type of the result.
     */
    public <T> T run(Algorithm<T> algorithm) {
        return algorithm.run(this);
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
