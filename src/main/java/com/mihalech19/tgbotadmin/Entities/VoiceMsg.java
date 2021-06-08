package com.mihalech19.tgbotadmin.Entities;
import jdk.jfr.Enabled;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.*;

@Entity
@Repository
@Table(name = "messages")
public class VoiceMsg implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4264025677185437530L;

	public VoiceMsg() {

	}

	@Id
	@Column(name = "fileuniqueid")
	public String getFileUniqueId() {
		return fileUniqueId;
	}

	public void setFileUniqueId(String fileUniqueId) {
		this.fileUniqueId = fileUniqueId;
	}

	@Column(name = "fileid")
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "sendtime")
	public Timestamp getSendtime() {
		return sendtime;
	}

	public void setSendtime(Timestamp sendtime) {
		this.sendtime = sendtime;
	}

	public VoiceMsg(String fileUniqueId, String fileId, String username, Timestamp sendtime) {
		super();
		this.fileUniqueId = fileUniqueId;
		this.fileId = fileId;
		this.username = username;
		this.sendtime = sendtime;
	}


	private String fileUniqueId;


	private String fileId;


	private String username;


    private Timestamp sendtime;



}
