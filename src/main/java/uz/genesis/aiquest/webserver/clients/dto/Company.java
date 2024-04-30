package uz.genesis.aiquest.webserver.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Company {
    private String name;
    private String universalName;
    private String logo;
}