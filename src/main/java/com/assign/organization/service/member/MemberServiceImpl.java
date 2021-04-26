package com.assign.organization.service.member;

import com.assign.organization.domain.member.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberDTO findMemberById(Long id) {

        Optional<Member> findMember = memberRepository.findById(id);

        if (!findMember.isPresent()) {
            return null;
        }

        Member member = findMember.get();

        return MemberDTO
                .builder()
                .name(member.getName())
                .duty(member.getDuty())
                .position(member.getPosition())
                .teamId(member.getTeam().getId())
                .build();
    }

    @Override
    public boolean deleteMemberByName(String name) {

        int deleteCount = memberRepository.deleteByName(name);

        return deleteCount > 0;
    }

    @Override
    public void deleteMemberById(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public void updateMember(MemberVO update) {

        Optional<Member> findMember = memberRepository.findById(update.getId());

        if (!findMember.isPresent()) {
            throw new NoResultException();
        }

        Member member = findMember.get();

        Contact newContact = Contact
                .builder()
                .cellPhone(update.getCellPhone() == null ?
                        member.getContact().getCellPhone() : update.getCellPhone())
                .businessCall(update.getBusinessCall() == null ?
                        member.getContact().getCellPhone() : update.getBusinessCall())
                .build();

        MemberDTO dto = MemberDTO
                .builder()
                .name(update.getName() == null ? member.getName() : update.getName())
                .duty(update.getDuty() == null ? member.getDuty() : update.getDuty())
                .contact(newContact)
                .position(member.getPosition())
                .build();

        member.update(dto);

    }

    @Override
    public List<MemberVO> findMemberByKeyword(String keyword) {
        List<Member> findMembers = memberRepository.findByLikeAllField(keyword);

        log.info(findMembers.toString());

        return findMembers.stream().map(m -> MemberVO
                .builder()
                .name(m.getName())
                .duty(m.getDuty())
                .id(m.getId())
                .businessCall(m.getContact().getBusinessCall())
                .cellPhone(m.getContact().getCellPhone())
                .build()).collect(Collectors.toList());
    }

    @Override
    public MemberDTO createMember(MemberVO newMember) {

        Contact contact = Contact
                .builder()
                .businessCall(newMember.getBusinessCall())
                .cellPhone(newMember.getCellPhone())
                .build();

        Member member = Member
                .builder()
                .id(newMember.getId())
                .name(newMember.getName())
                .duty(newMember.getDuty())
                .position(newMember.getPosition())
                .contact(contact)
                .build();

        Member savedMember = memberRepository.save(member);

        return MemberDTO
                .builder()
                .name(savedMember.getName())
                .teamId(savedMember.getTeam() == null ? null : savedMember.getTeam().getId())
                .duty(savedMember.getDuty())
                .contact(savedMember.getContact())
                .position(savedMember.getPosition())
                .build();
    }

    @Override
    public Optional<Member> findMemberByIdEntity(Long id) {
        return memberRepository.findById(id);
    }
}
