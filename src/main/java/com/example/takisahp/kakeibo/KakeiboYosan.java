package com.example.takisahp.kakeibo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.persistence.*;
@Entity
@Table(name="kakeibo_yosan")
@Getter
@Setter
@IdClass(value=KakeiboYosanKey.class)
@NoArgsConstructor
@AllArgsConstructor
public class KakeiboYosan {
    //家計簿ID
    @Id
    @NotBlank
    private String kakeibo_id;
    //項目名
    @Id
    @NotBlank
    private String kakeibo_koumoku;
    //実績金額
    @NotNull
    private Integer kakeibo_yosan_kingaku;
}
