package Movie;
// 2조 답안 보고 한 것

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Movie {
	
	private Scanner scanner = new Scanner(System.in);
	Random random = new Random();
	int userrow, usercol;
	
	private final int ROW = 4; // 영화관 좌석 행의 수
	private final int COL = 5; // 영화관 좌석 열의 수
	
	private String[][] seat; // 좌석 배열
	private HashMap<Integer, String> user; // 예매번호와 좌석을 저장할 맵
	private int number; // 예매번호
	private String userNumber; // 선택받은 좌석
	
	
	// Movie 생성자 함수
	{
		seat = new String[ROW][COL];
		// 좌석 초기화
		for(int i = 0; i < seat.length; i++) {
			for(int j = 0; j < seat[i].length; j++) {
				seat[i][j] = (i+1)+"-"+(j+1);
			}
		}

		user = new HashMap<>();

		
	}
	
	public void displaySeat() {
		
		System.out.println("**********좌석현황**********");
		for(int i = 0; i < seat.length; i++) {
			for(int j = 0; j < seat[i].length; j++) {
				System.out.printf("[%s]",seat[i][j]);
			}
			System.out.println();
		}
		System.out.println("**************************");

	}
	
	public void reservation() {
		displaySeat();
		
		System.out.println("좌석을 선택해주세요. 예) 1-1");
		System.out.println("이미 예매된 좌석은 \"예매\"라고 표시됩니다.");
		userNumber = scanner.nextLine();
		
		String regExp = "^[1-4]-[1-5]";
		boolean bo = userNumber.matches(regExp);
		
		if(bo == true) {
				
			for(int i = 0; i < seat.length; i++) {
				for(int j = 0; j < seat[i].length; j++) {

					if(seat[i][j].equals(userNumber)) {
						System.out.println("예매 가능한 좌석입니다. 예매하시겠습니까?");
						System.out.println("1. 예\n2. 아니오");
						String userChoice = scanner.nextLine();
						
						if(userChoice.equals("1")) {
							seat[i][j] = "예매";
							number = (int)(Math.random()*100000000);
							user.put(number, userNumber);
							
							System.out.println(user.toString());
							
							System.out.println("예매가 완료되었습니다.");
							System.out.printf("예매좌석 : [%s] 예매번호 : [%d]\n", userNumber, number);
							mainMenu();
						} else if (userChoice.equals("2")) {
							mainMenu();
						}  else {
							System.out.println("잘못 입력하셨습니다.");
						}
					}else if(userNumber.equals("예매")) {
						System.out.println("이미 선택된 좌석입니다.");
					} 				
				}
			}
			
		} else {
			System.out.println("잘못 입력하셨습니다.");
			reservation();
			return;
		}
		return;
	
	}
	
	public void showReservation() {
		
		System.out.println("예매번호를 입력해주세요.");
		String userInfo = scanner.nextLine();
		int userInfoInt = Integer.parseInt(userInfo);
		if(user.containsKey(userInfoInt)) {
			System.out.printf("고객님께서 예매하신 좌석은[%s]입니다.\n",user.get(userInfoInt).toString());
			mainMenu();
		} else {
			System.out.println("예매내역이 존재하지 않습니다.");
			mainMenu();
		}
		
	}
	
	public void cancel() {	
		
		System.out.println("예매번호를 입력해주세요.");
		String userInfo = scanner.nextLine();
		int userInfoInt = Integer.parseInt(userInfo);
		if(user.containsKey(userInfoInt)) {
			String userSeat = user.get(userInfoInt).toString();
			System.out.printf("고객님께서 예매하신 좌석은[%s]입니다.\n",userSeat);
			System.out.println("해당 내역의 예매를 취소하시겠습니까?");
			System.out.println("1. 예\\n2. 아니오");
			String userChoice = scanner.nextLine();
			
			// 예매 취소
			if( userChoice.equals("1")) {
				for(int i = 0; i < seat.length; i++) {
					for(int j = 0; j < seat[i].length; j++) {
						
						// 예매된 좌석을 찾아 다시 예매 전으로 초기화한다
						if(seat[i][j].equals("예매")) {
							seat[i][j] = (i+1) +"-" + (j+1);
							
							// 예매번호의 value값 삭제 
							if(seat[i][j].equals(userSeat)) {
								user.remove(userInfo);
							} else { // 나머지 좌석은 다시 "예매"로 초기화한다
								seat[i][j] = "예매";
							}							
						}				
					}
				}
				System.out.println("예매가 취소되었습니다.");
				mainMenu();
			} else if (!userChoice.equals("1") && !userChoice.equals("2")) {
				System.out.println("잘못 입력하셨습니다.");
				return;
			}			
			
		} else {
			System.out.println("예매내역이 존재하지 않습니다.");
			return;
		}

		
	}
	
	public void mainMenu() {
		
			
		System.out.println("********영화예매 시스템********");
		
		System.out.println("1. 예매하기\n2. 예매조회\n3. 예매취소\n0. 나가기");
		
		System.out.println("**************************");
		
		try {

			int menu = scanner.nextInt();
			
				switch(menu) {
				case 1: reservation();
					break;
				case 2: showReservation();
					break;
				case 3: cancel();
					break;
				case 0: System.out.println("이용해주셔서 감사합니다.");
						System.exit(0);
					break;					
				}		
			
		}  catch(InputMismatchException e) {
			System.out.println("잘못입력하셨습니다.");

		} catch ( Exception e) {
			System.out.println("잘못입력하셨습니다.");

		} 
		
	}
}



