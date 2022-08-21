package com.example.takisahp.entry;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //名前
    private String name;
    //性別
    private String gender;
    //誕生日
    private Date birthdate;
    //電話番号
    private String phonenumber;
    //Eメール
    private String email;
    //国籍
    private String nationality;

}
