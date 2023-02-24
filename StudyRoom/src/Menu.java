import java.util.Scanner;

public class Menu {
	public static void main(String[] args) {
		Management management = new Management();
		boolean loop = true;
		Scanner scn = new Scanner(System.in);
		
		do {
			try {
				System.out.println("[LaLaLa StudyRoom]");
				System.out.println("1.입장");
				System.out.println("2.퇴장");
				System.out.println("3.음식주문");
				System.out.println("4.관리자");
				System.out.println("5.종료");
				System.out.print("- - > " );
				String input = scn.next();
				int num = Integer.parseInt(input);
				
				System.out.println();
				
				if(num == 1) {
					management.setIn();
				} else if (num == 2) {
					management.setOut();
				} else if (num == 3) {
					management.orderFood();
				} else if (num == 4) {
					management.admin();
				} else if (num == 5) {
					loop = false;
					System.out.println();
					System.out.println("프로그램이 종료됩니다.");
				} else {
					System.out.println("입력하실 수 없는 번호입니다. ");
					System.out.println();
				}
			} catch(NumberFormatException e) {
				System.out.println("문자를 입력하셨습니다.");
				System.out.println();
			}
		}while(loop);
		scn.close();
	}
}
