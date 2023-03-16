package com.example.good_match.domain.member.repository;

import com.example.good_match.domain.member.model.Gender;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.global.util.StatesEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 테스트")
    void join() {
        // given
        Member member = Member.builder()
                .name("testname1")
                .email("test@test.com")
                .password("123123")
                .phoneNumber("010-1111-1111")
                .gender(Gender.MAN)
                .states(StatesEnum.SEOUL)
                .build();
        // when
        Member savedMember = memberRepository.save(member);
        // then
        assertThat("testname1").isEqualTo(savedMember.getName());
        assertThat("test@test.com").isEqualTo(savedMember.getEmail());
    }

    @Test
    @DisplayName("이메일로 사용자 찾기 테스트")
    void findByEmail() {
        // given
        Member member = Member.builder()
                .name("testname1")
                .email("test@test.com")
                .password("123123")
                .phoneNumber("010-1111-1111")
                .gender(Gender.MAN)
                .states(StatesEnum.SEOUL)
                .build();

        Member savedMember = memberRepository.save(member);

        // when
        Member testMember = memberRepository.findByEmail("test@test.com").orElseThrow();
        // then

        assertThat(testMember).isNotNull();

    }

    @Test
    @DisplayName("이름, 핸드폰 번호로 사용자 찾기 테스트")
    void findByNameAndPhoneNumber() {
        // given
        Member member = Member.builder()
                .name("testname1")
                .email("test@test.com")
                .password("123123")
                .phoneNumber("010-1111-1111")
                .gender(Gender.MAN)
                .states(StatesEnum.SEOUL)
                .build();
        memberRepository.save(member);
        // when
        Member testMember = memberRepository.findByNameAndPhoneNumber("testname1", "010-1111-1111").orElseThrow(() -> new IllegalArgumentException("일치하는 유저를 찾지 못했습니다."));

         // then
        assertThat(testMember).isNotNull();
    }

    @Test
    @DisplayName("핸드폰 번호 존재 여부 테스트")
    void existsByPhoneNumber() {
        // given
        Member member = Member.builder()
                .name("testname1")
                .email("test@test.com")
                .password("123123")
                .phoneNumber("010-1111-1111")
                .gender(Gender.MAN)
                .states(StatesEnum.SEOUL)
                .build();
        memberRepository.save(member);

        // when
        Boolean isExistPhoneNumber = memberRepository.existsByPhoneNumber("010-1111-1111");

        // then
        assertThat(isExistPhoneNumber).isTrue();
    }

    @Test
    @DisplayName("이메일 존재 여부 테스트")
    void existsByEmail() {
        // given
        Member member = Member.builder()
                .name("testname1")
                .email("test@test.com")
                .password("123123")
                .phoneNumber("010-1111-1111")
                .gender(Gender.MAN)
                .states(StatesEnum.SEOUL)
                .build();
        memberRepository.save(member);

        // when
        Boolean isExistEmail = memberRepository.existsByEmail("test@test.com");

        // then
        assertThat(isExistEmail).isTrue();
    }

    @Test
    @DisplayName("이메일, 비밀번호 일치 유저 찾기 테스트")
    void findByEmailAndPassword() {
        // given
        Member member = Member.builder()
                .name("testname1")
                .email("test@test.com")
                .password("123123")
                .phoneNumber("010-1111-1111")
                .gender(Gender.MAN)
                .states(StatesEnum.SEOUL)
                .build();
        memberRepository.save(member);

        // when
        Optional<Member> testMember = memberRepository.findByEmailAndPassword("test@test.com", "123123");

        // then
        assertThat(testMember).isNotNull();
    }
}