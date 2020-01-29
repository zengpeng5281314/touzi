package com.mytest.admin.po;

import java.io.Serializable;
import java.sql.Timestamp;

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
@Table(name = "t_regist_user_info")
@BeanMeta
@Getter
@Setter
public class TRegistUserInfoPo extends MBeanBase implements Serializable {

	private static final long serialVersionUID = 3540277290317901011L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="name")
	private String name;
	@Column(name="channel_name")
	private String channelName;
	@Column(name="phone")
	private String phone;
	@Column(name="total_money")
	private double total_money;
	@Column(name="over_money")
	private double overMoney;
	@Column(name="regist_time")
	private Timestamp registTime;
	@Column(name="login_time")
	private Timestamp loginTime;
	@Column(name="createtime")
	private Timestamp createTime;
	@Column(name="updatetime")
	private Timestamp updateTime;
	@Column(name="status")
	private int status;
	
}
