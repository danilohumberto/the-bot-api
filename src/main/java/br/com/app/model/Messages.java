package br.com.app.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Classe POJO
 * 
 * @author Danilo Humberto
 *
 */
@Data
@Entity
@Table(name = "MESSAGES")
public class Messages {

	@Id
	@GeneratedValue
	@Column(name = "ID", columnDefinition = "uuid", nullable = false)
	private UUID id;

	@Column(name = "CONVERSATION_ID", columnDefinition = "uuid", nullable = false)
	private UUID conversationId;

	@Column(name = "TIME_STAMP", nullable = false)
	private Date timestamp;

	@Column(name = "FROM_", columnDefinition = "uuid", nullable = false)
	private UUID from;

	@Column(name = "TO_", columnDefinition = "uuid", nullable = false)
	private UUID to;

	@Column(name = "TEXT", nullable = false)
	private String text;

}
