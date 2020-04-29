import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.text.ParseException;
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

import javafx.scene.chart.PieChart.Data;

import java.util.Calendar;

public class Manager{
  private final int[] maxDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
  private HashMap<Date, String> eventMap;
  public final String fileName = "calendar.txt";
 

  public Manager(){
    eventMap = new HashMap<Date, String>();
    readFile();
    Manager.getMonthGalendar();
  }
  public boolean isLeapYear(int year){ //윤년
    if(year%4 == 0 && (year%100 != 0 || year%400 == 0)){
      return true;
    }
    return false;
  }

  public int maxDaysOfMonth(int year, int month){ //달의 최대 일
    if(isLeapYear(year) && month == 2){
      return 29;
    }
    return maxDays[month - 1];
  }
  void printCalendar(){ //달력 출력 매소드
	  int year;
	  int month;
	  System.out.println("\n========== 달력 출력프로그램 ====================");
	  Scanner sc = new Scanner(System.in);
	  System.out.println("년도를 입력하세요 : ");
	  year = sc.nextInt();
	  System.out.println("월을 입력하세요 : "); 
	  month = sc.nextInt();
	 
    int maxDays = maxDaysOfMonth(year, month);
    int first = getFirstDayOfMonth(year, month);
    System.out.printf("=====================%4d년 %3d월 ==================\n" ,year, month);
    
    System.out.println(" 일\t 월\t 화\t 수\t 목\t 금\t 토\t");
    
    System.out.println("====================================================");
    int intDateNum = 1; //출력할 date를 저장할 변수
    
    for (int i = 1; intDateNum <= maxDays ; i++) {   // 출력한 Date 변수(intDateNum)가 1일부터 마지막일이 될때까지 반복.         
        
        if(i<first) System.out.printf("%3s\t","★"); //i가 요일숫자보다 작으면 공백으로 채우기   
        else{
           
             System.out.printf("%3d\t",intDateNum); //일반 출력
            
            intDateNum++; //출력할 date 증가
        }            
        if(i%7==0) System.out.println(); // i가 7의배수가 되면 줄바꿈
       
    }//for-----------------
    System.out.println();

  }
  
  public static void getMonthGalendar(){
      Calendar cal = Calendar.getInstance(); // 캘린더 객체 생성
      int thisYear = cal.get(Calendar.YEAR); //현재 년
      int thisMonth = cal.get(Calendar.MONTH)+1; //현재 달
     
      getMonthGalendar(thisYear,thisMonth); // 현재달의 달력을 출력
 }
  
  public static void getMonthGalendar(int intYear, int intMmonth){ //현재 달력 출력 매소드
	  Calendar cal = Calendar.getInstance();
      
      int thisYear = cal.get(Calendar.YEAR); //현재 년
      int thisMonth = cal.get(Calendar.MONTH)+1; //현재 달
      int today = cal.get(Calendar.DATE); //
      
      boolean booToday = (intYear==thisYear)&&(thisMonth==intMmonth);
      cal.set(intYear,intMmonth-1,1); //캘린더객체에 입력받은 년, 달, 그리고 Date을 1로설정
      
      int sDayNum = cal.get(Calendar.DAY_OF_WEEK); // 1일의 요일 얻어오기
      int endDate = cal.getActualMaximum(Calendar.DATE); //달의 마지막일 얻기
      
     
      int nowYear = cal.get(Calendar.YEAR);
      int nowMonth = cal.get(Calendar.MONTH);

      System.out.println("===================== "+ nowYear+"년  " + (nowMonth+1) + "월 ==================");
      //요일명 출력
      System.out.println(" 일\t 월\t 화\t 수\t 목\t 금\t 토\t");  
      System.out.println("====================================================");
      //1일이 시작되는 이전의 요일 공백으로 채우기 
      
      int intDateNum = 1; //출력할 date를 저장할 변수
  
      for (int i = 1; intDateNum <= endDate ; i++) {   // 출력한 Date 변수(intDateNum)가 1일부터 마지막일이 될때까지 반복.         
          
          if(i<sDayNum) System.out.printf("%3s\t","★"); //i가 요일숫자보다 작으면 공백으로 채우기   
          else{
              if(booToday && intDateNum==today) System.out.printf("[%2d]\t",intDateNum); //오늘 날짜 표시
              else System.out.printf("%3d\t",intDateNum); //일반 출력
              
              intDateNum++; //출력할 date 증가
          }            
          if(i%7==0) System.out.println(); // i가 7의배수가 되면 줄바꿈
         
      }
      System.out.println();
  }
  
  
  public int getFirstDayOfMonth(int year, int month){ //달의 첫날 받기
    int baseYear = 1970;
    int baseDay = 4;
    int count = 0;
    for(int i=baseYear; i<year; i++){
      count += isLeapYear(i)? 366: 365;
    }
    for(int i=1; i<month; i++){
      count += maxDaysOfMonth(year, i);
    }
    int day = (count+baseDay) % 7;
    if (day == 0)
      return 7;
    return day;
  }


  public void registerEvent(String strDate, String event){
    try{
    	 Date date = new SimpleDateFormat("yyyy-mm-dd").parse(strDate);
      eventMap.put(date, event);
    }
    catch (ParseException e){
      System.out.println("다시 입력해주세요.");
      return;
    }
  }

  public String searchEvent(String strDate){
	  try{
    	  Date date = new SimpleDateFormat("yyyy-mm-dd").parse(strDate);
      return eventMap.get(date);
      }
    catch (ParseException e){
    	System.out.println("다시 입력해주세요.");
      return "-1";
    }
  }
  
  public void scheduleRemoveAll() { //모든 일정 지우기 매소드
	  this.eventMap.clear();
  }
  

  void readFile(){
    try{
      BufferedReader reader = new BufferedReader(new FileReader(fileName));
      String data = "";
      while((data = reader.readLine()) != null){
        String event[] = data.split("/");
        registerEvent(event[0], event[1]);
      }
      reader.close();
    }
    catch (Exception e){
      return;
    }
  }

  void writeFile(){
    File f = new File(fileName);
    if(!f.exists()){
      try{
        f.createNewFile();
      }
      catch(Exception e){
        System.out.println("파일 생성 실패.");
        return;
      }
    }
    try{
      BufferedWriter writer = new BufferedWriter(new FileWriter(f, false));
      Iterator<Date> iterator = eventMap.keySet().iterator();
      SimpleDateFormat date = new SimpleDateFormat("yyyy-mm-dd");
      while(iterator.hasNext()){
        Date key = (Date)iterator.next();
        writer.write(date.format(key) + "/" + eventMap.get(key) + "\r\n");
      }
      writer.close();
    }
    catch (Exception e){
      System.out.println("파일 저장 실패.");
      return;
    }
  }
}


