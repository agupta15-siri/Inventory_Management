package game.poc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "delay")
public class Delay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long delayIdentifier;

    @Column(name = "delayTime")
    private String delayTime;
}
