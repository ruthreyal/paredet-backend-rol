package com.paredetapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CambiarPasswordRequest {
    private String actual;
    private String nueva;
}
