package ru.kata.spring.boot_security.demo.configs;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import ru.kata.spring.boot_security.demo.model.Role;

import java.io.IOException;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(GrantedAuthority.class, new GrantedAuthoritySerializer());
        module.addDeserializer(GrantedAuthority.class, new GrantedAuthorityDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }

    private static class GrantedAuthoritySerializer extends JsonSerializer<GrantedAuthority> {
        @Override
        public void serialize(GrantedAuthority value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.getAuthority());
        }
    }

    private static class GrantedAuthorityDeserializer extends JsonDeserializer<GrantedAuthority> {
        @Override
        public GrantedAuthority deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String authority = p.getValueAsString();
            return new Role(authority);
        }
    }
}