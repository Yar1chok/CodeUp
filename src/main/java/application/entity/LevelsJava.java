package application.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "levels_java", schema = "public", catalog = "project")
@IdClass(LevelsJavaPK.class)
public class LevelsJava {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "level", nullable = false)
    private Integer level;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "block", nullable = false)
    private Integer block;
    @Basic
    @Column(name = "chapter", nullable = false, length = -1)
    private String chapter;
    @Basic
    @Column(name = "text_level", nullable = false, length = -1)
    private String textLevel;
    @Basic
    @Column(name = "theory", nullable = false, length = -1)
    private String theory;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getBlock() {
        return block;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getTextLevel() {
        return textLevel;
    }

    public void setTextLevel(String textLevel) {
        this.textLevel = textLevel;
    }

    public String getTheory() {
        return theory;
    }

    public void setTheory(String theory) {
        this.theory = theory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LevelsJava that = (LevelsJava) o;

        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (block != null ? !block.equals(that.block) : that.block != null) return false;
        if (chapter != null ? !chapter.equals(that.chapter) : that.chapter != null) return false;
        if (textLevel != null ? !textLevel.equals(that.textLevel) : that.textLevel != null) return false;
        if (theory != null ? !theory.equals(that.theory) : that.theory != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = level != null ? level.hashCode() : 0;
        result = 31 * result + (block != null ? block.hashCode() : 0);
        result = 31 * result + (chapter != null ? chapter.hashCode() : 0);
        result = 31 * result + (textLevel != null ? textLevel.hashCode() : 0);
        result = 31 * result + (theory != null ? theory.hashCode() : 0);
        return result;
    }
}
