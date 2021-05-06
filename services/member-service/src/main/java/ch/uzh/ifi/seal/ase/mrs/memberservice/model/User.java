package ch.uzh.ifi.seal.ase.mrs.memberservice.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Class for storing login information of users
 */
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_user")
public class User {
    /**
     * Id of the User
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Username of the User
     */
    @Column(unique=true)
    private String username;

    /**
     * Password of the User
     */
    private String password;

    /**
     * User ID in Rating Table
     */
    private Long tblRatingUserId;

    /**
     * Last Trained On
     */
    private LocalDateTime lastTrainedOn;
}
