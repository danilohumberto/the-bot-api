package br.com.app.model;

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
@Table(name = "BOTS")
public class Bots {

	@Id
	@GeneratedValue
	@Column(name = "ID", columnDefinition = "uuid")
	private UUID id;

	@Column(name = "NAME", nullable = false)
	private String name;

}
