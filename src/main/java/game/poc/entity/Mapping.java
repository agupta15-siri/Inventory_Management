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
@Table(name = "mapping")
public class Mapping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long mappingIdentifier;

    @Column(name = "mappingid")
    private long mappingId;

    @Column(name = "assetname")
    private String assetName;

    @Column(name = "totalasset")
    private long totalAsset;

}
