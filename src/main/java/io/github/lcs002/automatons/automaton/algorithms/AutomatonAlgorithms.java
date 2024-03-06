package io.github.lcs002.automatons.automaton.algorithms;

import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.conversion.NFAToDFA;
import io.github.lcs002.automatons.automaton.algorithms.conversion.NFAToDFAEpsilon;
import io.github.lcs002.automatons.automaton.algorithms.minimization.MinimizationEquivalence;
import io.github.lcs002.automatons.automaton.algorithms.minimization.MinimizationTable;
import io.github.lcs002.automatons.automaton.algorithms.properties.*;
import io.github.lcs002.automatons.automaton.algorithms.utility.GetEpsilonClosure;
import io.github.lcs002.automatons.automaton.algorithms.utility.IsComplete;
import io.github.lcs002.automatons.automaton.algorithms.utility.IsDFA;
import io.github.lcs002.automatons.automaton.algorithms.utility.RemoveUnreachable;

import java.util.Set;

public final class AutomatonAlgorithms {
    private AutomatonAlgorithms() {}

    public static Automaton nfaToDfa(Automaton automaton) {
        return new NFAToDFA(automaton).call();
    }

    public static Automaton nfaToEpsilon(Automaton automaton) {
        return new NFAToDFAEpsilon(automaton).call();
    }

    public static Automaton minimizationEquivalence(Automaton automaton) {
        return new MinimizationEquivalence(automaton).call();
    }

    public static Automaton minimizationTable(Automaton automaton) {
        return new MinimizationTable(automaton).call();
    }

    public static Automaton complement(Automaton automaton) {
        return new Complement(automaton).call();
    }

    public static Automaton concatenation(Automaton automaton1, Automaton automaton2) {
        return new Concatenation(automaton1, automaton2).call();
    }

    public static Automaton intersection(Automaton automaton1, Automaton automaton2) {
        return new Intersection(automaton1, automaton2).call();
    }

    public static Automaton reversion(Automaton automaton) {
        return new Reversion(automaton).call();
    }

    public static Automaton union (Automaton automaton1, Automaton automaton2) {
        return new Union(automaton1, automaton2).call();
    }

    public static Set<String> eClosure(Automaton automaton, String state) {
        return new GetEpsilonClosure(automaton, state).call();
    }

    public static Boolean isComplete(Automaton automaton) {
        return new IsComplete(automaton).call();
    }

    public static Boolean isDFA(Automaton automaton) {
        return new IsDFA(automaton).call();
    }

    public static Automaton removeUnreachable(Automaton automaton) {
        return new RemoveUnreachable(automaton).call();
    }

    public static Automaton complete(Automaton automaton) {
        return new Complete(automaton).call();
    }
}
