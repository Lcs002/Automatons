package com.lvum;

import com.lvum.algorithms.Algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Automaton {
    public static final Character EPSILON = 'ε';
    public static final String SEPARATOR = "-";
    private Set<Character> language;
    private Set<String> states;
    private Set<Transition> transitions;
    private Set<String> finalStates;
    private String initialState;
    private int longestString;


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

    public void addTransition(String from, String to, Character entry) {
        if (!states.contains(from)) addState(from);
        if (!states.contains(to)) addState(to);
        if (!language.contains(entry)) return;
        transitions.add(new Transition(from, to, entry));
    }

    public void addEpsilonTransition(String from, String to) {
        if (!states.contains(from)) addState(from);
        if (!states.contains(to)) addState(to);
        transitions.add(new Transition(from, to, EPSILON));
    }

    public List<Transition> getTransitionsFrom(String from) {
        List<Transition> result = new ArrayList<>();
        for (Transition transition : transitions) {
            if (transition.from.equals(from)) {
                result.add(transition);
            }
        }
        return  result;
    }

    public List<Transition> getTransitionsTo(String to) {
        List<Transition> result = new ArrayList<>();
        for (Transition transition : transitions) {
            if (transition.to.equals(to)) {
                result.add(transition);
            }
        }
        return  result;
    }

    public Automaton run(Algorithm algorithm) {
        return algorithm.run(this);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%-"+longestString+"s |", " "));
        for (Character entry : language) {
            stringBuilder.append(String.format(" %-"+longestString+"s |", entry));
        }
        stringBuilder.append(String.format(" %-"+longestString+"s |", Automaton.EPSILON));
        stringBuilder.append('\n');
        for (String state : states) {
            stringBuilder.append(String.format("%-"+longestString+"s |", state));
            List<Transition> transitionsFrom = getTransitionsFrom(state);
            for (Character entry : language) {
                for (Transition transition : transitionsFrom) {
                    if (!transition.entry.equals(entry)) continue;
                    stringBuilder.append(String.format(" %-"+longestString+"s ", transition.to));
                }
                stringBuilder.append("|");
            }
            for (Transition transition : transitionsFrom) {
                if (!transition.entry.equals(EPSILON)) continue;
                stringBuilder.append(String.format(" %-"+longestString+"s ", transition.to));
            }
            stringBuilder.append("|");
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }


    private void updateLongestString(String value) {
        if (value.length() > longestString) {
            longestString = value.length();
        }
    }


    public record Transition(String from, String to, Character entry) {};
}
