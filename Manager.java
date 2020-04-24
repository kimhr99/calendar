import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.text.ParseException;
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class Manager{
  private final int[] maxDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
  private HashMap<Date, String> eventMap;
  public final String fileName = "calendar.txt";

  public Manager(){
    eventMap = new HashMap<Date, String>();
    readFile();
  }
  public boolean isLeapYear(int year){
    if(year%4 == 0 && (year%100 != 0 || year%400 == 0)){
      return true;
    }
    return false;
  }

  public int maxDaysOfMonth(int year, int month){
    if(isLeapYear(year) && month == 2){
      return 29;
    }
    return maxDays[month - 1];
  }
  void printCalendar(){
	  int year;
	  int month;
	  Scanner sc = new Scanner(System.in);
	  System.out.println("년도를 입력하세요 : ");
	  year = sc.nextInt();
	  System.out.println("월을 입력하세요 : "); 
	  month = sc.nextInt();
	 
    int maxDays = maxDaysOfMonth(year, month);
    int first = getFirstDayOfMonth(year, month);
    System.out.printf("        <<%4d년 %3d월>>\n", year, month);
    System.out.println("  일  월  화  수  목  금  토");
    System.out.println("============================");
    if(first != 7){
      for(int i=0; i<first; i++){
        System.out.print("    ");
      }
    }
    for(int i=1; i<=maxDays; i++){
      if((i%7) + first == 7)
        System.out.printf("  %2d\n", i);
      else
        System.out.printf("  %2d", i);
    }
    System.out.println("");
  }

  public int getFirstDayOfMonth(int year, int month){
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
      Iterator iterator = eventMap.keySet().iterator();
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
