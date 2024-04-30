package uz.genesis.aiquest.webserver.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EGPTType {
    GPT_4_WITH_JSON_MODE("gpt-4-1106-preview"),
    GPT_3_5("gpt-3.5-turbo");

    private final String caption;
}
