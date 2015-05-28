package FpsSetting;

import java.util.concurrent.TimeUnit;


public class FpsManager{
	
	private static long ONE_SEC_TO_NANO = TimeUnit.SECONDS.toNanos(1L);
	private static long ONE_MILLI_TO_NANO = TimeUnit.MILLISECONDS.toNanos(1L);
	
	private int maxfps;
	private int[] fpsBuffer;
	private int fpscnt;
	private long oneframetime;
	private long beforetime;
	private long sleeptime;
	private long Vtime;
	private int overtimecnt;
	private int overtimemax;
	private boolean overtimeflag;
	
	public FpsManager(int fpsmax,int otmax){
			if (fpsmax<0 || fpsmax>120){
				maxfps=60;
			}else{
				maxfps=fpsmax;
			}
			fpsBuffer = new int[maxfps];
			fpscnt=0;
			beforetime=System.nanoTime();
			Vtime=(long)(Math.floor((double)ONE_SEC_TO_NANO/maxfps));
			overtimecnt=0;
			if (otmax<0 || otmax<maxfps){
				overtimemax=maxfps/2;
						}else{
				overtimemax=otmax;
			}
			overtimeflag=false;
	}
	public boolean getovertimeflag(){
		return overtimeflag;
	}
	
	public int resetFpsmax(int fpsmax){
		
		if (fpsmax<0 || fpsmax>120){
			maxfps=60;
		}else{
			maxfps=fpsmax;
		}
		if (maxfps<overtimemax){
			overtimemax=maxfps/2;
		}
		return 0;
	}

	public int resetOtmax(int otmax){
		
		if (otmax<0 || otmax<maxfps){
			overtimemax=maxfps/2;
					}else{
			overtimemax=otmax;
		}
		
	return 0;	
	}
	
	public long fpsClc(){
		
		if (maxfps <= ++fpscnt){
			fpscnt=0;
		}
		
		oneframetime=System.nanoTime()-beforetime;
		sleeptime=Vtime-oneframetime;
		overtimeflag=false;
		if (sleeptime < ONE_MILLI_TO_NANO){
			if (++overtimecnt>overtimemax){
				overtimecnt=0;
				overtimeflag=true;
			}
			sleeptime = ONE_MILLI_TO_NANO;
		}
		
		int fps = (int)(ONE_SEC_TO_NANO/(oneframetime+sleeptime));
		fpsBuffer[fpscnt]=fps;
		beforetime=System.nanoTime()+sleeptime;
		return sleeptime;
	}
	public int getFps(){
		int allfps=0;
		for (int cnt=0;cnt < maxfps; cnt++){
			
			allfps+=fpsBuffer[cnt];
			
		}
		return allfps/maxfps;
	}
	
}
