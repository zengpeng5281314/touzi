package com.mytest.admin.po;

import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.base.annotation.BeanMeta;
import com.base.common.MBeanBase;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_first_into")
@BeanMeta
@Getter
@Setter
public class TFirstInfoPo extends MBeanBase implements Serializable {

	private static final long serialVersionUID = -5205176986938087990L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="day_time")
	private Date dayTime;
	@Column(name="regiest_num")
	private int regiestNum;
	@Column(name="regiest_num_rate")
	private double regiestNumRate;
	@Column(name="ticket_profit")
	private double ticketProfit;
	@Column(name="recharge_num")
	private int rechargeNum;
	@Column(name="recharge_num_rate")
	private double rechargeNumRate;
	@Column(name="recharge_money")
	private double rechargeMoney;
	@Column(name="recharge_money_rate")
	private double rechargeMoneyRate;
	@Column(name="close_out_num")
	private int closeOutNum;
	@Column(name="close_out_num_rate")
	private double closeOutNumRate;
	@Column(name="fee")
	private double fee;
	@Column(name="fee_rate")
	private double feeRate;
	@Column(name="scheduled_total")
	private double scheduledTotal;
	@Column(name="scheduled_total_rate")
	private double scheduledTotalRate;
	@Column(name="unsubscribe_total")
	private double unsubscribeTotal;
	@Column(name="unsubscribe_total_rate")
	private double unsubscribeTotalRate;
	@Column(name="unsubscribe_num")
	private int unsubscribeNum;
	@Column(name="unsubscribe_num_rate")
	private double unsubscribeNumRate;
	@Column(name="unsubscribe_money")
	private double unsubscribeMoney;
	@Column(name="unsubscribe_money_rate")
	private double unsubscribeMoneyRate;
	@Column(name="money_num")
	private int moneyNum;
	@Column(name="money_num_rate")
	private double moneyNumRate;
	@Column(name="createtime")
	private Timestamp createTime;
	@Column(name="updatetime")
	private Timestamp updateTime;
	@Column(name="status")
	private int status;
	
}
