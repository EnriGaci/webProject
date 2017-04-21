package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the message database table.
 * 
 */
@Entity
@Table(name="message")
@NamedQuery(name="Message.findAll", query="SELECT m FROM Message m")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="message_id")
	private Long messageId;

	@Lob
	private String message;
	
	private int hasReadMessage;

	public int getHasReadMessage() {
		return hasReadMessage;
	}

	public void setHasReadMessage(int hasReadMessage) {
		this.hasReadMessage = hasReadMessage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="from_id")
	private User user1;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="to_id")
	private User user2;

	public Message() {
	}

	public Long getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

}