package ParrotDataModel;

import ParrotRowGateWay.ParrotTypeRegistry;
import com.mysql.cj.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.BooleanUtils;

import java.sql.SQLException;


@Getter
@Setter
public class OwnedParrot {

    private Integer ownedParrotId;

    private Integer parrotTypeId;

    private String parrotName;

    private Integer parrotAge;

    private String color;

    private Boolean tamed;

    /**
     * Print out all Parrot information
     *
     * @throws SQLException
     */
    public void printParrotInformation() throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("My parrot information: \n");
        if (ownedParrotId != null) {
            sb.append("ParrotId: " + ownedParrotId + "\n");
        }
        if (this.parrotTypeId != null) {
            sb.append("Species: " + ParrotTypeRegistry.getParrotTypeById(parrotTypeId).getSpecies() + "\n");
        }
        if (!StringUtils.isNullOrEmpty(parrotName)) {
            sb.append("Name: " + parrotName + "\n");
        }
        if (parrotAge != null) {
            sb.append("Age: " + parrotAge + "\n");
        }
        if (StringUtils.isNullOrEmpty(color)) {
            sb.append("Name: " + color + "\n");
        }
        if (tamed != null) {
            sb.append("Tamed: " + BooleanUtils.toStringYesNo(tamed));
        }

        String parrotInformation = sb.toString();
        System.out.println(parrotInformation);
    }
}
