package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {
        //given
        Member member = new Member("memberA");

        //when
        Member savedMember = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(savedMember.getId()).get();
        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
        assertThat(findMember.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(findMember).isEqualTo(member);

    }

    @Test
    void delete() {
        //given
        Member memberA = new Member("memberA", 10, null);
        Member memberB = new Member("memberB", 20, null);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        memberRepository.delete(memberA);

        //then
        assertThatThrownBy(() -> memberRepository.findById(memberA.getId()).get())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void findAll() {
        //given
        Member memberA = new Member("memberA", 10, null);
        Member memberB = new Member("memberB", 20, null);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        List<Member> resultList = memberRepository.findAll();

        //then
        long count = memberRepository.count();
        assertThat(resultList.size()).isEqualTo(2);
        assertThat(resultList.size()).isEqualTo(count);
    }

    @Test
    void count() {
        //given
        Member memberA = new Member("memberA", 10, null);
        Member memberB = new Member("memberB", 20, null);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        long count = memberRepository.count();

        //then
        assertThat(count).isEqualTo(2);
    }

    @Test
    void findById() {
        //given
        Member memberA = new Member("memberA", 10, null);
        Member savedMember = memberRepository.save(memberA);

        //when
        Member findMember = memberRepository.findById(memberA.getId()).get();

        //then
        assertThat(findMember).isEqualTo(savedMember);
        assertThat(findMember.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(findMember).isEqualTo(memberA);
    }

    @Test
    void update() {
        //given
        Member memberA = new Member("memberA", 10, null);
        Member savedMember = memberRepository.save(memberA);

        //when
        savedMember.setUsername("memberB");

        //then
        Member findMember = memberRepository.findById(savedMember.getId()).get();
        assertThat(findMember.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(findMember.getUsername()).isEqualTo("memberB");
    }

    @Test
    void findByUsernameAndAgeGreaterThan() {
        //given
        Member memberA = new Member("member", 10, null);
        Member memberB = new Member("member", 20, null);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        List<Member> member = memberRepository.findByUsernameAndAgeGreaterThan("member", 15);

        //then
        assertThat(member.get(0).getUsername()).isEqualTo("member");
        assertThat(member.get(0).getAge()).isEqualTo(20);
    }

    @Test
    void findHelloBy() {
        //given
        Member memberA = new Member("member", 10, null);
        Member memberB = new Member("member", 20, null);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        List<Member> members = memberRepository.findHelloBy();

        //then
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void findByUsernameNamedQueryTest() {
        //given
        Member memberA = new Member("memberA", 10, null);
        Member memberB = new Member("memberB", 20, null);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        List<Member> members = memberRepository.findByUsernameNamedQuery(memberA.getUsername());

        //then
        assertThat(members.get(0).getUsername()).isEqualTo("memberA");
        assertThat(members.get(0).getAge()).isEqualTo(10);
    }

    @Test
    void findUserTest() {
        //given
        Member memberA = new Member("memberA", 10, null);
        Member memberB = new Member("memberB", 20, null);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        List<Member> members = memberRepository.findUser("memberA", 10);

        //then
        assertThat(members.get(0).getUsername()).isEqualTo("memberA");
        assertThat(members.get(0).getAge()).isEqualTo(10);
    }

}