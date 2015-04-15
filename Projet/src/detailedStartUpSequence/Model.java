package detailedStartUpSequence;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Samuel Trémouille
 */
public class Model {
    
    private HashMap<String,SubsystemModel> subsystems;
    
    public Model(){
	subsystems = new HashMap<String,SubsystemModel>();
    }
    
    public HashMap<String, SubsystemModel> getSubsystems(){
	return subsystems;
    }
}
