package com.example.good_match.domain.comment.model;

import com.example.good_match.domain.post.domain.Post;
import com.example.good_match.domain.member.model.Member;
import com.example.good_match.global.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment")
    private Long id;

    @Column(name = "contents")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void update(String contents) {
        this.contents = contents;
    }

    public boolean eqMember(Member member) {
        return this.member.equals(member);
    }
}
