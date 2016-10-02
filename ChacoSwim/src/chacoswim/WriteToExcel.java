package chacoswim;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteToExcel {

	private File file=null;
	private String day = "";
	private String term = "";
	private XSSFWorkbook wb=null;
	private XSSFSheet sheet;
	private Connection conn=null;
	private List<StudentObject> l1,l2,l3,l4,l5,l6;
	private List<String> c1,c2,c3,c4,c5,c6;
	private List<String> timeMark,lineMark;
	
	public WriteToExcel(File file,String term,String day){
		this.file=file;
		this.day=day;
		this.term=term;
	}
	
	public CellStyle setHeadStyle(){
		CellStyle titleStyle = wb.createCellStyle();
		Font titleFont = wb.createFont();
		titleFont.setBold(true);
		titleFont.setFontHeightInPoints((short)20);
		titleStyle.setFont(titleFont);
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		return titleStyle;
	}
	public CellStyle setLineStyle(String str){
		XSSFCellStyle titleStyle = wb.createCellStyle();
		Font titleFont = wb.createFont();
		titleStyle.setFont(titleFont);
		titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		titleStyle.setBorderBottom(CellStyle.BORDER_THIN);
		titleStyle.setBorderTop(CellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(CellStyle.BORDER_THIN);
		titleStyle.setBorderRight(CellStyle.BORDER_THIN);
		if(str.equals("head")){
			titleFont.setBold(true);
			titleFont.setFontHeightInPoints((short)12);
			titleStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(155,194,230)));
			titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
		else if(str.equals("line")){
			titleFont.setBold(true);
			titleFont.setFontHeightInPoints((short)13);
		}
		else{
			titleFont.setBold(false);
			titleFont.setFontHeightInPoints((short)10);
		}
		
		
		return titleStyle;
	}
	private int findMax(int... value){
		int max = -1;
		for(int i: value){
			if(i>max)max=i;
		}
		return max;
		
	}
	private List<List<String>> findList(@SuppressWarnings("unchecked") List<String>...lists){
		List<List<String>> list = new ArrayList<List<String>>();
		for(List<String> i:lists){
			if(!i.isEmpty())list.add(i);
		}
		return list;
	}
	
	
	public ResultSet getResult(){
		ResultSet rs=null;
		try{
			String query="";
			if(day.equals("All")){
				query = "select t.ID, t.SID, s.FirstName, s.LastName, s.CurLevel, t.Day, t.Time, t.Line, t.Coach from '"+term+"' t JOIN students s on s.sid = t.sid ORDER BY t.day, t.time, t.line, s.CurLevel";
				
			}
			else{
				//query = "select t.sid, s.FirstName, s.LastName, s.CurLevel, t.day, t.time, t.line, t.coach from '"+term+"' t JOIN students s on s.sid = t.sid WHERE t.day = '"+day+ 
				//	"' ORDER BY t.day, t.time, t.line, s.CurLevel";
				query = "select a.sid, s.firstName, s.lastName, level.name as CurLevel, a.day, a.time, a.line, c.name as Coach from"
						+" active_record a JOIN students s ON s.sid=a.sid"
						+" JOIN level ON a.levelID = level.id"
						+" JOIN coach c ON a.coachID = c.id"
						+" WHERE a.day = '"+day+"'";
			}
			PreparedStatement pst=conn.prepareStatement(query);
			rs = pst.executeQuery();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return rs;
	}
	public void dealDataP2(List<StudentObject> l12,List<String> c12){
		int a = timeMark.size();
		int count = 0,countSection=0;
		String levelL1="",coachL1="";
		List<String> leftList=new ArrayList<String>();
		List<String> rightList=new ArrayList<String>();
		
		
		//1
		
			//col 1
			while(count<a){
				for(StudentObject i:l12){
					if(i.getTime().equals(timeMark.get(count))){
						//get level row
						if(levelL1.isEmpty()) levelL1=i.getLevel().substring(i.getLevel().indexOf("_")+1)+"/";						
						else if(!levelL1.isEmpty()&&!levelL1.contains(i.getLevel().substring(i.getLevel().indexOf("_")+1)))levelL1=levelL1+i.getLevel().substring(i.getLevel().indexOf("_")+1);
						
						//get coach row
						if(coachL1.isEmpty())coachL1=i.getCoach();
						else if(!coachL1.isEmpty()&&!coachL1.contains(i.getCoach()))coachL1=coachL1+"/"+i.getCoach();
						
						//add students						
						if(i.getLevel().substring(i.getLevel().indexOf("_")+1).equals(levelL1.substring(0, levelL1.indexOf("/")))){
							leftList.add(i.getName()+"("+i.getLevel().substring(i.getLevel().length()-1)+")");
							
						}
						else{
							rightList.add(i.getName()+"("+i.getLevel().substring(i.getLevel().length()-1)+")");							
						}
					}
				}
				if(leftList.size()>rightList.size()){
					c12.addAll(leftList);
					for(int i=0;i<rightList.size();i++){
						c12.set(i+countSection, c12.get(i+countSection)+"/"+rightList.get(i));
					}
				}
				else{
					c12.addAll(rightList);
					for(int i=0;i<leftList.size();i++){
						c12.set(i+countSection, leftList.get(i)+"/"+c12.get(i+countSection));
					}
				}
				//check if list get enough 6 space
				for(;c12.size()<6+countSection;)c12.add(" ");
				c12.add(countSection, coachL1);
				c12.add(countSection, levelL1);
				leftList.clear();
				rightList.clear();
				levelL1="";
				coachL1="";
				countSection=c12.size();
				count++;
				
			}
			
		}
	
	
	
	public void dealData(){
		timeMark = new ArrayList<String>();
		lineMark = new ArrayList<String>();
		c1=new ArrayList<String>();
		c2=new ArrayList<String>();
		c3=new ArrayList<String>();
		c4=new ArrayList<String>();
		c5=new ArrayList<String>();
		c6=new ArrayList<String>();
		lineMark.add("Line 1");lineMark.add("Line 2");lineMark.add("Line 3");lineMark.add("Line 4");lineMark.add("Line 5");lineMark.add("Line 6");
		
		//Get LineMark and TimeMark
		for(StudentObject i:l1){
			if(!timeMark.contains(i.getTime())){
				timeMark.add(i.getTime());
			}
		}
		for(StudentObject i:l2){
			if(!timeMark.contains(i.getTime())){
				timeMark.add(i.getTime());
			}
		}
		for(StudentObject i:l3){
			if(!timeMark.contains(i.getTime())){
				timeMark.add(i.getTime());
			}
		}
		for(StudentObject i:l4){
			if(!timeMark.contains(i.getTime())){
				timeMark.add(i.getTime());
			}
		}
		for(StudentObject i:l5){
			if(!timeMark.contains(i.getTime())){
				timeMark.add(i.getTime());
			}
		}
		for(StudentObject i:l6){
			if(!timeMark.contains(i.getTime())){
				timeMark.add(i.getTime());
			}
		}
		
		Collections.sort(timeMark);
		
		
		if(l1.isEmpty()){
			lineMark.remove("Line 1");
			}
		else{
			dealDataP2(l1,c1);
			c1.add(0,"Line 1");
		}
		
		//2
		if(l2.isEmpty()){
			lineMark.remove("Line 2");
			}
		else{
			dealDataP2(l2,c2);
			c2.add(0,"Line 2");
		}
		//3
		if(l3.isEmpty()){
			lineMark.remove("Line 3");
			}
		else{
			dealDataP2(l3,c3);
			c3.add(0,"Line 3");
		}
		//4
		if(l4.isEmpty()){
			lineMark.remove("Line 4");
			}
		else{
			dealDataP2(l4,c4);
			c4.add(0,"Line 4");
		}
		//5
		if(l5.isEmpty()){
			lineMark.remove("Line 5");
			}
		else{
			dealDataP2(l5,c5);
			c5.add(0,"Line 5");
		}
		//6
		if(l6.isEmpty()){
			lineMark.remove("Line 6");
			}
		else{
			dealDataP2(l6,c6);
			c6.add(0,"Line 6");
		}
		
		//deal with level line
		
		
		//test
		
		
		
		//end test	
	}
	
	
	
	public void startWrite(){
		conn=sqliteConnection.dbConnector();
		try {
			wb = new XSSFWorkbook();
			sheet=wb.createSheet();
			
			//title row
			Row titleRow=sheet.createRow(0);
			titleRow.createCell(0, 5).setCellValue(term+"("+day+")");
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
			//title style
			titleRow.getCell(0).setCellStyle(setHeadStyle());
			
			//line row
			Row lineRow;
			//lineRow.createCell(0, 1);
			//lineRow.createCell(1).setCellValue("Line "+2);
			//lineRow.getCell(1).setCellStyle(setLineStyle());
			
			/*
			for(int i=1;i<=5;i++){
			lineRow.createCell(i).setCellValue("Swimmer "+i);
			lineRow.getCell(i).setCellStyle(setLineStyle());
			}
			*/
			
			ResultSet rs=getResult();
			
			//data 
			//create student object categorized by line no.
			l1=new ArrayList<>();
			l2=new ArrayList<>();
			l3=new ArrayList<>();
			l4=new ArrayList<>();
			l5=new ArrayList<>();
			l6=new ArrayList<>();
			
			
			try {
				while(rs.next()){
					
					String time= rs.getString("time").toString();
					String line = rs.getString("line").toString();
					String level = rs.getString("CurLevel").toString();
					String coach = rs.getString("coach").toString();
					String fname = rs.getString("FirstName").toString();
					String lname = rs.getString("LastName").toString();
					
					switch(rs.getString("line").toString()){
					case "1":
						l1.add(new StudentObject(time,line,level,coach,fname,lname));
						break;
					case "2":
						l2.add(new StudentObject(time,line,level,coach,fname,lname));
						break;
					case "3":
						l3.add(new StudentObject(time,line,level,coach,fname,lname));
						break;
					case "4":
						l4.add(new StudentObject(time,line,level,coach,fname,lname));
						break;
					case "5":
						l5.add(new StudentObject(time,line,level,coach,fname,lname));
						break;
					case "6":
						l6.add(new StudentObject(time,line,level,coach,fname,lname));
						break;
						default :
							JOptionPane.showMessageDialog(null, "No matches for : "+rs.getString("line")+" name: "+fname+" "+lname);
							break;
					}
					
				}//while rs
			
				
				//get all the columns of data
				dealData();
				
				//formating
				int totalRow = findMax(c1.size(),c2.size(),c3.size(),c4.size(),c5.size(),c6.size());
				@SuppressWarnings("unchecked")
				List<List<String>> list = findList(c1,c2,c3,c4,c5,c6);
				for(int i=1;i<=totalRow;i++){
					lineRow = sheet.createRow(i);
					for(int j=0;j<=list.size();j++){
						lineRow.createCell(j);
					}
				}
				
				
				//fill in data
				
				
				for(int col=1;col<=list.size();col++){
					for(int row=1;row<=totalRow;row++){
						
						Cell c = sheet.getRow(row).getCell(col);
							 c.setCellValue(list.get(col-1).get(row-1));
						if(row==1)
							c.setCellStyle(setLineStyle("line"));
						else if(row==2||row==3||row==10||row==11||row==18||row==19||row==26||row==27||row==34||row==35||row==42||row==43||row==50||row==51)
							c.setCellStyle(setLineStyle("head"));
						else
							c.setCellStyle(setLineStyle(""));
					}
				}
				
				//fill in time
				
				int x=0;
				CellRangeAddress range;
					for(int row=1;row<=totalRow;row+=8){
						Cell c = sheet.getRow(row).getCell(0);
						c.setCellValue(timeMark.get(x));
						c.setCellStyle(setLineStyle("line"));
						if(row==1){
							range=new CellRangeAddress(row,row+8,0,0);
							sheet.addMergedRegion(range);
							
							row++;
						}
						else {
							range=new CellRangeAddress(row,row+7,0,0);
							sheet.addMergedRegion(range);
						}
						RegionUtil.setBorderBottom(c.getCellStyle().getBorderBottom(), range, sheet, sheet.getWorkbook());
						RegionUtil.setBorderTop(c.getCellStyle().getBorderTop(), range, sheet, sheet.getWorkbook());
						RegionUtil.setBorderLeft(c.getCellStyle().getBorderLeft(), range, sheet, sheet.getWorkbook());
						RegionUtil.setBorderRight(c.getCellStyle().getBorderRight(), range, sheet, sheet.getWorkbook());
						x++;
					}
					
				//autoSize at last
				//sheet.setColumnWidth(0, 100);
				for(int i=0;i<=list.size();i++)sheet.autoSizeColumn(i, true);

				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
			
			
			
			
			
			FileOutputStream fout = new FileOutputStream(file);
			
			wb.write(fout);
			wb.close();
		    JOptionPane.showMessageDialog(null, "Finished");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
