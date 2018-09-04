package ru.papdevelop.vr.speechcloud;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Request {
    private String uuid;
    private String key;
    private Topic topic;
    private Lang lang;
    private Boolean disableAntimat;
    private byte[] payload;
}
