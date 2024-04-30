package uz.genesis.aiquest.webserver.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Geolocation{
    public String country;
    public String city;
    public String full;
}
