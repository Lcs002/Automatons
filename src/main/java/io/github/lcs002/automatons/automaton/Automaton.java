package io.github.lcs002.automatons.automaton;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.github.lcs002.automatons.automaton.algorithms.Complete;
import io.github.lcs002.automatons.automaton.algorithms.Equivalency;
import io.github.lcs002.automatons.automaton.algorithms.conversion.NFAToDFA;
import io.github.lcs002.automatons.automaton.algorithms.conversion.NFAEpsilonToDFA;
import io.github.lcs002.automatons.automaton.algorithms.minimization.MinimizationEquivalence;
import io.github.lcs002.automatons.automaton.algorithms.minimization.MinimizationTable;
import io.github.lcs002.automatons.automaton.algorithms.properties.*;
import io.github.lcs002.automatons.automaton.algorithms.utility.GetEpsilonClosure;
import io.github.lcs002.automatons.automaton.algorithms.utility.IsComplete;
import io.github.lcs002.automatons.automaton.algorithms.utility.IsDFA;
import io.github.lcs002.automatons.automaton.algorithms.utility.RemoveUnreachable;

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


    /** Set of characters that represents the alphabet.*/
    private final Set<Character> alphabet;

    /** Set of states of the automaton.*/
    private final Set<String> states;

    /** Set of transitions of the automaton.*/
    private final Set<Transition> transitions;

    /** Set of final states of the automaton.*/
    private final Set<String> finalStates;

    /** Initial state of the automaton.*/
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


    /**
     * Check if a state is a final state.
     * @param state State to check.
     * @return True if the state is a final state, false otherwise.
     */
    public boolean isFinal(String state) {
        return finalStates.contains(state);
    }

    /**
     * Get the alphabet of the automaton.
     * @return Set of characters that represents the alphabet.
     */
    public Set<Character> getAlphabet() {
        return alphabet;
    }

    /**
     * Get the final states of the automaton.
     * @return Set of final states.
     */
    public Set<String> getFinalStates() {
        return finalStates;
    }

    /**
     * Get the initial state of the automaton.
     * @return Initial state.
     */
    public String getInitialState() {
        return initialState;
    }

    /**
     * Get the states of the automaton.
     * @return Set of states.
     */
    public Set<String> getStates() {
        return states;
    }

    /**
     * Get the transitions of the automaton.
     * @return Set of transitions.
     */
    public Set<Transition> getTransitions() {
        return transitions;
    }


    /**
     * Convert the NFA to a DFA.
     * @return The DFA resultant of the conversion.
     * @see NFAToDFA
     */
    public Automaton nfaToDfa() {
        return new NFAToDFA(this).call();
    }

    /**
     * Convert the NFA with Epsilon transitions to a DFA.
     * @return The DFA resultant of the conversion.
     * @see NFAEpsilonToDFA
     */
    public Automaton nfaEpsilonToDfa() {
        return new NFAEpsilonToDFA(this).call();
    }

    /**
     * Minimize the automaton using the equivalence method.
     * @return The minimized DFA.
     * @see MinimizationEquivalence
     */
    public Automaton minimizeByEquivalence() {
        return new MinimizationEquivalence(this).call();
    }

    /**
     * Minimize the automaton using the table method.
     * @return The minimized DFA.
     * @see MinimizationTable
     */
    public Automaton minimizeByTable() {
        return new MinimizationTable(this).call();
    }

    /**
     * Calculates the complement of the automaton.
     * @return The complement of the automaton.
     * @see Complement
     */
    public Automaton complement() {
        return new Complement(this).call();
    }

    /**
     * Concatenates the automaton with another.
     * @param other Automaton to concatenate with.
     * @return The automaton resultant of the concatenation.
     * @see Concatenation
     */
    public Automaton concatenate(Automaton other) {
        return new Concatenation(this, other).call();
    }

    /**
     * Intersects the automaton with another.
     * @param other Automaton to intersect with.
     * @return The automaton resultant of the intersection.
     */
    public Automaton intersect(Automaton other) {
        return new Intersection(this, other).call();
    }

    /**
     * Reverses the automaton.
     * @return The automaton resultant of the reversal.
     * @see Reversion
     */
    public Automaton reverse() {
        return new Reversion(this).call();
    }

    /**
     * Unites the automaton with another.
     * @param other Automaton to unite with.
     * @return The automaton resultant of the union.
     * @see Union
     */
    public Automaton unite(Automaton other) {
        return new Union(this, other).call();
    }

    /**
     * Removes the unreachable states from the automaton.
     * @return The automaton without unreachable states.
     * @see RemoveUnreachable
     */
    public Automaton removeUnreachable() {
        return new RemoveUnreachable(this).call();
    }

    /**
     * Completes the automaton.
     * @return The automaton resultant of the completion.
     * @see Complete
     */
    public Automaton complete() {
        return new Complete(this).call();
    }

    /**
     * Get the Epsilon closure of a state.
     * @param state State to get the Epsilon closure.
     * @return Set of states that are reachable from the state with Epsilon transitions.
     * @see GetEpsilonClosure
     */
    public Set<String> eClosure(String state) {
        return new GetEpsilonClosure(this, state).call();
    }

    /**
     * Checks if the automaton is complete.
     * @return True if the automaton is complete, false otherwise.
     * @see IsComplete
     */
    public Boolean isComplete() {
        return new IsComplete(this).call();
    }

    /**
     * Checks if the automaton is a DFA.
     * @return True if the automaton is a DFA, false otherwise.
     * @see IsDFA
     */
    public Boolean isDfa() {
        return new IsDFA(this).call();
    }

    /**
     * Checks if the automaton is equivalent to another.
     * @param other Automaton to compare with.
     * @return True if the automaton is equivalent to the other, false otherwise.
     * @see Equivalency
     */
    public Boolean isEquivalent(Automaton other) {
        return new Equivalency(this, other).call();
    }


    /** Checks if the alphabet is not null and not empty. */
    private void checkIfSetAlphabet(Set<Character> alphabet) {
        // Check if the alphabet is not null
        if (alphabet == null) throw new IllegalArgumentException("Alphabet cannot be null");
        // Check if the alphabet is not empty
        if (alphabet.isEmpty()) throw new IllegalArgumentException("Alphabet cannot be empty");
    }

    /** Checks if the transitions are not null and not empty. */
    private void checkIfSetTransition(Set<Transition> transitions) {
        // Check if the transitions are not null
        if (transitions == null) throw new IllegalArgumentException("Transitions cannot be null");
        // Check if the transitions are not empty
        if (transitions.isEmpty()) throw new IllegalArgumentException("Transitions cannot be empty");
    }

    /** Checks if the initial state is not null and not empty. */
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

    /**
     * Automaton Builder.
     */
    @JsonPOJOBuilder
    public static class Builder {
        private Set<Character> alphabet;
        private Set<Transition> transitions;
        private Set<String> finalStates;
        private String initialState;


        /**
         * Create a new automaton builder.
         */
        public Builder() {
            this.alphabet = new HashSet<>();
            this.transitions = new HashSet<>();
            this.finalStates = new HashSet<>();
        }


        /**
         * Set the alphabet of the automaton.
         * @param alphabet Set of characters that represents the alphabet.
         * @return The builder.
         */
        public Builder setAlphabet(Set<Character> alphabet) {
            this.alphabet = alphabet;
            return this;
        }

        /**
         * Set the transitions of the automaton.
         * @param transitions Set of transitions.
         * @return The builder.
         */
        public Builder setTransitions(Set<Transition> transitions) {
            this.transitions = transitions;
            return this;
        }

        /**
         * Add a transition to the automaton. Entries must be present in the alphabet.
         * @param from Origin state.
         * @param to Destination state.
         * @param entry Symbol that triggers the transition.
         * @return The builder.
         */
        public Builder addTransition(String from, String to, Character entry) {
            Transition transition = new Transition(from, to, entry);
            this.transitions.add(transition);
            return this;
        }

        /**
         * Set the final states of the automaton.
         * @param finalStates Set of final states.
         * @return The builder.
         */
        public Builder setFinalStates(Set<String> finalStates) {
            this.finalStates = finalStates;
            return this;
        }

        /**
         * Add a final state to the automaton.
         * @param state Final state.
         * @return The builder.
         */
        public Builder addFinalState(String state) {
            this.finalStates.add(state);
            return this;
        }

        /**
         * Set the initial state of the automaton.
         * @param initialState Initial state.
         * @return The builder.
         */
        public Builder setInitialState(String initialState) {
            this.initialState = initialState;
            return this;
        }

        /**
         * Build the automaton.
         * @return The automaton.
         */
        public Automaton build() {
            return new Automaton(alphabet, transitions, finalStates, initialState);
        }
    }
}
