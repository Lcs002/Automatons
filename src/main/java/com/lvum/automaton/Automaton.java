package com.lvum.automaton;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.lvum.automaton.algorithms.Algorithm;
import com.lvum.automaton.serialize.AutomatonSerializer;

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


    /**
     * Creates a new automaton.
     * @param alphabet Set of symbols the automaton can read.
     */
    public Automaton(Set<Character> alphabet) {
        this.alphabet = alphabet;
        this.states = new HashSet<>();
        this.finalStates = new HashSet<>();
        this.transitions = new HashSet<>();
    }

    private Automaton(Set<Character> alphabet, Set<String> states, Set<Transition> transitions,
                      Set<String> finalStates, String initialState)
    {
        this.alphabet = alphabet;
        this.states = states;
        this.transitions = transitions;
        this.finalStates = finalStates;
        this.initialState = initialState;
    }


    public Set<Character> getAlphabet() {
        return alphabet;
    }

    public void addFinalState(String state) {
        if (!states.contains(state)) addState(state);
        finalStates.add(state);
    }

    public boolean isFinal(String state) {
        return finalStates.contains(state);
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public void setInitialState(String state) {
        initialState = state;
    }

    public String getInitialState() {
        return initialState;
    }

    public void addState(String state) {
        states.add(state);
    }

    public Set<String> getStates() {
        return states;
    }

    public void addTransition(String from, String to, Character entry) {
        if (!states.contains(from)) addState(from);
        if (!states.contains(to)) addState(to);
        if (!alphabet.contains(entry)) return;
        transitions.add(new Transition(from, to, entry));
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
    static class Builder {
        Set<Character> alphabet;
        Set<String> states;
        Set<Transition> transitions;
        Set<String> finalStates;
        String initialState;

        public Builder withAlphabet(Set<Character> alphabet) {
            this.alphabet = alphabet;
            return this;
        }

        public Builder withStates(Set<String> states) {
            this.states = states;
            return this;
        }

        public Builder withTransitions(Set<Transition> transitions) {
            this.transitions = transitions;
            return this;
        }

        public Builder withFinalStates(Set<String> finalStates) {
            this.finalStates = finalStates;
            return this;
        }

        public Builder withInitialState(String initialState) {
            this.initialState = initialState;
            return this;
        }

        public Automaton build() {
            return new Automaton(alphabet, states, transitions, finalStates, initialState);
        }
    }
}