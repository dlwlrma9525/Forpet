package kr.forpet.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "forpet_emergency")
public class Emergency {

    @ColumnInfo(name = "no")
    @PrimaryKey
    private Integer no;

    @ColumnInfo(name = "problem")
    private String problem;

    @ColumnInfo(name = "problem_image")
    private String problemImage;

    @ColumnInfo(name = "solution")
    private String solution;

    @ColumnInfo(name = "solution_image")
    private String solutionImage;

    @ColumnInfo(name = "pet_type")
    private String petType;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getProblemImage() {
        return problemImage;
    }

    public void setProblemImage(String problemImage) {
        this.problemImage = problemImage;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getSolutionImage() {
        return solutionImage;
    }

    public void setSolutionImage(String solutionImage) {
        this.solutionImage = solutionImage;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }
}