
package com.rmsi.mast.studio.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmsi.mast.studio.util.JsonDateSerializer;

/**
 * The persistent class for the action database table.
 * 
 */
@Entity
@Table(name = "payment_info")
public class PaymentInfo implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	private String receipt_no;
	
	@Id
	private long usin;
	
	private long amount;
	
	private Date payment_date;
	
	private Date update_date;

	private Date letter_generation_date;
	
	private String comment;

	
	public String getReceipt_no() {
		return receipt_no;
	}

	public void setReceipt_no(String receipt_no) {
		this.receipt_no = receipt_no;
	}

	public long getUsin() {
		return usin;
	}

	public void setUsin(long usin) {
		this.usin = usin;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public Date getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(Date payment_date) {
		this.payment_date = payment_date;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getLetter_generation_date() {
		return letter_generation_date;
	}

	public void setLetter_generation_date(Date letter_generation_date) {
		this.letter_generation_date = letter_generation_date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	
}