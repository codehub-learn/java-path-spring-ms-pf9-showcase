package gr.codelearn.spring.cloud.showcase.catalog.domain;

import gr.codelearn.spring.cloud.showcase.core.domain.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CATEGORIES")
@SequenceGenerator(name = "idGenerator", sequenceName = "CATEGORIES_SEQ", initialValue = 1, allocationSize = 1)
public class Category extends BaseModel {
	@NotNull
	@Column(length = 50, nullable = false)
	private String description;
}
