package com.ms.framework.world;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MapsWorld {
    @Value("${textsearch:}")
    private String textsearch;
    @Value("${mapskey:}")
    private String mapskey;
}
