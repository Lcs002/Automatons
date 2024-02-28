package com.lvum.automaton.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lvum.automaton.Automaton;

public interface AutomatonSerializer {
    String serialize(Automaton automaton) throws JsonProcessingException;
    String serializePretty(Automaton automaton) throws JsonProcessingException;
}
