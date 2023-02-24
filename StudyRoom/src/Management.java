import java.util.Calendar;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Management {
	// 스캐너 객체 생성 
	Scanner scanner = new Scanner(System.in);
	// 퇴장 시간 나타내기 위한 객체 생성
	Calendar time = Calendar.getInstance();
	private int currentNo;
	ArrayList<Customer> cst = new ArrayList<Customer>(10);
	SeatManage seatMan = new SeatManage();
	Payment pay = new Payment();
	// 퇴장 시간 나타낼 때 쓸 것
	int year = time.get(Calendar.YEAR);
	int month = time.get(Calendar.MONTH) + 1;
	int day = time.get(Calendar.DAY_OF_MONTH);
	int hour = time.get(Calendar.HOUR);
	int minute = time.get(Calendar.MINUTE);
	int second = time.get(Calendar.SECOND);
	
	// 생성자 함수
	Management() {
		currentNo = 0;
	}
	
	// 입장 함수
	void setIn() {
		System.out.println();
		System.out.print("이름을 입력하세요: ");
		String name = scanner.next();
		// 같은 이름을 사용할 수 없도록 하기 위해서
		int same = 0;
		for (int i = 0; i < currentNo; i++) {
			if(name.equals(cst.get(i).name)) {
				same = 1;
		}
			}
		// 만약 이름이 같지 않다면 좌석 입력 가능
		if(same == 1) {
			System.out.println("사용하실 수 없는 이름입니다.");
			System.out.println();
		} else {
			try {
				cst.add(currentNo, new Customer(name)); 
				System.out.println();
				seatMan.print();
				System.out.println("현재 남은 좌석은 " + (10-currentNo) + "석 입니다.(V:빈좌석/C:찬좌석).");
				System.out.print("원하는 좌석을 고르세요: ");
				String input = scanner.next();
				String input_ = input.substring(0,2);
				int num = Integer.parseInt(input_);
				
				int x = num/10; int y = num % 10;
				if(num >= 11 && num <= 15) {
					System.out.println();
					if(seatMan.setTable[x-1][y-1] == true) {
						seatMan.setSeat(num/10, num%10);
					} else {
						cst.get(currentNo).seatID = num;
						cst.get(currentNo).startTime = pay.setCheckIn();
						seatMan.setSeat(num/10, num%10);
						currentNo++;
					}
				} else if (num >= 21 && num<= 25) {
					System.out.println();
					if(seatMan.setTable[x-1][y-1] == true) {
						seatMan.setSeat(num/10, num%10);
					} else {
						cst.get(currentNo).seatID = num;
						cst.get(currentNo).startTime = pay.setCheckIn();
						seatMan.setSeat(num/10, num%10);
						currentNo++;
					}
				} else if(num < 10) {
					System.out.println();
					System.out.println("좌석번호를 잘못 입력하셨습니다.");
					System.out.println();
				} else {
					System.out.println();
					System.out.println("없는 좌석입니다.");
					System.out.println();
				}
			} catch(StringIndexOutOfBoundsException e) {
				System.out.println();
				System.out.println("좌석번호를 잘못 입력하셨습니다.");
				System.out.println();
			}
		}
}
	// 퇴장 함수
	void setOut() {
		if(currentNo == 0) {
			System.out.println("현재 사용되고 있는 좌석이 없습니다.");
			System.out.println();
		} else {
			System.out.print("이름을 입력하세요: ");
			String setOut_name = scanner.next();
			System.out.println();
			for(int i = 0; i < currentNo; i++) {
				if(setOut_name.equals(cst.get(i).name)) {
					System.out.println("당신의 좌석번호는 " + cst.get(i).seatID + "입니다.");
					System.out.println("해제되었습니다.");
					System.out.println();
					cst.get(i).endTime = pay.setCheckOut();
					long change = cst.get(i).endTime - cst.get(i).startTime;
					pay.total_Food_Income += cst.get(i).foodPay;
					pay.total_Income += cst.get(i).foodPay;
					System.out.println("사용시간: " + (change/1000)/60 + "분 " + (change/1000)%60 + "초");
					System.out.println("시간 사용 요금: " + pay.calculateFee(cst.get(i).startTime, cst.get(i).endTime) + "원");
					pay.total_Time_Income += pay.calculateFee(cst.get(i).startTime, cst.get(i).endTime);
					pay.total_Income += pay.total_Time_Income;
					System.out.println("음식 요금: " + cst.get(i).foodPay + "원");
					System.out.println("총 요금: " + (cst.get(i).foodPay + pay.calculateFee(cst.get(i).startTime, cst.get(i).endTime)) + "원");
					System.out.println();
					seatMan.releaseSeat(cst.get(i).seatID/10, cst.get(i).seatID%10);
					cst.remove(i);
					currentNo--;
					return;
				} 
			}
			System.out.println("찾는 이름이 없습니다.");
			System.out.println();
		}
	}
	
	// 관리자 메뉴
	void admin() {
		try {
			System.out.println("[관리자 메뉴]");
			System.out.println("1. 현재 좌석 상태 보기");
			System.out.println("2. 전체 좌석 리셋 하기");
			System.out.println("3. 손님 현황 보기");
			System.out.println("4. 총 수입 확인하기");
			System.out.print("- - > ");
			int admin_num = scanner.nextInt();
			// 현재 좌석 상태 보기
			if(admin_num == 1) {
				System.out.println();
				System.out.println("[현재 좌석 상태]");
				seatMan.print();
				System.out.println("찬 좌석: " + currentNo);
				System.out.println("남은 좌석: " + (10-currentNo));
				System.out.println();
				// 전체 좌석 리셋 하기 
			} else if(admin_num == 2) {
				seatMan.clear();
				for(int i = 0; i < currentNo; i++) {
					Calendar reset_time = Calendar.getInstance();
					cst.get(i).endTime = reset_time.getTimeInMillis();
					System.out.println(cst.get(i).name + "님, 좌석번호: " + cst.get(i).seatID);
					System.out.println("퇴장시간: " + year + "년 " + month + "월 " + day + "일 " + hour + "시 " + minute + "분 " + second + "초");
					long change = cst.get(i).endTime - cst.get(i).startTime;
					System.out.println("사용시간: " + (change/1000)/60 + "분 " + (change/1000)%60 + "초");
					System.out.println("시간 사용 요금: " + pay.calculateFee(cst.get(i).startTime, cst.get(i).endTime) + "원");
					pay.total_Food_Income += cst.get(i).foodPay;
					pay.total_Income += cst.get(i).foodPay;
					pay.total_Time_Income += pay.calculateFee(cst.get(i).startTime, cst.get(i).endTime);
					System.out.println("음식 요금: " + cst.get(i).foodPay + "원");
					System.out.println("총 요금: " + (cst.get(i).foodPay + pay.calculateFee(cst.get(i).startTime, cst.get(i).endTime)) + "원");
					System.out.println();
				}
				for(int i = 0; i < cst.size(); i++) {
					cst.remove(i);
				}
				currentNo = 0;
				seatMan.clear();
				System.out.println("모든 좌석이 해제되었습니다.");
				System.out.println();
				// 손님 현황 보기 
			} else if (admin_num == 3) {
				if(currentNo == 0) {
					System.out.println("손님이 없습니다.");
					System.out.println();
				} else {
					System.out.println("좌석번호     이름");
					System.out.println("-----------------");
					for(int i = 0; i < currentNo; i++) {
						System.out.println(cst.get(i).seatID + "         " + cst.get(i).name);
					}
					System.out.println();
				}
			} else if (admin_num == 4) {
				System.out.println("음식 수익: " + pay.total_Food_Income + "원");
				System.out.println("시간 수익: " + pay.total_Time_Income + "원");
				System.out.println("총 수익: " + (pay.total_Food_Income+pay.total_Time_Income) + "원");
				System.out.println();
			} else {
				System.out.println("없는 항목입니다.");
				System.out.println();
			}
		} catch(InputMismatchException ime) {
			System.out.println("입력하실 수 없는 키 입니다.");
			System.out.println();
		}
}

	void orderFood() {
		if(currentNo == 0) {
			System.out.println("현재 사용되고 있는 좌석이 없습니다.");
			return;
		}
		try {
			System.out.print("좌석번호를 입력하세요.: ");
			String s_ = scanner.next();
			int foodSeatNum = Integer.parseInt(s_);
			try {
				int x = foodSeatNum / 10; int y = foodSeatNum % 10;
				int foodNum = 0;
				int foodSum = 0;
				
				if (seatMan.setTable[x-1][y-1] == true) {
					System.out.println("1: 샌드위치           5000");
					System.out.println("2: 김치볶음밥          6500");
					System.out.println("3: 핫도그             3000");
					System.out.println("4: 아이스아메리카노      2500");
					System.out.println("5: 콜라, 사이다        1500");
					System.out.println("6: 아이스크림           1200");
					System.out.println("7: 생수                800");
					System.out.println();
					do {
						try {
							System.out.print("원하는 메뉴의 번호를 한 번에 하나씩 입력하세요(그만 주문하시려면 99 입력): ");
							String s = scanner.next();
							foodNum = Integer.parseInt(s);
							if(foodNum == 1) {
								foodSum += 5000;
								System.out.println("샌드위치 선택: " + 5000 + "원");
							} else if (foodNum == 2) {
								foodSum += 6500;
								System.out.println("김치볶음밥 선택: " + 6500 + "원");
							} else if (foodNum == 3) {
								foodSum += 3000;
								System.out.println("핫도그 선택: " + 3000 + "원");
							} else if (foodNum == 4) {
								foodSum += 2500;
								System.out.println("아이스아메리카노 선택: " + 2500 + "원");
							} else if(foodNum == 5) {
								foodSum += 1500;
								System.out.println("콜라, 사이다 선택: " + 1500 + "원");
							} else if(foodNum == 6) {
								foodSum += 1200;
								System.out.println("아이스크림 선택: " + 1200 + "원");
							} else if(foodNum == 7) {
								foodSum += 800;
								System.out.println("생수 선택: " + 800 + "원");
							} else if (foodNum == 99) {
								System.out.println("총 금액: " + foodSum + "원");
								System.out.println();
							} else {
								System.out.println("선택하신 번호에는 해당하는 품목이 없습니다.");
							}
						} catch(NumberFormatException e) {
							System.out.println("입력하실 수 없는 키 입니다.");
						}
					} while(foodNum < 99);
				} else {
					System.out.println("등록하신 좌석이 없습니다.");
					System.out.println("좌석을 등록해주세요.");
					System.out.println();
				}
				for(int i = 0; i < currentNo; i++) {
					if(cst.get(i).seatID == foodSeatNum) {
						cst.get(i).foodPay += foodSum;
					}
				}
			} catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("입력하실 수 없는 키 입니다.");
				System.out.println();
			}
		} catch(NumberFormatException e) {
			System.out.println("입력하실 수 없는 키 입니다.");
			System.out.println();
		}
	}
}

