package com.mytest.admin.dto;

import com.mytest.utils.Arith;

import lombok.Data;

@Data
public class HistoryDTO {

	private int moneyRechargeNum;
	private double fristMoney;
	private int fristNum;
	private double fristRechargeRate;
	private int regiestNum;
	private double useTicktRate;
	private int useTicktNum;

	public void updateRate() {
		if(regiestNum!=0){
			fristRechargeRate = Arith.mul(Arith.div(fristNum, regiestNum, 4), 100);
			useTicktRate = Arith.mul(Arith.div(useTicktNum, regiestNum, 4),100);
		}
	}

}
