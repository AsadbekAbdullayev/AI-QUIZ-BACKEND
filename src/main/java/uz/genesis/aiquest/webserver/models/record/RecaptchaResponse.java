package uz.genesis.aiquest.webserver.models.record;

public record RecaptchaResponse(Boolean success,String challege_ts,String hostname,Double score, String action) {
}
