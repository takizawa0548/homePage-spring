package com.example.takisahp.kakeibo;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class KakeiboYosanKey implements Serializable {

    private String kakeibo_id;
    //項目名
    private String kakeibo_koumoku;
}
