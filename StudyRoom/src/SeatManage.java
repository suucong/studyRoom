public class SeatManage {
	boolean setTable[][] = new boolean[2][5];
	public SeatManage() {
		for(int i = 0; i < setTable.length; i++) {
			for(int j = 0; j < setTable[i].length; j++) {
				setTable[i][j] = false;
			}
		}
	}
	
	void clear() {
		for(int i = 0; i < setTable.length; i++) {
			for(int j = 0; j < setTable[i].length; j++) {
				setTable[i][j] = false;
			}
		}
	}
	
	void print() {
		for(int i = 0; i < setTable.length; i++) {
			for(int j = 1; j <= setTable[i].length; j++) {
				if(setTable[i][j-1] == false) {
					System.out.print("V[" + (i+1)+j + "]");
				}
				else {
					System.out.print("C[" + (i+1)+j + "]");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	void setSeat(int x, int y) {
		if(setTable[x-1][y-1] == false) {
			setTable[x-1][y-1] = true;
			System.out.println("선택되었습니다.");
			System.out.println();
		}
		else if (setTable[x-1][y-1] == true) {
			System.out.println("이미 사용 중 입니다.");
			System.out.println();
		}
	}
	
	void releaseSeat(int x, int y) {
		if (setTable[x-1][y-1] == true) {
			setTable[x-1][y-1] = false;
		}
	}
}