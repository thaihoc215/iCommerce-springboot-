package com.shopme.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 128, unique = true)
    private String name;

    @Column(name = "alias", nullable = false, length = 64, unique = true)
    private String alias;

    @Column(name = "image", nullable = false, length = 128)
    private String image;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToOne //default eager
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent") //default lazy
    private Set<Category> children = new HashSet<>();

    @Transient
    private boolean hasChildren;

    public Category() {

    }

    public Category(String name) {
        this.name = name;
        this.alias = name;
        this.image = "default.jpg";
    }

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.alias = name;
        this.image = "default.jpg";
    }

    public Category(Integer id, String name, String alias) {
        this.id = id;
        this.name = name;
        this.alias = alias;
    }

    public Category(String name, String alias, String image) {
        this.name = name;
        this.alias = alias;
        this.image = "default.jpg";
    }

    public Category(Integer id, String name, String alias, String image, boolean enabled) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.image = image;
        this.enabled = enabled;
    }

    public Category(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.alias = category.getAlias();
        this.image = category.getImage();
        this.enabled = category.isEnabled();
        this.hasChildren = category.getChildren().size() > 0;
    }

    public Category(Category category, String subName) {
        this.id = category.getId();
        this.name = subName;
        this.alias = category.getAlias();
        this.image = category.getImage();
        this.enabled = category.isEnabled();
        this.hasChildren = category.getChildren().size() > 0;
    }

    public Category(String name, Category parent) {
        this(name);
        this.parent = parent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

    @Transient
    public String getImagePath() {
        if (id == null) {
            return "/images/image-thumbnail.png";
        }
        return "/category-images/" + id + "/" + this.image;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
}
