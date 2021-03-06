package com.assign.organization.controller.team;

import com.assign.organization.domain.team.TeamVO;
import com.assign.organization.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamAPIController {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamVO>> getTeamsWithMembers() {
        List<TeamVO> findTeamVOs = teamService.findAllTeamVOList();
        return ResponseEntity.of(Optional.of(findTeamVOs));
    }
}
