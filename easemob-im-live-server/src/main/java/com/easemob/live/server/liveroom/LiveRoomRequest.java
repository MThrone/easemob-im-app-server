package com.easemob.live.server.liveroom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author shenchong@easemob.com 2020/2/24
 */
@Data
public class LiveRoomRequest {

    private String name;

    private String description;

    @JsonProperty("maxusers")
    private Integer maxUsers;

    private String cover;

    private Map<String, Object> ext;
}
