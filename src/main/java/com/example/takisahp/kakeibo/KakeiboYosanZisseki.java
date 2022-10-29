package com.example.takisahp.kakeibo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@Setter
@IdClass(value=KakeiboYosanKey.class)
@NoArgsConstructor
@AllArgsConstructor
public class KakeiboYosanZisseki {
    @Id
    //家計簿ID
    private String kakeibo_id;
    @Id
    //家計簿項目
    private String kakeibo_koumoku;
    //家計簿予算金額
    private Integer kakeibo_yosan_kingaku;
    //家計簿実績金額
    private Integer kakeibo_zisseki_kingaku;
    //家計簿差額
    private Integer kakeibo_sagaku_kingaku;
}
