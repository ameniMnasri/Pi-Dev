package com.example.Activity.entity.Activity;

import com.example.Activity.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// Auto-increment
     Long actId;

     String title;
     Date ActivityDate;
     Integer reputation;
     Integer duration;

    @OneToOne(cascade = CascadeType.ALL)
    private ActivityType activityType;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

