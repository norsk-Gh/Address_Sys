package com.khankong.function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.khankong.address.Address;

public class DbAction {

	// 메인메소드는 address	가 가지고 있다
	
	
	
	// Field
	private final String url_mysql = ShareVar.url_mysql;    // ShareVar에 있는 정보 가져오기
	private final String id_mysql = ShareVar.id_mysql;
	private final String pw_mysql = ShareVar.pw_mysql;
	
	int seqno;
	String name;
	String telno;
	String address;
	String email;
	String relation;
	
	String comboSelect;
	String selectValue;
	

	
	
	
	// Constructor
	
	public DbAction(String comboSelect, String selectValue) {
		super();
		this.comboSelect = comboSelect;
		this.selectValue = selectValue;
	}


	public DbAction() {
	}
	// 3. 컨스트럭터를 쓰고서..메인 클라스에서는 SQL 관련 입력할걸 안하게 해준다! 
	// 4. 메인 클라스에서도 insertAction 메소드를 다시 만들어줬는데.. .String name = tfName.getText 변수를 설정해준 다음에 DbAction 파라미터에 바로 들어가게 한다. 
	// 5. 그리고 boolean msg = dbA ~ 변수 설정해주고 if(msg ==true) 이게 가능하다.
	
	
	public DbAction(String name, String telno, String address, String email, String relation) {
		super();
		this.name = name;
		this.telno = telno;
		this.address = address;
		this.email = email;
		this.relation = relation;

	}
	
	// 테이블 클릭했을떄 한줄만 가져오게하는 작업중
	public DbAction(int seqno) {
		super();
		this.seqno = seqno;
	}
	
	
	
	public DbAction(int seqno, String name, String telno, String address, String email, String relation) {
		super();
		this.seqno = seqno;
		this.name = name;
		this.telno = telno;
		this.address = address;
		this.email = email;
		this.relation = relation;
	}
	
	
	//##########################################
	// Method
	//##########################################
	
	// 입력
	// 2. 이거 복사해와서 퍼블릭으로 바꾸기!  메인에 있으면.. void로 써서 다 그자리에서 처리하면 되는데... 
	// 지금은 클라스가 다르기 때문에 ! Db액션에서 잘 됐는지 알려주기 위해 불린을 쓴다 -> 잘되면 true  이런거 알려주기 위해
	




	public boolean insertAction(){
        PreparedStatement ps = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
            @SuppressWarnings("unused")
			Statement stmt_mysql = conn_mysql.createStatement();    // 검색에서만 쓰긴 하는데.. 혼동되니까 그냥 적음

            String A = "insert into userinfo (name, telno, address, email, relation";
            String B = ") values (?,?,?,?,?)";

            ps = conn_mysql.prepareStatement(A+B);
            ps.setString(1, name.trim());       // Address.java 에 private으로 되어있어서 오류가 되어있음. -> 메소드 파라미터에 넣거나 컨스트럭터에넣어줌.
            ps.setString(2, telno.trim());		// 컨스트럭터 통해서 정보를 아예입력받은걸 필드 통해서 이쪽으로 공유받게 하는 방식을 사용
            ps.setString(3, address.trim());
            ps.setString(4, email.trim());
            ps.setString(5, relation.trim());
            ps.executeUpdate();

            conn_mysql.close();
            
            return true;    // -> 잘 싱행이 되었다/잘 끝났다는걸 Address 메인클라스에 알려주기 위해 이 리턴이 필요하다.
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
            
        }
	}

	
	// 전체검색(얘는 불린처럼 넘겨주는게 아니라 데이터를 넘겨줘야함! 어레이리스트 타입으로)
	
	public ArrayList<Bean> selectList(){
		
		ArrayList<Bean> beanList = new ArrayList<Bean>();
		
		String WhereDefault = "select seqno, name, telno, relation from userinfo ";

		
	        try{
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	            Statement stmt_mysql = conn_mysql.createStatement();
	
	            ResultSet rs = stmt_mysql.executeQuery(WhereDefault);
	            // 요건 한줄 가져오는건데.. 계속 다음 줄이 없을때까지 가져오는게 바로 next
	            
	            // 빈에서 차곡차곡 쌓은걸 레코드 단위로 가져오기때문에! 하나하나 풀어줘야함
	            // DB에서 정보를 가져와 하나하나씩 변수로 나눠준 행위
	            // 그리고 데이터가 몇개나 있는지 몰라서 이런거임
	            while(rs.next()){
	            	int wkSeq = rs.getInt(1);   // 여기는 DB 하고 인티져 맞춰줘야함
	            	String wkName = rs.getString(2);
	            	String wkTelno = rs.getString(3);
	            	String wkRelation = rs.getString(4);
	            // 변수를 다시 빈 형태(어레이리스트) 로 하나로 묶은 행위
	            	
	            	Bean bean = new Bean(wkSeq, wkName, wkTelno, wkRelation);
//	            	Bean bean = new Bean(wkSeq, wkName, wkTelno, wkRelation);
	            	
	            	
	            	beanList.add(bean);
	            	
	            }
	            
	            conn_mysql.close();
	        }
	        catch (Exception e){
	            e.printStackTrace();
	        }
	        return beanList;
	        
	}
	
	
	
	public Bean TableClick() {
    
		Bean bean = null;      // int i; 이거랑 같은거임
		
	  String WhereDefault = "select seqno, name, telno, address, email, relation from userinfo "; 
      String WhereDefault2 = "where seqno = " ;
      try{
          Class.forName("com.mysql.cj.jdbc.Driver");
          Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
          Statement stmt_mysql = conn_mysql.createStatement();

          ResultSet rs = stmt_mysql.executeQuery(WhereDefault + WhereDefault2 + seqno);
          															    	// 생성자에서받아온
          	// 이건 한번만 받아오는거기때문에! while할 필요가 없다
            // 데이터가 많지 않을때는 요롷게!
         
          
          if(rs.next()) {
          	int wkSeq = rs.getInt(1);
          	String wkName = rs.getString(2);
          	String wkTelno = rs.getString(3);
          	String wkAddress = rs.getString(4);
          	String wkEmail = rs.getString(5);
          	String wkRelation = rs.getString(6);
          	
          	// 이게 처음에 오류가 났었는데.. 이유가Bean컨스트럭터는!! 바로 Bean.java에 있기때문에! 거기서 만들어줘야함
          	bean = new Bean(wkSeq, wkName, wkTelno, wkAddress, wkEmail, wkRelation);
          }
          	
          conn_mysql.close();
      }
      catch (Exception e){
          e.printStackTrace();
      }
		return bean;
		
	}
	
	// ********
	
	public boolean DeleteAction() {
		
        PreparedStatement ps = null;
		
		 try{
           Class.forName("com.mysql.cj.jdbc.Driver");
           Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
           @SuppressWarnings("unused")
			Statement stmt_mysql = conn_mysql.createStatement();

           String A = "delete from userinfo where seqno = ? ";
           
           ps = conn_mysql.prepareStatement(A);
           
           ps.setInt(1,seqno);
           ps.executeUpdate();

           conn_mysql.close();
           
           return true;
       } catch (Exception e){
           e.printStackTrace();
           return false;
       }
		 
	}
	
	
	
	
		
//	public ArrayList<Bean> UpdateAction(){
//			
//			ArrayList<Bean> beanList = new ArrayList<Bean>();	
//			
//			
//			PreparedStatement ps = null;
//      
//			
//			
//			try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
//            @SuppressWarnings("unused")
//			Statement stmt_mysql = conn_mysql.createStatement();    // 검색에서만 쓰긴 하는데.. 혼동되니까 그냥 적음
//
//            String A = "update userinfo set name = ?, telno = ?, address = ?, email = ?, relation = ? ";
//            String B = " where seqno = ? ";
//
//            ps = conn_mysql.prepareStatement(A+B);
//          
//            ps.setString(1, name.trim());       // Address.java 에 private으로 되어있어서 오류가 되어있음. -> 메소드 파라미터에 넣거나 컨스트럭터에넣어줌.
//            ps.setString(2, telno.trim());		// 컨스트럭터 통해서 정보를 아예입력받은걸 필드 통해서 이쪽으로 공유받게 하는 방식을 사용
//            ps.setString(3, address.trim());
//            ps.setString(4, email.trim());
//            ps.setString(5, relation.trim());
//            ps.executeUpdate();
//
//            conn_mysql.close();
//            
//            
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//		
//	}
//      PreparedStatement ps = null;
//      try{
//          Class.forName("com.mysql.cj.jdbc.Driver");
//          Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
//          @SuppressWarnings("unused")
//			Statement stmt_mysql = conn_mysql.createStatement();
//
//          String A = "update userinfo set name = ?, telno = ?, address = ?, email = ?, relation = ? ";
//          String B = " where seqno = ? ";
//
//          ps = conn_mysql.prepareStatement(A+B);
//          
//          
//          
//          
//          ps.setString(1, addr.tfName.getText());
//          ps.setString(2, tfTelno.getText());
//          ps.setString(3, tfAddress.getText());
//          ps.setString(4, tfEmail.getText());
//          ps.setString(5, tfRelation.getText());
//          ps.setInt(6, Integer.parseInt(tfSeqno.getText()));
//          ps.executeUpdate();
//
//          conn_mysql.close();
//      } catch (Exception e){
//          e.printStackTrace();
//      }
//	
	


	
	public ArrayList<Bean> ConditionQueryAction() {
		
		ArrayList<Bean> beanList = new ArrayList<Bean>();
		
        String WhereDefault = "select seqno, name, telno, relation from userinfo where " + comboSelect;
        String WhereDefault2 = " like '%" + selectValue + "%'";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
            Statement stmt_mysql = conn_mysql.createStatement();

            ResultSet rs = stmt_mysql.executeQuery(WhereDefault + WhereDefault2);
       
            while(rs.next()){
            	int wkSeq = rs.getInt(1);   // 여기는 DB 하고 인티져 맞춰줘야함
            	String wkName = rs.getString(2);
            	String wkTelno = rs.getString(3);
            	String wkRelation = rs.getString(4);
            	
            	Bean bean = new Bean(wkSeq, wkName, wkTelno, wkRelation);
            	
             	beanList.add(bean);
            	}
            
            	conn_mysql.close();
        		}
		        catch (Exception e){
		            e.printStackTrace();
		        }
		
        return beanList;
        
	}
	
	
	
	
	
	
	
	
}
