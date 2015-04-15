package subsystem;

/**
 * @author S.Trémouille
 *
 */
public class OtpPvp {
	private int otpPvpCompleted;
	private int otpPvpToComplete;
	
	/**
	 * Constructor
	 * @param otpCompleted
	 * @param otpToComplete
	 */
	public OtpPvp(int otpCompleted,int otpToComplete){
		this.otpPvpCompleted=otpCompleted;
		this.otpPvpToComplete=otpToComplete;
	}
	
	
	@Override
	public String toString(){
		return otpPvpCompleted+"/"+otpPvpToComplete;
	}
	
	/**
	 * @return OtpCompleted
	 */
	public int getOtpCompleted(){
		return otpPvpCompleted;
	}
	
	/**
	 * @return OtpToComplete
	 */
	public int getOtpToComplete(){
		return otpPvpToComplete;
	}
}
