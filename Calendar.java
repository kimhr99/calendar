import java.util.Scanner;

public class Calendar {

  void printOption(){
    System.out.println("==========");
    System.out.println("| 1. 일정등록");
    System.out.println("| 2. 일정검색");
    System.out.println("| 3. 달력보기");
    System.out.println("| h. 도움말");
    System.out.println("| q. 종료");
    System.out.println("==========");
  }

  void registerEvent(Scanner s, Manager m){
    System.out.println("[새 일정 등록]");
    System.out.print("날짜 (yyyy-mm-dd)>");
    String date = s.next();
    System.out.print("일정>");
    String event = s.next();
    m.registerEvent(date, event);
  }

  void searchEvent(Scanner s, Manager m){
    System.out.println("[일정 검색]");
    System.out.println("날짜 (yyyy-mm-dd)>");
    String date = s.next();
    String event = m.searchEvent(date);
    switch(event){
      case "-1": break;
      default: System.out.println(event);
               break;
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Manager manager = new Manager();
    Calendar calendar = new Calendar();
    String command;
    boolean quit = false;
    calendar.printOption();
    while(!quit){
      System.out.print("명령(1, 2, 3, h, q)>");
      command = scanner.next();
      switch (command){
        case "1": calendar.registerEvent(scanner, manager);
                  break;
        case "2": calendar.searchEvent(scanner, manager);
                  break;
        case "3": manager.printCalendar(2020, 4);
        	      break;
        case "h": calendar.printOption();
                  break;
        case "q": quit = true;
                  manager.writeFile();
                  break;
        default:  System.out.println("다시 입력해주세요.");
                  break;
      }
    }
    System.out.printf("종료합니다.\n");
    scanner.close();
  }
}
