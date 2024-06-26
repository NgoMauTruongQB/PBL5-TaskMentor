package model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team_member_feedback")
public class TeamMemberFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "evaluator_id", referencedColumnName = "id")
    private User evaluator;

    @ManyToOne
    @JoinColumn(name = "evaluatee_id", referencedColumnName = "id")
    private User evaluatee;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "rating")
    private int rating;

    @Column(name = "insert_time")
    private Timestamp insertTime;

    @Column(name = "insert_by")
    private Integer insertBy;

    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "update_by")
    private Integer updateBy;

    @Column(name = "delete_time")
    private Timestamp deleteTime;

    @Column(name = "delete_by")
    private Integer deleteBy;
}
