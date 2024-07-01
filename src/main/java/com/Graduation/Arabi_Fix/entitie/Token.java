package com.Graduation.Arabi_Fix.entitie;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
//@Cacheable(value = false)
public class Token
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String token;


    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private Boolean expired;

    private Boolean revoked;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
