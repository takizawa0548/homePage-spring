package com.example.takisahp.kakeibo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="kakeibo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kakeibo {
//必要ないかと思ったが、マスターテーブルとトランザクションテーブルは分けたいので必要
    @Id
    //家計簿ID
    private String kakeibo_id;

    //登録日時
    private Date kakeibo_date;
}
