package application;

public class DefectLog {
    private String name;
    private String defectSymptom;
    private String defectResolution;
    private int lifecycleStepInjectedId;
    private int lifecycleStepRemovedId;
    private int defectCategoryId;

    // Constructors
    public DefectLog() {
    }

    public DefectLog(String name, String defectSymptom, String defectResolution, int lifecycleStepInjectedId, int lifecycleStepRemovedId, int defectCategoryId) {
        this.name = name;
        this.defectSymptom = defectSymptom;
        this.defectResolution = defectResolution;
        this.lifecycleStepInjectedId = lifecycleStepInjectedId;
        this.lifecycleStepRemovedId = lifecycleStepRemovedId;
        this.defectCategoryId = defectCategoryId;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDefectSymptom() {
        return defectSymptom;
    }

    public String getDefectResolution() {
        return defectResolution;
    }

    public int getLifecycleStepInjectedId() {
        return lifecycleStepInjectedId;
    }

    public int getLifecycleStepRemovedId() {
        return lifecycleStepRemovedId;
    }

    public int getDefectCategoryId() {
        return defectCategoryId;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDefectSymptom(String defectSymptom) {
        this.defectSymptom = defectSymptom;
    }

    public void setDefectResolution(String defectResolution) {
        this.defectResolution = defectResolution;
    }

    public void setLifecycleStepInjectedId(int lifecycleStepInjectedId) {
        this.lifecycleStepInjectedId = lifecycleStepInjectedId;
    }

    public void setLifecycleStepRemovedId(int lifecycleStepRemovedId) {
        this.lifecycleStepRemovedId = lifecycleStepRemovedId;
    }

    public void setDefectCategoryId(int defectCategoryId) {
        this.defectCategoryId = defectCategoryId;
    }
}
