package ru.papdevelop.vr.speechcloud;

import lombok.Builder;
import lombok.Data;

import java.io.File;

@Data
@Builder
public class Request {
    private String uuid;
    private String key;
    private Topic topic;
    private Lang lang;
    private Boolean disableAntimat;
    private File file;
}
