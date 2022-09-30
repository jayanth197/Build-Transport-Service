package com.cintap.transport.entity.common;
import java.io.Serializable;

 

import javax.persistence.Column;

import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.Table;

 

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Getter;

import lombok.NoArgsConstructor;

import lombok.Setter;

 

@Table(name="user_form_template")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFormTemplate implements Serializable{

       private static final long serialVersionUID = -2728269680216803329L;
       
       @Id
       @Column(name="usr_form_temp_id")
       private int usrFormTempId;

       @Column(name="partner_id")
       private String partnerId;

       @Column(name="template_id")
       private String templateId;

       @Column(name="created_date")
       private String createdDate;

       @Column(name="created_by")
       private String createdBy;
}