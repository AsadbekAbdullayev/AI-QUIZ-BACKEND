package uz.genesis.aiquest.webserver.models.dto.base;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public abstract class BaseDTO<R> {
    protected R id;


}
