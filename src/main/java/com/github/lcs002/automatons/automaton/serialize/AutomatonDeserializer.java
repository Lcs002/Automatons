package com.github.lcs002.automatons.automaton.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.lcs002.automatons.automaton.Automaton;

public interface AutomatonDeserializer {
    Automaton deserialize(String json) throws JsonProcessingException;
}
