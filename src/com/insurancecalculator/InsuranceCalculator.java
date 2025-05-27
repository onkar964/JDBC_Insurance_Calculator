package com.insurancecalculator;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Types;
import java.time.LocalDate;
import java.util.Scanner;

public class InsuranceCalculator {

	public static void main(String[] args) {
		Connection con = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your name -");
		String name = sc.nextLine();
		System.out.println("Enter your gender(male/female) -");
		String gender = sc.nextLine().toLowerCase();
		System.out.println("Enter your Date of birth (YYYY-MM-DD) -");
		LocalDate dob = LocalDate.parse(sc.nextLine());
		System.out.println("Enter loan amount -");
		double loanAount = sc.nextDouble();
		System.out.println("Enter loan tenure (2-4) -");
		int tenure = sc.nextInt();
		try {
			con = MysqlConnection.makeConnection();
			if(con!=null) {
				System.out.println("Connection Successful");
				CallableStatement cst = con.prepareCall("{CALL get_insurance_multiplier(?,?,?,?)}");
				cst.setDate(1,Date.valueOf(dob));
				cst.setInt(2, tenure);
				cst.setString(3, gender);
				cst.registerOutParameter(4, Types.DECIMAL);
				cst.execute();
				
				double result = cst.getDouble(4);
				
				if(result==0.0) {
					System.out.println("no loan amount multiplier found for this age");
				}else {
					double insurancePremium = (loanAount/1000.0)*result;
					System.out.println("Dear "+name+", your insurance premium is ₹" +insurancePremium);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			MysqlConnection.closeConnection(con);
		}
		

	}

}
