package pkgLadang;

public class Day{
	
	final int CLOCK_TICK = 6000;

	boolean stateTanam = true;
	long clockStart;
	int timeAhead = 0;
	boolean firstCycle = true;
	boolean timeRunning = true;
	
	int time = 70;
	
	
	public void nextHour(){
		if(this.time%10 == 0)
			this.time += 3;
		else
			this.time += 7;
		
		this.isOver();
	}
	
	public void resetTime(){
		this.firstCycle = true;
		this.time = 70;
		this.timeAhead = 0;
		this.stateTanam = true;
		this.timeRunning = true;
	}
	
	public void startTime(){
			this.clockStart = System.currentTimeMillis();
			this.firstCycle = false;
	}
	
	public boolean isTick(){
		int elapsed = (int) (System.currentTimeMillis() - this.clockStart) ;
		if(elapsed >= (CLOCK_TICK - this.timeAhead)){
			this.clockStart = System.currentTimeMillis();
			this.timeAhead = elapsed + this.timeAhead - CLOCK_TICK;
			return true;
		}
		else return false;
	}
	
	
	private void isOver(){
		if(this.time == 170){
			this.stateTanam = false;
			this.timeRunning = false;
		}
	}
	public String getTime(){
		String timeStr = "";
		timeStr = "" + (this.time/10) + ":" + (this.time%10) + "0";
		
		return timeStr;
	}
}