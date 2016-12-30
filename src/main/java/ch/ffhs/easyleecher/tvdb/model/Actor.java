package ch.ffhs.easyleecher.tvdb.model;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Dies ist ein Model der TVDBAPI
 * 
 * @author thierry baumann, pascal bieri
 */
public class Actor implements Comparable<Actor>, Serializable {

	// Default serial UID
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String role;
	private String image;
	private int sortOrder = 0;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}

	public String getImage() {
		return image;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setId(String id) {
		this.id = NumberUtils.toInt(id, 0);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = NumberUtils.toInt(sortOrder, 0);
	}

	@Override
	public int compareTo(Actor other) {
		return sortOrder - other.getSortOrder();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + sortOrder;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof Actor)) {
			return false;
		}

		Actor other = (Actor) obj;

		if (id != other.id) {
			return false;
		}

		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}

		if (role == null) {
			if (other.role != null) {
				return false;
			}
		} else if (!role.equals(other.role)) {
			return false;
		}

		return true;
	}
}
