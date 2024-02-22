package com.lvum;

import com.lvum.algorithms.Algorithm;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Representation of an automaton with methods to edit itself.
 */
public class Automaton {
    /**
     * Character that represents the Epsilon symbol.
     * <p>
     * Used for Epsilon transitions.
     */
    public static final Character EPSILON = 'ε';
    /**
     * Separator between states.
     * <p>
     * Example with '-': S1-S2
     */
    public static final String SEPARATOR = "-";
    private final Set<Character> language;
    private final Set<String> states;
    private final Set<Transition> transitions;
    private final Set<String> finalStates;
    private String initialState;
    private int longestString;


    /**
     * Creates a new automaton.
     * @param language Set of symbols the automaton can read.
     */
    public Automaton(Set<Character> language) {
        this.language = language;
        this.states = new HashSet<>();
        this.finalStates = new HashSet<>();
        this.transitions = new HashSet<>();
    }


    public Set<Character> getLanguage() {
        return language;
    }

    public void addFinalState(String state) {
        if (!states.contains(state)) addState(state);
        finalStates.add(state);
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
        updateLongestString(state);
    }

    public Set<String> getStates() {
        return states;
    }

    public void addTransition(String from, String to, Character entry) {
        if (!states.contains(from)) addState(from);
        if (!states.contains(to)) addState(to);
        if (!language.contains(entry)) return;
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%-"+longestString+"s |", " "));
        for (Character entry : language) {
            stringBuilder.append(String.format(" %-"+longestString+"s |", entry));
        }
        stringBuilder.append('\n');
        for (String state : states) {
            stringBuilder.append(String.format("%-"+longestString+"s |", state));
            Set<Transition> transitionsFrom = getTransitions().stream()
                    .filter(transition -> transition.from.equals(state))
                    .collect(Collectors.toSet());
            for (Character entry : language) {
                for (Transition transition : transitionsFrom) {
                    if (!transition.entry.equals(entry)) continue;
                    stringBuilder.append(String.format(" %-"+longestString+"s ", transition.to)
                            .replace('\u0000', ' '));
                }
                stringBuilder.append("|");
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }


    private void updateLongestString(String value) {
        if (value.length() > longestString) {
            longestString = value.length();
        }
    }


    /**
     * Representation of a transition between two states given an entry.
     * @param from Origin state.
     * @param to Destination state.
     * @param entry Symbol that triggers the transition.
     */
    public record Transition(String from, String to, Character entry) {}
}
