import com.lvum.Automaton;
import com.lvum.algorithms.NFAToDFAEpsilon;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NFAToDFATests {
    @Test
    void sameLanguage() {
        // Test 1
        // The language of the automaton must not change after the conversion
        Set<Character> language = new HashSet<>(Arrays.asList('0', '1'));
        Automaton automaton = new Automaton(language);

        automaton.addTransition("S1", "S2", '0');
        automaton.addTransition("S1", "S1", '0');
        automaton.addTransition("S1", "S1", '1');
        automaton.addTransition("S2", "S1", '0');
        automaton.addTransition("S2", "S2", '1');
        automaton.setInitialState("S1");
        automaton.addFinalState("S1");

        Automaton result = automaton.run(new NFAToDFAEpsilon());

        assertEquals(result.getLanguage(), language);
    }

    @Test
    void deterministic() {
        // Test 2
        // The resulting automaton must not have any state with the same entry more than once
        Set<Character> language = new HashSet<>(Arrays.asList('0', '1'));
        Automaton automaton = new Automaton(language);

        automaton.addTransition("S1", "S2", '0');
        automaton.addTransition("S1", "S1", '0');
        automaton.addTransition("S1", "S1", '1');
        automaton.addTransition("S2", "S1", '0');
        automaton.addTransition("S2", "S2", '1');
        automaton.setInitialState("S1");
        automaton.addFinalState("S1");

        Automaton result = automaton.run(new NFAToDFAEpsilon());

        for (Automaton.Transition transition : result.getTransitions()) {
            assertEquals(1, result.getTransitions().stream()
                    .filter(t -> t.from().equals(transition.from()))
                    .filter(t -> t.entry().equals(transition.entry()))
                    .count());
        }
    }

    @Test
    void equivalent() {
        // Test 3
        // The resulting automaton must be equivalent to the original one
        /*
        Two Automaton are equivalent if they satisfy the following conditions :
            1. The initial and final states of both the automatons must be same.
            2. Every pair of states chosen is from a different automaton only.
            3. While combining the states with the input alphabets, the pair results must be either both final states
             or intermediate states.(i.e. both should lie either in the final state or in the non-final state).
            4. If the resultant pair has different types of states, then it will be non-equivalent. (i.e. One lies in
             the final state and the other lies in the intermediate state ).
         */

        Set<Character> language = new HashSet<>(Arrays.asList('0', '1'));
        Automaton original = new Automaton(language);

        original.addTransition("S1", "S2", '0');
        original.addTransition("S1", "S1", '0');
        original.addTransition("S1", "S1", '1');
        original.addTransition("S2", "S1", '0');
        original.addTransition("S2", "S2", '1');
        original.setInitialState("S1");
        original.addFinalState("S1");

        Automaton result = original.run(new NFAToDFAEpsilon());

        assertTrue(original.isEquivalent(result));
    }
}
