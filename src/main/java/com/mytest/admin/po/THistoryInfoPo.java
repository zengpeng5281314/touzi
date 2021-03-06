package com.mytest.admin.po;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.base.annotation.BeanMeta;
import com.base.common.MBeanBase;
import com.mytest.utils.Arith;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_history_info")
@BeanMeta
@Getter
@Setter
public class THistoryInfoPo extends MBeanBase implements Serializable {

	private static final long serialVersionUID = -5525099549793529665L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="day_time")
	private Date dayTime;
	@Column(name="money_recharge_num")
	private int moneyRechargeNum;
	@Column(name="frist_money")
	private double fristMoney;
	@Column(name="frist_num")
	private int fristNum;
	@Column(name="regiest_num")
	private int regiestNum;
	@Column(name="regiest_num_rate")
	private double regiestNumRate;
	@Column(name="use_tickt_num")
	private int useTicktNum;
	@Column(name="createtime")
	private Timestamp createTime;
	@Column(name="updatetime")
	private Timestamp updateTime;
	@Column(name="status")
	private int status;
	
	@Transient
	private double fristRechargeRate;
	@Transient
	private double useTicktRate;
	
	public void updateRate() {
		if(regiestNum!=0){
			fristRechargeRate = Arith.mul(Arith.div(fristNum, regiestNum, 4), 100);
			useTicktRate = Arith.mul(Arith.div(useTicktNum, regiestNum, 4),100);
		}
	}
	
}
