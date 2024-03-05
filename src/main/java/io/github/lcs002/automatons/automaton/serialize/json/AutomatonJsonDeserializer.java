package io.github.lcs002.automatons.automaton.serialize.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.serialize.AutomatonDeserializer;

/**
 * <h2>Automaton Deserializer</h2>
 * <p>Class that deserializes a JSON string into an Automaton object.</p>
 */
public class AutomatonJsonDeserializer implements AutomatonDeserializer {
    @Override
    public Automaton deserialize(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, Automaton.class);
    }
}
