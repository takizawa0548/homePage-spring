package com.example.takisahp.kakeibo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KakeiboYosanForm {
    //家計簿ID
    @Id
    private String kakeibo_id;
    //項目名
    private String kakeibo_koumoku;
    //実績金額
    private Integer kakeibo_yosan_kingaku;
}
