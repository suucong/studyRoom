import java.util.Calendar;

public class Payment {
	static int total_Income;
	static int total_Food_Income;
	static int total_Time_Income;
	
	final int FEE_PER_MINUTE = 100;
	final int FEE_PER_HOUR = 5000;
	long calculateFee(long startTime, long endTime) {
		long fee = 0;
		long second = (endTime - startTime) / 1000;
		long minute_ = second / 60;
		double minute = second / 60.0 % 60.0;
		long hour = minute_ / 60;
		if (hour == 0) {
			fee = (long)(minute * FEE_PER_MINUTE);
		} else if (hour >= 1) {
			fee = (long)((hour * FEE_PER_HOUR) + (minute * FEE_PER_MINUTE));
		}
		return fee;
	}
	long setCheckIn() {
		Calendar time = Calendar.getInstance();
		int year = time.get(Calendar.YEAR);
		int month = time.get(Calendar.MONTH) + 1;
		int day = time.get(Calendar.DAY_OF_MONTH);
		int hour = time.get(Calendar.HOUR);
		int minute = time.get(Calendar.MINUTE);
		int second = time.get(Calendar.SECOND);
		System.out.println("입장시간: " + year + "년 " + month + "월 " + day + "일 " + hour + "시 " + minute + "분 " + second + "초");
		long start = time.getTimeInMillis();
		return start;
	}
	
	long setCheckOut() {
		Calendar time = Calendar.getInstance();
		int year = time.get(Calendar.YEAR);
		int month = time.get(Calendar.MONTH) + 1;
		int day = time.get(Calendar.DAY_OF_MONTH);
		int hour = time.get(Calendar.HOUR);
		int minute = time.get(Calendar.MINUTE);
		int second = time.get(Calendar.SECOND);
		System.out.println("퇴장시간: " + year + "년 " + month + "월 " + day + "일 " + hour + "시 " + minute + "분 " + second + "초");
		long end = time.getTimeInMillis();
		return end;
	}
}