package com.resolveai.stations.dto;

import java.util.Date;

public record StationDTO(
        Long id,
        Long cityId,
        Date installation,
        String postalCode
) {}
