package com.mildlamb.properties;

import com.mildlamb.pojo.Lamb;
import com.mildlamb.pojo.Wolf;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spirit")
@Data
public class SpiritProperties {
    private Lamb lamb;
    private Wolf wolf;
}
