package com.assign.organization.domain.member;

import com.assign.organization.domain.team.Team;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Column(nullable = false, unique = true)
    private String businessCall;

    @Column(nullable = false, unique = true)
    private String cellPhone;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String duty;

    @Builder
    public Member(String name, Team team, String businessCall, String cellPhone, String position, String duty) {
        this.name = name;
        this.team = team;
        this.businessCall = businessCall;
        this.cellPhone = cellPhone;
        this.position = position;
        this.duty = duty;
    }



    public void setTeam(Team team) {

        if(this.team != null) {
            this.team.getMembers().removeIf(m -> Objects.equals(m, this));
        }

        this.team = team;
        team.getMembers().add(this);
    }
}
