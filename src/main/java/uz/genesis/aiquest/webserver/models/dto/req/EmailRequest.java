package uz.genesis.aiquest.webserver.models.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EmailRequest {

    private String recipientEmail;
    private String profileAccessUrl;


}
