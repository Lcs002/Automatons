package com.github.lcs002.automatons.automaton.serialize.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lcs002.automatons.automaton.Automaton;
import com.github.lcs002.automatons.automaton.serialize.AutomatonDeserializer;

/**
 * <h1>Automaton Deserializer</h1>
 * <p>Class that deserializes a JSON string into an Automaton object.</p>
 */
public class AutomatonJsonDeserializer implements AutomatonDeserializer {
    @Override
    public Automaton deserialize(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, Automaton.class);
    }
}
