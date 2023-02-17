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
@Table(name = "assets")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long assetIdentifier;

    @Column(name = "assetname")
    private String assetName;

    @Column(name = "assetcount")
    private long assignedAssetCount;
}
