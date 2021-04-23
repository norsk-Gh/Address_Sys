package com.khankong.function;

public class Bean {

	// DB하고 완전 관련된 녀석이 BEAN
	// Bean class의 갯수는 연동하는 DB Table의 갯수와 같다! 
	// Getter n Setter 가 있는 클라스를 Bean 클라스라고 한다.
	// 같은 레코드에 옆으로 쭉 담아오려고 빈을 씀
	
	/* DB에서 각 Column 별로 떼온다 
	 * > 각각 때온거 변수를 줘서 다시 하나로 합친다(빈,ArrayList)
	 * > ArrayList<Bean> 으로 메서드를 만든곳에서 리턴값으로 준다.
	 * > 
	 */
	
	/* 만약 테이블 안에 있는 데이터베이스 정보들이 int 값들이 많으면!
	 * 오히려 스트링 값을 인트로 바꿔주고 메인 메소드에서 ? 아니 어차피 스트링으로 바꾸긴 해야함 ㅋㅋ
	 * int[] qTxt = {temp, temp1~~ } 할 수는 있는데 이렇게 쓸까?
	 * 
	 * 
	 *메인 클라스에서 4개 컬럼의 정보만 가져온건 GUI 만들떄 4개만 보이게 해서 그렇다.
	 */
		
	
	/* 1. Bean class 만들어주기
	 * 
	 * ArrayList 택배아저씨
	 * Bean 박스 (여러개 있을 수도 있음)
	 * 
	 * 
	 */
	
	
	// Field
	int seqno;
	String name;
	String telno;
	String address;
	String email;
	String relation;
	
	// Constructor
	
	public Bean() {
		
	}
	
	// 화면(이너테이블)에 띄우는건 4개만 있기떄문에 이렇게 4개만 선택했음

	public Bean(int seqno, String name, String telno, String relation) {
		super();
		this.seqno = seqno;
		this.name = name;
		this.telno = telno;
		this.relation = relation;
	}
	
	
	
	
	
	public Bean(int seqno, String name, String telno, String address, String email, String relation) {
		super();
		this.seqno = seqno;
		this.name = name;
		this.telno = telno;
		this.address = address;
		this.email = email;
		this.relation = relation;
	}

	// Method
	public int getSeqno() {
		return seqno;
	}

	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	
	
	
	
	
	
}
